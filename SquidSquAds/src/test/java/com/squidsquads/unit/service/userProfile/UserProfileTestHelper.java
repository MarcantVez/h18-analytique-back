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

    public CreateModifyRequest getCreateModifyRequestWithNameTooLong()
    {
        return buildCreateModifyRequest("Nom de profil vraiment trop long qui rentrera pas dans le champs texte de toute façon mais on prend des précautions parce qu'on est professionnel", USER_PROFILE_DESCRIPTION, USER_PROFILE_URLS);
    }

    public CreateModifyRequest getCreateModifyRequestWithDescTooLong()
    {
        return buildCreateModifyRequest(USER_PROFILE_NAME, "We're no strangers to love\nYou know the rules and so do I\nA full commitment's what I'm thinking of\nYou wouldn't get this from any other guy\nI just wanna tell you how I'm feeling\nGotta make you understand", USER_PROFILE_URLS);
    }

    public CreateModifyRequest getCreateModifyRequestWithUrlTooLong()
    {
        return buildCreateModifyRequest(USER_PROFILE_NAME, USER_PROFILE_DESCRIPTION, INVALID_USER_PROFILE_URLS);
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
