package com.squidsquads.unit.service.campaign;

import com.squidsquads.form.campaign.request.CreateRequest;
import com.squidsquads.form.campaign.request.UpdateRequest;
import com.squidsquads.form.campaign.response.*;
import com.squidsquads.model.account.Account;
import com.squidsquads.model.campaign.Campaign;
import com.squidsquads.model.campaign.CampaignProfile;
import com.squidsquads.model.profile.UserProfile;
import com.squidsquads.repository.campaign.CampaignProfileRepository;
import com.squidsquads.repository.campaign.CampaignRepository;
import com.squidsquads.repository.userProfile.UserProfileRepository;
import com.squidsquads.unit.service.AbstractPubAdminTest;
import com.squidsquads.utils.session.SessionManager;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class CampaignServiceTest extends AbstractPubAdminTest {

    String accountToken;

    @Before
    public void setup(){
        init();
        Account testAccount = new Account("PUB", "test@test.com", "1234", "0000-111111");
        testAccount.setAccountID(2L);
        accountToken = SessionManager.getInstance().addSession(testAccount);

        getCampaignService().campaignRepository = mock(CampaignRepository.class);
        getCampaignService().campaignProfileRepository = mock(CampaignProfileRepository.class);
        getCampaignService().profileRepository = mock(UserProfileRepository.class);
    }

    @Test
    public void testInvalidDateFormat(){
        CreateRequest request = new CreateRequest();
        request.setName("new");
        request.setRedirectUrl("redirecturl");
        request.setProfileIds(new Long[] {1L,2L});
        request.setStartDate("invalid date");
        request.setEndDate("2018-12-12");
        request.setBudget(BigDecimal.valueOf(4000));
        request.setImgHorizontal("hor");
        request.setImgVertical("ver");
        request.setImgMobile("mob");
        when(getCampaignService().campaignRepository.findByNameAndAccountID(request.getName(), 2L)).thenReturn(null);
        CreateResponse response = super.getCampaignService().create(accountToken,request);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
        assertEquals(CreateResponse.INVALID_DATE_FORMAT, response.getMessage());
    }

    @Test
    public void testCreateWithNullValues(){
        CreateRequest request = new CreateRequest();
        CreateResponse response = getCampaignService().create(accountToken,request);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
        assertEquals(CreateResponse.MISSING_FIELDS, response.getMessage());
    }

    @Test
    public void testCreateAlreadyExists(){
        CreateRequest request = new CreateRequest();
        request.setName("test1");
        request.setRedirectUrl("redirecturl");
        request.setProfileIds(new Long[] {1L,2L});
        request.setStartDate("invalid date");
        request.setEndDate("2018-12-12");
        request.setBudget(BigDecimal.valueOf(4000));
        request.setImgHorizontal("hor");
        request.setImgVertical("ver");
        request.setImgMobile("mob");
        when(getCampaignService().campaignRepository.findByNameAndAccountID(request.getName(), 2L)).thenReturn(getCampaign1());
        CreateResponse response = getCampaignService().create(accountToken,request);
        assertEquals(HttpStatus.CONFLICT, response.getStatus());
        assertEquals(CreateResponse.EXISTING_CAMPAIGN, response.getMessage());
    }

    @Test
    public void testCreation(){
        CreateRequest request = new CreateRequest();
        request.setName("new");
        request.setRedirectUrl("redirecturl");
        request.setProfileIds(new Long[] {1L,2L});
        request.setStartDate("2018-10-10");
        request.setEndDate("2018-12-12");
        request.setBudget(BigDecimal.valueOf(4000));
        request.setImgHorizontal("hor");
        request.setImgVertical("ver");
        request.setImgMobile("mob");
        Campaign created = new Campaign();
        created.setCampaignID(19L);
        when(getCampaignService().campaignRepository.findByNameAndAccountID(request.getName(), 2L)).thenReturn(null);
        when(getCampaignService().campaignRepository.save(any(Campaign.class))).thenReturn(created);
        when(getCampaignService().profileRepository.findByProfileIDAndAccountID(1L, 2L)).thenReturn(new UserProfile());
        when(getCampaignService().profileRepository.findByProfileIDAndAccountID(2L, 2L)).thenReturn(new UserProfile());
        when(getCampaignService().campaignProfileRepository.save(any(CampaignProfile.class))).thenReturn(null);
        CreateResponse response = getCampaignService().create(accountToken, request);
        assertEquals(CreateResponse.SUCCESS, response.getMessage());
        assertEquals(HttpStatus.OK, response.getStatus());
    }

    @Test
    public void testGetAll(){
        when(getCampaignService().campaignRepository.findByAccountID(2L)).thenReturn(getCampaignList());
        ListResponse response = getCampaignService().getAll(accountToken);
        assertEquals(response.getCampaigns().size(), getCampaignList().size());
        assertEquals(HttpStatus.OK, response.getStatus());
    }

    @Test
    public void testGetById(){
        when(getCampaignService().campaignRepository.findOne(2L)).thenReturn(getCampaign1());
        when(getCampaignService().campaignProfileRepository.findAllByCampaignID(2L)).thenReturn(getCampaignProfilesByCampaign());
        InfoResponse response = getCampaignService().getByID(accountToken, 2L);
        assertEquals(response.getProfileIds().length, getCampaign1().getProfileIds().length);
        assertEquals(response.getName(), getCampaign1().getName());
        assertEquals(HttpStatus.OK, response.getStatus());
    }

    @Test
    public void testGetByWrongId(){
        when(getCampaignService().campaignRepository.findOne(2L)).thenReturn(null);
        InfoResponse response = getCampaignService().getByID(accountToken, 2L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
    }

    @Test
    public void testModifyWrongId(){
        when(getCampaignService().campaignRepository.findOne(2L)).thenReturn(null);
        UpdateRequest updateRequest = new UpdateRequest();
        ModifyResponse response = getCampaignService().modify(accountToken, 2L, updateRequest);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
        assertEquals(ModifyResponse.CAMPAIGN_NOT_FOUND, response.getMessage());
    }

    @Test
    public void testModifyWrongDate(){
        when(getCampaignService().campaignRepository.findOne(2L)).thenReturn(getCampaign1());
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.setBudget(BigDecimal.valueOf(100));
        updateRequest.setName("updated");
        updateRequest.setProfileIds(new Long[]{1L,2L,4L});
        updateRequest.setImgHorizontal("hor");
        updateRequest.setImgVertical("vert");
        updateRequest.setImgMobile("mobile");
        updateRequest.setRedirectUrl("url");
        updateRequest.setStartDate("WRONG_DATE");
        updateRequest.setEndDate("WRONG_DATE");
        ModifyResponse response = getCampaignService().modify(accountToken, 2L, updateRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
        assertEquals(ModifyResponse.INVALID_DATE_FORMAT, response.getMessage());
    }

    @Test
    public void testModifyMissingFields(){
        when(getCampaignService().campaignRepository.findOne(2L)).thenReturn(getCampaign1());
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.setStartDate("2018-02-23");
        updateRequest.setEndDate("2018-03-23");
        ModifyResponse response = getCampaignService().modify(accountToken, 2L, updateRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
        assertEquals(ModifyResponse.MISSING_FIELDS, response.getMessage());
    }

    @Test
    public void testModifyCorrect(){
        when(getCampaignService().campaignRepository.findOne(2L)).thenReturn(getCampaign1());
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.setBudget(BigDecimal.valueOf(100));
        updateRequest.setName("updated");
        updateRequest.setProfileIds(new Long[]{1L,2L,4L});
        updateRequest.setImgHorizontal("hor");
        updateRequest.setImgVertical("vert");
        updateRequest.setImgMobile("mobile");
        updateRequest.setRedirectUrl("url");
        updateRequest.setStartDate("2018-02-23");
        updateRequest.setEndDate("2018-03-23");
        ModifyResponse response = getCampaignService().modify(accountToken, 2L, updateRequest);
        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(ModifyResponse.SUCCESS, response.getMessage());
    }

    @Test
    public void testDeleteNotFound(){
        when(getCampaignService().campaignRepository.findOne(2L)).thenReturn(null);
        DeleteResponse response = getCampaignService().delete(accountToken, 2L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
        assertEquals(DeleteResponse.CAMPAGNE_NOT_FOUND, response.getMessage());
    }

    @Test
    public void testDeleteUnauthorized(){
        when(getCampaignService().campaignRepository.findOne(4L)).thenReturn(getCampaign4());
        DeleteResponse response = getCampaignService().delete(accountToken, 4L);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatus());
    }

    @Test
    public void testWorkingDelete(){
        when(getCampaignService().campaignRepository.findOne(2L)).thenReturn(getCampaign1());
        DeleteResponse response = getCampaignService().delete(accountToken, 2L);
        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(DeleteResponse.SUCCESS, response.getMessage());
    }



}
