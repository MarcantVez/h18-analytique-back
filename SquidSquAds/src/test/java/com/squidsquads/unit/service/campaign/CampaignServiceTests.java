package com.squidsquads.unit.service.campaign;

import com.squidsquads.form.campaign.request.CreateRequest;
import com.squidsquads.form.campaign.response.CreateResponse;
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
    public void createFailsWhenBudgetIsNegative() {

        CreateRequest req = helper.getCreateRequestWhereBudgetIsNegative();
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

//    @Test
//    public void testCreateWithNullValues() {
//        CreateRequest request = new CreateRequest();
//        CreateResponse response = getCampaignService().create(token, request);
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
//        assertEquals(CreateResponse.MISSING_FIELDS, response.getMessage());
//    }
//
//    @Test
//    public void testCreateAlreadyExists() {
//        CreateRequest request = new CreateRequest();
//        request.setName("test1");
//        request.setRedirectUrl("redirecturl");
//        request.setProfileIds(new Integer[]{1, 2});
//        request.setStartDate("invalid date");
//        request.setEndDate("2018-12-12");
//        request.setBudget(BigDecimal.valueOf(4000));
//        request.setImgHorizontal("hor");
//        request.setImgVertical("ver");
//        request.setImgMobile("mob");
//        when(getCampaignService().campaignRepository.findByNameAndAccountID(request.getName(), 2)).thenReturn(getCampaign1());
//        CreateResponse response = getCampaignService().create(token, request);
//        assertEquals(HttpStatus.CONFLICT, response.getStatus());
//        assertEquals(CreateResponse.EXISTING_CAMPAIGN, response.getMessage());
//    }
//
//    @Test
//    public void testCreation() {
//        CreateRequest request = new CreateRequest();
//        request.setName("new");
//        request.setRedirectUrl("redirecturl");
//        request.setProfileIds(new Integer[]{1, 2});
//        request.setStartDate("2018-10-10");
//        request.setEndDate("2018-12-12");
//        request.setBudget(BigDecimal.valueOf(4000));
//        request.setImgHorizontal("hor");
//        request.setImgVertical("ver");
//        request.setImgMobile("mob");
//        Campaign created = new Campaign();
//        created.setCampaignID(19);
//        when(getCampaignService().campaignRepository.findByNameAndAccountID(request.getName(), 2)).thenReturn(null);
//        when(getCampaignService().campaignRepository.save(any(Campaign.class))).thenReturn(created);
//        when(getCampaignService().profileRepository.findByProfileIDAndAccountID(1, 2)).thenReturn(new UserProfile());
//        when(getCampaignService().profileRepository.findByProfileIDAndAccountID(2, 2)).thenReturn(new UserProfile());
//        when(getCampaignService().campaignProfileRepository.save(any(CampaignProfile.class))).thenReturn(null);
//        CreateResponse response = getCampaignService().create(token, request);
//        assertEquals(CreateResponse.SUCCESS, response.getMessage());
//        assertEquals(HttpStatus.OK, response.getStatus());
//    }
//
//    @Test
//    public void testGetAll() {
//        when(getCampaignService().campaignRepository.findByAccountID(2)).thenReturn(getCampaignList());
//        ListResponse response = getCampaignService().getAll(token);
//        assertEquals(response.getCampaigns().size(), getCampaignList().size());
//        assertEquals(HttpStatus.OK, response.getStatus());
//    }
//
//    @Test
//    public void testGetById() {
//        when(getCampaignService().campaignRepository.findOne(2)).thenReturn(getCampaign1());
//        when(getCampaignService().campaignProfileRepository.findAllByCampaignID(2)).thenReturn(getCampaignProfilesByCampaign());
//        InfoResponse response = getCampaignService().getByID(token, 2);
//        assertEquals(response.getProfileIds().length, getCampaign1().getProfileIds().length);
//        assertEquals(response.getName(), getCampaign1().getName());
//        assertEquals(HttpStatus.OK, response.getStatus());
//    }
//
//    @Test
//    public void testGetByWrongId() {
//        when(getCampaignService().campaignRepository.findOne(2)).thenReturn(null);
//        InfoResponse response = getCampaignService().getByID(token, 2);
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
//    }
//
//    @Test
//    public void testModifyWrongId() {
//        when(getCampaignService().campaignRepository.findOne(2)).thenReturn(null);
//        UpdateRequest updateRequest = new UpdateRequest();
//        ModifyResponse response = getCampaignService().modify(token, 2, updateRequest);
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
//        assertEquals(ModifyResponse.CAMPAIGN_NOT_FOUND, response.getMessage());
//    }
//
//    @Test
//    public void testModifyWrongDate() {
//        when(getCampaignService().campaignRepository.findOne(2)).thenReturn(getCampaign1());
//        UpdateRequest updateRequest = new UpdateRequest();
//        updateRequest.setBudget(BigDecimal.valueOf(100));
//        updateRequest.setName("updated");
//        updateRequest.setProfileIds(new Integer[]{1, 2, 4});
//        updateRequest.setImgHorizontal("hor");
//        updateRequest.setImgVertical("vert");
//        updateRequest.setImgMobile("mobile");
//        updateRequest.setRedirectUrl("url");
//        updateRequest.setStartDate("WRONG_DATE");
//        updateRequest.setEndDate("WRONG_DATE");
//        ModifyResponse response = getCampaignService().modify(token, 2, updateRequest);
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
//        assertEquals(ModifyResponse.INVALID_DATE_FORMAT, response.getMessage());
//    }
//
//    @Test
//    public void testModifyMissingFields() {
//        when(getCampaignService().campaignRepository.findOne(2)).thenReturn(getCampaign1());
//        UpdateRequest updateRequest = new UpdateRequest();
//        updateRequest.setStartDate("2018-02-23");
//        updateRequest.setEndDate("2018-03-23");
//        ModifyResponse response = getCampaignService().modify(token, 2, updateRequest);
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
//        assertEquals(ModifyResponse.MISSING_FIELDS, response.getMessage());
//    }
//
//    @Test
//    public void testModifyCorrect() {
//        when(getCampaignService().campaignRepository.findOne(2)).thenReturn(getCampaign1());
//        UpdateRequest updateRequest = new UpdateRequest();
//        updateRequest.setBudget(BigDecimal.valueOf(100));
//        updateRequest.setName("updated");
//        updateRequest.setProfileIds(new Integer[]{1, 2, 4});
//        updateRequest.setImgHorizontal("hor");
//        updateRequest.setImgVertical("vert");
//        updateRequest.setImgMobile("mobile");
//        updateRequest.setRedirectUrl("url");
//        updateRequest.setStartDate("2018-02-23");
//        updateRequest.setEndDate("2018-03-23");
//        ModifyResponse response = getCampaignService().modify(token, 2, updateRequest);
//        assertEquals(HttpStatus.OK, response.getStatus());
//        assertEquals(ModifyResponse.SUCCESS, response.getMessage());
//    }
//
//    @Test
//    public void testDeleteNotFound() {
//        when(getCampaignService().campaignRepository.findOne(2)).thenReturn(null);
//        DeleteResponse response = getCampaignService().delete(token, 2);
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
//        assertEquals(DeleteResponse.CAMPAGNE_NOT_FOUND, response.getMessage());
//    }
//
//    @Test
//    public void testDeleteUnauthorized() {
//        when(getCampaignService().campaignRepository.findOne(4)).thenReturn(getCampaign4());
//        DeleteResponse response = getCampaignService().delete(token, 4);
//        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatus());
//    }
//
//    @Test
//    public void testWorkingDelete() {
//        when(getCampaignService().campaignRepository.findOne(2)).thenReturn(getCampaign1());
//        DeleteResponse response = getCampaignService().delete(token, 2);
//        assertEquals(HttpStatus.OK, response.getStatus());
//        assertEquals(DeleteResponse.SUCCESS, response.getMessage());
//    }

}
