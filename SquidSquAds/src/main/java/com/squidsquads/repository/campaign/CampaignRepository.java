package com.squidsquads.repository.campaign;

import com.squidsquads.model.campaign.Campaign;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampaignRepository extends CrudRepository<Campaign, Long> {

    Campaign findByCampaignID(Long campaignID);

    List<Campaign> findByAccountID(Long accountID);

    Campaign findByNameAndAccountID(String name, Long accountID);
}