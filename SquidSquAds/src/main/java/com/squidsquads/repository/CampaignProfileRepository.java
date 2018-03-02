package com.squidsquads.repository;

import com.squidsquads.model.CampaignProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampaignProfileRepository extends JpaRepository<CampaignProfile, Integer> {

    List<CampaignProfile> findAllByCampaignID(Integer campaignID);

    List<CampaignProfile> findAllByProfileID(Integer profileID);

    CampaignProfile findByCampaignIDAndProfileID(Integer campaignID, Integer profileID);

    void deleteAllByCampaignID(Integer campaignID);

}
