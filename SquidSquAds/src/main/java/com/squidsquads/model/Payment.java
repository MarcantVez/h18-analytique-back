package com.squidsquads.model;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "paiement")
public class Payment {

    @Id
    @SequenceGenerator(name = "paiement_id_paiement_seq", sequenceName = "paiement_id_paiement_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "paiement_id_paiement_seq")
    @Column(name = "id_paiement")
    private Integer paymentID;

    @Column(name = "id_compte")
    private Integer accountID;

    @Column(name = "montant")
    private BigDecimal amount;

    @Column(name = "date_paiement")
    private Date datePayment;

    public Payment() {
    }

    public Payment(Integer accountID, BigDecimal amount) {
        this.accountID = accountID;
        this.amount = amount;
    }

    @PrePersist
    void createdNow() {
        this.datePayment = new Date();
    }

    public Integer getAccountID() {
        return accountID;
    }

    public Integer getPaymentID() {
        return paymentID;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Date getDatePayment() {
        return datePayment;
    }

    public void setPaymentID(Integer paymentID) {
        this.paymentID = paymentID;
    }

    public void setAccountID(Integer accountID) {
        this.accountID = accountID;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setDatePayment(Date datePayment) {
        this.datePayment = datePayment;
    }
}
