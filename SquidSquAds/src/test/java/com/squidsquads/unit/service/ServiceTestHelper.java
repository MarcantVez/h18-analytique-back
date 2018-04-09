package com.squidsquads.unit.service;

import com.squidsquads.form.account.request.CreateRequest;
import com.squidsquads.form.account.request.LoginRequest;
import com.squidsquads.form.account.request.ResetPasswordRequest;
import com.squidsquads.model.*;
import com.squidsquads.utils.DateFormatter;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ServiceTestHelper {

    protected final int ACCOUNT_ID = 1;
    protected final int CAMPAIGN_ID = 4;
    protected final int CAMPAIGN2_ID = 5;

    protected final String EMAIL_PUB = "ads@ads.com";
    protected final String EMAIL_WEB = "web@web.com";

    protected final String CLEAR_PASSWORD = "Password1!";
    protected final String HASHED_PASSWORD = "$2a$10$qyyJ6RAOdVdLzrF1EFsgkuE5/2P9IkK/G4mZoGoDyWrV7bm8pz.lu";

    protected final String BANK_ACCOUNT = "00012345";
    protected final String DOMAIN = "https://www.google.ca";

    protected final String CAMPAIGN_NAME = "Ma campagne ultra!";
    protected final String CAMPAIGN2_NAME = "Ma campagne améliorée!";
    protected final String REDIRECT_URL = "https://www.facebook.com";
    protected final Integer[] PROFILE_IDS = new Integer[]{41, 42, 43};
    protected final String START_DATE = "2018-01-01";
    protected final String END_DATE = "2019-01-01";
    protected final String HOR_URL = "https://www.horizontal.image";
    protected final String VER_URL = "https://www.vertical.image";
    protected final String MOB_URL = "https://www.mobile.image";

    protected final String USER_PROFILE_NAME = "Les personnes naives";
    protected final String USER_PROFILE_DESCRIPTION = "On va se faire de l'oseille sur ces gens";
    protected final String[] USER_PROFILE_URLS = new String[]{"https://www.facebook.com", "https://www.google.com"};
    protected final String[] INVALID_USER_PROFILE_URLS = new String[]{"https://www.google.ca/search?rlz=1C1CHBF_frCA746CA746&ei=0pfJWpjvMcaz5gKc-IWYBw&q=We%27re+no+strangers+to+love+You+know+the+rules+and+so+do+I+A+full+commitment%27s+what+I%27m+thinking+of+You+wouldn%27t+get+this+from+any+other+guy+I+just+wanna+tell+you+how+I%27m+feeling+Gotta+make+you+understand+Never+gonna+give+you+up+Never+gonna+let+you+down+Never+gonna+run+around+and+desert+you+Never+gonna+make+you+cry+Never+gonna+say+goodbye+Never+gonna+tell+a+lie+and+hurt+you&oq=We%27re+no+strangers+to+love+You+know+the+rules+and+so+do+I+A+full+commitment%27s+what+I%27m+thinking+of+You+wouldn%27t+get+this+from+any+other+guy+I+just+wanna+tell+you+how+I%27m+feeling+Gotta+make+you+understand+Never+gonna+give+you+up+Never+gonna+let+you+down+Never+gonna+run+around+and+desert+you+Never+gonna+make+you+cry+Never+gonna+say+goodbye+Never+gonna+tell+a+lie+and+hurt+you&gs_l=psy-ab.3..0i71k1l8.108037.108037.0.108369.1.1.0.0.0.0.0.0..0.0....0...1.1.64.psy-ab..1.0.0....0.-76x8BOE6BE", "https://www.google.com"};

    protected final int SITE_ID = 1;
    protected final int SITE_PROFILE_ID = 1;
    protected final String SITE_URL = "https://www.facebook.com";

    /////////////
    // Account //
    /////////////

    public Account getAccountPub() {
        Account pub = new Account(AdminType.PUB.name(), EMAIL_PUB, HASHED_PASSWORD, BANK_ACCOUNT);
        pub.setAccountID(ACCOUNT_ID);
        return pub;
    }

    public Account getAccountWeb() {
        Account web = new Account(AdminType.WEB.name(), EMAIL_WEB, HASHED_PASSWORD, BANK_ACCOUNT);
        web.setAccountID(ACCOUNT_ID);
        return web;
    }

    ////////////////////
    // Web Site Admin //
    ////////////////////

    public WebSiteAdmin getWebSiteAdmin() {
        WebSiteAdmin webSiteAdmin = new WebSiteAdmin(ACCOUNT_ID, DOMAIN);
        webSiteAdmin.setWebSiteAdminID(2);
        return webSiteAdmin;
    }

    //////////////
    // Campaign //
    //////////////

    public Campaign getCampaign() {
        Campaign campaign = new Campaign();
        campaign.setCampaignID(CAMPAIGN_ID);
        campaign.setAccountID(ACCOUNT_ID);
        campaign.setName(CAMPAIGN_NAME);
        campaign.setProfileIds(PROFILE_IDS);
        campaign.setCreationDate(new Date());
        campaign.setRedirectUrl(REDIRECT_URL);
        campaign.setStartDate(DateFormatter.StringToDate(START_DATE));
        campaign.setEndDate(DateFormatter.StringToDate(END_DATE));
        campaign.setImgHorizontal(HOR_URL);
        campaign.setImgVertical(VER_URL);
        campaign.setImgMobile(MOB_URL);
        return campaign;
    }

    public Campaign getCampaign2() {
        Campaign campaign = getCampaign();
        campaign.setAccountID(2);
        campaign.setCampaignID(CAMPAIGN2_ID);
        return campaign;
    }

    public UserProfile getUserProfile()
    {
        UserProfile userProfile = new UserProfile();
        userProfile.setProfileID(ACCOUNT_ID);
        userProfile.setAccountID(41);
        userProfile.setName(USER_PROFILE_NAME);
        userProfile.setDescription(USER_PROFILE_DESCRIPTION);
        return userProfile;
    }

    public Site getSite()
    {
        Site site = new Site();
        site.setSiteID(SITE_ID);
        site.setUserProfileID(SITE_PROFILE_ID);
        site.setUrl(SITE_URL);
        return site;
    }
}
