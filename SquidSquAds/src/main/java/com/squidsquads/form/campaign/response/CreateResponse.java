package com.squidsquads.form.campaign.response;

import org.springframework.http.HttpStatus;

public class CreateResponse {

    private static final String SUCCESS = "La campagne publicitaire a été créée";
    private static final String MISSING_FIELDS = "Tous les champs requis doivent être remplis";
    private static final String EXISTING_CAMPAIGN = "Nom de campagne déjà utilisé";
    private static final String INVALID_ACCOUNT_NUMBER = "Numéro de compte invalide";
    private static final String INVALID_DATE_FORMAT = "Le format de date fournis est invalide, utilisez le format \"yyyy-MM-dd\"";
    private static final String INVALID_END_DATE = "La date de fin doit être plus tard que la date de début";
    private static final String INVALID_NAME_FORMAT = "Le nom de la campagne ne doit pas dépasser 100 caractères";
    private static final String INVALID_IMG_URL_FORMAT = "Les url des l'images ne doit pas dépasser 100 caractères";
    private static final String INVALID_REDIRECT_URL_FORMAT = "Les url de redirection ne doit pas dépasser 100 caractères";

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

    public CreateResponse invalidEndDate() {
        status = HttpStatus.BAD_REQUEST;
        message = INVALID_END_DATE;
        return this;
    }

    public CreateResponse invalidName() {
        status = HttpStatus.BAD_REQUEST;
        message = INVALID_NAME_FORMAT;
        return this;
    }

    public CreateResponse invalidHorizontalImgUrl() {
        status = HttpStatus.BAD_REQUEST;
        message = INVALID_IMG_URL_FORMAT;
        return this;
    }

    public CreateResponse invalidVerticalImgUrl() {
        status = HttpStatus.BAD_REQUEST;
        message = INVALID_IMG_URL_FORMAT;
        return this;
    }

    public CreateResponse invalidMobileImgUrl() {
        status = HttpStatus.BAD_REQUEST;
        message = INVALID_IMG_URL_FORMAT;
        return this;
    }

    public CreateResponse invalidRedirectUrl() {
        status = HttpStatus.BAD_REQUEST;
        message = INVALID_REDIRECT_URL_FORMAT;
        return this;
    }
}
