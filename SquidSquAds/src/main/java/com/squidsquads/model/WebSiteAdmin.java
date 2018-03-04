package com.squidsquads.model;

import javax.persistence.*;

@Entity
@Table(name = "sitewebadmin")
public class WebSiteAdmin {

    @Id
    @SequenceGenerator(name = "sitewebadmin_id_sitewebadmin_seq", sequenceName = "sitewebadmin_id_sitewebadmin_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sitewebadmin_id_sitewebadmin_seq")
    @Column(name = "id_sitewebadmin")
    private Integer webSiteAdminID;

    @Column(name = "id_compte")
    private Integer accountID;

    @Column(name = "url")
    private String url;

    public WebSiteAdmin() {
    }

    public WebSiteAdmin(Integer accountID, String url) {
        this.accountID = accountID;
        this.url = url;
    }

    public Integer getWebSiteAdminID() {
        return webSiteAdminID;
    }

    public void setWebSiteAdminID(Integer webSiteAdminID) {
        this.webSiteAdminID = webSiteAdminID;
    }

    public Integer getAccountID() {
        return accountID;
    }

    public void setAccountID(Integer accountID) {
        this.accountID = accountID;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
