package com.squidsquads.controller;

import com.squidsquads.form.account.request.CreateRequest;
import com.squidsquads.form.account.request.LoginRequest;
import com.squidsquads.form.account.request.ResetPasswordRequest;
import com.squidsquads.form.account.response.*;
import com.squidsquads.model.AdminType;
import com.squidsquads.service.AccountService;
import com.squidsquads.service.BannerService;
import com.squidsquads.utils.session.SessionAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController("AccountController")
@RequestMapping("/account")
public class AccountController {

    @Autowired
    AccountService accountService;

    @Autowired
    BannerService bannerService;

    // --------------------------------------------------------------------- //
    @PostMapping("/login")
    public ResponseEntity<AbstractLoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {

        AbstractLoginResponse loginResponse = accountService.login(loginRequest);
        return ResponseEntity.status(loginResponse.getStatus()).body(loginResponse);
    }

    // --------------------------------------------------------------------- //
    @PostMapping("")
    public ResponseEntity<?> create(@Valid @RequestBody CreateRequest createRequest) {

        CreateResponse createResponse = accountService.create(createRequest);
        return ResponseEntity.status(createResponse.getStatus()).body(createResponse);
    }

    // --------------------------------------------------------------------- //
    @GetMapping("")
    @SessionAuthorize({AdminType.PUB, AdminType.WEB})
    public ResponseEntity<?> getInfo(@RequestHeader("Token") String token) {

        InfoResponse infoResponse = accountService.getInfo(token);
        return ResponseEntity.status(infoResponse.getStatus()).body(infoResponse);
    }

    // --------------------------------------------------------------------- //
    @PostMapping("/password")
    @SessionAuthorize({AdminType.PUB, AdminType.WEB})
    public ResponseEntity<?> resetPassword(@RequestHeader("Token") String token, @Valid @RequestBody ResetPasswordRequest resetPasswordRequest) {

        ResetPasswordResponse rpr = accountService.resetPassword(token, resetPasswordRequest);
        return ResponseEntity.status(rpr.getStatus()).body(rpr);
    }

    // --------------------------------------------------------------------- //
    @GetMapping("/banner")
    @SessionAuthorize(AdminType.WEB)
    public ResponseEntity<?> getBannerIDs(@RequestHeader("Token") String token) {

        BannerListResponse response = bannerService.getAll(token);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
