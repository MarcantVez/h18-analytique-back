package com.squidsquads.form.campaign.response;

import com.squidsquads.model.campaign.Campaign;
import com.squidsquads.utils.DateFormatter;
import org.springframework.http.HttpStatus;

public class CampaignDetailResponse {

    private Long campaignId;
    private String name;
    private Long accountId;
    private Long[] profileIds;
    private String creationDate;
    private String horizontalImg;
    private String verticalImg;
    private String mobileImg;
    private String redirectUrl;
    private String startDate;
    private String endDate;
    private float budget;

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
        campaignId = c.getCampaignId();
        name = c.getName();
        accountId = c.getAccountId();
        profileIds = c.getProfileIds();
        creationDate = DateFormatter.DateToString(c.getCreationDate());
        horizontalImg = c.getHorizontalImg();
        verticalImg = c.getVerticalImg();
        mobileImg = c.getMobileImg();
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

    public String getCreationDate() {
        return creationDate;
    }

    public String getHorizontalImg() {
        return horizontalImg;
    }

    public String getVerticalImg() {
        return verticalImg;
    }

    public String getMobileImg() {
        return mobileImg;
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

    public float getBudget() {
        return budget;
    }

    public String getMessage() {
        return message;
    }
}
