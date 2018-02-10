package com.squidsquads.form.account.response;

import org.springframework.http.HttpStatus;

public class LoginFailedResponse extends AbstractLoginResponse {

    private static final String LOGIN_FAILED_REASON = "Courriel ou mot de passe incorrect";

    private final String message;

    public LoginFailedResponse() {
        super(false, HttpStatus.UNAUTHORIZED);
        this.message = LOGIN_FAILED_REASON;
    }

    public String getMessage() {
        return message;
    }
}
