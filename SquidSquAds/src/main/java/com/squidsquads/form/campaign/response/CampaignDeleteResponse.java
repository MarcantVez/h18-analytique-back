package com.squidsquads.form.campaign.response;

import org.springframework.http.HttpStatus;

public class CampaignDeleteResponse {
    private HttpStatus status;

    public CampaignDeleteResponse() {
    }

    public CampaignDeleteResponse notFound(){
        status = HttpStatus.NOT_FOUND;
        return this;
    }

    public CampaignDeleteResponse unauthorized(){
        status = HttpStatus.UNAUTHORIZED;
        return this;
    }

    public CampaignDeleteResponse ok(){
        status = HttpStatus.OK;
        return this;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
