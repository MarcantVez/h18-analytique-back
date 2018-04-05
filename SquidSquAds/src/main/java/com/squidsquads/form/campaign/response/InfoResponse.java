package com.squidsquads.form.campaign.response;

import com.squidsquads.model.Campaign;
import com.squidsquads.utils.DateFormatter;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;

public class InfoResponse {

    private String name;
    private Integer accountId;
    private Integer[] profileIds;
    private String dateCreated;
    private String imgHorizontal;
    private String imgVertical;
    private String imgMobile;
    private String redirectUrl;
    private String startDate;
    private String endDate;

    private HttpStatus status;
    private String message;

    private final String CREATE_FIELDS_MISSING = "Tous les champs requis doivent être remplis";

    public InfoResponse() {
    }

    public InfoResponse ok(Campaign c) {
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
        status = HttpStatus.OK;
        return this;
    }

    public InfoResponse notFound() {
        status = HttpStatus.NOT_FOUND;
        return this;
    }

    public InfoResponse failed() {
        status = HttpStatus.INTERNAL_SERVER_ERROR;
        return this;
    }

    public InfoResponse fieldsMissing() {
        status = HttpStatus.BAD_REQUEST;
        message = CREATE_FIELDS_MISSING;
        return this;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public Integer[] getProfileIds() {
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

    public String getMessage() {
        return message;
    }
}
