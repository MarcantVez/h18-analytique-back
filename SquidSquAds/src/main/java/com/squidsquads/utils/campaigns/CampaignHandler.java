package com.squidsquads.utils.campaigns;

import com.squidsquads.Application;
import com.squidsquads.model.BannerCampaign;
import com.squidsquads.model.Campaign;
import com.squidsquads.model.Royalty;
import com.squidsquads.model.Visit;
import com.squidsquads.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
@EnableScheduling
public class CampaignHandler {
    private static final Logger log = LoggerFactory.getLogger(CampaignHandler.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private VisitRepository visitRepository;

    @Autowired
    private RoyaltyRepository royaltyRepository;

    @Autowired
    private BannerCampaignRepository bannerCampaignRepository;

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Scheduled(fixedRate = 600000)
    public void reportCurrentTime() {
        List<Campaign> campaigns = campaignRepository.findAll();
        if (!campaigns.isEmpty()) {
            for (Campaign c : campaigns) {
                //Vérifier que la campagne non expirée
                c.handleActiveStatus();
                campaignRepository.save(c);
            }
        }
    }
}
