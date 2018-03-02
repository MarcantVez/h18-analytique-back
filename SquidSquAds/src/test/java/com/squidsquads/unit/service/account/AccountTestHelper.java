package com.squidsquads.unit.service.account;

import com.squidsquads.form.account.request.CreateRequest;
import com.squidsquads.form.account.request.LoginRequest;
import com.squidsquads.form.account.request.ResetPasswordRequest;
import com.squidsquads.model.Account;
import com.squidsquads.model.AdminType;
import com.squidsquads.model.WebSiteAdmin;

public class AccountTestHelper {

    private final int ACCOUNT_ID = 1;

    private final String EMAIL_PUB = "ads@ads.com";
    private final String EMAIL_WEB = "web@web.com";

    private final String CLEAR_PASSWORD = "Password1!";
    private final String HASHED_PASSWORD = "$2a$10$qyyJ6RAOdVdLzrF1EFsgkuE5/2P9IkK/G4mZoGoDyWrV7bm8pz.lu";

    private final String BANK_ACCOUNT = "000-12345";
    private final String DOMAIN = "https://www.google.ca";

    /////////////
    // Account //
    /////////////

    public Account getAccountPub() {
        Account pub = new Account(AdminType.PUB.name(), EMAIL_PUB, HASHED_PASSWORD, BANK_ACCOUNT);
        pub.setAccountID(ACCOUNT_ID);
        return pub;
    }

    public Account getAccountWeb() {
        Account web = new Account(AdminType.WEB.name(), EMAIL_WEB, HASHED_PASSWORD, BANK_ACCOUNT);
        web.setAccountID(ACCOUNT_ID);
        return web;
    }

    ///////////
    // Login //
    ///////////

    public LoginRequest getLoginRequest() {
        return buildLoginRequest(EMAIL_PUB, CLEAR_PASSWORD);
    }

    public LoginRequest getLoginRequestWhereEmailIsNull() {
        return buildLoginRequest(null, CLEAR_PASSWORD);
    }

    public LoginRequest getLoginRequestWherePasswordIsNull() {
        return buildLoginRequest(EMAIL_PUB, null);
    }

    public LoginRequest getLoginRequestWherePasswordIsWrong() {
        return buildLoginRequest(EMAIL_PUB, "wrong password");
    }

    private LoginRequest buildLoginRequest(String email, String password) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(email);
        loginRequest.setPassword(password);
        return loginRequest;
    }

    ////////////
    // Create //
    ////////////

    public CreateRequest getCreateRequestForWeb() {
        return buildCreateRequest(AdminType.WEB.name(), EMAIL_WEB, DOMAIN, BANK_ACCOUNT, CLEAR_PASSWORD, CLEAR_PASSWORD);
    }

    public CreateRequest getCreateRequestForPub() {
        return buildCreateRequest(AdminType.PUB.name(), EMAIL_PUB, "", BANK_ACCOUNT, CLEAR_PASSWORD, CLEAR_PASSWORD);
    }

    public CreateRequest getCreateRequestWhereAdminTypeIsNotValid() {
        return buildCreateRequest("not valid", EMAIL_PUB, DOMAIN, BANK_ACCOUNT, CLEAR_PASSWORD, CLEAR_PASSWORD);
    }

    public CreateRequest getCreateRequestWhereOneParameterIsNullOrEmpty() {
        return buildCreateRequest(AdminType.PUB.name(), EMAIL_PUB, DOMAIN, null, CLEAR_PASSWORD, " ");
    }

    public CreateRequest getCreateRequestWhereAdminTypeIsWebAndDomainIsEmpty() {
        return buildCreateRequest(AdminType.WEB.name(), EMAIL_WEB, " ", BANK_ACCOUNT, CLEAR_PASSWORD, CLEAR_PASSWORD);
    }

    public CreateRequest getCreateRequestWhereBankFormatIsWrong() {
        return buildCreateRequest(AdminType.WEB.name(), EMAIL_WEB, DOMAIN, "not valid", CLEAR_PASSWORD, CLEAR_PASSWORD);
    }

    public CreateRequest getCreateRequestWherePasswordsDoNotMatch() {
        return buildCreateRequest(AdminType.WEB.name(), EMAIL_WEB, DOMAIN, BANK_ACCOUNT, CLEAR_PASSWORD, "not the same");
    }

    private CreateRequest buildCreateRequest(String adminType, String email, String domain, String bank,
                                             String password, String confirmPassword) {
        CreateRequest createRequest = new CreateRequest();
        createRequest.setAdminType(adminType);
        createRequest.setEmail(email);
        createRequest.setDomain(domain);
        createRequest.setBank(bank);
        createRequest.setPassword(password);
        createRequest.setConfirmPassword(confirmPassword);
        return createRequest;
    }

    public WebSiteAdmin getWebSiteAdmin() {
        return new WebSiteAdmin(ACCOUNT_ID, DOMAIN);
    }

    public ResetPasswordRequest getResetPasswordRequest() {
        return buildResetPasswordRequest(CLEAR_PASSWORD, "new", "new");
    }

    public ResetPasswordRequest getResetPasswordRequestWhereOldPasswordIsWrong() {
        return buildResetPasswordRequest("wrong old password", "new", "new");
    }

    public ResetPasswordRequest getResetPasswordRequestWhereNewPasswordsAreNotTheSame() {
        return buildResetPasswordRequest(CLEAR_PASSWORD, "new", "not so new");
    }

    private ResetPasswordRequest buildResetPasswordRequest(String old, String newPassword, String confirmNew) {
        ResetPasswordRequest req = new ResetPasswordRequest();
        req.setOldPassword(old);
        req.setNewPassword(newPassword);
        req.setConfirmNewPassword(confirmNew);
        return req;
    }
}
