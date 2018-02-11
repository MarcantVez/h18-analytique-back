package com.squidsquads.model.account;

import org.springframework.stereotype.Component;
import javax.persistence.*;
import java.util.Date;

@Component
@Entity
@Table(name = "compteutilisateur")
public class Account {

    private static final long serialVersionUID = -3009157732242241606L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "numero_compte")
    private long accountID;

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

    // CONSTRUCTOR

    public Account() {
    }

    public Account(String adminType, String email, String password, String bankAccount) {
        this.adminType = adminType;
        this.email = email;
        this.password = password;
        this.bankAccount = bankAccount;
    }

    // JPA required method

    @PrePersist
    void createdNow() {
        this.dateCreated = new Date();
    }

    public long getAccountID() {
        return accountID;
    }

    public void setAccountID(long accountID) {
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
