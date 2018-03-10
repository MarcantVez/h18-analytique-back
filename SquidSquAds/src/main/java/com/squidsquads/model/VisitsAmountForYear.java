package com.squidsquads.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "vw_stat_year")
public class VisitsAmountForYear {
    @Id
    @Column(name = "row_number")
    private Integer id;

    @Column(name = "id_sitewebadmin")
    private Integer websiteID;

    @Column(name = "monthofyear")
    private Integer monthOfYear;

    @Column(name = "sum")
    private Integer amount;

    @Column(name = "avg")
    private Integer average;

    public VisitsAmountForYear() {
    }

    public VisitsAmountForYear(Integer id, Integer websiteID, Integer monthOfYear, Integer amount, Integer average) {
        this.id = id;
        this.websiteID = websiteID;
        this.monthOfYear = monthOfYear;
        this.amount = amount;
        this.average = average;
    }

    public Integer getId() {
        return id;
    }

    public Integer getWebsiteID() {
        return websiteID;
    }

    public Integer getMonthOfYear() {
        return monthOfYear;
    }

    public Integer getAmount() {
        return amount;
    }

    public Integer getAverage() {
        return average;
    }
}
