package com.squidsquads.form.userProfile.response;

import org.springframework.http.HttpStatus;

public class CreateUserProfileResponse {

    private static final String SUCCESS = "Le profil utilisateur a été crée";
    private static final String MISSING_FIELDS = "Tous les champs requis doivent être remplis";
    private static final String EXISTING_PROFILE = "Nom de profil déjà utilisé";
    private static final String INVALID_URL = "URL invalide";
    private static final String INVALID_ACCOUNT_NUMBER = "Numéro de compte invalide";

    private HttpStatus status;
    private String message;

    public CreateUserProfileResponse() {
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public CreateUserProfileResponse success() {
        status = HttpStatus.OK;
        message = SUCCESS;
        return this;
    }

    public CreateUserProfileResponse fieldsMissing() {
        status = HttpStatus.BAD_REQUEST;
        message = MISSING_FIELDS;
        return this;
    }

    public CreateUserProfileResponse profileAlreadyExists() {
        status = HttpStatus.CONFLICT;
        message = EXISTING_PROFILE;
        return this;
    }

    public CreateUserProfileResponse invalidAccountNumber() {
        status = HttpStatus.INTERNAL_SERVER_ERROR;
        message = INVALID_ACCOUNT_NUMBER;
        return this;
    }

    public CreateUserProfileResponse invalidURL() {
        status = HttpStatus.BAD_REQUEST;
        message = INVALID_URL;
        return this;
    }
}
