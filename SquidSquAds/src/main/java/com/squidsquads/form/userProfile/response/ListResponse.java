package com.squidsquads.form.userProfile.response;

import org.springframework.http.HttpStatus;

import java.util.List;

public class ListResponse {

    private List<ListResponseItem> profiles;
    private HttpStatus status;

    public ListResponse() {
    }

    public ListResponse ok(List<ListResponseItem> profiles) {
        status = HttpStatus.OK;
        this.profiles = profiles;
        return this;
    }

    public ListResponse unauthorized() {
        status = HttpStatus.UNAUTHORIZED;
        profiles = null;
        return this;
    }

    public List<ListResponseItem> getProfiles() {
        return profiles;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
