package model.dto;

import model.Campaign;

import java.text.SimpleDateFormat;

/**
 * @author Marc-Antoine Vézina
 * @Date_Of_Creation: 2018-02-01
 */

public class DTOCampaignListItem implements IDataTransferObject<DTOCampaignListItem, Campaign> {

    private int id;
    private String nom;
    private String dateCreation;

    public DTOCampaignListItem(int id, String nom, String dateCreation) {
        this.id = id;
        this.nom = nom;
        this.dateCreation = dateCreation;
    }


    @Override
    public DTOCampaignListItem fromClass(Campaign campaign) {
        return new DTOCampaignListItem(
                campaign.getAccountId(),
                campaign.getName(),
                new SimpleDateFormat("yyyy-MM-dd").format(campaign.getCreationDate())
        );
    }


    // ----- GETTERS AND SETTERS

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }
}
