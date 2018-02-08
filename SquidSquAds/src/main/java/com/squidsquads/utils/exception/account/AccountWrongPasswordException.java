package com.squidsquads.utils.exception.account;

public class AccountWrongPasswordException extends Exception {
    public AccountWrongPasswordException(String message) {
        super(message);
    }
}
