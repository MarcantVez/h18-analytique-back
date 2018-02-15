package com.squidsquads.unit.service.campaign;

import com.squidsquads.form.campaign.request.CreateRequest;
import com.squidsquads.form.campaign.response.CreateResponse;
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



}
