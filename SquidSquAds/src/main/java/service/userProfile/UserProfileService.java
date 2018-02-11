package service.userProfile;

import form.userProfile.request.CreateUserProfileRequest;
import form.userProfile.response.CreateUserProfileResponse;
import form.userProfile.response.EditUserProfileResponse;
import form.userProfile.response.UserProfileInfoResponse;
import form.userProfile.response.UserProfileResponse;
import form.validator.UserProfileValidator;
import model.userProfile.UserProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import repository.userProfile.UserProfileRepository;

/**
 * @author: Dulce Cayetano
 * @Date_Of_Creation: 2018-02-05
 * @Last_modified_by:
 * @Date_of_last_modification:
 **/
@Service
public class UserProfileService {

    private static final Logger logger = LoggerFactory.getLogger(UserProfileService.class);

    @Autowired
    public UserProfileRepository userProfileRepository;

    /**
     * Find an user profile with name
     *
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
     *
     * @param createUserProfileRequest
     * @return
     */
    public CreateUserProfileResponse createUserProfile(CreateUserProfileRequest createUserProfileRequest) {

        //Check for missing fields
        if (!UserProfileValidator.isUserProfileRequestComplete(createUserProfileRequest)) {
            return new CreateUserProfileResponse().fieldsMissing();
        }
        // Check if the profile already exists
        if (findByName(createUserProfileRequest.getName()) != null) {
            return new CreateUserProfileResponse().profileAlreadyExists();
        }

        //Check if all the urls are valid
        for (int i = 0; i < createUserProfileRequest.getUrl().length; i++) {
            //profileAlreadyExists
            if (!UserProfileValidator.isURLValid(createUserProfileRequest.getUrl()[i])) {
                return new CreateUserProfileResponse().invalidURL();
            }
        }

        UserProfile userProfile = userProfileRepository.save(new UserProfile(
                new Integer(createUserProfileRequest.getAccountNumber()),
                createUserProfileRequest.getName(), createUserProfileRequest.getDescription(),
                createUserProfileRequest.getUrl())
        );

        return new CreateUserProfileResponse().success();
    }

    public EditUserProfileResponse editUserProfile(CreateUserProfileRequest request) {

        // Check if profile exists
        if (findByProfileID(new Long(request.getName())) != null) {
            return new EditUserProfileResponse().invalidUser();
        }
        //Check for missing fields
        if (!UserProfileValidator.isUserProfileRequestComplete(request)) {
            return new EditUserProfileResponse().fieldsMissing();
        }
        //TODO CHECK URL
        //Check if all the urls are valid
        for (int i = 0; i < request.getUrl().length; i++) {
            //profileAlreadyExists
            if (!UserProfileValidator.isURLValid(request.getUrl()[i])) {
                return new EditUserProfileResponse().invalidURL();
            }
        }

        UserProfile userProfile = userProfileRepository.save(new UserProfile(new Integer(request.getAccountNumber()),
                request.getName(), request.getDescription(), request.getUrl())
        );

        return new EditUserProfileResponse().success();
    }

    public UserProfileInfoResponse getUserProfileByName(String name) {

        // Check if exists
        if (findByName(name) == null){
            return new UserProfileInfoResponse(HttpStatus.BAD_REQUEST, "Utilisateur n'existe pas", "",
                    "", null);
        }
        UserProfile profile = findByName(name);
        return new UserProfileInfoResponse(HttpStatus.OK, "", name, profile.getDescription(), profile.getUrl());
    }

    public UserProfileInfoResponse getUserProfileByID(String id) {

        // Check if exists
        if (findByProfileID(new Long(id)) == null){
            return new UserProfileInfoResponse(HttpStatus.BAD_REQUEST, "Utilisateur n'existe pas", "",
                    "", null);
        }
        UserProfile profile = findByProfileID(new Long(id));
        return new UserProfileInfoResponse(HttpStatus.OK, "", id, profile.getDescription(), profile.getUrl());
    }


}
