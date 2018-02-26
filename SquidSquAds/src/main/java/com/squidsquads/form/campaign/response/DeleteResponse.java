package com.squidsquads.form.campaign.response;

import org.springframework.http.HttpStatus;

public class DeleteResponse {

    public static final String CAMPAGNE_NOT_FOUND = "La campagne n'existe pas";
    public static final String SUCCESS = "La campagne a été supprimée avec succès";

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
        message = CAMPAGNE_NOT_FOUND;
        return this;
    }

    public DeleteResponse failed() {
        status = HttpStatus.INTERNAL_SERVER_ERROR;
        return this;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
