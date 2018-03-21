package com.squidsquads.repository;

import com.squidsquads.model.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Integer> {

    List<Campaign> findByAccountID(Integer accountID);

    Campaign findByCampaignID(Integer campaignID);

    Campaign findByNameAndAccountID(String name, Integer accountID);

    List<Campaign> findAll();
}