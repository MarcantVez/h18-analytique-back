package form.account.response;

import org.springframework.http.HttpStatus;

public class CreateResponse {

    private static final String CREATE_SUCCEEDED = "Compte créé";
    private static final String CREATE_FAILED_CONFLICT = "Courriel déjà utilisé";
    private static final String CREATE_INVALID_TYPE = "Type d'administrateur invalide";

    private HttpStatus status;
    private String message;

    public CreateResponse() {}

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public CreateResponse ok() {
        status = HttpStatus.OK;
        message = CREATE_SUCCEEDED;
        return this;
    }

    public CreateResponse emailAlreadyUsed() {
        status = HttpStatus.CONFLICT;
        message = CREATE_FAILED_CONFLICT;
        return this;
    }

    public CreateResponse invalidAdminType() {
        status = HttpStatus.BAD_REQUEST;
        message = CREATE_INVALID_TYPE;
        return this;
    }
}
