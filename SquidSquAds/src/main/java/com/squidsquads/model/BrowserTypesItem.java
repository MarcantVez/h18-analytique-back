package com.squidsquads.model;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;

@Entity
@Table(name = "browsertypes_view")
@Immutable
public class BrowserTypesItem {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "navigateur")
    private String name;

    @Column(name = "ratio")
    private int ratio;

    @Column(name = "websiteid")
    private Integer websiteID;

    public BrowserTypesItem() {
    }

    public BrowserTypesItem(String name, int ratio, Integer websiteID) {
        this.name = name;
        this.ratio = ratio;
        this.websiteID = websiteID;
    }

    public String getName() {
        return name;
    }

    public int getRatio() {
        return ratio;
    }

    public Integer getWebsiteID() {
        return websiteID;
    }
}
