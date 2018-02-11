package com.squidsquads.repository.campaign;

import com.squidsquads.model.campaign.CampaignProfile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampaignProfileRepository extends CrudRepository<CampaignProfile, Long> {

    List<CampaignProfile> findAllByCampaignID(Long campaignID);

    List<CampaignProfile> findAllByProfileID(Long profileID);

    CampaignProfile findByCampaignIDAndProfileID(Long campaignID, Long profileID);

    void deleteAllByCampaignID(Long campaignID);

}
