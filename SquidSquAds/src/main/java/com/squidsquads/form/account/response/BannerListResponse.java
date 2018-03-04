package com.squidsquads.form.account.response;

import org.springframework.http.HttpStatus;

import java.util.List;

public class BannerListResponse {

    private HttpStatus status;
    private Integer webSiteAdminID = null;
    private Integer horID = null;
    private Integer verID = null;
    private Integer mobID = null;

    public BannerListResponse() {
    }

    public BannerListResponse ok(Integer webSiteAdminID, Integer horID, Integer verID, Integer mobID) {
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

    public Integer getWebSiteAdminID() {
        return webSiteAdminID;
    }

    public Integer getHorID() {
        return horID;
    }

    public Integer getVerID() {
        return verID;
    }

    public Integer getMobID() {
        return mobID;
    }
}
