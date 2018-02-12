package com.squidsquads.form.campaign.request;

import java.math.BigDecimal;

public class CreateRequest {

    private String name;
    private String redirectUrl;
    private Long[] profileIds;
    private String startDate;
    private String endDate;
    private BigDecimal budget;
    private String imgHorizontal;
    private String imgVertical;
    private String imgMobile;

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

    public BigDecimal getBudget() {
        return budget;
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
}
