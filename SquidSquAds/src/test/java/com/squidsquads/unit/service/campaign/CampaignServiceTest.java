package com.squidsquads.unit.service.campaign;

import com.squidsquads.form.campaign.request.UpdateRequest;
import com.squidsquads.model.campaign.Campaign;
import com.squidsquads.repository.campaign.CampaignRepository;
import com.squidsquads.service.campaign.CampaignService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class CampaignServiceTest {

    @Autowired
    CampaignService campaignService;

    @Test
    public void testCampaignCreation(){
        CampaignRepository repository = mock(CampaignRepository.class);
        Campaign testCampaign = new Campaign(
                null, 0L,
                "testCampaign",
                "https://URL_TO_IMG_HOST",
                "https://URL_TO_IMG_HOST",
                "https://URL_TO_IMG_HOST",
                "http://www.google.ca",
                new Date(),
                new Date(),
                800,
                new Long[]{Long.valueOf(1), Long.valueOf(2), Long.valueOf(3)}
        );

        when(repository.save(testCampaign)).thenReturn(new Campaign(
                Long.valueOf(1),
                0L,
                "testCampaign",
                "https://URL_TO_IMG_HOST",
                "https://URL_TO_IMG_HOST",
                "https://URL_TO_IMG_HOST",
                "http://www.google.ca",
                new Date(),
                new Date(),
                800,
                new Long[]{Long.valueOf(1), Long.valueOf(2), Long.valueOf(3)})
        );
        // TODO complete.
    }
}
