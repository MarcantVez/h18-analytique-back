package com.squidsquads.form.stats.response;

import com.squidsquads.model.RoyaltyAmountStat;

public class RoyaltyAmountDTO {
    private String period;
    private Float sommeVue;
    private Float sommeCible;
    private Float sommeClique;
    private Float sommeCibleClique;

    public RoyaltyAmountDTO(RoyaltyAmountStat royaltyAmountStat) {
        this.period = royaltyAmountStat.getPeriod();
        this.sommeCible = royaltyAmountStat.getSomme_cible();
        this.sommeClique = royaltyAmountStat.getSomme_clique();
        this.sommeVue = royaltyAmountStat.getSomme_vue();
        this.sommeCibleClique = royaltyAmountStat.getSomme_cible_clique();
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
