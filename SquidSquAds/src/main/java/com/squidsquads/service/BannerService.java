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

import java.util.ArrayList;
import java.util.List;

@Service
public class BannerService {

    private static final Logger logger = LoggerFactory.getLogger(BannerService.class);

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

        // Pour le moment, aller chercher une campagne aléatoire
        Campaign campaign = campaignService.getRandom();

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

    public BannerResponse targetedPublicityForBanner(String userFingerprint) {
        TrackingInfo trackingInfo = trackingInfoRepository.findFirstByFingerprintOrderByDateTimeDesc(userFingerprint);
        /* Chercher dans chaque campagne le profil utilisateur qui correspond au fingerprint du client */

        //Trouver la liste de toutes les campagnes
        List<Campaign> campaignList = campaignRepository.findAll();

        // Trouver les campagnes actives
        List<Campaign> activeCampaignList = findActiveCampaigns(campaignList);

        // Si aucune campagne active
        if (activeCampaignList.isEmpty()){
            //Retourner la banniere de SquidSquads
        } else {
            List<UserProfile> matchedProfile = matchProfiles(trackingInfo, activeCampaignList);
            // Si la liste est vide
                //Retourner une banniere random parmi les campagnes actives

            // Si la liste comporte seulement un profil
                // Retourner la banniere de la campagne

            // Si la liste comporte plus que 1 profil
                // Trouver la ponderation de chaque profil
                    // Si multiples profils ont la meme ponderation
                    // Retourner random entre les profils

            // Sinon
                // Retourner le profil avec la plus grande ponderation
        }

    }

    /**
     * Trouver le profil utilisateur qui correspond au fingerprint du client
     *
     * @param userInfo
     * @return
     */
    private List<UserProfile> matchProfiles(TrackingInfo userInfo, List<Campaign> activeCampaignList) {
        List<UserProfile> matchedProfiles = new ArrayList<>();

        // Vérifier si les informations sur l'utilisateurs correspondent à un ou plusieurs profils
        // Pour chaque campagne active
        for (Campaign campaign : activeCampaignList) {
            // Trouver tous les profils correspondants à la campagne
            Integer[] profileLists = campaign.getProfileIds();
            List<UserProfile> userProfiles = new ArrayList<>();
            for (int i = 0; i < profileLists.length; i++) {
                userProfiles.add(userProfileRepository.findByProfileIDAndAccountID(profileLists[i],
                        campaign.getAccountID()));
            }
            // Pour chaque profil attribué à la campagne
            for (UserProfile userProfile : userProfiles) {
                //TODO
                // Checker si le pingerprint de l'utilisateur correspond au profil
                // Si oui ajouter le profil a la liste

                // Sinon rien faire
            }
        }
        return matchedProfiles;
    }

    /**
     * Extraire une liste de campagnes qui sont actives
     *
     * @param campaignList liste de campagnes à vériier
     * @return liste de campagnes actives
     */
    private List<Campaign> findActiveCampaigns(List<Campaign> campaignList) {
        List<Campaign> activeCamapaigns = new ArrayList<>();

        for (Campaign campaign : campaignList) {
            if (campaign.isActive()) {
                activeCamapaigns.add(campaign);
            }
        }
        return activeCamapaigns;
    }
}
