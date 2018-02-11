package controller;

import form.account.request.CreateRequest;
import form.account.request.LoginRequest;
import form.account.request.ResetPasswordRequest;
import form.account.response.AbstractLoginResponse;
import form.account.response.CreateResponse;
import form.account.response.InfoResponse;
import form.account.response.ResetPasswordResponse;
import form.userProfile.request.CreateUserProfileRequest;
import form.userProfile.response.CreateUserProfileResponse;
import form.userProfile.response.EditUserProfileResponse;
import form.userProfile.response.UserProfileInfoResponse;
import form.userProfile.response.UserProfileResponse;
import model.account.AdminType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.userProfile.UserProfileService;
import utils.session.SessionAuthorize;

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

    @PostMapping("/create")
    @SessionAuthorize({AdminType.PUB})
    public ResponseEntity<?> createUserProfile(@Valid @RequestBody CreateUserProfileRequest request) {

        CreateUserProfileResponse response = userProfileService.createUserProfile(request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/{profileName}")
    @SessionAuthorize({AdminType.PUB})
    public ResponseEntity<?> getUserProfileByName(@PathVariable ("profileName") String profileName) {

        UserProfileInfoResponse response = userProfileService.getUserProfileByName(profileName);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/{profileID}")
    @SessionAuthorize({AdminType.PUB})
    public ResponseEntity<?> getUserProfileByID(@PathVariable ("profileID") String profileID) {

        UserProfileInfoResponse response = userProfileService.getUserProfileByID(profileID);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PutMapping("/{profileID}")
    @SessionAuthorize({AdminType.PUB})
    public ResponseEntity<?> editUserProfile(@PathVariable String profileID,
                                             @Valid @RequestBody CreateUserProfileRequest request) {

        EditUserProfileResponse response = userProfileService.editUserProfile(request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

}
