package com.squidsquads.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "vw_stat_royalty")
public class RoyaltyAmount {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name="period")
    private String period;

    @Column(name="id_compte")
    private Integer compte;

    @Column(name="sum_vue")
    private Float somme_vue;

    @Column(name="sum_cible")
    private Float somme_cible;

    @Column(name="sum_clique")
    private Float somme_clique;

    @Column(name="sum_cible_clique")
    private Float somme_cible_clique;

    public RoyaltyAmount() {}

    public RoyaltyAmount(Integer id,String period, Integer compte, Float somme_vue, Float somme_cible, Float somme_clique, Float somme_cible_clique){
        this.id = id;
        this.period = period;
        this.compte = compte;
        this.somme_vue = somme_vue;
        this.somme_cible = somme_cible;
        this.somme_clique = somme_clique;
        this.somme_cible_clique = somme_cible_clique;
    }

    public Integer getId() {return id;}
    public String getPeriod() {return period;}
    public Integer getCompte() {return compte;}
    public Float getSomme_vue() {return somme_vue;}
    public Float getSomme_cible() {return somme_cible;}
    public Float getSomme_clique() {return somme_clique;}
    public Float getSomme_cible_clique() {return somme_cible_clique;}
}