package com.squidsquads.form.visit.response;

import org.springframework.http.HttpStatus;

import java.util.List;

public class BannerListResponse {

    private List<ListBannerResponseItem> banners;
    private HttpStatus status;

    public BannerListResponse() {
    }

    public BannerListResponse ok(List<ListBannerResponseItem> banners) {
        status = HttpStatus.OK;
        this.banners = banners;
        return this;
    }

    public BannerListResponse failed() {
        status = HttpStatus.INTERNAL_SERVER_ERROR;
        banners = null;
        return this;
    }

    public List<ListBannerResponseItem> getBanners() {
        return banners;
    }

    public HttpStatus getStatus() {
        return status;
    }

}
