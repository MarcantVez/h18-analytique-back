package form.userProfile.response;

import org.springframework.http.HttpStatus;

/**
 * @author: Dulce Cayetano
 * @Date_Of_Creation: 2018-02-07
 * @Last_modified_by:
 * @Date_of_last_modification:
 **/
public class EditUserProfileResponse {


    private static final String SUCCESS = "Le profil d'utilisateur a été sauvegardé";
    private static final String MISSING_FIELDS = "Tous les champs requis doivent être remplis";
    private static final String INVALID_URL = "URL invalide";
    private static final String INVALID_ACCOUNT_NUMBER = "Numéro de compte invalide";
    private static final String INVALID_PROFILE = "Le profil spécifié n'existe pas";

    private HttpStatus status;
    private String message;

    public EditUserProfileResponse() {
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public EditUserProfileResponse success() {
        status = HttpStatus.OK;
        message = SUCCESS;
        return this;
    }

    public EditUserProfileResponse fieldsMissing() {
        status = HttpStatus.BAD_REQUEST;
        message = MISSING_FIELDS;
        return this;
    }

    public EditUserProfileResponse invalidAccountNumber() {
        status = HttpStatus.BAD_REQUEST;
        message = INVALID_ACCOUNT_NUMBER;
        return this;
    }

    public EditUserProfileResponse invalidURL() {
        status = HttpStatus.BAD_REQUEST;
        message = INVALID_URL;
        return this;
    }

    public EditUserProfileResponse invalidUser() {
        status = HttpStatus.BAD_REQUEST;
        message = INVALID_PROFILE;
        return this;
    }
}
