package com.squidsquads.form.campaign.response;

import com.squidsquads.model.campaign.Campaign;
import org.springframework.http.HttpStatus;

public class CampaignDetailResponse {
    private Campaign campaign;
    private HttpStatus status;
    private String message;

    private static final String CREATE_FIELDS_MISSING = "Tous les champs requis doivent être remplis";

    public CampaignDetailResponse() {
    }

    public CampaignDetailResponse unauthorized(){
        campaign = null;
        status = HttpStatus.UNAUTHORIZED;
        return this;
    }

    public CampaignDetailResponse notFound(){
        campaign = null;
        status = HttpStatus.NOT_FOUND;
        return this;
    }

    public CampaignDetailResponse fieldsMissing(){
        campaign = null;
        status = HttpStatus.BAD_REQUEST;
        message = CREATE_FIELDS_MISSING;
        return this;
    }

    public CampaignDetailResponse ok(Campaign c){
        campaign = c;
        status = HttpStatus.OK;
        return this;
    }

    public Campaign getCampaign() {
        return campaign;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
