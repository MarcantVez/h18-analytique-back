package com.squidsquads.form.campaign.response;

/**
 * @author Marc-Antoine Vézina
 * @Date_Of_Creation: 2018-02-01
 */

public class CampaignListResponseItem {

    private Long id;
    private String nom;
    private String creationDate;

    public CampaignListResponseItem(Long id, String nom, String creationDate) {
        this.id = id;
        this.nom = nom;
        this.creationDate = creationDate;
    }

    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getCreationDate() {
        return creationDate;
    }
}
