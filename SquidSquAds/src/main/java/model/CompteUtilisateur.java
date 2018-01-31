package model;


import org.springframework.stereotype.Component;
import javax.persistence.*;
import java.util.Date;

/**
 * @author: Dulce Cayetano
 * @Date_Of_Creation: 2018-01-18
 * @Last_modified_by:
 * @Date_of_last_modification:
 **/

// Un bon gros site pour apprendre a utiliser les fonctionnalites CRUD
// http://javasampleapproach.com/spring-framework/use-spring-jpa-postgresql-spring-boot#4Create_Spring_JPA_Repository_Interface

@Component
@Entity
@Table(name = "CompteUtilisateur")
public class CompteUtilisateur {

    private static final long serialVersionUID = -3009157732242241606L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "numero_compte")
    private long numeroCompte;

    @Column(name = "type_admin")
    private String typeAdmin;

    @Column(name = "courriel")
    private String courriel;

    @Column(name = "mot_de_passe")
    private String motDePasse;

    @Column(name = "no_compte_banque")
    private String noCompteBanque;

    @Column(name = "date_creation")
    private Date dateCreation;

    // CONSTRUCTOR

    public CompteUtilisateur() {}

    public CompteUtilisateur(String typeAdmin, String courriel, String motDePasse, String noCompteBanque, Date dateCreation) {
        this.typeAdmin = typeAdmin;
        this.courriel = courriel;
        this.motDePasse = motDePasse;
        this.noCompteBanque = noCompteBanque;
        this.dateCreation = dateCreation;
    }

    public static CompteUtilisateur creerAdminWeb(String courriel, String motDePasse, String noCompteBanque)
    {
        CompteUtilisateur adminWeb = new CompteUtilisateur(TypeAdmin.WEB.toString(), courriel, motDePasse, noCompteBanque, new Date());
        return  adminWeb;
    }

    public static CompteUtilisateur creerAdminPub(String courriel, String motDePasse, String noCompteBanque)
    {
        CompteUtilisateur adminPub = new CompteUtilisateur(TypeAdmin.PUB.toString(), courriel, motDePasse, noCompteBanque, new Date());
        return  adminPub;
    }


    // JPA required method

    @PrePersist
    void createdNow() {
        this.dateCreation = new Date();
    }



    // GETTER AND SETTER

    public long getNumeroCompte() {
        return numeroCompte;
    }

    public void setNumeroCompte(long numeroCompte) {
        this.numeroCompte = numeroCompte;
    }

    public String getTypeAdmin() {
        return typeAdmin;
    }

    public void setTypeAdmin(String typeAdmin) {
        this.typeAdmin = typeAdmin;
    }

    public String getCourriel() {
        return courriel;
    }

    public void setCourriel(String courriel) {
        this.courriel = courriel;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getNoCompteBanque() {
        return noCompteBanque;
    }

    public void setNoCompteBanque(String noCompteBanque) {
        this.noCompteBanque = noCompteBanque;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    @Override
    public String toString() {
        return "CompteUtilisateur{" +
                "numeroCompte=" + numeroCompte +
                ", typeAdmin='" + typeAdmin + '\'' +
                ", courriel='" + courriel + '\'' +
                ", motDePasse='" + motDePasse + '\'' +
                ", noCompteBanque='" + noCompteBanque + '\'' +
                ", dateCreation='" + dateCreation + '\'' +
                '}';
    }
}
