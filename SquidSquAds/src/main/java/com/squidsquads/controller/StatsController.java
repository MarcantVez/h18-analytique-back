package com.squidsquads.controller;

import com.squidsquads.form.stats.response.StatsResponse;
import com.squidsquads.service.stats.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("StatsController")
@RequestMapping("/stats")
public class StatsController {

    @Autowired
    StatsService statsService;

    @GetMapping("")
    public ResponseEntity<StatsResponse> logVisit(@RequestParam Long userid) {
        StatsResponse response = statsService.getWebStatsForUser(userid);
        return ResponseEntity.status(response.getStatus()).body(null);
    }

}
