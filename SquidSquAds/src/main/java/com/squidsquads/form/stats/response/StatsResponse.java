package com.squidsquads.form.stats.response;

import org.springframework.http.HttpStatus;

public class StatsResponse {
    private HttpStatus status;
    // TODO add content to class

    public StatsResponse(){}

    public StatsResponse ok() {
        status = HttpStatus.OK;
        return this;
    }

    public StatsResponse unauthorised() {
        status = HttpStatus.UNAUTHORIZED;
        return this;
    }


    // TODO remove once service is implemented
    public StatsResponse todo() {
        status = HttpStatus.NOT_IMPLEMENTED;
        return this;
    }

    public HttpStatus getStatus() {
        return status;
    }
}

