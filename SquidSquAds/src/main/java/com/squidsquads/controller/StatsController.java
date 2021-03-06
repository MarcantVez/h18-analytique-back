package com.squidsquads.controller;

import com.squidsquads.form.stats.response.BrowserTypeStatsResponse;
import com.squidsquads.form.stats.response.VisitStatsResponse;
import com.squidsquads.form.stats.response.RoyaltyStatsResponse;
import com.squidsquads.model.AdminType;
import com.squidsquads.service.StatsService;
import com.squidsquads.utils.session.SessionAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("StatsController")
@RequestMapping("/stats")
public class StatsController {

    @Autowired
    StatsService statsService;

    @GetMapping("/browsertypes")
    @SessionAuthorize(AdminType.WEB)
    public ResponseEntity<BrowserTypeStatsResponse> getBrowserStatsForWebAdmin(@RequestHeader("Token") String token) {
        BrowserTypeStatsResponse response = statsService.getBrowserStatsForUser(token);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/visits")
    @SessionAuthorize(AdminType.WEB)
    public ResponseEntity<VisitStatsResponse> getTrafficStatsForWebAdmin(@RequestHeader("Token") String token) {
        VisitStatsResponse response = statsService.getVisitsStatsForUser(token);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/royalty")
    @SessionAuthorize(AdminType.WEB)
    public ResponseEntity<RoyaltyStatsResponse> getRoyaltyStatsForWebAdmin(@RequestHeader("Token") String token) {
        RoyaltyStatsResponse response = statsService.getRoyaltyStatsForUser(token);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

}
