package com.squidsquads.form.campaign.response;

public class CampaignListResponseItem {

    private final Long id;
    private final String name;
    private final String dateCreated;

    public CampaignListResponseItem(Long id, String name, String dateCreated) {
        this.id = id;
        this.name = name;
        this.dateCreated = dateCreated;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDateCreated() {
        return dateCreated;
    }
}
