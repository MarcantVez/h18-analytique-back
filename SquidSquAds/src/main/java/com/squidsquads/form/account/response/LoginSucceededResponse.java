package com.squidsquads.form.account.response;

import org.springframework.http.HttpStatus;

public class LoginSucceededResponse extends AbstractLoginResponse {

    private final long accountId;
    private final boolean isAdminPub;
    private final boolean isAdminWeb;
    private final String displayName;
    private final String token;

    public LoginSucceededResponse(long accountId, boolean isAdminPub, boolean isAdminWeb, String displayName, String token) {
        super(true, HttpStatus.OK);
        this.accountId = accountId;
        this.isAdminPub = isAdminPub;
        this.isAdminWeb = isAdminWeb;
        this.displayName = displayName;
        this.token = token;
    }

    public long getAccountId() {
        return accountId;
    }

    public boolean isAdminPub() {
        return isAdminPub;
    }

    public boolean isAdminWeb() {
        return isAdminWeb;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getToken() {
        return token;
    }
}
