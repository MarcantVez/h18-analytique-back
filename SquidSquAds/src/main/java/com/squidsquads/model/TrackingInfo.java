package com.squidsquads.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "infodesuivi")
public class TrackingInfo {

    @Id
    @SequenceGenerator(name = "infodesuivi_id_infodesuivi_seq", sequenceName = "infodesuivi_id_infodesuivi_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "infodesuivi_id_infodesuivi_seq")
    @Column(name = "id_infodesuivi")
    private Integer TrackingInfoId;

    @Column(name = "id_sitewebadmin")
    private Integer adminWebSiteId;

    @Column(name = "id_agentutilisateur")
    private Integer userAgentId;

    @Column(name = "empreinte")
    private String fingerprint;

    @Column(name = "urlactuel")
    private String currentUrl;

    @Column(name = "urlprovenance")
    private String previousUrl;

    @Column(name = "adresse_ipv4")
    private String ipv4Address;

    @Column(name = "adresse_ipv6")
    private String ipv6Address;

    @Column(name = "taille_ecran")
    private String screenSize;

    @Column(name = "langue")
    private String language;

    @Column(name = "tempsecoule")
    private int timeSpent;

    @Column(name = "date_heure")
    private Timestamp dateTime;

    public TrackingInfo() {
    }

    public TrackingInfo(Integer adminWebSiteId, Integer userAgentId, String fingerprint, String currentUrl, String previousUrl, String ipv4Address, String ipv6Address, String screenSize, String language, int timeSpent) {
        this.adminWebSiteId = adminWebSiteId;
        this.userAgentId = userAgentId;
        this.fingerprint = fingerprint;
        this.currentUrl = currentUrl;
        this.previousUrl = previousUrl;
        this.ipv4Address = ipv4Address;
        this.ipv6Address = ipv6Address;
        this.screenSize = screenSize;
        this.language = language;
        this.timeSpent = timeSpent;
        this.dateTime = Timestamp.from(Instant.now());
    }

    public Integer getTrackingInfoId() {
        return TrackingInfoId;
    }

    public Integer getAdminWebSiteId() {
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

    public Integer getUserAgentId() {
        return userAgentId;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }
}
