package com.squidsquads.service.visit;

import com.squidsquads.form.account.response.BannerListResponse;
import com.squidsquads.form.banner.response.BannerResponse;
import com.squidsquads.model.account.WebSiteAdmin;
import com.squidsquads.model.campaign.Campaign;
import com.squidsquads.model.traffic.Banner;
import com.squidsquads.model.traffic.BannerCampaign;
import com.squidsquads.model.traffic.Orientation;
import com.squidsquads.repository.visit.BannerCampaignRepository;
import com.squidsquads.repository.visit.BannerRepository;
import com.squidsquads.service.account.WebSiteAdminService;
import com.squidsquads.service.campaign.CampaignService;
import com.squidsquads.utils.session.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BannerService {

    private static final Logger logger = LoggerFactory.getLogger(BannerService.class);

    @Autowired
    public BannerRepository bannerRepository;

    @Autowired
    public BannerCampaignRepository bannerCampaignRepository;

    @Autowired
    public CampaignService campaignService;

    @Autowired
    public WebSiteAdminService webSiteAdminService;

    /**
     * Créer une bannière
     */
    public Banner create(Long accountID, String orientation) {
        return bannerRepository.save(new Banner(accountID, orientation));
    }

    /**
     * Trouver les bannières d'un compte
     */
    public BannerListResponse getAll(String token) {

        Long accountID = SessionManager.getInstance().getAccountIdForToken(token);

        // Si le compte n'a pas de session ici, c'est un probleme serveur
        if (accountID == SessionManager.NO_SESSION) {
            logger.error("Un compte basé sur un token de session est introuvable");
            return new BannerListResponse().failed();
        }

        WebSiteAdmin webSiteAdmin = webSiteAdminService.findByAccountID(accountID);

        List<Banner> banners = bannerRepository.findByAccountID(accountID);

        Long horID = null;
        Long verID = null;
        Long mobID = null;

        for (Banner banner : banners) {
            switch (Orientation.valueOf(banner.getOrientation())) {
                case HOR:
                    horID = banner.getBannerId();
                    break;
                case VER:
                    verID = banner.getBannerId();
                    break;
                case MOB:
                    mobID = banner.getBannerId();
                    break;
            }
        }


        return new BannerListResponse().ok(webSiteAdmin.getWebSiteAdminID(), horID, verID, mobID);
    }

    public BannerResponse getPublicityForBanner(Long bannerID) {

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
}
