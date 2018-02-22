package com.squidsquads.model.traffic;

import javax.persistence.*;

@Entity
@Table(name = "typenavigateur_agent")
public class BrowserTypeAgent {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "numero_typenavigateur_agent")
    private Long browserTypeAgentId;

    @Column(name="numero_typenavigateur")
    private Long browserTypeId;

    @Column(name="numero_agentutilisateur")
    private Long userAgentId;

    public BrowserTypeAgent() {
    }

    public BrowserTypeAgent(Long browserTypeId, Long userAgentId) {
        this.browserTypeId = browserTypeId;
        this.userAgentId = userAgentId;
    }

    public Long getBrowserTypeAgentId() {
        return browserTypeAgentId;
    }

    public void setBrowserTypeAgentId(Long browserTypeAgentId) {
        this.browserTypeAgentId = browserTypeAgentId;
    }

    public Long getBrowserTypeId() {
        return browserTypeId;
    }

    public void setBrowserTypeId(Long browserTypeId) {
        this.browserTypeId = browserTypeId;
    }

    public Long getUserAgentId() {
        return userAgentId;
    }

    public void setUserAgentId(Long userAgentId) {
        this.userAgentId = userAgentId;
    }
}
