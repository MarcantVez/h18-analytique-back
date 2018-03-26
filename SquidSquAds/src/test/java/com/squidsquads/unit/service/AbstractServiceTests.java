package com.squidsquads.unit.service;

import com.squidsquads.repository.*;
import com.squidsquads.service.*;
import com.squidsquads.utils.TimeSpentCalculator;
import org.junit.BeforeClass;
import org.springframework.test.util.ReflectionTestUtils;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.mock;

public class AbstractServiceTests {

    // Services
    private static AccountService accountService;
    private static BannerService bannerService;
    private static CampaignService campaignService;
    private static UserProfileService userProfileService;
    private static VisitService visitService;
    private static WebSiteAdminService webSiteAdminService;
    private static PaymentService paymentService;
    private static RoyaltyService royaltyService;

    // Repositories
    private static AccountRepository accountRepository;
    private static BannerRepository bannerRepository;
    private static BannerCampaignRepository bannerCampaignRepository;
    private static CampaignRepository campaignRepository;
    private static CampaignProfileRepository campaignProfileRepository;
    private static SiteRepository siteRepository;
    private static TrackingInfoRepository trackingInfoRepository;
    private static UserAgentRepository userAgentRepository;
    private static UserProfileRepository userProfileRepository;
    private static WebSiteAdminRepository webSiteAdminRepository;
    private static VisitRepository visitRepository;
    private static PaymentRepository paymentRepository;
    private static RoyaltyRepository royaltyRepository;

    // Utilities
    private static TimeSpentCalculator calculator;
    private static HttpServletRequest request;

    @BeforeClass
    public static void beforeAbstractTest() {

        instantiateServices();

        mockRepositories();

        assignAutowiredFieldsUsingReflection();
    }

    private static void instantiateServices() {

        accountService = new AccountService();
        bannerService = new BannerService();
        campaignService = new CampaignService();
        userProfileService = new UserProfileService();
        visitService = new VisitService();
        webSiteAdminService = new WebSiteAdminService();
        paymentService = new PaymentService();
        royaltyService = new RoyaltyService();
    }

    private static void mockRepositories() {

        accountRepository = mock(AccountRepository.class);
        bannerRepository = mock(BannerRepository.class);
        bannerCampaignRepository = mock(BannerCampaignRepository.class);
        campaignRepository = mock(CampaignRepository.class);
        campaignProfileRepository = mock(CampaignProfileRepository.class);
        siteRepository = mock(SiteRepository.class);
        trackingInfoRepository = mock(TrackingInfoRepository.class);
        userAgentRepository = mock(UserAgentRepository.class);
        userProfileRepository = mock(UserProfileRepository.class);
        webSiteAdminRepository = mock(WebSiteAdminRepository.class);
        visitRepository = mock(VisitRepository.class);
        royaltyRepository = mock(RoyaltyRepository.class);
        paymentRepository = mock(PaymentRepository.class);
        calculator = mock(TimeSpentCalculator.class);
        request = mock(HttpServletRequest.class);
    }

    private static void assignAutowiredFieldsUsingReflection() {

        // Account Service
        ReflectionTestUtils.setField(accountService, "accountRepository", accountRepository);
        ReflectionTestUtils.setField(accountService, "bannerService", bannerService);
        ReflectionTestUtils.setField(accountService, "webSiteAdminService", webSiteAdminService);

        // Banner Service
        ReflectionTestUtils.setField(bannerService, "bannerRepository", bannerRepository);
        ReflectionTestUtils.setField(bannerService, "bannerCampaignRepository", bannerCampaignRepository);
        ReflectionTestUtils.setField(bannerService, "campaignRepository", campaignRepository);
        ReflectionTestUtils.setField(bannerService, "webSiteAdminService", webSiteAdminService);

        // Campaign Service
        ReflectionTestUtils.setField(campaignService, "campaignRepository", campaignRepository);
        ReflectionTestUtils.setField(campaignService, "campaignProfileRepository", campaignProfileRepository);
        ReflectionTestUtils.setField(campaignService, "userProfileService", userProfileService);

        // User Profile Service
        ReflectionTestUtils.setField(userProfileService, "userProfileRepository", userProfileRepository);
        ReflectionTestUtils.setField(userProfileService, "siteRepository", siteRepository);
        ReflectionTestUtils.setField(userProfileService, "accountService", accountService);

        // Visit Service
        ReflectionTestUtils.setField(visitService, "trackingInfoRepository", trackingInfoRepository);
        ReflectionTestUtils.setField(visitService, "userAgentRepository", userAgentRepository);
        ReflectionTestUtils.setField(visitService, "calculator", calculator);
        ReflectionTestUtils.setField(visitService, "request", request);

        // Web Site Admin Service
        ReflectionTestUtils.setField(webSiteAdminService, "webSiteAdminRepository", webSiteAdminRepository);

        // Payment service
        ReflectionTestUtils.setField(paymentService, "paymentRepository", paymentRepository);
        ReflectionTestUtils.setField(paymentService, "royaltyRepository", royaltyRepository);
        ReflectionTestUtils.setField(paymentService, "visitRepository", visitRepository);

        // Royalty Service
        ReflectionTestUtils.setField(royaltyService, "royaltyRepository", royaltyRepository);
    }

    /////////////
    // Getters //
    /////////////

    public static AccountService getAccountService() {
        return accountService;
    }

    public static BannerService getBannerService() {
        return bannerService;
    }

    public static CampaignService getCampaignService() {
        return campaignService;
    }

    public static UserProfileService getUserProfileService() {
        return userProfileService;
    }

    public static VisitService getVisitService() {
        return visitService;
    }

    public static WebSiteAdminService getWebSiteAdminService() {
        return webSiteAdminService;
    }

    public static PaymentService getPaymentService() {
        return paymentService;
    }

    public static RoyaltyService getRoyaltyService() {
        return royaltyService;
    }

    public static AccountRepository getAccountRepository() {
        return accountRepository;
    }

    public static BannerRepository getBannerRepository() {
        return bannerRepository;
    }

    public static BannerCampaignRepository getBannerCampaignRepository() {
        return bannerCampaignRepository;
    }

    public static CampaignRepository getCampaignRepository() {
        return campaignRepository;
    }

    public static CampaignProfileRepository getCampaignProfileRepository() {
        return campaignProfileRepository;
    }

    public static SiteRepository getSiteRepository() {
        return siteRepository;
    }

    public static TrackingInfoRepository getTrackingInfoRepository() {
        return trackingInfoRepository;
    }

    public static UserAgentRepository getUserAgentRepository() {
        return userAgentRepository;
    }

    public static UserProfileRepository getUserProfileRepository() {
        return userProfileRepository;
    }

    public static WebSiteAdminRepository getWebSiteAdminRepository() {
        return webSiteAdminRepository;
    }

    public static VisitRepository getVisitRepository() {
        return visitRepository;
    }

    public static PaymentRepository getPaymentRepository() {
        return paymentRepository;
    }

    public static RoyaltyRepository getRoyaltyRepository() {
        return royaltyRepository;
    }

    public static TimeSpentCalculator getCalculator() {
        return calculator;
    }

    public static HttpServletRequest getRequest() {
        return request;
    }
}
