package com.squidsquads.controller;

import com.squidsquads.form.banner.request.GetBannerRequest;
import com.squidsquads.form.banner.request.RedirectRequest;
import com.squidsquads.form.banner.response.BannerResponse;
import com.squidsquads.form.banner.response.RedirectResponse;
import com.squidsquads.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController("BannerController")
@RequestMapping("/banner")
public class BannerController {

    @Autowired
    BannerService bannerService;

    // --------------------------------------------------------------------- //
    @PostMapping("")
    public ResponseEntity<BannerResponse> logVisit(@Valid @RequestBody GetBannerRequest getBannerRequest) {
        BannerResponse response = bannerService.getPublicityForBanner(getBannerRequest.getBannerID());
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    // --------------------------------------------------------------------- //
    @PostMapping("/redirect")
    public ResponseEntity<BannerResponse> redirect(@Valid @RequestBody RedirectRequest redirectRequest) {
        RedirectResponse redirectResponse = bannerService.getRedirectUrl(redirectRequest.getVisitID(), redirectRequest.getRedirectUrl());
        return ResponseEntity.status(redirectResponse.getStatus()).location(URI.create(redirectResponse.getRedirectUrl())).build();
    }
}
