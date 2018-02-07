package form.account.response;

import form.Form;

public class AuthenticateResponse extends Form {
    private boolean isAuthenticated;
    private Long accountID;
    private boolean isAdminPub;
    private boolean isAdminWeb;
    private String token;


    public AuthenticateResponse(int status, boolean isAuthenticated, Long accountID, boolean isAdminPub, boolean isAdminWeb, String token) {
        super(status);
        this.isAuthenticated = isAuthenticated;
        this.accountID = accountID;
        this.isAdminPub = isAdminPub;
        this.isAdminWeb = isAdminWeb;
        this.token = token;
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        isAuthenticated = authenticated;
    }

    public Long getAccountID() {
        return accountID;
    }

    public void setAccountID(Long accountID) {
        this.accountID = accountID;
    }

    public boolean isAdminPub() {
        return isAdminPub;
    }

    public void setAdminPub(boolean adminPub) {
        isAdminPub = adminPub;
    }

    public boolean isAdminWeb() {
        return isAdminWeb;
    }

    public void setAdminWeb(boolean adminWeb) {
        isAdminWeb = adminWeb;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
