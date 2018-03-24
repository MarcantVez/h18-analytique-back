package com.squidsquads.form.payment.response;

import org.springframework.http.HttpStatus;

public class CreateResponse {

    private static final String SUCCESS = "Le paiement a été créé";
    private static final String NO_ROYALTY_FOUND = "Impossible de créer un paiement sans redevances";
    private static final String MISSING_FIELDS = "Tous les champs requis doivent être remplis";
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

    public CreateResponse noAmount() {
        status = HttpStatus.OK;
        message = NO_ROYALTY_FOUND;
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


}
