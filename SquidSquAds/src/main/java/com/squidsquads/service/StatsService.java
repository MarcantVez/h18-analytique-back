package com.squidsquads.service;

import com.squidsquads.form.stats.response.BrowserTypeStatsResponse;
import com.squidsquads.form.stats.response.VisitStatsResponse;
import com.squidsquads.form.stats.response.RoyaltyStatsResponse;
import com.squidsquads.model.*;
import com.squidsquads.repository.*;
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

    @Autowired
    VisitStatsForDayRepository visitStatsForDayRepository;
    @Autowired
    VisitStatsForMonthRepository visitStatsForMonthRepository;
    @Autowired
    VisitStatsForWeekRepository visitStatsForWeekRepository;
    @Autowired
    VisitStatsForYearRepository visitStatsForYearRepository;
    @Autowired
    RoyaltyStatsRepository royaltyStatsRepository;

    public BrowserTypeStatsResponse getBrowserStatsForUser(String token) {
        Integer accountID = SessionManager.getInstance().getAccountIdForToken(token);
        if (SessionManager.NO_SESSION.equals(accountID)) {
            return new BrowserTypeStatsResponse().failed();
        }
        // Trouver le site lié au compte
        WebSiteAdmin webSiteAdmin = webSiteRepository.findByAccountID(accountID);
        List<BrowserTypesItem> browserTypesItems = browserTypesRepository.findAllByWebsiteIDOrderByRatioDesc(webSiteAdmin.getWebSiteAdminID());
        return new BrowserTypeStatsResponse().ok(browserTypesItems);
    }

    public VisitStatsResponse getVisitsStatsForUser(String token) {
        Integer accountID = SessionManager.getInstance().getAccountIdForToken(token);
        if (SessionManager.NO_SESSION.equals(accountID)) {
            return new VisitStatsResponse().failed();
        }

        WebSiteAdmin webSiteAdmin = webSiteRepository.findByAccountID(accountID);
        List<VisitsAmountFor24h> visitsForDay = visitStatsForDayRepository.findAllByWebsiteID(webSiteAdmin.getWebSiteAdminID());
        List<VisitsAmountForWeek> visitsForWeek = visitStatsForWeekRepository.findAllByWebsiteID(webSiteAdmin.getWebSiteAdminID());
        List<VisitsAmountForMonth> visitsForMonth = visitStatsForMonthRepository.findAllByWebsiteID(webSiteAdmin.getWebSiteAdminID());
        List<VisitsAmountForYear> visitsForYear = visitStatsForYearRepository.findAllByWebsiteID(webSiteAdmin.getWebSiteAdminID());

        return new VisitStatsResponse().ok(visitsForDay,visitsForWeek, visitsForMonth, visitsForYear);
    }

    public RoyaltyStatsResponse getRoyaltyStatsForUser(String token) {
        Integer accountID = SessionManager.getInstance().getAccountIdForToken(token);
        if (SessionManager.NO_SESSION.equals(accountID)) {
            return new RoyaltyStatsResponse().failed();
        }
        List<RoyaltyAmountStat> royaltyAmountStats = royaltyStatsRepository.findAllByCompte(accountID);
        return new RoyaltyStatsResponse().ok(royaltyAmountStats);
    }
}
