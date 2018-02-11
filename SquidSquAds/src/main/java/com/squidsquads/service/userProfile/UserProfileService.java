package com.squidsquads.service.userProfile;

import com.squidsquads.form.userProfile.request.CreateUserProfileRequest;
import com.squidsquads.form.userProfile.request.DeleteUserProfileRequest;
import com.squidsquads.form.userProfile.response.*;
import com.squidsquads.form.validator.UserProfileValidator;
import com.squidsquads.model.profile.UserProfile;
import com.squidsquads.model.site.Site;
import com.squidsquads.repository.site.SiteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.squidsquads.repository.userProfile.UserProfileRepository;

/**
 * @author: Dulce Cayetano
 * @Date_Of_Creation: 2018-02-05
 * @Last_modified_by: Dulce Cayetano
 * @Date_of_last_modification: 2018-02-10
 **/
@Service
public class UserProfileService {

    private static final Logger logger = LoggerFactory.getLogger(UserProfileService.class);

    @Autowired
    public UserProfileRepository userProfileRepository;

    @Autowired
    public SiteRepository siteRepository;

    /**
     * Find an user profile with name
     * @param name: name of the profile
     * @return the user profile
     */
    private UserProfile findByName(String name) {
        return userProfileRepository.findByName(name);
    }

    /**
     * Find the the user profile with ID
     *
     * @param profileID: ID of the user profile
     * @return the user profile
     */
    private UserProfile findByProfileID(Long profileID) {
        return userProfileRepository.findByProfileID(profileID);
    }


    /**
     * Creates a new profile
     */
    public CreateUserProfileResponse createUserProfile(CreateUserProfileRequest request) {

        //Check for missing fields
        if (!UserProfileValidator.isUserProfileRequestComplete(request)) {
            return new CreateUserProfileResponse().fieldsMissing();
        }
        // Check if the profile already exists
        if (findByName(request.getName()) != null) {
            return new CreateUserProfileResponse().profileAlreadyExists();
        }

        //Check if all the urls are valid
        for (int i = 0; i < request.getUrl().length; i++) {
            //profileAlreadyExists
            if (!UserProfileValidator.isURLValid(request.getUrl()[i])) {
                return new CreateUserProfileResponse().invalidURL();
            }
        }

        // Create the profile
        UserProfile userProfile = userProfileRepository.save(new UserProfile(
                new Integer(request.getAccountNumber()), request.getName(), request.getDescription())
        );

        // Create the sites for the profile
        for (int i = 0; i < request.getUrl().length; i++) {
            Site site = siteRepository.save(new Site(userProfile.getProfileID(), request.getUrl()[i]));
        }

        return new CreateUserProfileResponse().success();
    }

    /**
     * Edits existing user profile
     */
    public EditUserProfileResponse editUserProfile(CreateUserProfileRequest request) {

        // Check if profile exists
        if (findByProfileID(new Long(request.getName())) != null) {
            return new EditUserProfileResponse().invalidUser();
        }
        //Check for missing fields
        if (!UserProfileValidator.isUserProfileRequestComplete(request)) {
            return new EditUserProfileResponse().fieldsMissing();
        }

        //Check if all the urls are valid
        for (int i = 0; i < request.getUrl().length; i++) {
            //profileAlreadyExists
            if (!UserProfileValidator.isURLValid(request.getUrl()[i])) {
                return new EditUserProfileResponse().invalidURL();
            }
        }

        // Create the profile
        UserProfile userProfile = userProfileRepository.save(new UserProfile(new Integer(request.getAccountNumber()),
                request.getName(), request.getDescription())
        );

        // Create the sites for the profile
        for (int i = 0; i < request.getUrl().length; i++) {
            Site site = siteRepository.save(new Site(userProfile.getProfileID(), request.getUrl()[i]));
        }

        return new EditUserProfileResponse().success();
    }

    /**
     * Get user profile with name
     */
    public UserProfileInfoResponse getUserProfileByName(String name) {

        // Check if exists
        if (findByName(name) == null){
            return new UserProfileInfoResponse(HttpStatus.BAD_REQUEST, "Utilisateur n'existe pas", "",
                    "");
        }
        UserProfile profile = findByName(name);
        return new UserProfileInfoResponse(HttpStatus.OK, "", name, profile.getDescription());
    }

    /**
     * Get user profile with ID
     */
    public UserProfileInfoResponse getUserProfileByID(String id) {

        // Check if exists
        if (findByProfileID(new Long(id)) == null){
            return new UserProfileInfoResponse(HttpStatus.BAD_REQUEST, "Utilisateur n'existe pas", "",
                    "");
        }
        UserProfile profile = findByProfileID(new Long(id));
        return new UserProfileInfoResponse(HttpStatus.OK, "", id, profile.getDescription());
    }

    /**
     * Deletes existing user profile
     */
    public UserProfileDeleteResponse deleteUserProfile (DeleteUserProfileRequest request){

        // Check if profile exists
        if (userProfileRepository.findByProfileID(new Long(request.getUserProfileID())) == null) {
            return new UserProfileDeleteResponse().profileNotFound();
        }
        userProfileRepository.delete(new Long(request.getUserProfileID()));

        for (int i = 0; i < request.getUrl().length; i++){
            //Check if site exists
            if(siteRepository.findByUserProfileID(request.getUserProfileID()) == null){
                return new UserProfileDeleteResponse().profileNotFound();
            }
            siteRepository.delete(siteRepository.findByUserProfileID(request.getUserProfileID()));
        }

        return new UserProfileDeleteResponse().success();
    }


}
