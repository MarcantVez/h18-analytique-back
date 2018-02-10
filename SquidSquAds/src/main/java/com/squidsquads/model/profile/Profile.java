package com.squidsquads.model.profile;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "profildutilisateur")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "numero_ProfilDUtilisateur")
    private Long profileId;

    @Column(name = "numero_Compte")
    private int accountNumber;

    @Column(name = "Nom")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "date_Creation")
    private Date createDate;

    // CONSTRUCTOR

    public Profile() {
    }

    public Profile(int accountNumber, String name, String description) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.description = description;
        this.createDate = new Date();
    }

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
