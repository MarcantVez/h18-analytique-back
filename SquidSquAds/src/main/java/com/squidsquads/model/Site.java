package com.squidsquads.model;

import javax.persistence.*;

@Entity
@Table(name = "site")
public class Site {

    @Id
    @SequenceGenerator(name = "site_id_site_seq", sequenceName = "site_id_site_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "site_id_site_seq")
    @Column(name = "id_site")
    private Integer siteID;

    @Column(name = "id_profilutilisateur")
    private Integer userProfileID;

    @Column(name = "url")
    private String url;

    public Site() {
    }

    public Site(Integer userProfileID, String url) {
        this.userProfileID = userProfileID;
        this.url = url;
    }

    public Integer getSiteID() {
        return siteID;
    }

    public void setSiteID(Integer siteID) {
        this.siteID = siteID;
    }

    public Integer getUserProfileID() {
        return userProfileID;
    }

    public void setUserProfileID(Integer userProfileID) {
        this.userProfileID = userProfileID;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
