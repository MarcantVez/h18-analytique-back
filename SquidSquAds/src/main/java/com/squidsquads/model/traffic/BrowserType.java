package com.squidsquads.model.traffic;

import javax.persistence.*;

@Entity
@Table(name = "typenavigateur")
public class BrowserType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "numero_typenavigateur")
    private Long browserTypeId;

    @Column(name = "nom")
    private String name;

    public BrowserType() {
    }

    public BrowserType(String name) {
        this.name = name;
    }

    public Long getBrowserTypeId() {
        return browserTypeId;
    }

    public void setBrowserTypeId(Long browserTypeId) {
        this.browserTypeId = browserTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Browser Type : " + this.name;
    }
}
