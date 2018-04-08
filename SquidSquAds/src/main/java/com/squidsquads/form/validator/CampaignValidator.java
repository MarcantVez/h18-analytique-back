package com.squidsquads.form.validator;

import com.squidsquads.form.campaign.request.CreateRequest;
import com.squidsquads.form.campaign.request.UpdateRequest;

public class CampaignValidator {
    private static final int NAME_MAX_LENTGH = 100;
    private static final int IMG_URL_MAX_LENTGH = 100;
    private static final int REDIRECT_URL_MAX_LENTGH = 100;

    public static boolean isCreateRequestComplete(CreateRequest req) {
        // Champs requis pour une Campagne
        return CommonValidator.notEmpty(req.getName()) &&
                CommonValidator.notEmpty(req.getRedirectUrl()) &&
                CommonValidator.notEmpty(req.getStartDate()) &&
                CommonValidator.notEmpty(req.getEndDate()) &&
                CommonValidator.notEmpty(req.getImgHorizontal()) &&
                CommonValidator.notEmpty(req.getImgVertical()) &&
                CommonValidator.notEmpty(req.getImgMobile());
    }

    public static boolean isUpdateRequestComplete(UpdateRequest req) {
        // Champs requis pour une Campagne
        return CommonValidator.notEmpty(req.getName()) &&
                CommonValidator.notEmpty(req.getRedirectUrl()) &&
                CommonValidator.notEmpty(req.getStartDate()) &&
                CommonValidator.notEmpty(req.getEndDate()) &&
                CommonValidator.notEmpty(req.getImgHorizontal()) &&
                CommonValidator.notEmpty(req.getImgVertical()) &&
                CommonValidator.notEmpty(req.getImgMobile());
    }

    public static boolean isCampaignNameValid(String name) {
        return name.length() <= NAME_MAX_LENTGH;
    }

    public static boolean isImgUrlValid(String img) {
        return img.length() <= IMG_URL_MAX_LENTGH;
    }

    public static boolean isRedirectUrlValid(String url) {
        return url.length() <= IMG_URL_MAX_LENTGH;
    }
}
