package com.squidsquads.service.stats;

import com.squidsquads.form.stats.response.BrowserTypeStatsResponse;
import com.squidsquads.model.account.WebSiteAdmin;
import com.squidsquads.model.stats.BrowserTypesItem;
import com.squidsquads.repository.account.WebSiteAdminRepository;
import com.squidsquads.repository.stats.BrowserTypesItemRepository;
import com.squidsquads.utils.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatsService {

    @Autowired
    BrowserTypesItemRepository browserTypesRepository;

    @Autowired
    WebSiteAdminRepository webSiteRepository;

    public BrowserTypeStatsResponse getBrowserStatsForUser(String token) {
        Long accountID = SessionManager.getInstance().getAccountIdForToken(token);
        if (accountID == SessionManager.NO_SESSION) {
            return new BrowserTypeStatsResponse().unauthorised();
        }

        // find website for this account
        WebSiteAdmin webSiteAdmin = webSiteRepository.findByAccountID(accountID);

        List<BrowserTypesItem> browserTypesItems = browserTypesRepository.findAllByWebsiteIDOrderByRatioDesc(webSiteAdmin.getWebSiteAdminID());
        return new BrowserTypeStatsResponse().ok(browserTypesItems);
    }
}
