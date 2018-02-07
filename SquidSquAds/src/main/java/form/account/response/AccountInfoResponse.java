package form.account.response;

import form.account.Form;

public class AccountInfoResponse extends Form {
    private Long accountID;
    private boolean isAdminPub;
    private boolean isAdminWeb;
    private String token;
    private String email;
    private String bank;
    private String url;

    public AccountInfoResponse(int status, Long accountID, boolean isAdminPub, boolean isAdminWeb, String token, String email, String bank, String url) {
        super(status);
        this.accountID = accountID;
        this.isAdminPub = isAdminPub;
        this.isAdminWeb = isAdminWeb;
        this.token = token;
        this.email = email;
        this.bank = bank;
        this.url = url;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
