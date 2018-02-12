package com.squidsquads.form.campaign.response;

import org.springframework.http.HttpStatus;

import java.util.List;

public class ListResponse {

    private List<ListResponseItem> campaigns;
    private HttpStatus status;

    public ListResponse() {
    }

    public ListResponse ok(List<ListResponseItem> campaigns) {
        status = HttpStatus.OK;
        this.campaigns = campaigns;
        return this;
    }

    public ListResponse failed() {
        status = HttpStatus.INTERNAL_SERVER_ERROR;
        campaigns = null;
        return this;
    }

    public List<ListResponseItem> getCampaigns() {
        return campaigns;
    }

    public HttpStatus getStatus() {
        return status;
    }

}
