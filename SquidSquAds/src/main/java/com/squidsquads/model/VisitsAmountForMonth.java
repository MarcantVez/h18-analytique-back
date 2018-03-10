package com.squidsquads.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "vw_stat_month")
public class VisitsAmountForMonth {
    @Id
    @Column(name = "row_number")
    private Integer id;

    @Column(name = "id_sitewebadmin")
    private Integer websiteID;

    @Column(name = "dayofmonth")
    private Integer dayOfMonth;

    @Column(name = "sum")
    private Integer amount;

    @Column(name = "avg")
    private Integer average;

    public VisitsAmountForMonth() {
    }

    public VisitsAmountForMonth(Integer id, Integer websiteID, Integer dayOfMonth, Integer amount, Integer average) {
        this.id = id;
        this.websiteID = websiteID;
        this.dayOfMonth = dayOfMonth;
        this.amount = amount;
        this.average = average;
    }

    public Integer getId() {
        return id;
    }

    public Integer getWebsiteID() {
        return websiteID;
    }

    public Integer getDayOfMonth() {
        return dayOfMonth;
    }

    public Integer getAmount() {
        return amount;
    }

    public Integer getAverage() {
        return average;
    }
}
