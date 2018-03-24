package com.squidsquads.form.payment.response;

import org.springframework.http.HttpStatus;

public class AmountDueResponse {
    private HttpStatus status;
    private Float fromViews;
    private Float fromTargetedViews;
    private Float fromClicks;
    private Float fromTargetedClicks;
    private Float totalAmount;

    public AmountDueResponse() {
    }

    public AmountDueResponse ok(Float fromViews, Float fromTargetedViews, Float fromClicks, Float fromTargetedClicks){
        this.fromViews = fromViews;
        this.fromTargetedViews = fromTargetedViews;
        this.fromClicks = fromClicks;
        this.fromTargetedClicks = fromTargetedClicks;
        this.totalAmount = fromClicks+fromViews+fromTargetedClicks+fromTargetedViews;
        this.status = HttpStatus.OK;
        return this;
    }

    public AmountDueResponse failed(){
        this.status = HttpStatus.BAD_REQUEST;
        return this;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public Float getFromViews() {
        return fromViews;
    }

    public Float getFromTargetedViews() {
        return fromTargetedViews;
    }

    public Float getFromClicks() {
        return fromClicks;
    }

    public Float getFromTargetedClicks() {
        return fromTargetedClicks;
    }

    public Float getTotalAmount() {
        return totalAmount;
    }
}
