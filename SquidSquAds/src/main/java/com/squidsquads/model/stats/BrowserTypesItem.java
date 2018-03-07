package com.squidsquads.model.stats;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;

@Entity
@Table(name = "browsertypes_view")
@Immutable
public class BrowserTypesItem {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "navigateur")
    private String name;

    @Column(name = "ratio")
    private int ratio;

    @Column(name = "websiteid")
    private Long websiteID;

    public BrowserTypesItem() {
    }

    public BrowserTypesItem(String name, int ratio, Long websiteID) {
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

    public Long getAdminID() {
        return websiteID;
    }
}
