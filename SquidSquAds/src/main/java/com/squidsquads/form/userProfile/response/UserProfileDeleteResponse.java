package com.squidsquads.form.userProfile.response;

import org.springframework.http.HttpStatus;

/**
 * @author: Dulce Cayetano
 * @Date_Of_Creation: 2018-02-10
 * @Last_modified_by:
 * @Date_of_last_modification:
 **/
public class UserProfileDeleteResponse {

    public static final String PROFILE_NOT_FOUND = "Le profil utilisateur n'existe pas";
    public static final String SUCCESS = "Le profil a été supprimé avec  succès";

    private HttpStatus status;
    private String message;

    public UserProfileDeleteResponse() {
    }

    public UserProfileDeleteResponse profileNotFound() {
        this.status = HttpStatus.NOT_FOUND;
        this.message = PROFILE_NOT_FOUND;
        return this;
    }

    public UserProfileDeleteResponse siteNotFound() {
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
        return this;
    }

    public UserProfileDeleteResponse success() {
        this.status = HttpStatus.OK;
        this.message = SUCCESS;
        return this;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
