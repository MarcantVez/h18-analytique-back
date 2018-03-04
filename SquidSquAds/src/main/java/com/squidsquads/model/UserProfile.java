package com.squidsquads.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "profilutilisateur")
public class UserProfile {

    @Id
    @SequenceGenerator(name = "profilutilisateur_id_profilutilisateur_seq", sequenceName = "profilutilisateur_id_profilutilisateur_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profilutilisateur_id_profilutilisateur_seq")
    @Column(name = "id_profilutilisateur")
    private Integer profileID;

    @Column(name = "id_compte")
    private Integer accountID;

    @Column(name = "nom")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "date_creation")
    private Date dateCreated;

    public UserProfile() {
    }

    public UserProfile(Integer accountID, String name, String description) {
        this.accountID = accountID;
        this.name = name;
        this.description = description;
    }

    @PrePersist
    void creationTimeStamp() {
        this.dateCreated = new Date();
    }

    public Integer getProfileID() {
        return profileID;
    }

    public void setProfileID(Integer profileID) {
        this.profileID = profileID;
    }

    public Integer getAccountID() {
        return accountID;
    }

    public void setAccountID(Integer accountID) {
        this.accountID = accountID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}
