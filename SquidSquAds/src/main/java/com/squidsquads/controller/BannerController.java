package com.squidsquads.controller;

import com.squidsquads.form.banner.response.BannerResponse;
import com.squidsquads.model.Campaign;
import com.squidsquads.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("BannerController")
@RequestMapping("/banner")
public class BannerController {

    @Autowired
    BannerService bannerService;

    // --------------------------------------------------------------------- //
    @GetMapping("/{bannerID}")
    public ResponseEntity<BannerResponse> logVisit(@PathVariable("bannerID") Integer bannerID) {
        BannerResponse response = bannerService.getPublicityForBanner(bannerID);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

}
