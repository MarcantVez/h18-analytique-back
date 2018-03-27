package com.squidsquads.repository;

import com.squidsquads.model.BannerCampaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BannerCampaignRepository extends JpaRepository<BannerCampaign, Integer> {
    List<BannerCampaign> findAllByCampaignID(Integer CampaignID);
}
