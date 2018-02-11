package com.squidsquads.model.site;

import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

import javax.persistence.*;

/**
 * @author: Dulce Cayetano
 * @Date_Of_Creation: 2018-02-10
 * @Last_modified_by:
 * @Date_of_last_modification:
 **/
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

    public Site  (){}

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
