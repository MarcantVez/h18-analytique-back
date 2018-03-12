package com.squidsquads.form.userProfile.response;

import org.springframework.http.HttpStatus;

public class DeleteResponse {

    private static final String PROFILE_NOT_FOUND = "Le profil utilisateur n'existe pas";
    private static final String SUCCESS = "Le profil utilisateur a été supprimé";
    private static final String INVALID_ACCOUNT_NUMBER = "Numéro de compte invalide";

    private HttpStatus status;
    private String message;

    public DeleteResponse() {
    }

    public DeleteResponse ok() {
        status = HttpStatus.OK;
        message = SUCCESS;
        return this;
    }

    public DeleteResponse notFound() {
        status = HttpStatus.NOT_FOUND;
        message = PROFILE_NOT_FOUND;
        return this;
    }

    public DeleteResponse failed() {
        status = HttpStatus.INTERNAL_SERVER_ERROR;
        message = INVALID_ACCOUNT_NUMBER;
        return this;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
