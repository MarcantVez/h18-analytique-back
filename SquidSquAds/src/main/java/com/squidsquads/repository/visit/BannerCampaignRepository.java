package com.squidsquads.repository.visit;

import com.squidsquads.model.traffic.BannerCampaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BannerCampaignRepository extends JpaRepository<BannerCampaign, Long> {

}
