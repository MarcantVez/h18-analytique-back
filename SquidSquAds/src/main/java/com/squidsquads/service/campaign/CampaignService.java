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
import com.squidsquads.form.campaign.request.CampaignCreateRequest;
import com.squidsquads.utils.exception.campaign.CampaignException;
import com.squidsquads.utils.exception.campaign.CampaignFormatException;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
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
    public Campaign updateCampaign(Campaign Campagin) {
        try {
            return updateCampaignByID(Campagin.getCampaignId());
        } catch (NotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Modifier une
    public Campaign updateCampaignByID(Long campaignID) throws NotFoundException {
        Campaign campaign = findOneById(campaignID);
        if( campaign == null )
        {
            throw new NotFoundException("Campagne " + campaignID + " inexistante");
        }
        return campaignRepository.save(campaign);
    }

    public Campaign addCampaign(CampaignCreateRequest newCampaign) throws CampaignException {
        try {
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
            Campaign created = campaignRepository.save(campaign);
            for(long id : newCampaign.profileIds){
                campaignProfileRepository.save(new CampaignProfile(id, created.getCampaignId()));
            }
            return created;
        }catch (ParseException e) {
            throw new CampaignFormatException("Le format de date pour cette campagne est invalide");
        }
    }

    public void deleteCampaign(Campaign campaign) {
        try {
            deleteCampaignById(campaign.getCampaignId());
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
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
