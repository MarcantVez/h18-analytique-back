package com.squidsquads.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "redevance")
public class Royalty {

    private static final float ROYALTY_FEE_VIEWED = 0.01f;
    private static final float ROYALTY_FEE_TARGETED_VIEWED = 0.03f;
    private static final float ROYALTY_FEE_CLICKED = 0.05f;
    private static final float ROYALTY_FEE_TARGETED_CLICKED = 0.10f;

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
    private Float amount;

    @Column(name = "est_reclame")
    private Boolean isClaimed;

    @Column(name = "date_creation")
    private Date creationDate;

    public Royalty(){}

    public Royalty(Integer accountID, Integer visitID, Float amount, Boolean isClaimed) {
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

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
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

    public static float getFee(boolean isTargeted, boolean isClicked)
    {
        float fee = 0.f;

        if( isTargeted )
        {
            fee = (isClicked) ? ROYALTY_FEE_TARGETED_CLICKED : ROYALTY_FEE_TARGETED_VIEWED;
        }
        else
        {
            fee = (isClicked) ? ROYALTY_FEE_CLICKED : ROYALTY_FEE_VIEWED;
        }

        return fee;
    }
}
