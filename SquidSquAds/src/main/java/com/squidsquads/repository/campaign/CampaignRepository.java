package com.squidsquads.repository.campaign;

import com.squidsquads.model.campaign.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Long> {

    Campaign findByCampaignID(Long campaignID);

    List<Campaign> findByAccountID(Long accountID);

    Campaign findByNameAndAccountID(String name, Long accountID);
}