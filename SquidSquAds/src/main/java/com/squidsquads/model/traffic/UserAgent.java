package com.squidsquads.model.traffic;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "agentutilisateur")
public class UserAgent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_agentutilisateur")
    private Long id;

    @Column(name = "agentutilisateurbrut")
    private String userAgentString;

    @Column(name = "versionnavigateur")
    private String browserVersion;

    @Column(name = "navigateur")
    private String browser;

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

    public UserAgent(String userAgentString, String browserVersion, String browser, String operatingSystem, String browserInfo, String platform, String platformInfo, String browserPlugins) {
        this.userAgentString = userAgentString;
        this.browserVersion = browserVersion;
        this.browser = browser;
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

    public String getUserAgentString() {
        return userAgentString;
    }

    public String getBrowserVersion() {
        return browserVersion;
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

    public String getBrowser() {
        return browser;
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
