package com.squidsquads.form.campaign.response;

import org.springframework.http.HttpStatus;

public class ModifyResponse {

    public static final String SUCCESS = "La campagne publicitaire a été modifié";
    public static final String MISSING_FIELDS = "Tous les champs requis doivent être remplis";
    public static final String CAMPAIGN_NOT_FOUND = "Le profil utilisateur n'existe pas";
    public static final String INVALID_ACCOUNT_NUMBER = "Numéro de compte invalide";
    public static final String INVALID_DATE_FORMAT = "Le format de date fournis est invalide, utilisez le format \"yyyy-MM-dd\"";

    private HttpStatus status;
    private String message;

    public ModifyResponse() {
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public ModifyResponse ok() {
        status = HttpStatus.OK;
        message = SUCCESS;
        return this;
    }

    public ModifyResponse fieldsMissing() {
        status = HttpStatus.BAD_REQUEST;
        message = MISSING_FIELDS;
        return this;
    }

    public ModifyResponse failed() {
        status = HttpStatus.INTERNAL_SERVER_ERROR;
        message = INVALID_ACCOUNT_NUMBER;
        return this;
    }

    public ModifyResponse invalidDateFormat() {
        status = HttpStatus.BAD_REQUEST;
        message = INVALID_DATE_FORMAT;
        return this;
    }

    public ModifyResponse notFound() {
        status = HttpStatus.NOT_FOUND;
        message = CAMPAIGN_NOT_FOUND;
        return this;
    }
}
