package com.squidsquads.form.userProfile.response;

import org.springframework.http.HttpStatus;

import java.util.List;

public class ListResponse {

    private List<ListResponseItem> profiles;
    private HttpStatus status;

    public ListResponse() {
    }

    public ListResponse ok(List<ListResponseItem> items) {
        this.status = HttpStatus.OK;
        this.profiles = items;
        return this;
    }

    public ListResponse unauthorized() {
        this.status = HttpStatus.UNAUTHORIZED;
        this.profiles = null;
        return this;
    }

    public List<ListResponseItem> getProfiles() {
        return profiles;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
