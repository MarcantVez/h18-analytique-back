package com.squidsquads.service;

import com.squidsquads.form.account.response.BannerListResponse;
import com.squidsquads.form.banner.response.BannerResponse;
import com.squidsquads.form.banner.response.RedirectResponse;
import com.squidsquads.model.*;
import com.squidsquads.repository.*;
import com.squidsquads.utils.Serializer;
import com.squidsquads.utils.session.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class BannerService {

    private static final Logger logger = LoggerFactory.getLogger(BannerService.class);
    private static final String SQUIDSQUADS_COOKIE = "_squidsquads";
    private static final int SQUIDSQUADS_CAMPAIGN_ID = 1;

    @Autowired
    private BannerRepository bannerRepository;

    @Autowired
    private BannerCampaignRepository bannerCampaignRepository;

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
    private VisitService visitService;

    @Autowired
    private RoyaltyService royaltyService;

    @Autowired
    private HttpServletRequest request;

    private Random randomGenerator = new Random();

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

        // Aller chercher une campagne ciblée
        CampaignTargeted campaignTargeted = getCampaignForATargetedAd();

        if (campaignTargeted == null) {
            return new BannerResponse().failed();
        }

        Campaign campaign = campaignTargeted.getCampaign();

        // Créer un lien entre la campagne et la bannière pour aider aux statistiques
        BannerCampaign bannerCampaign = new BannerCampaign(bannerID, campaign.getCampaignID());
        bannerCampaignRepository.save(bannerCampaign);

        // À partir des informations de la campagne, bâtir la réponse
        String alt = campaign.getName();
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

        // Créer une visite pour la campagne et la bannière
        Visit visit = visitService.create(bannerID, false, campaignTargeted.isTargeted());

        if (visit == null) {
            return new BannerResponse().failed();
        }

        Royalty royalty = royaltyService.create(banner.getAccountID(), visit, campaignTargeted.isTargeted(), false);

        if (royalty == null) {
            return new BannerResponse().failed();
        }

        return new BannerResponse().ok(src, alt, campaign.getRedirectUrl(), visit.getVisitID());
    }

    private CampaignTargeted getCampaignForATargetedAd() {

        CampaignTargeted campaignTargeted = null;

        // Vérifier la présence du cookie de tracking
        Cookie cookie = WebUtils.getCookie(request, SQUIDSQUADS_COOKIE);

        if (cookie == null) {
            List<Campaign> campaignList = campaignRepository.findAll();
            List<Campaign> activeCampaignList = findActiveCampaigns(campaignList);
            return new CampaignTargeted(getRandomCampaignInArray(activeCampaignList), false);
        }

        // Récupérer le cookie et des headers dans la requête
        String userFingerprint = cookie.getValue();

        // Trouver la liste de toutes les campagnes
        List<Campaign> campaignList = campaignRepository.findAll();

        // Trouver les campagnes actives
        List<Campaign> activeCampaignList = findActiveCampaigns(campaignList);

        // Si aucune campagne active
        if (activeCampaignList.isEmpty()) {
            // Retourner la bannière de SquidSquads (première campagne créée)
            campaignTargeted = new CampaignTargeted(campaignRepository.findOne(SQUIDSQUADS_CAMPAIGN_ID), false);
        } else {
            // Retourner le count de la totalité des sites visités (distinct/uniques) d’une empreinte
            int countTotalVisitedWebSites = (trackingInfoRepository.findAllByFingerprint(Serializer.fromString(userFingerprint))).size();

            // Trouver une ou plusieurs campagnes ciblées
            List<CampaignTargeted> matchedCampaign = findTargetedCampaigns(activeCampaignList, countTotalVisitedWebSites, userFingerprint);

            // Si la liste est vide retourner une bannière random parmi les campagnes actives
            if (matchedCampaign.isEmpty()) {
                campaignTargeted = new CampaignTargeted(getRandomCampaignInArray(activeCampaignList), false);

            } else if (matchedCampaign.size() == 1) {
                campaignTargeted = new CampaignTargeted(matchedCampaign.get(0).getCampaign(), matchedCampaign.get(0).isTargeted());

            } else {
                campaignTargeted = getRandomCampaignTargetedInArray(matchedCampaign);
            }
        }

        return campaignTargeted;
    }

    // Obtenir une campagne aléatoire parmi les campagnes de la liste
    private CampaignTargeted getRandomCampaignTargetedInArray(List<CampaignTargeted> campaigns) {
        int index = randomGenerator.nextInt(campaigns.size());
        return campaigns.get(index);
    }

    // Obtenir une campagne aléatoire parmi les campagnes de la liste
    private Campaign getRandomCampaignInArray(List<Campaign> campaigns) {
        int index = randomGenerator.nextInt(campaigns.size());
        return campaigns.get(index);
    }

    /**
     * Trouver le profil utilisateur qui correspond au fingerprint du client
     */
    private List<CampaignTargeted> findTargetedCampaigns(List<Campaign> activeCampaignList, int countTotalSites, String userFingerprint) {

        List<CampaignTargeted> matchedCampaigns = new ArrayList<>();

        double biggestCampaignRatio = 0;
        double sumProfilesRatio;
        double sumCampaignRatio;

        int countCiblee = 0;

        // Vérifier si les informations sur l'utilisateur correspondent à une ou plusieurs campagnes
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
                for (Site site : sites) {
                    int countTotalTargetedSites = (trackingInfoRepository.findAllByFingerprintAndCurrentUrl(Serializer.fromString(userFingerprint), site.getUrl())).size();
                    if (countTotalSites > 0)
                        sumProfilesRatio += (double) countTotalTargetedSites / (double) countTotalSites;
                    if (sumProfilesRatio > 0.0) {
                        countCiblee++;
                    }
                }
                sumCampaignRatio += sumProfilesRatio;
            }

            // La moyenne de la somme des ponderations des profils d'une campagne
            double currentCampaignRatio = sumCampaignRatio / userProfiles.size();

            if (currentCampaignRatio > biggestCampaignRatio) {
                biggestCampaignRatio = currentCampaignRatio;
                matchedCampaigns.clear();
                matchedCampaigns.add(new CampaignTargeted(campaign, (countCiblee > 0)));
            } else if (currentCampaignRatio == biggestCampaignRatio) {
                matchedCampaigns.add(new CampaignTargeted(campaign, (countCiblee > 0)));
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

    @Transactional
    public RedirectResponse getRedirectUrl(Integer visitID, String redirectUrl) {

        // Mettre à jour la visite
        Visit visit = visitService.findByID(visitID);
        if (visit == null) {
            return new RedirectResponse().failed();
        }
        boolean isTargeted = visit.getTargeted();
        visit.setClicked(true);
        visitService.update(visit);

        // Mettre à jour la redevance
        Royalty royalty = royaltyService.findByVisitID(visitID);
        if (royalty == null) {
            return new RedirectResponse().failed();
        }
        royalty.setAmount(royaltyService.getFee(isTargeted, true));
        royaltyService.update(royalty);

        return new RedirectResponse().redirect(redirectUrl);
    }
}
