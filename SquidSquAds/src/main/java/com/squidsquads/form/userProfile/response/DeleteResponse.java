package com.squidsquads.form.userProfile.response;

import org.springframework.http.HttpStatus;

public class DeleteResponse {

    private static final String PROFILE_NOT_FOUND = "Le profil utilisateur n'existe pas";
    private static final String SUCCESS = "Le profil a été supprimé avec succès";

    private HttpStatus status;
    private String message;

    public DeleteResponse() {
    }

    public DeleteResponse unauthorized() {
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
        return this;
    }

    public DeleteResponse notFound() {
        this.status = HttpStatus.NOT_FOUND;
        this.message = PROFILE_NOT_FOUND;
        return this;
    }

    public DeleteResponse ok() {
        this.status = HttpStatus.OK;
        this.message = SUCCESS;
        return this;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
