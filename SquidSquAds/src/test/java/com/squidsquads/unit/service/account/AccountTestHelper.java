package com.squidsquads.unit.service.account;

import com.squidsquads.form.account.request.CreateRequest;
import com.squidsquads.form.account.request.LoginRequest;
import com.squidsquads.form.account.request.ResetPasswordRequest;
import com.squidsquads.model.AdminType;
import com.squidsquads.unit.service.ServiceTestHelper;

public class AccountTestHelper extends ServiceTestHelper {

    /////////////////////
    // Account : Login //
    /////////////////////

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

    //////////////////////
    // Account : Create //
    //////////////////////

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

    //////////////////////////////
    // Account : Reset Password //
    //////////////////////////////

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
