package com.squidsquads.form.account.response;

import org.springframework.http.HttpStatus;

import java.util.List;

public class BannerListResponse {

    private HttpStatus status;
    private Long webSiteAdminID = null;
    private Long horID = null;
    private Long verID = null;
    private Long mobID = null;

    public BannerListResponse() {
    }

    public BannerListResponse ok(Long webSiteAdminID, Long horID, Long verID, Long mobID) {
        status = HttpStatus.OK;
        this.webSiteAdminID = webSiteAdminID;
        this.horID = horID;
        this.verID = verID;
        this.mobID = mobID;
        return this;
    }

    public BannerListResponse failed() {
        status = HttpStatus.INTERNAL_SERVER_ERROR;
        return this;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public Long getWebSiteAdminID() {
        return webSiteAdminID;
    }

    public Long getHorID() {
        return horID;
    }

    public Long getVerID() {
        return verID;
    }

    public Long getMobID() {
        return mobID;
    }
}
