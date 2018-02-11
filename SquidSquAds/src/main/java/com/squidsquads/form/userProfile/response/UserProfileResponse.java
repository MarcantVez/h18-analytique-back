package com.squidsquads.form.userProfile.response;

import org.springframework.http.HttpStatus;

/**
 * @author: Dulce Cayetano
 * @Date_Of_Creation: 2018-02-06
 * @Last_modified_by:
 * @Date_of_last_modification:
 **/
public class UserProfileResponse {

    private static final String SUCCESS = "Profil existant";
    private static final String MISSING_FIELDS = "Tous les champs requis doivent être remplis";
    private static final String INVALID_URL = "URL invalide";
    private static final String INVALID_ACCOUNT_NUMBER = "Numéro de compte invalide";

    private HttpStatus status;
    private String message;

    public UserProfileResponse() {
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public UserProfileResponse success() {
        status = HttpStatus.OK;
        message = SUCCESS;
        return this;
    }

    public UserProfileResponse fieldsMissing() {
        status = HttpStatus.BAD_REQUEST;
        message = MISSING_FIELDS;
        return this;
    }

    public UserProfileResponse invalidAccountNumber() {
        status = HttpStatus.BAD_REQUEST;
        message = INVALID_ACCOUNT_NUMBER;
        return this;
    }

    public UserProfileResponse invalidURL() {
        status = HttpStatus.BAD_REQUEST;
        message = INVALID_URL;
        return this;
    }
}
