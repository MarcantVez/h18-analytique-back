package com.squidsquads.unit.service;

import com.squidsquads.model.account.Account;
import com.squidsquads.model.account.WebSiteAdmin;
import com.squidsquads.model.campaign.Campaign;
import com.squidsquads.model.campaign.CampaignProfile;
import com.squidsquads.model.profile.UserProfile;
import com.squidsquads.repository.account.AccountRepository;
import com.squidsquads.repository.account.WebSiteAdminRepository;
import com.squidsquads.repository.campaign.CampaignProfileRepository;
import com.squidsquads.repository.campaign.CampaignRepository;
import com.squidsquads.repository.userProfile.UserProfileRepository;
import com.squidsquads.service.account.AccountService;
import com.squidsquads.service.account.WebSiteAdminService;
import com.squidsquads.service.campaign.CampaignService;
import org.junit.BeforeClass;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AbstractPubAdminTest {
    static CampaignService campaignService;
    static CampaignRepository campaignRepository;
    static CampaignProfileRepository campaignProfileRepository;
    static UserProfileRepository userProfileRepository;

    static Campaign campaign1, campaign2, campaign3, campaign4;
    static UserProfile profile1, profile2;
    static List<UserProfile> profileList;
    static CampaignProfile campaignProfile1, campaignProfile2, campaignProfile3, campaignProfile4;
    static List<Campaign> campaignList;
    static List<CampaignProfile> campaignProfilesByProfile, campaignProfilesByCampaign;


    @BeforeClass
    public static void init() {
        // Instance des services
        campaignService = new CampaignService();

        campaignList = new ArrayList<>();
        campaignProfilesByProfile = new ArrayList<>();
        campaignProfilesByCampaign = new ArrayList<>();
        profileList = new ArrayList<>();

        // Dummy Campaigns
        campaign1 = new Campaign(
                1L, 2L, "test1", "http://testH.ts", "http://testV.ts", "http://testM.ts", "http://redire.ct", new Date(), new Date(), BigDecimal.valueOf(1000), new Long[]{1L, 3L}
        );
        campaign1.setProfileIds(new Long[]{1L, 2L});
        campaignProfile1 = new CampaignProfile(1L, 1L);
        campaignProfile2 = new CampaignProfile(2L, 1L);
        campaign2 = new Campaign(
                2L, 2L, "test2", "http://testH.ts", "http://testV.ts", "http://testM.ts", "http://redire.ct", new Date(), new Date(), BigDecimal.valueOf(2000), new Long[]{1L, 2L}
        );
        campaign2.setProfileIds(new Long[]{2L});
        campaignProfile3 = new CampaignProfile(2L, 2L);
        campaign3 = new Campaign(
                3L, 2L, "test3", "http://testH.ts", "http://testV.ts", "http://testM.ts", "http://redire.ct", new Date(), new Date(), BigDecimal.valueOf(3000), new Long[]{2L, 3L}
        );
        campaign3.setProfileIds(new Long[]{1L});
        campaignProfile4 = new CampaignProfile(1L, 3L);
        campaign4 = new Campaign(
                4L, 3L, "test3", "http://testH.ts", "http://testV.ts", "http://testM.ts", "http://redire.ct", new Date(), new Date(), BigDecimal.valueOf(3000), new Long[]{2L, 3L}
        );

        profile1 = new UserProfile(2L, "old", "people aged over 65");
        profile2 = new UserProfile(2L, "young", "teenagers to young adults");

        campaignList.add(campaign1);
        campaignList.add(campaign2);
        campaignList.add(campaign3);
        campaignProfilesByProfile.add(campaignProfile1);
        campaignProfilesByProfile.add(campaignProfile4);
        campaignProfilesByCampaign.add(campaignProfile1);
        campaignProfilesByCampaign.add(campaignProfile2);
        profileList.add(profile1);
        profileList.add(profile2);

        // Mock account repositery
        campaignRepository = mock(CampaignRepository.class);
        campaignProfileRepository = mock(CampaignProfileRepository.class);
        userProfileRepository = mock(UserProfileRepository.class);
    }

    public static CampaignService getCampaignService() {
        return campaignService;
    }

    public static CampaignRepository getCampaignRepository() {
        return campaignRepository;
    }

    public static CampaignProfileRepository getCampaignProfileRepository() {
        return campaignProfileRepository;
    }

    public static UserProfileRepository getUserProfileRepository() {
        return userProfileRepository;
    }

    public static List<UserProfile> getProfileList() {
        return profileList;
    }

    public static List<Campaign> getCampaignList() {
        return campaignList;
    }

    public static List<CampaignProfile> getCampaignProfilesByProfile() {
        return campaignProfilesByProfile;
    }

    public static List<CampaignProfile> getCampaignProfilesByCampaign() {
        return campaignProfilesByCampaign;
    }

    public static Campaign getCampaign1() {
        return campaign1;
    }


    public static Campaign getCampaign4() {
        return campaign4;
    }

    private static AccountService accountService;
    private static WebSiteAdminService webSiteAdminService;

    private static AccountRepository accountRepository;
    private static WebSiteAdminRepository webSiteAdminRepository;

    private static Account account;
    private static WebSiteAdmin webSiteAdmin;
}
