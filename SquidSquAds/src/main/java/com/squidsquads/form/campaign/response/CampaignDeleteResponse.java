package com.squidsquads.form.campaign.response;

import org.springframework.http.HttpStatus;

public class CampaignDeleteResponse {

    private static final String CAMPAGNE_NOT_FOUND = "La campagne n'existe pas";
    private static final String SUCCESS = "La campagne a été supprimée avec succès";

    private HttpStatus status;
    private String message;

    public CampaignDeleteResponse() {
    }

    public CampaignDeleteResponse unauthorized() {
        status = HttpStatus.UNAUTHORIZED;
        return this;
    }

    public CampaignDeleteResponse notFound() {
        status = HttpStatus.NOT_FOUND;
        message = CAMPAGNE_NOT_FOUND;
        return this;
    }

    public CampaignDeleteResponse ok() {
        status = HttpStatus.OK;
        message = SUCCESS;
        return this;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
