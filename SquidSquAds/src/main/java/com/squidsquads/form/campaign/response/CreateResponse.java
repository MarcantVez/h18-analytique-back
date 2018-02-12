package com.squidsquads.form.campaign.response;

import org.springframework.http.HttpStatus;

public class CreateResponse {

    private static final String SUCCESS = "La campagne publicitaire a été créée";
    private static final String MISSING_FIELDS = "Tous les champs requis doivent être remplis";
    private static final String EXISTING_CAMPAIGN = "Nom de campagne déjà utilisé";
    private static final String INVALID_ACCOUNT_NUMBER = "Numéro de compte invalide";

    private HttpStatus status;
    private String message;

    public CreateResponse() {
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public CreateResponse ok() {
        status = HttpStatus.OK;
        message = SUCCESS;
        return this;
    }

    public CreateResponse failed() {
        status = HttpStatus.INTERNAL_SERVER_ERROR;
        message = INVALID_ACCOUNT_NUMBER;
        return this;
    }

    public CreateResponse fieldsMissing() {
        status = HttpStatus.BAD_REQUEST;
        message = MISSING_FIELDS;
        return this;
    }

    public CreateResponse campaignAlreadyExists() {
        status = HttpStatus.CONFLICT;
        message = EXISTING_CAMPAIGN;
        return this;
    }
}
