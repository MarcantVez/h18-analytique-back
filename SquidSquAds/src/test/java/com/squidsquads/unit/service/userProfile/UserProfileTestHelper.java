package com.squidsquads.unit.service.userProfile;

import com.squidsquads.form.userProfile.request.CreateModifyRequest;
import com.squidsquads.unit.service.ServiceTestHelper;

public class UserProfileTestHelper extends ServiceTestHelper {


    public CreateModifyRequest getCreateModifyRequestMissingFields()
    {
        return buildCreateModifyRequest(USER_PROFILE_NAME, "", new String[]{});
    }

    public CreateModifyRequest getCreateModifyRequestExistingProfile()
    {
        return buildCreateModifyRequest(USER_PROFILE_NAME, USER_PROFILE_DESCRIPTION, USER_PROFILE_URLS);
    }

    public CreateModifyRequest getCreateModifyRequestInvalidURLs()
    {
        return buildCreateModifyRequest(USER_PROFILE_NAME, USER_PROFILE_DESCRIPTION, new String[]{"", "google.com"});
    }

    public CreateModifyRequest getCreateModifyRequestCorrect()
    {
        return buildCreateModifyRequest(USER_PROFILE_NAME, USER_PROFILE_DESCRIPTION, USER_PROFILE_URLS);
    }

    private CreateModifyRequest buildCreateModifyRequest(String name, String description, String[] urls)
    {
        CreateModifyRequest createModifyRequest = new CreateModifyRequest();

        createModifyRequest.setName(name);
        createModifyRequest.setDescription(description);
        createModifyRequest.setUrls(urls);

        return createModifyRequest;
    }
}
