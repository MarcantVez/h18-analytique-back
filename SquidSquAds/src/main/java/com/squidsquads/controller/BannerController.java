package com.squidsquads.controller;

import com.squidsquads.form.banner.request.RedirectRequest;
import com.squidsquads.form.banner.response.BannerResponse;
import com.squidsquads.form.banner.response.RedirectResponse;
import com.squidsquads.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @GetMapping("/{bannerID}")
    public ResponseEntity<BannerResponse> getBanner(@PathVariable("bannerID") Integer bannerID) {
        BannerResponse response = bannerService.getPublicityForBanner(bannerID);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    // --------------------------------------------------------------------- //
    @GetMapping("/redirect")
    public ResponseEntity<BannerResponse> redirect(@RequestParam("visitID") Integer visitID, @RequestParam("redirectUrl") String redirectUrl) {
        RedirectResponse redirectResponse = bannerService.getRedirectUrl(visitID, redirectUrl);

        if (redirectResponse.getStatus().value() == HttpStatus.FOUND.value())
            return ResponseEntity.status(redirectResponse.getStatus()).location(URI.create(redirectResponse.getRedirectUrl())).build();
        else
            return ResponseEntity.status(redirectResponse.getStatus()).build();
    }
}
