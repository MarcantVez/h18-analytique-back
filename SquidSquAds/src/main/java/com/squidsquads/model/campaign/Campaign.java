package com.squidsquads.model.campaign;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "campagne")
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "numero_campagne")
    private Long campaignID;

    @Column(name = "nom")
    private String name;

    @Column(name = "numero_compte")
    private Long accountID;

    private Long[] profileIds;

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

    public Campaign() {
    }

    public Campaign(Long campaignID, Long accountID, String name, String imgHorizontal, String imgVertical, String imgMobile, String redirectUrl, Date startDate, Date endDate, BigDecimal budget, Long[] linkedProfiles) {
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

    public Long getCampaignID() {
        return campaignID;
    }

    public void setCampaignID(Long campaignID) {
        this.campaignID = campaignID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAccountID() {
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

    public void setAccountID(Long accountID) {
        this.accountID = accountID;
    }

    public Long[] getProfileIds() {
        return profileIds;
    }

    public void setProfileIds(Long[] profileIds) {
        this.profileIds = profileIds;
    }

    @Override
    public String toString() {
        return "Campagne{" +
                "numeroCampagne=" + campaignID + "\"" +
                "nom=" + name + "\"" +
                "numeroCompte=" + accountID + "\"" +
                "dateCreation=" + creationDate + "\"" +
                "imageHor=" + imgHorizontal + "\"" +
                "imageVer=" + imgVertical + "\"" +
                "imageMob=" + imgMobile + "\"" +
                "urlRedirection=" + redirectUrl + "\"" +
                "dateDebut=" + startDate + "\"" +
                "dateFin=" + endDate + "\"" +
                "budget=" + budget + "}";
    }
}