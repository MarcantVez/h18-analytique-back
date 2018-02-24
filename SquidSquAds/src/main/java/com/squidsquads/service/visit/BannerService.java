package com.squidsquads.service.visit;

import com.squidsquads.form.account.request.CreateRequest;
import com.squidsquads.form.account.request.LoginRequest;
import com.squidsquads.form.account.request.ResetPasswordRequest;
import com.squidsquads.form.account.response.*;
import com.squidsquads.form.validator.AccountValidator;
import com.squidsquads.model.account.Account;
import com.squidsquads.model.account.AdminType;
import com.squidsquads.model.account.WebSiteAdmin;
import com.squidsquads.model.traffic.Banner;
import com.squidsquads.model.traffic.Orientation;
import com.squidsquads.repository.account.AccountRepository;
import com.squidsquads.repository.visit.BannerRepository;
import com.squidsquads.utils.session.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BannerService {

    @Autowired
    public BannerRepository bannerRepository;

    /**
     * Créer une bannière
     */
    public Banner create(Long accountID, String orientation) {
        return bannerRepository.save(new Banner(accountID, orientation));
    }

}
