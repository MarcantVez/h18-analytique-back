package com.squidsquads.form.stats.response;

import com.squidsquads.model.RoyaltyAmount;

public class RoyaltyAmountDTO {
    private String period;
    private Float sommeVue;
    private Float sommeCible;
    private Float sommeClique;
    private Float sommeCibleClique;

    public RoyaltyAmountDTO(RoyaltyAmount royaltyAmount) {
        this.period = royaltyAmount.getPeriod();
        this.sommeCible = royaltyAmount.getSomme_cible();
        this.sommeClique = royaltyAmount.getSomme_clique();
        this.sommeVue = royaltyAmount.getSomme_vue();
        this.sommeCibleClique = royaltyAmount.getSomme_cible_clique();
    }

    public String getPeriod() {
        return period;
    }

    public Float getSommeVue() {
        return sommeVue;
    }

    public Float getSommeCible() {
        return sommeCible;
    }

    public Float getSommeClique() {
        return sommeClique;
    }

    public Float getSommeCibleClique() {
        return sommeCibleClique;
    }
}
