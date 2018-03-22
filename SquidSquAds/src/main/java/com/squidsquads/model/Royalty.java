package com.squidsquads.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "redevance")
public class Royalty {

    @Id
    @SequenceGenerator(name = "redevance_id_redevance_seq", sequenceName = "redevance_id_redevance_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "redevance_id_redevance_seq")
    @Column(name = "id_redevance")
    private Integer royaltyID;

    @Column(name = "id_compte")
    private Integer accountID;

    @Column(name = "id_visite")
    private Integer visitID;

    @Column(name = "montant")
    private BigDecimal amount;

    @Column(name = "est_reclame")
    private Boolean isClaimed;

    @Column(name = "date_creation")
    private Date creationDate;

    public Royalty() {
    }

    public Royalty(Integer accountID, Integer visitID, BigDecimal amount, Boolean isClaimed) {
        this.accountID = accountID;
        this.visitID = visitID;
        this.amount = amount;
        this.isClaimed = isClaimed;
        this.creationDate = new Date();
    }

    public Integer getRoyaltyID() {
        return royaltyID;
    }

    public void setRoyaltyID(Integer royaltyID) {
        this.royaltyID = royaltyID;
    }

    public Integer getAccountID() {
        return accountID;
    }

    public void setAccountID(Integer accountID) {
        this.accountID = accountID;
    }

    public Integer getVisitID() {
        return visitID;
    }

    public void setVisitID(Integer visitID) {
        this.visitID = visitID;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Boolean getClaimed() {
        return isClaimed;
    }

    public void setClaimed(Boolean claimed) {
        isClaimed = claimed;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
