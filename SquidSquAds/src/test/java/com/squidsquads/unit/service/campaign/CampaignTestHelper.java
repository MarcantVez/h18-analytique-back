package com.squidsquads.unit.service.campaign;

import com.squidsquads.form.campaign.request.CreateRequest;
import com.squidsquads.unit.service.ServiceTestHelper;

import java.math.BigDecimal;

public class CampaignTestHelper extends ServiceTestHelper {

    ///////////////////////
    // Campaign : Create //
    ///////////////////////

    public CreateRequest getCreateRequest() {
        return buildCreateRequest(CAMPAIGN_NAME, REDIRECT_URL, PROFILE_IDS, START_DATE, END_DATE, BUDGET, HOR_URL, VER_URL, MOB_URL);
    }

    public CreateRequest getCreateRequestWhereOneParameterIsNullOrEmpty() {
        return buildCreateRequest(CAMPAIGN_NAME, " ", PROFILE_IDS, START_DATE, END_DATE, BUDGET, HOR_URL, VER_URL, MOB_URL);
    }

    public CreateRequest getCreateRequestWhereBudgetIsNegative() {
        return buildCreateRequest(CAMPAIGN_NAME, REDIRECT_URL, PROFILE_IDS, START_DATE, END_DATE, BigDecimal.valueOf(-20), HOR_URL, VER_URL, MOB_URL);
    }

    private CreateRequest buildCreateRequest(String name, String redurl, Integer[] ids, String start, String end,
                                             BigDecimal budget, String hor, String ver, String mob) {
        CreateRequest request = new CreateRequest();
        request.setName(name);
        request.setRedirectUrl(redurl);
        request.setProfileIds(ids);
        request.setStartDate(start);
        request.setEndDate(end);
        request.setBudget(budget);
        request.setImgHorizontal(hor);
        request.setImgVertical(ver);
        request.setImgMobile(mob);
        return request;
    }
}
