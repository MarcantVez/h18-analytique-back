package com.squidsquads.form.banner.response;

import org.springframework.http.HttpStatus;

public class BannerResponse {

    private HttpStatus status;
    private String src = null;
    private String alt = null;
    private String redirectUrl = null;
    private Integer visitID = null;

    public BannerResponse() {
    }

    public BannerResponse ok(String src, String alt, String redirectUrl, Integer visitID) {
        status = HttpStatus.OK;
        this.src = src;
        this.alt = alt;
        this.redirectUrl = redirectUrl;
        this.visitID = visitID;
        return this;
    }

    public BannerResponse failed() {
        status = HttpStatus.BAD_REQUEST;
        return this;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getSrc() {
        return src;
    }

    public String getAlt() {
        return alt;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public Integer getVisitID() {
        return visitID;
    }

    public void setVisitID(Integer visitID) {
        this.visitID = visitID;
    }
}
