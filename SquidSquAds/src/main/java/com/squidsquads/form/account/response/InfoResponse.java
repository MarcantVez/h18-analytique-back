package com.squidsquads.form.account.response;

import org.springframework.http.HttpStatus;

public class InfoResponse {

    private static final String INFO_FAILED = "Un problème est survenu";
    private static final String INFO_OK = "OK";

    private HttpStatus status;
    private String message;
    private String email;
    private String domain;
    private String bankAccount;

    public InfoResponse() {
    }

    public InfoResponse ok(String email, String domain, String bankAccount) {
        this.status = HttpStatus.OK;
        this.message = INFO_OK;
        this.email = email;
        this.domain = domain;
        this.bankAccount = bankAccount;
        return this;
    }

    public InfoResponse failed() {
        status = HttpStatus.INTERNAL_SERVER_ERROR;
        message = INFO_FAILED;
        return this;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getEmail() {
        return email;
    }

    public String getDomain() {
        return domain;
    }

    public String getBankAccount() {
        return bankAccount;
    }
}
