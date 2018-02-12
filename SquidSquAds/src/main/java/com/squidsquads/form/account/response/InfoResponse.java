package com.squidsquads.form.account.response;

import org.springframework.http.HttpStatus;

public class InfoResponse {

    private final HttpStatus status;
    private final String email;
    private final String domain;
    private final String bankAccount;

    public InfoResponse(HttpStatus status, String email, String domain, String bankAccount) {
        this.status = status;
        this.email = email;
        this.domain = domain;
        this.bankAccount = bankAccount;
    }

    public HttpStatus getStatus() {
        return status;
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
