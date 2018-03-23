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

//    @Scheduled(fixedRate = 120000)
//    public void reportCurrentTime() {
//        List<Campaign> campaigns = campaignRepository.findAll();
//
//        if (!campaigns.isEmpty()) {
//            for (Campaign c : campaigns) {
//                //Vérifier que la campagne est active
//                System.out.println("Verification de la camapagne: " + c.getName());
//                boolean isActive = false;
//                Date currentDate = new Date();
//                if (currentDate.equals(c.getStartDate()) || currentDate.equals(c.getEndDate())) {
//                    isActive = true;
//                } else if (currentDate.after(c.getStartDate()) && currentDate.before(c.getEndDate())) {
//                    isActive = true;
//                }
//                System.out.println("La campagne " + c.getName() + " est active = " + isActive);
//                c.setActive(isActive);
//                log.info("The campaign " + c.getName() + "is active = " + isActive, dateFormat.format(new Date()));
//            }
//        } else {
//            System.out.println("No campaigns were found");
//        }
//    }
}
