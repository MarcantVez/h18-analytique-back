package com.squidsquads.form.userProfile.response;

import org.springframework.http.HttpStatus;

public class CreateModifyResponse {

    private static final String SUCCESS = "Le profil d'utilisateur a été sauvegardé";
    private static final String MISSING_FIELDS = "Tous les champs requis doivent être remplis";
    private static final String INVALID_URL = "URL invalide";
    private static final String INVALID_ACCOUNT_NUMBER = "Numéro de compte invalide";
    private static final String PROFILE_NOT_FOUND = "Le profil utilisateur n'existe pas";

    private HttpStatus status;
    private String message;

    public CreateModifyResponse() {
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public CreateModifyResponse success() {
        status = HttpStatus.OK;
        message = SUCCESS;
        return this;
    }

    public CreateModifyResponse fieldsMissing() {
        status = HttpStatus.BAD_REQUEST;
        message = MISSING_FIELDS;
        return this;
    }

    public CreateModifyResponse invalidAccountNumber() {
        status = HttpStatus.INTERNAL_SERVER_ERROR;
        message = INVALID_ACCOUNT_NUMBER;
        return this;
    }

    public CreateModifyResponse invalidURL() {
        status = HttpStatus.BAD_REQUEST;
        message = INVALID_URL;
        return this;
    }

    public CreateModifyResponse notFound() {
        status = HttpStatus.NOT_FOUND;
        message = PROFILE_NOT_FOUND;
        return this;
    }
}
