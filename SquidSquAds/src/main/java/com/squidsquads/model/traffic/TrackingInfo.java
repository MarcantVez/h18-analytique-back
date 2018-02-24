package com.squidsquads.model.traffic;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalTime;

@Entity
@Table(name = "infodesuivi")
public class TrackingInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name= "id_infodesuivi")
    private Long TrackingInfoId;

    @Column(name= "id_sitewebadmin")
    private Long adminWebSiteId;

    @Column(name= "empreinte")
    private String fingerprint;

    @Column(name= "urlactuel")
    private String currentUrl;

    @Column(name= "urlprovenance")
    private String previousUrl;

    @Column(name= "adresse_ipv4")
    private String ipv4Address;

    @Column(name= "adresse_ipv6")
    private String ipv6Address;

    @Column(name= "taille_ecran")
    private String screenSize;

    @Column(name= "langue")
    private String language;

    @Column(name= "tempsecoule")
    private int timeSpent;

    @Column(name= "date_heure")
    private Timestamp dateTime;

    public TrackingInfo() {
    }

    public TrackingInfo(Long adminWebSiteId, String fingerprint, String currentUrl, String previousUrl, String ipv4Address, String ipv6Address, String screenSize, String language, int timeSpent) {
        this.adminWebSiteId = adminWebSiteId;
        this.fingerprint = fingerprint;
        this.currentUrl = currentUrl;
        this.previousUrl = previousUrl;
        this.ipv4Address = ipv4Address;
        this.ipv6Address = ipv6Address;
        this.screenSize = screenSize;
        this.language = language;
        this.timeSpent = timeSpent;
        Instant now = Instant.now();
        this.dateTime = Timestamp.from(now);
    }

    public Long getTrackingInfoId() {
        return TrackingInfoId;
    }

    public Long getAdminWebSiteId() {
        return adminWebSiteId;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public String getCurrentUrl() {
        return currentUrl;
    }

    public String getPreviousUrl() {
        return previousUrl;
    }

    public String getIpv4Address() {
        return ipv4Address;
    }

    public String getIpv6Address() {
        return ipv6Address;
    }

    public String getScreenSize() {
        return screenSize;
    }

    public String getLanguage() {
        return language;
    }

    public int getTimeSpent() {
        return timeSpent;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }
}
