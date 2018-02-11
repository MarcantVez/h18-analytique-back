package com.squidsquads.form.campaign.response;

import com.squidsquads.model.campaign.Campaign;
import com.squidsquads.utils.DateFormatter;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;

public class CampaignDetailResponse {

    private Long campaignId;
    private String name;
    private Long accountId;
    private Long[] profileIds;
    private String dateCreated;
    private String imgHorizontal;
    private String imgVertical;
    private String imgMobile;
    private String redirectUrl;
    private String startDate;
    private String endDate;
    private BigDecimal budget;

    private HttpStatus status;
    private String message;

    private static final String CREATE_FIELDS_MISSING = "Tous les champs requis doivent être remplis";

    public CampaignDetailResponse() {
    }

    public CampaignDetailResponse unauthorized() {
        status = HttpStatus.UNAUTHORIZED;
        return this;
    }

    public CampaignDetailResponse notFound() {
        status = HttpStatus.NOT_FOUND;
        return this;
    }

    public CampaignDetailResponse fieldsMissing() {
        status = HttpStatus.BAD_REQUEST;
        message = CREATE_FIELDS_MISSING;
        return this;
    }

    public CampaignDetailResponse ok(Campaign c) {
        campaignId = c.getCampaignID();
        name = c.getName();
        accountId = c.getAccountID();
        profileIds = c.getProfileIds();
        dateCreated = DateFormatter.DateToString(c.getCreationDate());
        imgHorizontal = c.getImgHorizontal();
        imgVertical = c.getImgVertical();
        imgMobile = c.getImgMobile();
        redirectUrl = c.getRedirectUrl();
        startDate = DateFormatter.DateToString(c.getStartDate());
        endDate = DateFormatter.DateToString(c.getEndDate());
        budget = c.getBudget();
        status = HttpStatus.OK;
        return this;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public Long getCampaignId() {
        return campaignId;
    }

    public String getName() {
        return name;
    }

    public Long getAccountId() {
        return accountId;
    }

    public Long[] getProfileIds() {
        return profileIds;
    }

    public String getDateCreated() {
        return dateCreated;
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

    public String getRedirectUrl() {
        return redirectUrl;
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

    public String getMessage() {
        return message;
    }
}
