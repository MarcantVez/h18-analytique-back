package com.squidsquads.service.campaign;

import com.squidsquads.form.campaign.response.CampaignListResponse;
import com.squidsquads.model.profile.Profile;
import com.squidsquads.repository.campaign.CampaignProfileRepository;
import com.squidsquads.repository.campaign.CampaignRepository;
import com.squidsquads.repository.profile.ProfileRepository;
import com.squidsquads.utils.DateFormatter;
import javassist.NotFoundException;
import com.squidsquads.model.campaign.Campaign;
import com.squidsquads.model.campaign.CampaignProfile;
import com.squidsquads.form.campaign.response.CampaignListResponseItem;
import org.springframework.beans.factory.annotation.Autowired;
import com.squidsquads.form.campaign.request.CampaignCreateUpdateRequest;
import com.squidsquads.utils.exception.campaign.CampaignException;
import com.squidsquads.utils.exception.campaign.CampaignFormatException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.util.*;

@Service
public class CampaignService {
    @Autowired
    CampaignRepository campaignRepository;
    @Autowired
    CampaignProfileRepository campaignProfileRepository;
    @Autowired
    ProfileRepository profileRepository;


    // Trouver les campagnes publicitaires d'un compte
    public CampaignListResponse findAllForAuthor(int accountId) {
        List<Campaign> campaigns = campaignRepository.findByAccountId(accountId);
        List<CampaignListResponseItem> responseList = new ArrayList<>();
        for (Campaign c : campaigns){
            responseList.add(
                new CampaignListResponseItem(
                    c.getCampaignId(),
                    c.getName(),
                    DateFormatter.DateToString(c.getCreationDate())
                )
            );
        }
        return new CampaignListResponse(200, responseList);
    }

    // Trouver une campagne publicitaire par son identificateur
    public Campaign findOneById(Long campaingID) {
        return campaignRepository.findOne(campaingID);
    }

    // Modifier une Campagne.
    @Transactional
    public Campaign updateCampaign(CampaignCreateUpdateRequest updatedCampaign) {
        Campaign campaign = new Campaign(
                updatedCampaign.campaignId,
                updatedCampaign.name,
                updatedCampaign.imageHor,
                updatedCampaign.imageVer,
                updatedCampaign.imageMob,
                updatedCampaign.redirectUrl,
                DateFormatter.StringToDate(updatedCampaign.dateDebut),
                DateFormatter.StringToDate(updatedCampaign.dateFin),
                updatedCampaign.budget,
                updatedCampaign.profileIds
        );
        campaignProfileRepository.deleteAllByCampaignID(updatedCampaign.campaignId);
        for(long id : updatedCampaign.profileIds){
            campaignProfileRepository.save(new CampaignProfile(id, updatedCampaign.campaignId));
        }
        campaign.setAccountId(0);
        return  campaignRepository.save(campaign);
    }

    public Campaign addCampaign(CampaignCreateUpdateRequest newCampaign) {
        Campaign campaign = new Campaign(
                null,
                newCampaign.name,
                newCampaign.imageHor,
                newCampaign.imageVer,
                newCampaign.imageMob,
                newCampaign.redirectUrl,
                DateFormatter.StringToDate(newCampaign.dateDebut),
                DateFormatter.StringToDate(newCampaign.dateFin),
                newCampaign.budget,
                newCampaign.profileIds
        );
        campaign.setAccountId(0);
        Campaign created = campaignRepository.save(campaign);
        for(long id : newCampaign.profileIds){
            campaignProfileRepository.save(new CampaignProfile(id, created.getCampaignId()));
        }
        return created;
    }

    public void deleteCampaignById(Long campaignId) throws NotFoundException {
        Campaign campaign = findOneById(campaignId);
        if(campaign == null)
        {
            throw new NotFoundException("Campagne " + campaign + " inexistante");
        } else {
            campaignRepository.delete(campaignId);
        }
    }
}
