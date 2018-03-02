package com.squidsquads.utils.session;

import com.squidsquads.model.AdminType;

import java.util.Date;

public class Session {

    private final AdminType adminType;
    private final Integer accountId;
    private final String email;
    private String token;
    private Date expire;

    public Session(AdminType adminType, Integer accountId, String email, String token, Date expire) {
        this.adminType = adminType;
        this.accountId = accountId;
        this.email = email;
        this.token = token;
        this.expire = expire;
    }

    public AdminType getAdminType() {
        return adminType;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }

    public Date getExpire() {
        return expire;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setExpire(Date expire) {
        this.expire = expire;
    }
}
