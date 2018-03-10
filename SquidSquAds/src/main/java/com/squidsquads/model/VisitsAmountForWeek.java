package com.squidsquads.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "vw_stat_week")
public class VisitsAmountForWeek {
    @Id
    @Column(name = "row_number")
    private Integer id;

    @Column(name = "id_sitewebadmin")
    private Integer websiteID;

    @Column(name = "dayofweek")
    private Integer dayOfweek;

    @Column(name = "sum")
    private Integer amount;

    @Column(name = "avg")
    private Integer average;

    public VisitsAmountForWeek() {
    }

    public VisitsAmountForWeek(Integer id, Integer websiteID, Integer dayOfweek, Integer amount, Integer average) {
        this.id = id;
        this.websiteID = websiteID;
        this.dayOfweek = dayOfweek;
        this.amount = amount;
        this.average = average;
    }

    public Integer getId() {
        return id;
    }

    public Integer getWebsiteID() {
        return websiteID;
    }

    public Integer getDayOfweek() {
        return dayOfweek;
    }

    public Integer getAmount() {
        return amount;
    }

    public Integer getAverage() {
        return average;
    }
}
