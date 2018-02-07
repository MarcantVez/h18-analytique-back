package model.dto;

/**
 * @author Marc-Antoine Vézina
 * @Date_Of_Creation: 2018-02-01
 */

public class CampaignPreview {
    private Long id;
    private String nom;
    private String dateCreation;

    public CampaignPreview(Long id, String nom, String dateCreation) {
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
