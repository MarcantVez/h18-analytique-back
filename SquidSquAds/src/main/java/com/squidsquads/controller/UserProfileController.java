package com.squidsquads.controller;

import com.squidsquads.form.userProfile.request.CreateModifyRequest;
import com.squidsquads.form.userProfile.response.*;
import com.squidsquads.model.AdminType;
import com.squidsquads.service.UserProfileService;
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
    @PostMapping("")
    @SessionAuthorize(AdminType.PUB)
    public ResponseEntity<?> create(@RequestHeader("Token") String token, @Valid @RequestBody CreateModifyRequest request) {

        CreateResponse response = userProfileService.create(token, request);
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
    public ResponseEntity<?> getByID(@RequestHeader("Token") String token, @PathVariable("profileID") Integer profileID) {

        InfoResponse response = userProfileService.getByID(token, profileID);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    // --------------------------------------------------------------------- //
    @PutMapping("/{profileID}")
    @SessionAuthorize(AdminType.PUB)
    public ResponseEntity<?> modify(@RequestHeader("Token") String token, @PathVariable("profileID") Integer profileID, @Valid @RequestBody CreateModifyRequest request) {

        ModifyResponse response = userProfileService.modify(token, profileID, request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    // --------------------------------------------------------------------- //
    @DeleteMapping("/{profileID}")
    @SessionAuthorize(AdminType.PUB)
    public ResponseEntity<?> delete(@RequestHeader("Token") String token, @PathVariable("profileID") Integer profileID) {

        DeleteResponse response = userProfileService.delete(token, profileID);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

}
