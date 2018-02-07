package form.campaign.response;

import form.Form;

/**
 * @author Marc-Antoine Vézina
 * @Date_Of_Creation: 2018-02-01
 */

public class CampaignListResponse extends Form{
    private Long id;
    private String nom;
    private String dateCreation;

    public CampaignListResponse(int status, Long id, String nom, String dateCreation) {
        super(status);
        this.id = id;
        this.nom = nom;
        this.dateCreation = dateCreation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
