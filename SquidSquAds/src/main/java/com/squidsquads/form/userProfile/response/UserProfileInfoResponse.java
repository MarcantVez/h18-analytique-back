package com.squidsquads.form.userProfile.response;

import org.springframework.http.HttpStatus;

/**
 * @author: Dulce Cayetano
 * @Date_Of_Creation: 2018-02-06
 * @Last_modified_by:
 * @Date_of_last_modification:
 **/
public class UserProfileInfoResponse {

    private HttpStatus status;
    private String message;
    private String name;
    private String description;

    public UserProfileInfoResponse(HttpStatus status, String message, String name, String description) {
        this.status = status;
        this.message = message;
        this.name = name;
        this.description = description;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
