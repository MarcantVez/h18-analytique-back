package com.squidsquads.service.account;

import com.squidsquads.form.account.request.CreateRequest;
import com.squidsquads.form.account.request.LoginRequest;
import com.squidsquads.form.account.request.ResetPasswordRequest;
import com.squidsquads.form.account.response.*;
import com.squidsquads.form.validator.AccountValidator;
import com.squidsquads.model.account.Account;
import com.squidsquads.model.account.AdminType;
import com.squidsquads.model.account.WebSiteAdmin;
import com.squidsquads.repository.account.AccountRepository;
import com.squidsquads.utils.session.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    public AccountRepository accountRepository;

    @Autowired
    public WebSiteAdminService webSiteAdminService;

    /**
     * Trouver un compte utilisateur en fonction de son email
     */
    private Account findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    /**
     * Trouver un compte utilisateur en fonction de son ID
     */
    public Account findByAccountID(long accountID) {
        return accountRepository.findByAccountID(accountID);
    }

    /**
     * Tenter de connecter l'utilisateur au système et de lui attribuer un token de session
     */
    public AbstractLoginResponse login(LoginRequest loginRequest) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        Account account = findByEmail(loginRequest.getEmail());

        // Si le compte n'existe pas
        if (account == null) {
            return new LoginFailedResponse();
        }

        //To generate a encoded password http://www.devglan.com/online-tools/bcrypt-hash-generator
        Boolean passwordMatch = encoder.matches(loginRequest.getPassword(), account.getPassword());

        // Si le mot de passe n'est pas le bon
        if (!passwordMatch) {
            return new LoginFailedResponse();
        }

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

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        // Sinon on crée le compte utilisateur
        Account account = accountRepository.save(new Account(
                createRequest.getAdminType(),
                createRequest.getEmail(),
                encoder.encode(createRequest.getPassword()), //encryption du mot de passe
                createRequest.getBank()
        ));

        // Entité supplémentaire pour les admins web
        if (AdminType.WEB == AdminType.valueOf(createRequest.getAdminType())) {
            webSiteAdminService.create(account.getAccountID(), createRequest.getDomain());
        }

        return new CreateResponse().ok();
    }

    /**
     * Obtenir de l'information par rapport à un compte (sauf le mot de passe)
     */
    public InfoResponse getInfo(String token) {

        long accountId = SessionManager.getInstance().getAccountIdForToken(token);
        Account account = findByAccountID(accountId);

        // Si le compte n'existe pas ici, c'est un probleme serveur
        if (account == null) {
            logger.error("Un compte basé sur un token de session est introuvable");
            return new InfoResponse(HttpStatus.INTERNAL_SERVER_ERROR, "", "", "");
        }

        String domain = "";

        // Entité supplémentaire pour les admins web
        if (AdminType.WEB == account.getAdminTypeValue()) {
            WebSiteAdmin webSiteAdmin = webSiteAdminService.findByAccountID(accountId);
            domain = webSiteAdmin.getUrl();
        }

        return new InfoResponse(HttpStatus.OK, account.getEmail(), domain, account.getBankAccount());
    }

    /**
     * Rénitialiser le mot de passe de l'utilisateur
     */
    public ResetPasswordResponse resetPassword(String token, ResetPasswordRequest rpr) {

        long accountId = SessionManager.getInstance().getAccountIdForToken(token);
        Account account = findByAccountID(accountId);

        // Si le compte n'existe pas ici, c'est un probleme serveur
        if (account == null) {
            logger.error("Un compte basé sur un token de session est introuvable");
            return new ResetPasswordResponse().failed();
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        Boolean passwordMatch = encoder.matches(rpr.getOldPassword(),account.getPassword());

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
