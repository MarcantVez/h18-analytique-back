package com.squidsquads.form.validator;

import com.squidsquads.form.campaign.request.CreateRequest;

public class CampaignCreateValidator {

    public static boolean isCreateRequestComplete(CreateRequest req) {
        // Champs requis pour une Campagne
        return  CommonValidator.notEmpty(req.getName()) &&
                CommonValidator.notEmpty(req.getRedirectUrl()) &&
                CommonValidator.notEmpty(req.getStartDate()) &&
                CommonValidator.notEmpty(req.getEndDate()) &&
                CommonValidator.isPositive(req.getBudget()) &&
                CommonValidator.notEmpty(req.getImageHor()) &&
                CommonValidator.notEmpty(req.getImageVer()) &&
                CommonValidator.notEmpty(req.getImageMob());
    }
}
