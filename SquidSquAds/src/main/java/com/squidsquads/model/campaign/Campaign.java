package com.squidsquads.model.campaign;

import javax.persistence.*;
import java.util.Date;

/**
 * @author  Marc-Antoine Vézina
 * @Date_Of_Creation 2018-02-01
 * @Last_modified_by
 * @Date_of_last_modification
 **/

@Entity
@Table(name="campagne")
public class Campaign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "numero_campagne")
    private Long campaignId;

    @Column(name = "nom")
    private String name;

    @Column(name = "numero_compte")
    private Long accountId;

    private Long[] profileIds;

    @Column(name = "date_creation")
    private Date creationDate;

    @Column(name = "image_hor")
    private String horizontalImg;

    @Column(name = "image_ver")
    private String verticalImg;

    @Column(name = "image_mob")
    private String mobileImg;

    @Column(name = "url_de_redirection")
    private String redirectUrl;

    @Column(name = "date_debut")
    private Date startDate;

    @Column(name = "date_fin")
    private Date endDate;

    @Column(name = "budget")
    private float budget;

    // CONSTRUCTOR

    public Campaign(){}

    public Campaign(Long campaignId, Long accountId, String name, String horizontalImg, String verticalImg, String mobileImg, String redirectUrl, Date startDate, Date endDate, float budget, Long[] linkedProfiles) {
        this.campaignId = campaignId;
        this.accountId = accountId;
        this.name = name;
        this.horizontalImg = horizontalImg;
        this.verticalImg = verticalImg;
        this.mobileImg = mobileImg;
        this.redirectUrl = redirectUrl;
        this.startDate = startDate;
        this.endDate = endDate;
        this.budget = budget;
        this.creationDate = new Date();
        this.profileIds = linkedProfiles;
    }

    // GETTERS + SETTERS

    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAccountId() {
        return accountId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getHorizontalImg() {
        return horizontalImg;
    }

    public void setHorizontalImg(String horizontalImg) {
        this.horizontalImg = horizontalImg;
    }

    public String getVerticalImg() {
        return verticalImg;
    }

    public void setVerticalImg(String verticalImg) {
        this.verticalImg = verticalImg;
    }

    public String getMobileImg() {
        return mobileImg;
    }

    public void setMobileImg(String mobileImg) {
        this.mobileImg = mobileImg;
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

    public float getBudget() {
        return budget;
    }

    public void setBudget(float budget) {
        this.budget = budget;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long[] getProfileIds() {
        return profileIds;
    }

    public void setProfileIds(Long[] profileIds) {
        this.profileIds = profileIds;
    }

    @Override
    public String toString() {
        return "Campagne{"+
                "numeroCampagne="+campaignId+"\""+
                "nom="+name+"\"" +
                "numeroCompte="+accountId+"\"" +
                "dateCreation="+creationDate+"\"" +
                "imageHor="+horizontalImg+"\"" +
                "imageVer="+verticalImg+"\"" +
                "imageMob="+mobileImg+"\"" +
                "urlRedirection="+redirectUrl+"\"" +
                "dateDebut="+startDate+"\"" +
                "dateFin="+endDate+"\"" +
                "budget="+budget+"}";
    }
}