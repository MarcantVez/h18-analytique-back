package com.squidsquads.controller;

import com.squidsquads.form.userProfile.request.CreateModifyRequest;
import com.squidsquads.form.userProfile.response.*;
import com.squidsquads.model.account.AdminType;
import com.squidsquads.service.userProfile.UserProfileService;
import com.squidsquads.utils.session.SessionAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController("UserProfileController")
@RequestMapping("/profil")
public class UserProfileController {

    @Autowired
    UserProfileService userProfileService;

    // --------------------------------------------------------------------- //
    @PostMapping("/create")
    @SessionAuthorize(AdminType.PUB)
    public ResponseEntity<?> create(@RequestHeader("Token") String token, @Valid @RequestBody CreateModifyRequest request) {

        CreateUserProfileResponse response = userProfileService.create(token, request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    // --------------------------------------------------------------------- //
    @GetMapping("")
    @SessionAuthorize(AdminType.PUB)
    public ResponseEntity<?> getAll(@RequestHeader("Token") String token) {

        ListResponse response = userProfileService.getAll(token);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    // --------------------------------------------------------------------- //
    @GetMapping("/{profileID}")
    @SessionAuthorize(AdminType.PUB)
    public ResponseEntity<?> getByID(@RequestHeader("Token") String token, @PathVariable("profileID") Long profileID) {

        InfoResponse response = userProfileService.getByID(token, profileID);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    // --------------------------------------------------------------------- //
    @PutMapping("/{profileID}")
    @SessionAuthorize(AdminType.PUB)
    public ResponseEntity<?> modify(@RequestHeader("Token") String token, @PathVariable("profileID") Long profileID, @Valid @RequestBody CreateModifyRequest request) {

        CreateModifyResponse response = userProfileService.modify(token, profileID, request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    // --------------------------------------------------------------------- //
    @DeleteMapping("/{profileID}")
    @SessionAuthorize(AdminType.PUB)
    public ResponseEntity<?> delete(@RequestHeader("Token") String token, @PathVariable("profileID") Long profileID) {

        DeleteResponse response = userProfileService.delete(token, profileID);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

}
