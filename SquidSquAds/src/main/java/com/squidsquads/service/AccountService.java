package com.squidsquads.service;

import com.squidsquads.form.account.request.CreateRequest;
import com.squidsquads.form.account.request.LoginRequest;
import com.squidsquads.form.account.request.ResetPasswordRequest;
import com.squidsquads.form.account.response.*;
import com.squidsquads.form.validator.AccountValidator;
import com.squidsquads.model.Account;
import com.squidsquads.model.AdminType;
import com.squidsquads.model.Orientation;
import com.squidsquads.model.WebSiteAdmin;
import com.squidsquads.repository.AccountRepository;
import com.squidsquads.utils.session.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BannerService bannerService;

    @Autowired
    private WebSiteAdminService webSiteAdminService;

    private BCryptPasswordEncoder encoder;

    public AccountService() {
        encoder = new BCryptPasswordEncoder();
    }

    /**
     * Trouver un compte utilisateur en fonction de son email
     */
    private Account findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    /**
     * Trouver un compte utilisateur en fonction de son ID
     */
    public Account findByAccountID(Integer accountID) {
        return accountRepository.findByAccountID(accountID);
    }

    /**
     * Tenter de connecter l'utilisateur au système et de lui attribuer un token de session
     */
    public AbstractLoginResponse login(LoginRequest loginRequest) {

        // Si un des champs est vide, ne pas appeler la BD pour rien
        if (loginRequest.getEmail() == null || loginRequest.getPassword() == null) {
            return new LoginFailedResponse();
        }

        Account account = findByEmail(loginRequest.getEmail());

        // Si le compte n'existe pas
        if (account == null) {
            return new LoginFailedResponse();
        }

        // Si le mot de passe n'est pas le bon
        if (!encoder.matches(loginRequest.getPassword(), account.getPassword())) {
            return new LoginFailedResponse();
        }

        // Créer un nouveau token de session pour l'utilisateur
        String token = SessionManager.getInstance().addSession(account);

        return new LoginSucceededResponse(
                account.getAccountID(),
                AdminType.PUB == account.getAdminTypeValue(),
                AdminType.WEB == account.getAdminTypeValue(),
                account.getEmail(),
                token
        );
    }

    /**
     * Créer un compte administrateur de site web ou de publicité
     */
    @Transactional
    public CreateResponse create(CreateRequest createRequest) {

        // Si le type d'admin n'est pas PUB ou WEB
        if (!AdminType.PUB.name().equals(createRequest.getAdminType()) &&
                !AdminType.WEB.name().equals(createRequest.getAdminType())) {
            return new CreateResponse().invalidAdminType();
        }

        // Si des champs sont nuls ou vides
        if (!AccountValidator.isCreateRequestComplete(createRequest)) {
            return new CreateResponse().fieldsMissing();
        }

        // Si le numéro de compte de banque ne suit pas la forme BBB-CCCCC
        if (!AccountValidator.isBankAccountValid(createRequest.getBank())) {
            return new CreateResponse().invalidBankAccount();
        }

        // Si les mots de passes ne correspondent pas
        if (!createRequest.getPassword().equals(createRequest.getConfirmPassword())) {
            return new CreateResponse().wrongPasswords();
        }

        // Si le courriel est déjà utilisé par un autre utilisateur
        if (findByEmail(createRequest.getEmail()) != null) {
            return new CreateResponse().emailAlreadyUsed();
        }

        // Sinon on crée le compte utilisateur
        Account account = accountRepository.save(new Account(
                createRequest.getAdminType(),
                createRequest.getEmail(),
                encoder.encode(createRequest.getPassword()), //encryption du mot de passe
                createRequest.getBank()
        ));

        // Entités supplémentaires pour les admins web
        if (AdminType.WEB.name().equals(createRequest.getAdminType())) {
            webSiteAdminService.create(account.getAccountID(), createRequest.getDomain());
            bannerService.create(account.getAccountID(), Orientation.HOR.name());
            bannerService.create(account.getAccountID(), Orientation.VER.name());
            bannerService.create(account.getAccountID(), Orientation.MOB.name());
        }

        return new CreateResponse().ok();
    }

    /**
     * Obtenir de l'information par rapport à un compte (sauf le mot de passe)
     */
    public InfoResponse getInfo(String token) {

        Integer accountId = SessionManager.getInstance().getAccountIdForToken(token);
        Account account = findByAccountID(accountId);

        // Si le compte n'existe pas ici, c'est un probleme serveur
        if (account == null) {
            logger.error("Un compte basé sur un token de session est introuvable");
            return new InfoResponse().failed();
        }

        String domain = "";

        // Entité supplémentaire pour les admins web
        if (AdminType.WEB == account.getAdminTypeValue()) {
            WebSiteAdmin webSiteAdmin = webSiteAdminService.findByAccountID(accountId);
            domain = webSiteAdmin.getUrl();
        }

        return new InfoResponse().ok(account.getEmail(), domain, account.getBankAccount());
    }

    /**
     * Rénitialiser le mot de passe de l'utilisateur
     */
    public ResetPasswordResponse resetPassword(String token, ResetPasswordRequest rpr) {

        Integer accountId = SessionManager.getInstance().getAccountIdForToken(token);
        Account account = findByAccountID(accountId);

        // Si le compte n'existe pas ici, c'est un probleme serveur
        if (account == null) {
            logger.error("Un compte basé sur un token de session est introuvable");
            return new ResetPasswordResponse().failed();
        }

        Boolean passwordMatch = encoder.matches(rpr.getOldPassword(), account.getPassword());

        // Si l'ancien mot de passe n'est pas le bon
        if (!passwordMatch) {
            return new ResetPasswordResponse().wrongOldPassword();
        }

        // Si les nouveaux mots de passe ne correspondent pas
        if (!rpr.getNewPassword().equals(rpr.getConfirmNewPassword())) {
            return new ResetPasswordResponse().wrongNewPasswords();
        }

        account.setPassword(encoder.encode(rpr.getNewPassword()));
        accountRepository.save(account);

        return new ResetPasswordResponse().ok();
    }
}
