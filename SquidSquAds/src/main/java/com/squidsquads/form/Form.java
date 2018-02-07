package com.squidsquads.form;

public abstract class Form {
    protected int status;

    public Form(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
