package service;

import javassist.NotFoundException;
import model.Campaign;
import org.springframework.beans.factory.annotation.Autowired;
import repository.CampaignRepository;

import java.util.List;

public class CampaignService implements ICampaignService {
    @Autowired
    CampaignRepository campaignRepository;

    // Trouver les campagnes publicitaires d'un compte
    @Override
    public List<Campaign> findAllForAuthor(Long accountId) {
        return campaignRepository.findByAuthor(accountId);
    }

    // Trouver une campagne publicitaire par son identificateur
    @Override
    public Campaign findOneById(Long campaingID) {
        return campaignRepository.findOne(campaingID);
    }

    // Modifier une Campagne.
    @Override
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

    @Override
    public Campaign addCampaign(Campaign campaign) {
        return campaignRepository.save(campaign);
    }

    @Override
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
