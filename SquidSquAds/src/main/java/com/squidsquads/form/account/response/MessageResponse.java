package com.squidsquads.form.account.response;

import com.squidsquads.form.Form;

public class MessageResponse extends Form{
    private String message;

    public MessageResponse(int status, String message) {
        super(status);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
