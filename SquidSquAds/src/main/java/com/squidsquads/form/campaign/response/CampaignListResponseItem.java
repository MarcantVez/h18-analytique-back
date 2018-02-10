package com.squidsquads.form.campaign.response;

/**
 * @author Marc-Antoine Vézina
 * @Date_Of_Creation: 2018-02-01
 */

public class CampaignListResponseItem {
    private Long id;
    private String nom;
    private String dateCreation;

    public CampaignListResponseItem(Long id, String nom, String dateCreation) {
        this.id = id;
        this.nom = nom;
        this.dateCreation = dateCreation;
    }

    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getDateCreation() {
        return dateCreation;
    }
}
