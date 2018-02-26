package com.squidsquads.form.visit.response;

import org.springframework.http.HttpStatus;

public class VisitResponse {
    private HttpStatus status;

    public VisitResponse() {
    }

    public VisitResponse ok() {
        status = HttpStatus.OK;
        return this;
    }

    public VisitResponse failed() {
        status = HttpStatus.BAD_REQUEST;
        return this;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
