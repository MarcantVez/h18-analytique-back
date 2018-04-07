package com.squidsquads.model;

import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "campagne")
public class Campaign {

    @Id
    @SequenceGenerator(name = "campagne_id_campagne_seq", sequenceName = "campagne_id_campagne_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "campagne_id_campagne_seq")
    @Column(name = "id_campagne")
    private Integer campaignID;

    @Column(name = "nom")
    private String name;

    @Column(name = "id_compte")
    private Integer accountID;

    @Transient
    private Integer[] profileIds;

    @Column(name = "date_creation")
    private Date creationDate;

    @Column(name = "image_hor")
    private String imgHorizontal;

    @Column(name = "image_ver")
    private String imgVertical;

    @Column(name = "image_mob")
    private String imgMobile;

    @Column(name = "url_de_redirection")
    private String redirectUrl;

    @Column(name = "date_debut")
    private Date startDate;

    @Column(name = "date_fin")
    private Date endDate;

    @Column(name = "budget")
    private BigDecimal budget;

    private boolean isActive;

    public Campaign() {
    }

    public Campaign(Integer campaignID, Integer accountID, String name, String imgHorizontal, String imgVertical, String imgMobile, String redirectUrl, Date startDate, Date endDate, BigDecimal budget, Integer[] linkedProfiles) {
        this.campaignID = campaignID;
        this.accountID = accountID;
        this.name = name;
        this.imgHorizontal = imgHorizontal;
        this.imgVertical = imgVertical;
        this.imgMobile = imgMobile;
        this.redirectUrl = redirectUrl;
        this.startDate = startDate;
        this.endDate = endDate;
        this.budget = budget;
        this.creationDate = new Date();
        this.profileIds = linkedProfiles;
    }

//    @PostLoad
    public void handleActiveStatus(){
        boolean isActive = false;
        Date currentDate = new Date();
        if (currentDate.equals(startDate) || currentDate.equals(endDate)) {
            isActive = true;
        } else if (currentDate.after(startDate) && currentDate.before(endDate)) {
            isActive = true;
        }
        this.isActive = isActive;
    }

//    @PostLoad
//    public void handleMaxBudget(BigDecimal amountSpent) {
//        this.isActive = (budget.compareTo(amountSpent) > 0);
//    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Integer getCampaignID() {
        return campaignID;
    }

    public void setCampaignID(Integer campaignID) {
        this.campaignID = campaignID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAccountID() {
        return accountID;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getImgHorizontal() {
        return imgHorizontal;
    }

    public void setImgHorizontal(String imgHorizontal) {
        this.imgHorizontal = imgHorizontal;
    }

    public String getImgVertical() {
        return imgVertical;
    }

    public void setImgVertical(String imgVertical) {
        this.imgVertical = imgVertical;
    }

    public String getImgMobile() {
        return imgMobile;
    }

    public void setImgMobile(String imgMobile) {
        this.imgMobile = imgMobile;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public void setAccountID(Integer accountID) {
        this.accountID = accountID;
    }

    public Integer[] getProfileIds() {
        return profileIds;
    }

    public void setProfileIds(Integer[] profileIds) {
        this.profileIds = profileIds;
    }
}