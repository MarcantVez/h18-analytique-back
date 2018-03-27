package com.squidsquads.utils.campaigns;

import com.squidsquads.model.Campaign;
import com.squidsquads.repository.CampaignRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author: Dulce Cayetano
 * @Date_Of_Creation: 2018-03-22
 * @Last_modified_by:
 * @Date_of_last_modification:
 **/
@Component
public class CampaignHandler {
    private static final Logger log = LoggerFactory.getLogger(CampaignHandler.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    private CampaignRepository campaignRepository;

    @Scheduled(fixedRate = 600000)
    public void reportCurrentTime() {
        List<Campaign> campaigns = campaignRepository.findAll();
        if (!campaigns.isEmpty()) {
            for (Campaign c : campaigns) {
                //Vérifier que la campagne est active
                c.handleActiveStatus();
                log.debug("The campaign " + c.getName() + "is active = " + c.isActive(), dateFormat.format(new Date()));
            }
        } else {
            log.debug("No active campaigns were found.", dateFormat.format(new Date()));
        }
    }
}
