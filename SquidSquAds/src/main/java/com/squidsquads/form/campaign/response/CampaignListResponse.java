package com.squidsquads.form.campaign.response;

import com.squidsquads.form.Form;

import java.util.ArrayList;
import java.util.List;

public class CampaignListResponse extends Form {
    public List<CampaignListResponseItem> items;

    public CampaignListResponse(int status, List<CampaignListResponseItem> items) {
        super(status);
        this.items = items;
    }


}
