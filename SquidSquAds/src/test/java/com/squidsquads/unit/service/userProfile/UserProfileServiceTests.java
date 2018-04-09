package com.squidsquads.unit.service.userProfile;

import com.squidsquads.form.userProfile.response.CreateResponse;
import com.squidsquads.form.userProfile.response.DeleteResponse;
import com.squidsquads.form.userProfile.response.InfoResponse;
import com.squidsquads.form.userProfile.response.ModifyResponse;
import com.squidsquads.model.Account;
import com.squidsquads.model.Site;
import com.squidsquads.model.UserProfile;
import com.squidsquads.unit.service.AbstractServiceTests;
import com.squidsquads.utils.session.SessionManager;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

public class UserProfileServiceTests extends AbstractServiceTests {

    private UserProfileTestHelper helper = new UserProfileTestHelper();

    private Account pub;
    private String token;

    @Before
    public void setup() {

        pub = helper.getAccountPub();
        token = SessionManager.getInstance().addSession(pub);
    }

    /////////////////
    ///  Create  ////
    /////////////////

    @Test
    public void findByNotExistingProfileIDAndAccountID() {
        when(getUserProfileRepository().findByProfileIDAndAccountID(1, 1)).thenReturn(null);

        UserProfile up = getUserProfileService().findByProfileIDAndAccountID(1, 1);

        assertNull(up);

        reset(getUserProfileRepository());
    }

    @Test
    public void createWrongToken() {
        CreateResponse createResponse = getUserProfileService().create("WrongToken", null);

        assertCreateResponse(createResponse, HttpStatus.INTERNAL_SERVER_ERROR, "Numéro de compte invalide");
    }

    @Test
    public void createMissingFields() {
        CreateResponse createResponse = getUserProfileService().create(token, helper.getCreateModifyRequestMissingFields());

        assertCreateResponse(createResponse, HttpStatus.BAD_REQUEST, "Tous les champs requis doivent être remplis");
    }

    @Test
    public void createExistingProfile() {
        when(getUserProfileRepository().findByNameAndAccountID(anyString(), anyInt())).thenReturn(helper.getUserProfile());

        CreateResponse createResponse = getUserProfileService().create(token, helper.getCreateModifyRequestExistingProfile());

        assertCreateResponse(createResponse, HttpStatus.CONFLICT, "Nom de profil déjà utilisé");

        reset(getUserProfileRepository());
    }

    @Test
    public void createInvalidURLs() {
        CreateResponse createResponse = getUserProfileService().create(token, helper.getCreateModifyRequestInvalidURLs());

        assertCreateResponse(createResponse, HttpStatus.BAD_REQUEST, "URL invalide");
    }

    @Test
    public void createUserProfileSavingError() {
        when(getUserProfileRepository().save(any(UserProfile.class))).thenReturn(null);

        CreateResponse createResponse = getUserProfileService().create(token, helper.getCreateModifyRequestCorrect());

        assertCreateResponse(createResponse, HttpStatus.INTERNAL_SERVER_ERROR, "Numéro de compte invalide");

        reset(getUserProfileRepository());
    }

    @Test
    public void createSiteSavingError() {
        when(getSiteRepository().save(any(Site.class))).thenReturn(null);

        CreateResponse createResponse = getUserProfileService().create(token, helper.getCreateModifyRequestCorrect());

        assertCreateResponse(createResponse, HttpStatus.INTERNAL_SERVER_ERROR, "Numéro de compte invalide");

        reset(getSiteRepository());
    }

    @Test
    public void createNameTooLong() {
        CreateResponse createResponse = getUserProfileService().create(token, helper.getCreateModifyRequestWithNameTooLong());
        assertCreateResponse(createResponse, HttpStatus.BAD_REQUEST, "Le nom du profil ne peut dépasser 100 caractères");
    }

    @Test
    public void createDescTooLong() {
        CreateResponse createResponse = getUserProfileService().create(token, helper.getCreateModifyRequestWithDescTooLong());
        assertCreateResponse(createResponse, HttpStatus.BAD_REQUEST, "La description du profil ne peut dépasser 200 caractères");
    }

    @Test
    public void createUrlsTooLong() {
        CreateResponse createResponse = getUserProfileService().create(token, helper.getCreateModifyRequestWithUrlTooLong());
        assertCreateResponse(createResponse, HttpStatus.BAD_REQUEST, "Les urls ciblés ne peuvent dépasser 200 caractères");
    }

    @Test
    public void createValid() {
        when(getUserProfileRepository().save(any(UserProfile.class))).thenReturn(helper.getUserProfile());
        when(getSiteRepository().save(any(Site.class))).thenReturn(helper.getSite());

        CreateResponse createResponse = getUserProfileService().create(token, helper.getCreateModifyRequestCorrect());

        assertCreateResponse(createResponse, HttpStatus.OK, "Le profil utilisateur a été créé");

        reset(getUserProfileRepository());
        reset(getSiteRepository());
    }

    //////////////////
    ///  GetByID  ////
    //////////////////

    @Test
    public void getByIDWrongToken()
    {
        InfoResponse infoResponse = getUserProfileService().getByID("WrongToken", 1);

        assertInfoResponse(infoResponse, HttpStatus.INTERNAL_SERVER_ERROR, null, null, null);
    }

    @Test
    public void getByIDNoProfile()
    {
        InfoResponse infoResponse = getUserProfileService().getByID(token, 1);

        assertInfoResponse(infoResponse, HttpStatus.NOT_FOUND, "", "", new Site[]{});
    }

    @Test
    public void getByIDNoSite()
    {
        when(getUserProfileRepository().findByProfileIDAndAccountID(anyInt(), anyInt())).thenReturn(helper.getUserProfile());
        InfoResponse infoResponse = getUserProfileService().getByID(token, 1);

        assertInfoResponse(infoResponse, HttpStatus.OK, "Les personnes naives", "On va se faire de l'oseille sur ces gens", new Site[]{});

        reset(getUserProfileRepository());
    }

    @Test
    public void getByIDValid()
    {
        List<Site> sites = new ArrayList<>();
        sites.add(helper.getSite());
        when(getSiteRepository().findByUserProfileID(anyInt())).thenReturn(sites);
        when(getUserProfileRepository().findByProfileIDAndAccountID(anyInt(), anyInt())).thenReturn(helper.getUserProfile());

        InfoResponse infoResponse = getUserProfileService().getByID(token, 1);

        assertInfoResponse(infoResponse, HttpStatus.OK, "Les personnes naives", "On va se faire de l'oseille sur ces gens", new Site[]{helper.getSite()});

        reset(getSiteRepository());
        reset(getUserProfileRepository());
    }


    //////////////////
    ///   Modify  ////
    //////////////////

    @Test
    public void modifyWrongToken()
    {
        when(getAccountRepository().findByAccountID(anyInt())).thenReturn(null);

        ModifyResponse modifyResponse = getUserProfileService().modify("Wrong token", 1, helper.getCreateModifyRequestCorrect());

        assertModifyResponse(modifyResponse, HttpStatus.INTERNAL_SERVER_ERROR, "Numéro de compte invalide");
    }

    @Test
    public void modifyNoAccount()
    {
        when(getAccountRepository().findByAccountID(anyInt())).thenReturn(null);

        ModifyResponse modifyResponse = getUserProfileService().modify(token, 1, helper.getCreateModifyRequestCorrect());

        assertModifyResponse(modifyResponse, HttpStatus.INTERNAL_SERVER_ERROR, "Numéro de compte invalide");

        reset(getAccountRepository());
    }

    @Test
    public void modifyInvalidProfileID()
    {
        when(getAccountRepository().findByAccountID(anyInt())).thenReturn(helper.getAccountPub());
        when(getUserProfileRepository().findByProfileIDAndAccountID(anyInt(), anyInt())).thenReturn(null);

        ModifyResponse modifyResponse = getUserProfileService().modify(token, 1, helper.getCreateModifyRequestCorrect());

        assertModifyResponse(modifyResponse, HttpStatus.NOT_FOUND, "Le profil utilisateur n'existe pas");

        reset(getAccountRepository());
        reset(getUserProfileRepository());
    }


    @Test
    public void modifyMissingField()
    {
        when(getAccountRepository().findByAccountID(anyInt())).thenReturn(helper.getAccountPub());
        when(getUserProfileRepository().findByProfileIDAndAccountID(anyInt(), anyInt())).thenReturn(helper.getUserProfile());

        ModifyResponse modifyResponse = getUserProfileService().modify(token, 1, helper.getCreateModifyRequestMissingFields());

        assertModifyResponse(modifyResponse, HttpStatus.BAD_REQUEST, "Tous les champs requis doivent être remplis");

        reset(getAccountRepository());
        reset(getUserProfileRepository());
    }


    @Test
    public void modifyInvalidURL()
    {
        when(getAccountRepository().findByAccountID(anyInt())).thenReturn(helper.getAccountPub());
        when(getUserProfileRepository().findByProfileIDAndAccountID(anyInt(), anyInt())).thenReturn(helper.getUserProfile());

        ModifyResponse modifyResponse = getUserProfileService().modify(token, 1, helper.getCreateModifyRequestInvalidURLs());

        assertModifyResponse(modifyResponse, HttpStatus.BAD_REQUEST, "URL invalide");

        reset(getAccountRepository());
        reset(getUserProfileRepository());
    }

    @Test
    public void modifyNameTooLong() {
        when(getAccountRepository().findByAccountID(anyInt())).thenReturn(helper.getAccountPub());
        when(getUserProfileRepository().findByProfileIDAndAccountID(anyInt(), anyInt())).thenReturn(helper.getUserProfile());
        ModifyResponse resp = getUserProfileService().modify(token, 1, helper.getCreateModifyRequestWithNameTooLong());
        assertModifyResponse(resp, HttpStatus.BAD_REQUEST, "Le nom du profil ne peut dépasser 100 caractères");
    }

    @Test
    public void modifyDescTooLong() {
        when(getAccountRepository().findByAccountID(anyInt())).thenReturn(helper.getAccountPub());
        when(getUserProfileRepository().findByProfileIDAndAccountID(anyInt(), anyInt())).thenReturn(helper.getUserProfile());
        ModifyResponse resp = getUserProfileService().modify(token, 1, helper.getCreateModifyRequestWithDescTooLong());
        assertModifyResponse(resp, HttpStatus.BAD_REQUEST, "La description du profil ne peut dépasser 200 caractères");
    }

    @Test
    public void modifyUrlsTooLong() {
        when(getAccountRepository().findByAccountID(anyInt())).thenReturn(helper.getAccountPub());
        when(getUserProfileRepository().findByProfileIDAndAccountID(anyInt(), anyInt())).thenReturn(helper.getUserProfile());
        ModifyResponse resp = getUserProfileService().modify(token, 1,helper.getCreateModifyRequestWithUrlTooLong());
        assertModifyResponse(resp, HttpStatus.BAD_REQUEST, "Les urls ciblés ne peuvent dépasser 200 caractères");
    }


    @Test
    public void modifyValid()
    {
        when(getAccountRepository().findByAccountID(anyInt())).thenReturn(helper.getAccountPub());
        when(getUserProfileRepository().findByProfileIDAndAccountID(anyInt(), anyInt())).thenReturn(helper.getUserProfile());
        when(getUserProfileRepository().save(any(UserProfile.class))).thenReturn(helper.getUserProfile());
        when(getSiteRepository().save(any(Site.class))).thenReturn(helper.getSite());
        doNothing().when(getSiteRepository()).delete(any(Site.class));

        ModifyResponse modifyResponse = getUserProfileService().modify(token, 1, helper.getCreateModifyRequestCorrect());

        assertModifyResponse(modifyResponse, HttpStatus.OK, "Le profil utilisateur a été modifié");

        reset(getAccountRepository());
        reset(getUserProfileRepository());
        reset(getSiteRepository());
    }


    //////////////////
    ///   Delete  ////
    //////////////////

    @Test
    public void deleteWrongToken()
    {
        DeleteResponse deleteResponse = getUserProfileService().delete("Wrong token", 1);

        assertDeleteResponse(deleteResponse, HttpStatus.INTERNAL_SERVER_ERROR, "Numéro de compte invalide");
    }

    @Test
    public void deleteNoUserProfile()
    {
        DeleteResponse deleteResponse = getUserProfileService().delete(token, 1);

        assertDeleteResponse(deleteResponse, HttpStatus.NOT_FOUND, "Le profil utilisateur n'existe pas");
    }

    @Test
    public void deleteValid()
    {
        when(getUserProfileRepository().findByProfileIDAndAccountID(anyInt(), anyInt())).thenReturn(helper.getUserProfile());

        DeleteResponse deleteResponse = getUserProfileService().delete(token, 1);

        assertDeleteResponse(deleteResponse, HttpStatus.OK, "Le profil utilisateur a été supprimé");

        reset(getUserProfileRepository());
    }

    /////////////////////////
    ///  Assert methods  ////
    /////////////////////////

    private void assertCreateResponse(CreateResponse createResponse, HttpStatus expectedStatus, String expectedMessage)
    {
        assertNotNull(createResponse);
        assertTrue(createResponse instanceof CreateResponse);
        assertEquals(expectedStatus, createResponse.getStatus());
        assertEquals(expectedMessage, createResponse.getMessage());
    }

    private void assertModifyResponse(ModifyResponse modifyResponse, HttpStatus expectedStatus, String expectedMessage)
    {
        assertNotNull(modifyResponse);
        assertTrue(modifyResponse instanceof ModifyResponse);
        assertEquals(expectedStatus, modifyResponse.getStatus());
        assertEquals(expectedMessage, modifyResponse.getMessage());
    }

    private void assertDeleteResponse(DeleteResponse deleteResponse, HttpStatus expectedStatus, String expectedMessage)
    {
        assertNotNull(deleteResponse);
        assertTrue(deleteResponse instanceof DeleteResponse);
        assertEquals(expectedStatus, deleteResponse.getStatus());
        assertEquals(expectedMessage, deleteResponse.getMessage());
    }

    private void assertInfoResponse(InfoResponse infoResponse, HttpStatus expectedStatus, String expectedName, String expectedDescription, Site[] expectedURLs)
    {
        assertNotNull(infoResponse);
        assertTrue(infoResponse instanceof InfoResponse);
        assertEquals(expectedStatus, infoResponse.getStatus());
        if(infoResponse.getName() != null) assertEquals(expectedName, infoResponse.getName());
        if(infoResponse.getDescription() != null) assertEquals(expectedDescription, infoResponse.getDescription());

        Site[] urls = infoResponse.getUrls();
        if(urls != null)
        {
            assertEquals(expectedURLs.length, urls.length);
            if( expectedURLs.length == urls.length )
            {
                for( int it = 0; it < expectedURLs.length; it++ )
                {
                    assertEquals(expectedURLs[it].getSiteID(), urls[it].getSiteID());
                    assertEquals(expectedURLs[it].getUserProfileID(), urls[it].getUserProfileID());
                    assertEquals(expectedURLs[it].getUrl(), urls[it].getUrl());
                }
            }
        }
    }
}
