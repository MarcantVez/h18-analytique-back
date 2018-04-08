package com.squidsquads.form.account.response;

import org.springframework.http.HttpStatus;

public class CreateResponse {

    private static final String CREATE_SUCCEEDED = "Compte créé";
    private static final String CREATE_FIELDS_MISSING = "Tous les champs requis doivent être remplis";
    private static final String CREATE_FAILED_CONFLICT = "Courriel déjà utilisé";
    private static final String CREATE_INVALID_TYPE = "Type d'administrateur invalide";
    private static final String CREATE_INVALID_BANK = "Numéro de compte de banque invalide";
    private static final String CREATE_WRONG_PASSWORDS = "Mots de passe ne sont pas identiques";
    private static final String CREATE_INVALID_EMAIL= "Le courriel ne respecte pas le format demandé (100 caractères maximum)";

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
        message = CREATE_SUCCEEDED;
        return this;
    }

    public CreateResponse fieldsMissing() {
        status = HttpStatus.BAD_REQUEST;
        message = CREATE_FIELDS_MISSING;
        return this;
    }

    public CreateResponse wrongPasswords() {
        status = HttpStatus.BAD_REQUEST;
        message = CREATE_WRONG_PASSWORDS;
        return this;
    }

    public CreateResponse emailAlreadyUsed() {
        status = HttpStatus.CONFLICT;
        message = CREATE_FAILED_CONFLICT;
        return this;
    }

    public CreateResponse invalidAdminType() {
        status = HttpStatus.BAD_REQUEST;
        message = CREATE_INVALID_TYPE;
        return this;
    }

    public CreateResponse invalidBankAccount() {
        status = HttpStatus.BAD_REQUEST;
        message = CREATE_INVALID_BANK;
        return this;
    }

    public CreateResponse invalidEmailFormat() {
        status = HttpStatus.BAD_REQUEST;
        message = CREATE_INVALID_EMAIL;
        return this;
    }
}
