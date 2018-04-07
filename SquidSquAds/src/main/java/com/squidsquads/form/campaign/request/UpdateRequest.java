package com.squidsquads.form.campaign.request;

import java.math.BigDecimal;

public class UpdateRequest {

    private String name;
    private String redirectUrl;
    private Integer[] profileIds;
    private String startDate;
    private String endDate;
    private String imgHorizontal;
    private String imgVertical;
    private String imgMobile;

    public String getName() {
        return name;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public Integer[] getProfileIds() {
        return profileIds;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getImgHorizontal() {
        return imgHorizontal;
    }

    public String getImgVertical() {
        return imgVertical;
    }

    public String getImgMobile() {
        return imgMobile;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public void setProfileIds(Integer[] profileIds) {
        this.profileIds = profileIds;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setImgHorizontal(String imgHorizontal) {
        this.imgHorizontal = imgHorizontal;
    }

    public void setImgVertical(String imgVertical) {
        this.imgVertical = imgVertical;
    }

    public void setImgMobile(String imgMobile) {
        this.imgMobile = imgMobile;
    }
}
