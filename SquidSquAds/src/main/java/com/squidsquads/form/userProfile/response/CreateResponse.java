package com.squidsquads.form.userProfile.response;

import org.springframework.http.HttpStatus;

public class CreateResponse {

    private static final String SUCCESS = "Le profil utilisateur a été créé";
    private static final String MISSING_FIELDS = "Tous les champs requis doivent être remplis";
    private static final String EXISTING_PROFILE = "Nom de profil déjà utilisé";
    private static final String INVALID_URL = "URL invalide";
    private static final String INVALID_ACCOUNT_NUMBER = "Numéro de compte invalide";
    private static final String INVALID_NAME_FORMAT = "Le nom du profil ne peut dépasser 100 caractères";
    private static final String INVALID_DESC_FORMAT = "La description du profil ne peut dépasser 200 caractères";
    private static final String INVALID_URL_FORMAT = "Les urls ciblés ne peuvent dépasser 200 caractères";

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

    public CreateResponse invalidNameFormat() {
        status = HttpStatus.BAD_REQUEST;
        message = INVALID_NAME_FORMAT;
        return this;
    }

    public CreateResponse invalidDescFormat() {
        status = HttpStatus.BAD_REQUEST;
        message = INVALID_DESC_FORMAT;
        return this;
    }

    public CreateResponse invalidURLFormat() {
        status = HttpStatus.BAD_REQUEST;
        message = INVALID_URL_FORMAT;
        return this;
    }
}
