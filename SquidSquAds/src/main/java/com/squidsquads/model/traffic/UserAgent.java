package com.squidsquads.model.traffic;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "agentutilisateur")
public class UserAgent {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_agentutilisateur")
    private Long id;

    @Column(name = "id_infodesuivi")
    private Long trackingInfoId;

    @Column(name = "agentutilisateurbrut")
    private String userAgentString;

    @Column(name = "versionnavigateur")
    private String browserVersion;

    @Column(name = "navigateur")
    private String navigateur;

    @Column(name = "systeme_operation")
    private String operatingSystem;

    @Column(name = "information_navigateur")
    private String browserInfo;

    @Column(name = "plateforme")
    private String platform;

    @Column(name = "information_plateforme")
    private String platformInfo;

    @Column(name = "extension_navigateur")
    private String browserPlugins;

    @Column(name = "date_heure")
    private Timestamp dateTime;

    public UserAgent() {
    }

    public UserAgent(Long trackingInfoId, String userAgentString, String browserVersion, String navigateur, String operatingSystem, String browserInfo, String platform, String platformInfo, String browserPlugins) {
        this.trackingInfoId = trackingInfoId;
        this.userAgentString = userAgentString;
        this.browserVersion = browserVersion;
        this.navigateur = navigateur;
        this.operatingSystem = operatingSystem;
        this.browserInfo = browserInfo;
        this.platform = platform;
        this.platformInfo = platformInfo;
        this.browserPlugins = browserPlugins;
        Instant now = Instant.now();
        this.dateTime = Timestamp.from(now);
    }

    public Long getId() {
        return id;
    }

    public Long getTrackingInfoId() {
        return trackingInfoId;
    }

    public String getUserAgentString() {
        return userAgentString;
    }

    public String getBrowserVersion() {
        return browserVersion;
    }

    public String getNavigateur() {
        return navigateur;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public String getBrowserInfo() {
        return browserInfo;
    }

    public String getPlatform() {
        return platform;
    }

    public String getPlatformInfo() {
        return platformInfo;
    }

    public String getBrowserPlugins() {
        return browserPlugins;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    @Override
    public String toString() {
        return this.userAgentString;
    }

    @Override
    public boolean equals(Object obj) {
        return this.userAgentString.equals(((UserAgent) obj).getUserAgentString());
    }
}
