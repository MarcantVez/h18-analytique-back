package com.squidsquads.form.userProfile.request;

/**
 * @author: Dulce Cayetano
 * @Date_Of_Creation: 2018-02-11
 * @Last_modified_by:
 * @Date_of_last_modification:
 **/
public class DeleteUserProfileRequest {

    private long userProfileID;
    private String[] url;

    public long getUserProfileID() {

        return userProfileID;
    }

    public void setUserProfileID(long userProfileID) {
        this.userProfileID = userProfileID;
    }

    public String[] getUrl() {
        return url;
    }

    public void setUrl(String[] url) {
        this.url = url;
    }
}
