package com.squidsquads.controller;

import com.squidsquads.form.userProfile.request.DeleteUserProfileRequest;
import com.squidsquads.form.userProfile.response.UserProfileDeleteResponse;
import com.squidsquads.model.account.AdminType;
import com.squidsquads.form.userProfile.request.CreateUserProfileRequest;
import com.squidsquads.form.userProfile.response.CreateUserProfileResponse;
import com.squidsquads.form.userProfile.response.EditUserProfileResponse;
import com.squidsquads.form.userProfile.response.UserProfileInfoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.squidsquads.service.userProfile.UserProfileService;
import com.squidsquads.utils.session.SessionAuthorize;


import javax.validation.Valid;

/**
 * @author: Dulce Cayetano
 * @Date_Of_Creation: 2018-02-05
 * @Last_modified_by:
 * @Date_of_last_modification:
 **/
@RestController("UserProfileController")
@RequestMapping("/user-profile")
public class UserProfileController {

    @Autowired
    UserProfileService userProfileService;

    /**
     * Create user profile
     */
    @PostMapping("/create")
    @SessionAuthorize({AdminType.PUB})
    public ResponseEntity<?> createUserProfile(@Valid @RequestBody CreateUserProfileRequest request) {

        CreateUserProfileResponse response = userProfileService.createUserProfile(request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    /**
     * Get User profile with name
     */
    @GetMapping("/{profileName}")
    @SessionAuthorize(AdminType.PUB)
    public ResponseEntity<?> getUserProfileByName(@PathVariable ("profileName") String profileName) {

        UserProfileInfoResponse response = userProfileService.getUserProfileByName(profileName);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    /**
     * Get User profile with ID
     */
    @GetMapping("/{profileID}")
    @SessionAuthorize(AdminType.PUB)
    public ResponseEntity<?> getUserProfileByID(@PathVariable ("profileID") String profileID) {

        UserProfileInfoResponse response = userProfileService.getUserProfileByID(profileID);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    /**
     * Edit user profile
     */
    @PutMapping("/{profileID}")
    @SessionAuthorize(AdminType.PUB)
    public ResponseEntity<?> editUserProfile(@PathVariable String profileID,
                                             @Valid @RequestBody CreateUserProfileRequest request) {

        EditUserProfileResponse response = userProfileService.editUserProfile(request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    /**
     * Delete user profile
     */
    @DeleteMapping("/{profileID}")
    @SessionAuthorize(AdminType.PUB)
    public ResponseEntity<?> deleteUserProfile(@PathVariable String profileID,
                                               @Valid @RequestBody DeleteUserProfileRequest request){
        UserProfileDeleteResponse response = userProfileService.deleteUserProfile(request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

}
