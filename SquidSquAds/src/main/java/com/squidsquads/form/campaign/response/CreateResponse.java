package com.squidsquads.form.campaign.response;

import org.springframework.http.HttpStatus;

public class CreateResponse {

    public static final String SUCCESS = "La campagne publicitaire a été créée";
    public static final String MISSING_FIELDS = "Tous les champs requis doivent être remplis";
    public static final String EXISTING_CAMPAIGN = "Nom de campagne déjà utilisé";
    public static final String INVALID_ACCOUNT_NUMBER = "Numéro de compte invalide";
    public static final String INVALID_DATE_FORMAT = "Le format de date fournis est invalide, utilisez le format \"yyyy-MM-dd\"";

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

    public CreateResponse invalidDateFormat() {
        status = HttpStatus.BAD_REQUEST;
        message = INVALID_DATE_FORMAT;
        return this;
    }


}
