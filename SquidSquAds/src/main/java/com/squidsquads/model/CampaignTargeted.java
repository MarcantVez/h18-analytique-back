package com.squidsquads.model;

public class CampaignTargeted {

    private Campaign campaign;
    private boolean isTargeted;

    public CampaignTargeted(Campaign campaign, boolean isTargeted) {
        this.campaign = campaign;
        this.isTargeted = isTargeted;
    }

    public Campaign getCampaign() {
        return campaign;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }

    public boolean isTargeted() {
        return isTargeted;
    }
}
