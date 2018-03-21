package com.squidsquads.form.banner.response;

import org.springframework.http.HttpStatus;

public class BannerResponse {

    private HttpStatus status;
    private String src = null;
    private String alt = null;
    private Integer campaignID = null;

    public BannerResponse() {
    }

    public BannerResponse ok(String src, String alt, Integer campaignID) {
        status = HttpStatus.OK;
        this.src = src;
        this.alt = alt;
        this.campaignID = campaignID;
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

    public Integer getCampaignID() {
        return campaignID;
    }
}
