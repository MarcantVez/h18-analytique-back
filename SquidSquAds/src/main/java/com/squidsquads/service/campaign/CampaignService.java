package com.squidsquads.service.campaign;

import com.squidsquads.form.campaign.response.CampaignListResponse;
import com.squidsquads.repository.campaign.CampaignProfileRepository;
import com.squidsquads.repository.campaign.CampaignRepository;
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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CampaignService {
    @Autowired
    CampaignRepository campaignRepository;
    @Autowired
    CampaignProfileRepository campaignProfileRepository;


    // Trouver les campagnes publicitaires d'un compte
    public CampaignListResponse findAllForAuthor(int accountId) {
        List<Campaign> campaigns = campaignRepository.findByAccountId(accountId);
        List<CampaignListResponseItem> responseList = new ArrayList<>();
        for (Campaign c : campaigns){
            responseList.add(
                new CampaignListResponseItem(
                    c.getCampaignId(),
                    c.getName(),
                    DateFormatter.DateToString((Date) c.getCreationDate())
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
    public Campaign updateCampaign(CampaignCreateUpdateRequest updatedCampaign) {
        Campaign campaign = new Campaign(
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
        campaign.setAccountId(0);
        campaign.setCreationDate(new Date());
        return campaignRepository.save(campaign);
    }

    public Campaign addCampaign(CampaignCreateUpdateRequest newCampaign) {
        Campaign campaign = new Campaign(
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
        campaign.setCreationDate(new Date());
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
