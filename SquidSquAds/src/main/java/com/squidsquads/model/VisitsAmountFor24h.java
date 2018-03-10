package com.squidsquads.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "vw_stat_24h")
public class VisitsAmountFor24h{
    @Id
    @Column(name = "row_number")
    private Integer id;

    @Column(name = "id_sitewebadmin")
    private Integer websiteID;

    @Column(name = "timeofday")
    private Date timeOfDay;

    @Column(name = "sum")
    private Integer amount;

    @Column(name = "avg")
    private Integer average;

    public VisitsAmountFor24h() {
    }

    public VisitsAmountFor24h(Integer id, Integer websiteID, Date timeOfDay, Integer amount, Integer average) {
        this.id = id;
        this.websiteID = websiteID;
        this.timeOfDay = timeOfDay;
        this.amount = amount;
        this.average = average;
    }

    public Integer getId() {
        return id;
    }

    public Integer getWebsiteID() {
        return websiteID;
    }

    public Date getTimeOfDay() {
        return timeOfDay;
    }

    public Integer getAmount() {
        return amount;
    }

    public Integer getAverage() {
        return average;
    }
}
