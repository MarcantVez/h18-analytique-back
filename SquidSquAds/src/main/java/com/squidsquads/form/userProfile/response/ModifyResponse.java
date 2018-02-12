package com.squidsquads.form.userProfile.response;

import org.springframework.http.HttpStatus;

public class ModifyResponse {

    private static final String SUCCESS = "Le profil utilisateur a été modifié";
    private static final String MISSING_FIELDS = "Tous les champs requis doivent être remplis";
    private static final String PROFILE_NOT_FOUND = "Le profil utilisateur n'existe pas";
    private static final String INVALID_URL = "URL invalide";
    private static final String INVALID_ACCOUNT_NUMBER = "Numéro de compte invalide";

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

    public ModifyResponse invalidURL() {
        status = HttpStatus.BAD_REQUEST;
        message = INVALID_URL;
        return this;
    }

    public ModifyResponse notFound() {
        status = HttpStatus.NOT_FOUND;
        message = PROFILE_NOT_FOUND;
        return this;
    }
}
