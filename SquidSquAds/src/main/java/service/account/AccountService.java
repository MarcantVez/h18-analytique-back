package service.account;

import form.account.request.CreateRequest;
import form.account.request.LoginRequest;
import form.account.request.ResetPasswordRequest;
import form.account.response.*;
import model.account.Account;
import model.account.AdminType;
import model.account.WebSiteAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import repository.account.AccountRepository;
import utils.session.SessionManager;

@Service
public class AccountService {

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
    private Account findByAccountID(long accountID) {
        return accountRepository.findByAccountID(accountID);
    }

    /**
     * Tenter de connecter l'utilisateur au système et de lui attribuer un token de session
     */
    public AbstractLoginResponse login(LoginRequest loginRequest) {

        Account account = findByEmail(loginRequest.getEmail());

        if (account == null || !account.getPassword().equals(loginRequest.getPassword())) {
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
    public CreateResponse create(CreateRequest createRequest) {

        if (findByEmail(createRequest.getEmail()) != null) {
            return new CreateResponse().emailAlreadyUsed();
        }

        if (!AdminType.PUB.name().equals(createRequest.getAdminType()) &&
                !AdminType.WEB.name().equals(createRequest.getAdminType())) {
            return new CreateResponse().invalidAdminType();
        }

        Account account = accountRepository.save(new Account(
                createRequest.getAdminType(),
                createRequest.getEmail(),
                createRequest.getPassword(),
                createRequest.getBank()
        ));

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

        if (account == null) {
            return new InfoResponse(HttpStatus.INTERNAL_SERVER_ERROR, "", "", "");
        }

        String domain = "";

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

        if (account == null) {
            return new ResetPasswordResponse().failed();
        }

        if (!rpr.getOldPassword().equals(account.getPassword())) {
            return new ResetPasswordResponse().wrongOldPassword();
        }

        if (!rpr.getNewPassword().equals(rpr.getConfirmNewPassword())) {
            return new ResetPasswordResponse().wrongNewPasswords();
        }

        account.setPassword(rpr.getNewPassword());
        accountRepository.save(account);

        return new ResetPasswordResponse().ok();
    }
}
