package com.squidsquads.unit.service.campaign;

import com.squidsquads.form.campaign.request.CreateRequest;
import com.squidsquads.form.campaign.request.UpdateRequest;
import com.squidsquads.unit.service.ServiceTestHelper;

import java.math.BigDecimal;

public class CampaignTestHelper extends ServiceTestHelper {

    ///////////////////////
    // Campaign : Create //
    ///////////////////////

    public CreateRequest getCreateRequest() {
        return buildCreateRequest(CAMPAIGN_NAME, REDIRECT_URL, PROFILE_IDS, START_DATE, END_DATE, HOR_URL, VER_URL, MOB_URL);
    }

    public CreateRequest getCreateRequestWhereOneParameterIsNullOrEmpty() {
        return buildCreateRequest(CAMPAIGN_NAME, " ", PROFILE_IDS, START_DATE, END_DATE, HOR_URL, VER_URL, MOB_URL);
    }

    public CreateRequest getCreateRequestWithNameTooLong() {
        return buildCreateRequest("Une Campagne Avec Un Nom Tellement Long Qu'on Se Demande Vraiment Si Les Gens En Marketing Sont Payés Au Mot.", REDIRECT_URL, PROFILE_IDS, START_DATE, END_DATE, HOR_URL, VER_URL, MOB_URL);
    }

    public CreateRequest getCreateRequestWithHorizontalImgURlTooLong() {
        return buildCreateRequest(CAMPAIGN_NAME, REDIRECT_URL, PROFILE_IDS, START_DATE, END_DATE, "ASGDHGFGSWGEasdasdedafadSHNBSAFSVDBSEWEFSDDBGFNDJTT#wsgdfvfadfewrgesthrfdsawgeshtgzsfeew2134ewqesgredzfs", VER_URL, MOB_URL);
    }

    public CreateRequest getCreateRequestWithVerticalImgURlTooLong() {
        return buildCreateRequest(CAMPAIGN_NAME, REDIRECT_URL, PROFILE_IDS, START_DATE, END_DATE, HOR_URL, "ASGDHGFGSWGEasdasdedafadSHNBSAFSVDBSEWEFSDDBGFNDJTT#wsgdfvfadfewrgesthrfdsawgeshtgzsfeew2134ewqesgredzfs", MOB_URL);
    }

    public CreateRequest getCreateRequestWithMobileImgURlTooLong() {
        return buildCreateRequest(CAMPAIGN_NAME, REDIRECT_URL, PROFILE_IDS, START_DATE, END_DATE, HOR_URL, VER_URL, "ASGDHGFGSWGEasdasdedafadSHNBSAFSVDBSEWEFSDDBGFNDJTT#wsgdfvfadfewrgesthrfdsawgeshtgzsfeew2134ewqesgredzfs");
    }

    public CreateRequest getCreateRequestWithRedirectURlTooLong() {
        return buildCreateRequest(CAMPAIGN_NAME, "ASGDHGFGSWGEasdasdedafadSHNBSAFSVDBSEWEFSDDBGFNDJTT#wsgdfvfadfewrgesthrfdsawgeshtgzsfeew2134ewqesgredzfs", PROFILE_IDS, START_DATE, END_DATE, HOR_URL, VER_URL, MOB_URL);
    }

    private CreateRequest buildCreateRequest(String name, String redurl, Integer[] ids, String start, String end,
                                             String hor, String ver, String mob) {
        CreateRequest request = new CreateRequest();
        request.setName(name);
        request.setRedirectUrl(redurl);
        request.setProfileIds(ids);
        request.setStartDate(start);
        request.setEndDate(end);
        request.setImgHorizontal(hor);
        request.setImgVertical(ver);
        request.setImgMobile(mob);
        return request;
    }

    ///////////////////////
    // Campaign : UPDATE //
    ///////////////////////

    private UpdateRequest buildUpdateRequest(String name, String redurl, Integer[] ids, String start, String end,
                                             String hor, String ver, String mob) {
        UpdateRequest request = new UpdateRequest();
        request.setName(name);
        request.setRedirectUrl(redurl);
        request.setProfileIds(ids);
        request.setStartDate(start);
        request.setEndDate(end);
        request.setImgHorizontal(hor);
        request.setImgVertical(ver);
        request.setImgMobile(mob);
        return request;
    }

    public UpdateRequest getUpdateRequest() {
        return buildUpdateRequest(CAMPAIGN2_NAME, REDIRECT_URL, PROFILE_IDS, START_DATE, END_DATE, HOR_URL, VER_URL, MOB_URL);
    }

    public UpdateRequest getUpdateRequestWhereOneParameterIsNullOrEmpty() {
        return buildUpdateRequest(CAMPAIGN2_NAME, " ", PROFILE_IDS, START_DATE, END_DATE, HOR_URL, VER_URL, MOB_URL);
    }

    public UpdateRequest getUpdateRequestWithNameTooLong() {
        return buildUpdateRequest("Une Campagne Avec Un Nom Tellement Long Qu'on Se Demande Vraiment Si Les Gens En Marketing Sont Payés Au Mot.", REDIRECT_URL, PROFILE_IDS, START_DATE, END_DATE, HOR_URL, VER_URL, MOB_URL);
    }

    public UpdateRequest getUpdateRequestWithHorizontalImgURlTooLong() {
        return buildUpdateRequest(CAMPAIGN2_NAME, REDIRECT_URL, PROFILE_IDS, START_DATE, END_DATE, "ASGDHGFGSWGEasdasdedafadSHNBSAFSVDBSEWEFSDDBGFNDJTT#wsgdfvfadfewrgesthrfdsawgeshtgzsfeew2134ewqesgredzfs", VER_URL, MOB_URL);
    }

    public UpdateRequest getUpdateRequestWithVerticalImgURlTooLong() {
        return buildUpdateRequest(CAMPAIGN2_NAME, REDIRECT_URL, PROFILE_IDS, START_DATE, END_DATE, HOR_URL, "ASGDHGFGSWGEasdasdedafadSHNBSAFSVDBSEWEFSDDBGFNDJTT#wsgdfvfadfewrgesthrfdsawgeshtgzsfeew2134ewqesgredzfs", MOB_URL);
    }

    public UpdateRequest getUpdateRequestWithMobileImgURlTooLong() {
        return buildUpdateRequest(CAMPAIGN2_NAME, REDIRECT_URL, PROFILE_IDS, START_DATE, END_DATE, HOR_URL, VER_URL, "ASGDHGFGSWGEasdasdedafadSHNBSAFSVDBSEWEFSDDBGFNDJTT#wsgdfvfadfewrgesthrfdsawgeshtgzsfeew2134ewqesgredzfs");
    }

    public UpdateRequest getUpdateRequestWithRedirectURlTooLong() {
        return buildUpdateRequest(CAMPAIGN2_NAME, "ASGDHGFGSWGEasdasdedafadSHNBSAFSVDBSEWEFSDDBGFNDJTT#wsgdfvfadfewrgesthrfdsawgeshtgzsfeew2134ewqesgredzfs", PROFILE_IDS, START_DATE, END_DATE, HOR_URL, VER_URL, MOB_URL);
    }
}
