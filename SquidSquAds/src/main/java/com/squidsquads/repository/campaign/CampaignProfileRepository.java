package com.squidsquads.repository.campaign;

import com.squidsquads.model.campaign.CampaignProfile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampaignProfileRepository extends CrudRepository<CampaignProfile, Long> {

    List<CampaignProfile> findAllByCampaignID(long campaignId);

    List<CampaignProfile> findAllByProfileID(long profileId);

    CampaignProfile findByCampaignIDAndProfileID(long campaignId, long ProfileId);

    void deleteAllByCampaignID(long campaignId);

}
