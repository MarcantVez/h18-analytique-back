package com.squidsquads.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "compteutilisateur")
public class Account {

    @Id

    @SequenceGenerator(name = "compteutilisateur_id_compte_seq", sequenceName = "compteutilisateur_id_compte_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "compteutilisateur_id_compte_seq")
    @Column(name = "id_compte")
    private Integer accountID;

    @Column(name = "type_admin")
    private String adminType;

    @Column(name = "courriel")
    private String email;

    @Column(name = "mot_de_passe")
    private String password;

    @Column(name = "no_compte_banque")
    private String bankAccount;

    @Column(name = "date_creation")
    private Date dateCreated;

    public Account() {
    }

    public Account(String adminType, String email, String password, String bankAccount) {
        this.adminType = adminType;
        this.email = email;
        this.password = password;
        this.bankAccount = bankAccount;
    }

    @PrePersist
    void createdNow() {
        this.dateCreated = new Date();
    }

    public Integer getAccountID() {
        return accountID;
    }

    public void setAccountID(Integer accountID) {
        this.accountID = accountID;
    }

    public String getAdminType() {
        return adminType;
    }

    public AdminType getAdminTypeValue() {
        return AdminType.valueOf(adminType);
    }

    public void setAdminType(String adminType) {
        this.adminType = adminType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}
