package com.squidsquads.form.visit.response;

import org.springframework.http.HttpStatus;

public class CookieCreationResponse {
    private HttpStatus status;
    private String fingerprint;

    public CookieCreationResponse() {
    }

    public CookieCreationResponse ok(String fingerprint) {
        this.status = HttpStatus.OK;
        this.fingerprint = fingerprint;
        return this;
    }

    public CookieCreationResponse failed() {
        this.status = HttpStatus.BAD_REQUEST;
        return this;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getFingerprint() {
        return fingerprint;
    }
}
