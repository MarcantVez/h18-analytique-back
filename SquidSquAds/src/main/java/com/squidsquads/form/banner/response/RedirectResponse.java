package com.squidsquads.form.banner.response;

import org.springframework.http.HttpStatus;

public class RedirectResponse {
    private HttpStatus status;
    private String redirectUrl;

    public RedirectResponse() {
    }

    public RedirectResponse redirect(String url){
        this.status = HttpStatus.FOUND;
        this.redirectUrl = url;
        return this;
    }

    public RedirectResponse failed(){
        this.status = HttpStatus.BAD_REQUEST;
        this.redirectUrl = "";
        return this;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }
}