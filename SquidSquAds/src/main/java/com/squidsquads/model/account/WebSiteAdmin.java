package com.squidsquads.model.account;

import org.springframework.stereotype.Component;

import javax.persistence.*;

@Component
@Entity
@Table(name = "sitewebadmin")
public class WebSiteAdmin {

    private static final long serialVersionUID = -3009157732242241606L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "numero_sitewebadmin")
    private long webSiteAdminID;

    @Column(name = "numero_compte")
    private long accountID;

    @Column(name = "url")
    private String url;

    public WebSiteAdmin() {
    }

    public WebSiteAdmin(long accountID, String url) {
        this.accountID = accountID;
        this.url = url;
    }

    public long getWebSiteAdminID() {
        return webSiteAdminID;
    }

    public void setWebSiteAdminID(long webSiteAdminID) {
        this.webSiteAdminID = webSiteAdminID;
    }

    public long getAccountID() {
        return accountID;
    }

    public void setAccountID(long accountID) {
        this.accountID = accountID;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
