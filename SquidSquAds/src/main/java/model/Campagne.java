package model;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Date;

/**
 * @author: Marc-Antoine Vézina
 * @Date_Of_Creation: 2018-02-01
 * @Last_modified_by:
 * @Date_of_last_modification:
 **/

@Component
@Entity
@Table(name="Campagne")
public class Campagne{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "numero_campagne")
    private long numeroCampagne;

    @Column(name = "nom")
    private String nom;

    @Column(name = "numero_compte")
    private int numeroCompte;

    @Column(name = "date_creation")
    private Date dateCreation;

    @Column(name = "image_hor")
    private String imageHor;

    @Column(name = "image_ver")
    private String imageVer;

    @Column(name = "image_mob")
    private String imageMob;

    @Column(name = "url_de_redirection")
    private String urlRedirection;

    @Column(name = "date_debut")
    private Date dateDebut;

    @Column(name = "date_fin")
    private Date dateFin;

    @Column(name = "budget")
    private float budget;

    // CONSTRUCTOR

    public Campagne(){}

    public Campagne(String nom, int numeroCompte, String imageHor, String imageVer, String imageMob, String urlRedirection, Date dateDebut, Date dateFin, float budget) {
        this.nom = nom;
        this.numeroCompte = numeroCompte;
        this.imageHor = imageHor;
        this.imageVer = imageVer;
        this.imageMob = imageMob;
        this.urlRedirection = urlRedirection;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.budget = budget;
    }

    // JPA required method
    @PrePersist
    void createdNow() {
        this.dateCreation = new Date();
    }

    // GETTERS + SETTERS


    public long getNumeroCampagne() {
        return numeroCampagne;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getNumeroCompte() {
        return numeroCompte;
    }

    public void setNumeroCompte(int numeroCompte) {
        this.numeroCompte = numeroCompte;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getImageHor() {
        return imageHor;
    }

    public void setImageHor(String imageHor) {
        this.imageHor = imageHor;
    }

    public String getImageVer() {
        return imageVer;
    }

    public void setImageVer(String imageVer) {
        this.imageVer = imageVer;
    }

    public String getImageMob() {
        return imageMob;
    }

    public void setImageMob(String imageMob) {
        this.imageMob = imageMob;
    }

    public String getUrlRedirection() {
        return urlRedirection;
    }

    public void setUrlRedirection(String urlRedirection) {
        this.urlRedirection = urlRedirection;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public float getBudget() {
        return budget;
    }

    public void setBudget(float budget) {
        this.budget = budget;
    }

    @Override
    public String toString() {
        return "Campagne{"+
                "numeroCampagne="+numeroCampagne+"\""+
                "nom="+nom+"\"" +
                "numeroCompte="+numeroCompte+"\"" +
                "dateCreation="+dateCreation+"\"" +
                "imageHor="+imageHor+"\"" +
                "imageVer="+imageVer+"\"" +
                "imageMob="+imageMob+"\"" +
                "urlRedirection="+urlRedirection+"\"" +
                "dateDebut="+dateDebut+"\"" +
                "dateFin="+dateFin+"\"" +
                "budget="+budget+"}";
    }
}

