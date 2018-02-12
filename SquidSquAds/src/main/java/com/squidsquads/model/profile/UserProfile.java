package com.squidsquads.model.profile;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Date;

@Component
@Entity
@Table(name = "profildutilisateur")
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "numero_profildutilisateur")
    private long profileID;

    @Column(name = "numero_compte")
    private long accountID;

    @Column(name = "nom")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "date_creation")
    private Date dateCreated;

    public UserProfile() {
    }

    public UserProfile(long accountID, String name, String description) {
        this.accountID = accountID;
        this.name = name;
        this.description = description;
    }

    @PrePersist
    void creationTimeStamp() {
        this.dateCreated = new Date();
    }

    public long getProfileID() {
        return profileID;
    }

    public void setProfileID(long profileID) {
        this.profileID = profileID;
    }

    public long getAccountID() {
        return accountID;
    }

    public void setAccountID(long accountID) {
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
