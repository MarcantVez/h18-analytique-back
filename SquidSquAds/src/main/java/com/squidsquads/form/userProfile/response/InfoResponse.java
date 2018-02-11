package com.squidsquads.form.userProfile.response;

import com.squidsquads.model.profile.UserProfile;
import com.squidsquads.model.site.Site;
import org.springframework.http.HttpStatus;

import java.util.List;

public class InfoResponse {

    private HttpStatus status;
    private String name;
    private String description;
    private Site[] urls;

    public InfoResponse() {
    }

    public InfoResponse(HttpStatus status, String name, String description, Site[] urls) {
        this.status = status;
        this.name = name;
        this.description = description;
        this.urls = urls;
    }

    public InfoResponse failed() {
        status = HttpStatus.INTERNAL_SERVER_ERROR;
        return this;
    }

    public InfoResponse notFound() {
        status = HttpStatus.NOT_FOUND;
        return this;
    }

    public InfoResponse ok(UserProfile userProfile, List<Site> sites) {
        status = HttpStatus.OK;
        name = userProfile.getName();
        description = userProfile.getDescription();
        urls = sites.toArray(new Site[0]);
        return this;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Site[] getUrls() {
        return urls;
    }
}
