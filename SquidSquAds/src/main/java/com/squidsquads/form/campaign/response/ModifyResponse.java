package com.squidsquads.form.campaign.response;

import org.springframework.http.HttpStatus;

public class ModifyResponse {

    private static final String SUCCESS = "La campagne publicitaire a été modifiée";
    private static final String MISSING_FIELDS = "Tous les champs requis doivent être remplis";
    private static final String CAMPAIGN_NOT_FOUND = "Le profil utilisateur n'existe pas";
    private static final String EXISTING_CAMPAIGN = "Nom de campagne déjà utilisé";
    private static final String INVALID_ACCOUNT_NUMBER = "Numéro de compte invalide";
    private static final String INVALID_DATE_FORMAT = "Le format de date fournis est invalide, utilisez le format \"yyyy-MM-dd\"";
    private static final String INVALID_NAME_FORMAT = "Le nom de la campagne ne doit pas dépasser 100 caractères";
    private static final String INVALID_IMG_URL_FORMAT = "Les url des l'images ne doit pas dépasser 100 caractères";
    private static final String INVALID_REDIRECT_URL_FORMAT = "Les url de redirection ne doit pas dépasser 100 caractères";

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

    public ModifyResponse campaignAlreadyExists() {
        status = HttpStatus.CONFLICT;
        message = EXISTING_CAMPAIGN;
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


    public ModifyResponse invalidName() {
        status = HttpStatus.BAD_REQUEST;
        message = INVALID_NAME_FORMAT;
        return this;
    }

    public ModifyResponse invalidHorizontalImgUrl() {
        status = HttpStatus.BAD_REQUEST;
        message = INVALID_IMG_URL_FORMAT;
        return this;
    }

    public ModifyResponse invalidVerticalImgUrl() {
        status = HttpStatus.BAD_REQUEST;
        message = INVALID_IMG_URL_FORMAT;
        return this;
    }

    public ModifyResponse invalidMobileImgUrl() {
        status = HttpStatus.BAD_REQUEST;
        message = INVALID_IMG_URL_FORMAT;
        return this;
    }

    public ModifyResponse invalidRedirectUrl() {
        status = HttpStatus.BAD_REQUEST;
        message = INVALID_REDIRECT_URL_FORMAT;
        return this;
    }
}
