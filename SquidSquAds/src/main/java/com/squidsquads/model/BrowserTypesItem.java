package com.squidsquads.model;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;

@Entity
@Table(name = "vw_stat_browser")
@Immutable
public class BrowserTypesItem {
    @Id
    @Column(name = "rowid")
    private Integer id;

    @Column(name = "id_sitewebadmin")
    private Integer websiteID;

    @Column(name = "navigateur")
    private String name;

    @Column(name = "count")
    private Integer count;

    @Column(name = "ratio")
    private float ratio;



    public BrowserTypesItem() {
    }

    public BrowserTypesItem(Integer websiteID, String name, Integer count, float ratio) {
        this.websiteID = websiteID;
        this.name = name;
        this.count = count;
        this.ratio = ratio;
    }

    public Integer getId() {
        return id;
    }

    public Integer getWebsiteID() {
        return websiteID;
    }

    public String getName() {
        return name;
    }

    public Integer getCount() {
        return count;
    }

    public float getRatio() {
        return ratio;
    }
}
