package com.squidsquads.form.campaign.request;

public class CreateRequest {

    private String name;
    private String redirectUrl;
    private Long[] profileIds;
    private String startDate;
    private String endDate;
    private float budget;
    private String imageHor;
    private String imageVer;
    private String imageMob;

    public String getName() {
        return name;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public Long[] getProfileIds() {
        return profileIds;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public float getBudget() {
        return budget;
    }

    public String getImageHor() {
        return imageHor;
    }

    public String getImageVer() {
        return imageVer;
    }

    public String getImageMob() {
        return imageMob;
    }
}
