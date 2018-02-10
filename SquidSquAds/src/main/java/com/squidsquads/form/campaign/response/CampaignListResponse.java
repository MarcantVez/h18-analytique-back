package com.squidsquads.form.campaign.response;

import org.springframework.http.HttpStatus;

import java.util.List;

public class CampaignListResponse {

    private List<CampaignListResponseItem> items;
    private HttpStatus status;

    public CampaignListResponse() {
    }

    public CampaignListResponse ok(List<CampaignListResponseItem> items) {
        this.status = HttpStatus.OK;
        this.items = items;
        return this;
    }

    public CampaignListResponse unauthorized() {
        this.status = HttpStatus.UNAUTHORIZED;
        this.items = null;
        return this;
    }

    public List<CampaignListResponseItem> getItems() {
        return items;
    }

    public HttpStatus getStatus() {
        return status;
    }

}
