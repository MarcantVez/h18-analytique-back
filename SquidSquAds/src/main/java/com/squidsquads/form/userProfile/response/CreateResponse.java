package com.squidsquads.form.userProfile.response;

import org.springframework.http.HttpStatus;

public class CreateResponse {

    private static final String SUCCESS = "Le profil utilisateur a été créé";
    private static final String MISSING_FIELDS = "Tous les champs requis doivent être remplis";
    private static final String EXISTING_PROFILE = "Nom de profil déjà utilisé";
    private static final String INVALID_URL = "URL invalide";
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

    public CreateResponse profileAlreadyExists() {
        status = HttpStatus.CONFLICT;
        message = EXISTING_PROFILE;
        return this;
    }

    public CreateResponse invalidURL() {
        status = HttpStatus.BAD_REQUEST;
        message = INVALID_URL;
        return this;
    }
}
