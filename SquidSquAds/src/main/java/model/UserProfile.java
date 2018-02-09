package model;


import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author: Dulce Cayetano
 * @Date_Of_Creation: 2018-02-05
 * @Last_modified_by:
 * @Date_of_last_modification:
 **/
@Component
@Entity
@Table(name = "profildutilisateur")
public class UserProfile {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "numero_profilutilisateur")
    private int profileNumber;

    @Column(name = "numero_compte")
    private int accountNumber;

    @Column(name = "nom")
    private String nom;

    @Column(name = "description")
    private String description;

    @Column(name = "date_creation")
    private Timestamp creationTime;

    // CONSTRUCTOR

    public UserProfile(){}

    public UserProfile(int accountNumber, String nom, String description) {
        this.accountNumber = accountNumber;
        this.nom = nom;
        this.description = description;
    }

    // JPA required method

    @PrePersist
    void creationTimeStamp() {
        this.creationTime = new Timestamp(new Date().getTime());
    }

    public int getProfileNumber() {
        return profileNumber;
    }

    public void setProfileNumber(int profileNumber) {
        this.profileNumber = profileNumber;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Timestamp creationTime) {
        this.creationTime = creationTime;
    }
}
