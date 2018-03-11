package com.squidsquads.service;

import com.squidsquads.form.account.response.BannerListResponse;
import com.squidsquads.form.banner.response.BannerResponse;
import com.squidsquads.model.*;
import com.squidsquads.repository.*;
import com.squidsquads.utils.session.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class BannerService {

    private static final Logger logger = LoggerFactory.getLogger(BannerService.class);
    public static final String SQUIDSQUADS_COOKIE = "_squidsquads";

    @Autowired
    private BannerRepository bannerRepository;

    @Autowired
    private BannerCampaignRepository bannerCampaignRepository;

    @Autowired
    private CampaignService campaignService;

    @Autowired
    private WebSiteAdminService webSiteAdminService;

    @Autowired
    private TrackingInfoRepository trackingInfoRepository;

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private CampaignProfileRepository campaignProfileRepository;

    @Autowired
    private SiteRepository siteRepository;

    @Autowired
    private HttpServletRequest request;

    /**
     * Créer une bannière
     */
    public Banner create(Integer accountID, String orientation) {
        return bannerRepository.save(new Banner(accountID, orientation));
    }

    /**
     * Trouver les bannières d'un compte
     */
    public BannerListResponse getAll(String token) {

        Integer accountID = SessionManager.getInstance().getAccountIdForToken(token);

        // Si le compte n'a pas de session ici, c'est un probleme serveur
        if (SessionManager.NO_SESSION.equals(accountID)) {
            logger.error("Un compte basé sur un token de session est introuvable");
            return new BannerListResponse().failed();
        }

        WebSiteAdmin webSiteAdmin = webSiteAdminService.findByAccountID(accountID);

        List<Banner> banners = bannerRepository.findByAccountID(accountID);

        Integer horID = null;
        Integer verID = null;
        Integer mobID = null;

        for (Banner banner : banners) {
            switch (Orientation.valueOf(banner.getOrientation())) {
                case HOR:
                    horID = banner.getBannerID();
                    break;
                case VER:
                    verID = banner.getBannerID();
                    break;
                case MOB:
                    mobID = banner.getBannerID();
                    break;
            }
        }


        return new BannerListResponse().ok(webSiteAdmin.getWebSiteAdminID(), horID, verID, mobID);
    }

    /**
     * Trouver une publicité à afficher dans la bannière
     */
    public BannerResponse getPublicityForBanner(Integer bannerID) {

        // Récupérer la bannière appropriée pour le site
        Banner banner = bannerRepository.findOne(bannerID);

        if (banner == null) {
            return new BannerResponse().failed();
        }

        // Aller chercher une campagne ciblee
        Campaign campaign = getCampaignForATargetedAd();

        if (campaign == null) {
            return new BannerResponse().failed();
        }

        // Créer un lien entre la campagne et la bannière pour aider aux statistiques
        BannerCampaign bannerCampaign = new BannerCampaign(bannerID, campaign.getCampaignID());
        bannerCampaignRepository.save(bannerCampaign);

        // À partir des informations de la campagne, bâtir la réponse
        String alt = campaign.getName();
        String redirectUrl = campaign.getRedirectUrl();
        String src = null;

        switch (Orientation.valueOf(banner.getOrientation())) {
            case HOR:
                src = campaign.getImgHorizontal();
                break;
            case MOB:
                src = campaign.getImgMobile();
                break;
            case VER:
                src = campaign.getImgVertical();
                break;
        }

        return new BannerResponse().ok(src, alt, redirectUrl);
    }

    public Campaign getCampaignForATargetedAd() {
        // Vérifier la présence du cookie de tracking
        Cookie cookie = WebUtils.getCookie(request, SQUIDSQUADS_COOKIE);
        // Récupérer le cookie et des headers dans la requête
        String userFingerprint = cookie.getValue();

        //Trouver la liste de toutes les campagnes
        List<Campaign> campaignList = campaignRepository.findAll();

        // Trouver les campagnes actives
        List<Campaign> activeCampaignList = findActiveCampaigns(campaignList);

        // Si aucune campagne active
        if (activeCampaignList.isEmpty()){
            //Retourner la banniere de SquidSquads
            return null;
        } else {
            // Retourner le count de la totalité des sites visités (distinct/uniques) d’une empreinte
            int countTotalVisitedWebSites = 0;
            countTotalVisitedWebSites = (trackingInfoRepository.findAllByFingerprint(userFingerprint)).size();

            // Trouver une ou plusieurs campagnes ciblees
            List<Campaign> matchedCampaign = findTargetedCampaigns(activeCampaignList, countTotalVisitedWebSites, userFingerprint);

            // Si la liste est vide
                //Retourner une banniere random parmi les campagnes actives
            if (matchedCampaign.isEmpty()) {
                return getRandomCampaignInArray(activeCampaignList);
            } else if (matchedCampaign.size() == 1) {
                return matchedCampaign.get(0);
            } else if (matchedCampaign.size() > 1) {
                return getRandomCampaignInArray(matchedCampaign);
            }
        }


        return null;
    }

    // Obtenir un item aleatoire d'un tableau
    // https://stackoverflow.com/questions/5034370/retrieving-a-random-item-from-arraylist
    public Campaign getRandomCampaignInArray(List<Campaign> campaigns) {
        Random randomGenerator = new Random();
        int index = randomGenerator.nextInt(campaigns.size());
        Campaign c = campaigns.get(index);
        return c;
    }

    /**
     * Trouver le profil utilisateur qui correspond au fingerprint du client
     * @param activeCampaignList
     * @param countTotalSites
     * @param userFingerprint
     * @return
     */
    private List<Campaign> findTargetedCampaigns(List<Campaign> activeCampaignList, int countTotalSites, String userFingerprint) {
        List<Campaign> matchedCampaigns = new ArrayList<>();

        int biggestCampaignRatio = 0;

        int sumProfilesRatio = 0;
        int sumCampaignRatio = 0;

        // Vérifier si les informations sur l'utilisateurs correspondent à une ou plusieurs campagnes
        // Pour chaque campagne active
        for (Campaign campaign : activeCampaignList) {

            sumCampaignRatio = 0;

            // Trouver tous les profils correspondants à la campagne
            List<CampaignProfile> campaignProfileList = campaignProfileRepository.findAllByCampaignID(campaign.getCampaignID());
            List<UserProfile> userProfiles = new ArrayList<>();
            for (int i = 0; i < campaignProfileList.size(); i++) {
                userProfiles.add(userProfileRepository.findByProfileIDAndAccountID(campaignProfileList.get(i).getProfileID(),
                        campaign.getAccountID()));
            }
            // Pour chaque profil attribué à la campagne
            for (UserProfile userProfile : userProfiles) {
                sumProfilesRatio = 0;

                // Trouver tous les sites correspondants au profil
                List<Site> sites = siteRepository.findByUserProfileID(userProfile.getProfileID());

                // Pour chaque site
                for (Site site: sites) {
                    int countTotalTargetedSites = (trackingInfoRepository.findAllByFingerprintAndCurrentUrl(userFingerprint, site.getUrl())).size();
                    sumProfilesRatio += Math.round(countTotalTargetedSites/countTotalSites);
                }
                sumCampaignRatio += sumProfilesRatio;
            }

            // La moyenne de la somme des ponderations des profils d'une campagne
            int currentCampaignRatio = Math.round(sumCampaignRatio/userProfiles.size());

            if (currentCampaignRatio > biggestCampaignRatio) {
                biggestCampaignRatio = currentCampaignRatio;
                matchedCampaigns.clear();
                matchedCampaigns.add(campaign);
            } else if (currentCampaignRatio == biggestCampaignRatio) {
                matchedCampaigns.add(campaign);
            }
        }
        return matchedCampaigns;
    }

    /**
     * Extraire une liste de campagnes qui sont actives
     *
     * @param campaignList liste de campagnes à vériier
     * @return liste de campagnes actives
     */
    private List<Campaign> findActiveCampaigns(List<Campaign> campaignList) {
        List<Campaign> activeCampaigns = new ArrayList<>();

        for (Campaign campaign : campaignList) {
            if (campaign.isActive()) {
                activeCampaigns.add(campaign);
            }
        }
        return activeCampaigns;
    }

}
