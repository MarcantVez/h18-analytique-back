package com.squidsquads.service.visit;

import com.squidsquads.form.account.request.CreateRequest;
import com.squidsquads.form.account.request.LoginRequest;
import com.squidsquads.form.account.request.ResetPasswordRequest;
import com.squidsquads.form.account.response.*;
import com.squidsquads.form.campaign.response.ListResponse;
import com.squidsquads.form.campaign.response.ListResponseItem;
import com.squidsquads.form.validator.AccountValidator;
import com.squidsquads.form.visit.response.BannerListResponse;
import com.squidsquads.form.visit.response.ListBannerResponseItem;
import com.squidsquads.model.account.Account;
import com.squidsquads.model.account.AdminType;
import com.squidsquads.model.account.WebSiteAdmin;
import com.squidsquads.model.campaign.Campaign;
import com.squidsquads.model.traffic.Banner;
import com.squidsquads.model.traffic.Orientation;
import com.squidsquads.repository.account.AccountRepository;
import com.squidsquads.repository.visit.BannerRepository;
import com.squidsquads.service.campaign.CampaignService;
import com.squidsquads.utils.DateFormatter;
import com.squidsquads.utils.session.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class BannerService {

    private static final Logger logger = LoggerFactory.getLogger(BannerService.class);

    @Autowired
    public BannerRepository bannerRepository;

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

        List<Banner> banners = bannerRepository.findByAccountID(accountID);
        List<ListBannerResponseItem> responseList = new ArrayList<>();

        for (Banner b : banners) {
            responseList.add(
                    new ListBannerResponseItem(
                            b.getBannerId(),
                            b.getOrientation()
                    )
            );
        }

        return new BannerListResponse().ok(responseList);
    }

}
