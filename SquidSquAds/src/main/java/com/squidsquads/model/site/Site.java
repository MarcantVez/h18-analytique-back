package com.squidsquads.model.site;

import org.springframework.stereotype.Component;

import javax.persistence.*;

@Component
@Entity
@Table(name = "site")
public class Site {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "numero_site")
    private long siteID;

    @Column(name = "numero_profildutilisateur")
    private long userProfileID;

    @Column(name = "url")
    private String url;

    public Site() {
    }

    public Site(long userProfileID, String url) {
        this.userProfileID = userProfileID;
        this.url = url;
    }

    public long getSiteID() {
        return siteID;
    }

    public void setSiteID(long siteID) {
        this.siteID = siteID;
    }

    public long getUserProfileID() {
        return userProfileID;
    }

    public void setUserProfileID(long userProfileID) {
        this.userProfileID = userProfileID;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
