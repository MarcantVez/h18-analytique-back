package com.squidsquads.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "visite")
public class Visit {

    @Id
    @SequenceGenerator(name = "visite_id_visite_seq", sequenceName = "visite_id_visite_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "visite_id_visite_seq")
    @Column(name = "id_visite")
    private Integer visitID;

    @Column(name = "id_banniere")
    private Integer bannerID;

    @Column(name = "est_Cliquee")
    private Boolean isClicked;

    @Column(name = "est_Ciblee")
    private Boolean isTargeted;

    @Column(name = "date_heure")
    private Date dateTime;

    public Visit() {
    }

    public Visit(Integer bannerID, Boolean isClicked, Boolean isTargeted) {
        this.bannerID = bannerID;
        this.isClicked = isClicked;
        this.isTargeted = isTargeted;
        this.dateTime = new Date();
    }

    public Integer getVisitID() {
        return visitID;
    }

    public void setVisitID(Integer visitID) {
        this.visitID = visitID;
    }

    public Integer getBannerID() {
        return bannerID;
    }

    public void setBannerID(Integer bannerID) {
        this.bannerID = bannerID;
    }

    public Boolean getClicked() {
        return isClicked;
    }

    public void setClicked(Boolean clicked) {
        isClicked = clicked;
    }

    public Boolean getTargeted() {
        return isTargeted;
    }

    public void setTargeted(Boolean targeted) {
        isTargeted = targeted;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
}
