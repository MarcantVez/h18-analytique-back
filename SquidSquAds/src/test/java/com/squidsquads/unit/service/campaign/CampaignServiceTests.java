package com.squidsquads.unit.service.campaign;

import com.squidsquads.form.campaign.request.CreateRequest;
import com.squidsquads.form.campaign.request.UpdateRequest;
import com.squidsquads.form.campaign.response.CreateResponse;
import com.squidsquads.form.campaign.response.DeleteResponse;
import com.squidsquads.form.campaign.response.ListResponse;
import com.squidsquads.form.campaign.response.ModifyResponse;
import com.squidsquads.model.Account;
import com.squidsquads.unit.service.AbstractServiceTests;
import com.squidsquads.utils.session.SessionManager;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class CampaignServiceTests extends AbstractServiceTests {

    private CampaignTestHelper helper = new CampaignTestHelper();

    private Account pub;
    private String token;

    @Before
    public void setup() {

        pub = helper.getAccountPub();
        token = SessionManager.getInstance().addSession(pub);
    }

    ////////////
    // Create //
    ////////////

    @Test
    public void createFailsWhenOneParameterIsNullOrEmpty() {
        CreateRequest req = helper.getCreateRequestWhereOneParameterIsNullOrEmpty();
        CreateResponse res = getCampaignService().create(token, req);

        assertEquals(HttpStatus.BAD_REQUEST, res.getStatus());
        assertEquals("Tous les champs requis doivent être remplis", res.getMessage());
    }

    @Test
    public void createFailsWhenCampaignNameAlreadyTaken() {

        when(getCampaignRepository().findByNameAndAccountID(anyString(), anyInt())).thenReturn(helper.getCampaign());

        CreateRequest req = helper.getCreateRequest();
        CreateResponse res = getCampaignService().create(token, req);

        assertEquals(HttpStatus.CONFLICT, res.getStatus());
        assertEquals("Nom de campagne déjà utilisé", res.getMessage());
    }

    @Test
    public void createFailsWhenCampaignNameIsTooLong() {
        CreateRequest req = helper.getCreateRequestWithNameTooLong();
        CreateResponse res = getCampaignService().create(token, req);

        assertEquals(HttpStatus.BAD_REQUEST, res.getStatus());
        assertEquals("Le nom de la campagne ne doit pas dépasser 100 caractères", res.getMessage());
    }

    @Test
    public void createFailsWhenHorizontalImgURlIsTooLong() {
        CreateRequest req = helper.getCreateRequestWithHorizontalImgURlTooLong();
        CreateResponse res = getCampaignService().create(token, req);

        assertEquals(HttpStatus.BAD_REQUEST, res.getStatus());
        assertEquals("Les url des l'images ne doit pas dépasser 100 caractères", res.getMessage());
    }

    @Test
    public void createFailsWhenVerticalImgURlIsTooLong() {
        CreateRequest req = helper.getCreateRequestWithVerticalImgURlTooLong();
        CreateResponse res = getCampaignService().create(token, req);

        assertEquals(HttpStatus.BAD_REQUEST, res.getStatus());
        assertEquals("Les url des l'images ne doit pas dépasser 100 caractères", res.getMessage());
    }

    @Test
    public void createFailsWhenMobileImgURlIsTooLong() {
        CreateRequest req = helper.getCreateRequestWithMobileImgURlTooLong();
        CreateResponse res = getCampaignService().create(token, req);

        assertEquals(HttpStatus.BAD_REQUEST, res.getStatus());
        assertEquals("Les url des l'images ne doit pas dépasser 100 caractères", res.getMessage());
    }

    @Test
    public void createFailsWhenRedirectURlIsTooLong() {
        CreateRequest req = helper.getCreateRequestWithRedirectURlTooLong();
        CreateResponse res = getCampaignService().create(token, req);

        assertEquals(HttpStatus.BAD_REQUEST, res.getStatus());
        assertEquals("Les url de redirection ne doit pas dépasser 100 caractères", res.getMessage());
    }

    @Test
    public void testCreate(){
        when(getCampaignRepository().findByNameAndAccountID(anyString(), anyInt())).thenReturn(null);
        CreateRequest req = helper.getCreateRequest();
        CreateResponse res = getCampaignService().create(token, req);

        assertEquals(HttpStatus.OK, res.getStatus());
        assertEquals("La campagne publicitaire a été créée", res.getMessage());
    }

    ////////////
    // Update //
    ////////////

    @Test
    public void  updateFailsWhenOneParameterIsNullOrEmpty() {
        when(getCampaignRepository().findOne(helper.getCampaign().getCampaignID())).thenReturn(helper.getCampaign());
        UpdateRequest req = helper.getUpdateRequestWhereOneParameterIsNullOrEmpty();
        ModifyResponse res = getCampaignService().modify(token, helper.getCampaign().getCampaignID(), req);

        assertEquals(HttpStatus.BAD_REQUEST, res.getStatus());
        assertEquals("Tous les champs requis doivent être remplis", res.getMessage());
    }

    @Test
    public void updateFailsWhenCampaignNameAlreadyTaken() {
        when(getCampaignRepository().findOne(helper.getCampaign().getCampaignID())).thenReturn(helper.getCampaign());
        when(getCampaignRepository().findByNameAndAccountID(anyString(), anyInt())).thenReturn(helper.getCampaign2());

        UpdateRequest req = helper.getUpdateRequest();
        ModifyResponse res = getCampaignService().modify(token, helper.getCampaign().getCampaignID(), req);

        assertEquals(HttpStatus.CONFLICT, res.getStatus());
        assertEquals("Nom de campagne déjà utilisé", res.getMessage());
    }

    @Test
    public void updateFailsWhenCampaignNameIsTooLong() {
        when(getCampaignRepository().findOne(helper.getCampaign().getCampaignID())).thenReturn(helper.getCampaign());
        UpdateRequest req = helper.getUpdateRequestWithNameTooLong();
        ModifyResponse res = getCampaignService().modify(token, helper.getCampaign().getCampaignID() ,req);

        assertEquals(HttpStatus.BAD_REQUEST, res.getStatus());
        assertEquals("Le nom de la campagne ne doit pas dépasser 100 caractères", res.getMessage());
    }

    @Test
    public void updateFailsWhenHorizontalImgURlIsTooLong() {
        when(getCampaignRepository().findOne(helper.getCampaign().getCampaignID())).thenReturn(helper.getCampaign());
        UpdateRequest req = helper.getUpdateRequestWithHorizontalImgURlTooLong();
        ModifyResponse res = getCampaignService().modify(token, helper.getCampaign().getCampaignID(), req);

        assertEquals(HttpStatus.BAD_REQUEST, res.getStatus());
        assertEquals("Les url des l'images ne doit pas dépasser 100 caractères", res.getMessage());
    }

    @Test
    public void updateFailsWhenVerticalImgURlIsTooLong() {
        when(getCampaignRepository().findOne(helper.getCampaign().getCampaignID())).thenReturn(helper.getCampaign());
        UpdateRequest req = helper.getUpdateRequestWithVerticalImgURlTooLong();
        ModifyResponse res = getCampaignService().modify(token, helper.getCampaign().getCampaignID() ,req);

        assertEquals(HttpStatus.BAD_REQUEST, res.getStatus());
        assertEquals("Les url des l'images ne doit pas dépasser 100 caractères", res.getMessage());
    }

    @Test
    public void updateFailsWhenMobileImgURlIsTooLong() {
        when(getCampaignRepository().findOne(helper.getCampaign().getCampaignID())).thenReturn(helper.getCampaign());
        UpdateRequest req = helper.getUpdateRequestWithMobileImgURlTooLong();
        ModifyResponse res = getCampaignService().modify(token, helper.getCampaign().getCampaignID() ,req);

        assertEquals(HttpStatus.BAD_REQUEST, res.getStatus());
        assertEquals("Les url des l'images ne doit pas dépasser 100 caractères", res.getMessage());
    }

    @Test
    public void updateFailsWhenRedirectURlIsTooLong() {
        when(getCampaignRepository().findOne(helper.getCampaign().getCampaignID())).thenReturn(helper.getCampaign());
        UpdateRequest req = helper.getUpdateRequestWithRedirectURlTooLong();
        ModifyResponse res = getCampaignService().modify(token, helper.getCampaign().getCampaignID() ,req);

        assertEquals(HttpStatus.BAD_REQUEST, res.getStatus());
        assertEquals("Les url de redirection ne doit pas dépasser 100 caractères", res.getMessage());
    }

    @Test
    public void testUpdate(){
        when(getCampaignRepository().findByNameAndAccountID(anyString(), anyInt())).thenReturn(helper.getCampaign());
        UpdateRequest req = helper.getUpdateRequest();
        ModifyResponse res = getCampaignService().modify(token, helper.getCampaign().getCampaignID(), req);

        assertEquals(HttpStatus.OK, res.getStatus());
        assertEquals("La campagne publicitaire a été modifiée", res.getMessage());
    }

    ////////////
    // Delete //
    ////////////

    @Test
    public void testDeleteNotFound() {
        when(getCampaignRepository().findOne(2)).thenReturn(null);
        DeleteResponse response = getCampaignService().delete(token, 2);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
        assertEquals("La campagne n'existe pas", response.getMessage());
    }

    @Test
    public void testDeleteUnauthorized() {
        when(getCampaignRepository().findOne(4)).thenReturn(helper.getCampaign2());
        DeleteResponse response = getCampaignService().delete(token, 4);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatus());
    }

    @Test
    public void testWorkingDelete() {
        when(getCampaignRepository().findOne(2)).thenReturn(helper.getCampaign());
        DeleteResponse response = getCampaignService().delete(token, 2);
        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals("La campagne a été supprimée avec succès", response.getMessage());
    }

}
