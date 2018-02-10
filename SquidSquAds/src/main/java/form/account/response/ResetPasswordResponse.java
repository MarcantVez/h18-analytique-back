package form.account.response;

import org.springframework.http.HttpStatus;

public class ResetPasswordResponse {

    private static final String RESET_SUCCEEDED = "Mot de passe modifié";
    private static final String RESET_FAILED = "Un problème est survenu";
    private static final String RESET_WRONG_OLD_PASSWORD = "Ancien mot de passe n'est pas valide";
    private static final String RESET_WRONG_NEW_PASSWORDS = "Nouveaux mots de passe ne sont pas identiques";

    private HttpStatus status;
    private String message;

    public ResetPasswordResponse() {
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public ResetPasswordResponse ok() {
        status = HttpStatus.OK;
        message = RESET_SUCCEEDED;
        return this;
    }

    public ResetPasswordResponse failed() {
        status = HttpStatus.INTERNAL_SERVER_ERROR;
        message = RESET_FAILED;
        return this;
    }

    public ResetPasswordResponse wrongOldPassword() {
        status = HttpStatus.BAD_REQUEST;
        message = RESET_WRONG_OLD_PASSWORD;
        return this;
    }

    public ResetPasswordResponse wrongNewPasswords() {
        status = HttpStatus.BAD_REQUEST;
        message = RESET_WRONG_NEW_PASSWORDS;
        return this;
    }
}
