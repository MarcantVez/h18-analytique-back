package com.squidsquads.service.visit;

import com.squidsquads.form.account.response.BannerListResponse;
import com.squidsquads.form.account.response.ListBannerResponseItem;
import com.squidsquads.model.traffic.Banner;
import com.squidsquads.repository.visit.BannerRepository;
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
