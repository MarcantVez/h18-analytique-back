package service.campaign;

import repository.campaign.CampaignPreviewRepository;
import repository.campaign.CampaignProfileRepository;
import repository.campaign.CampaignRepository;
import javassist.NotFoundException;
import model.campaign.Campaign;
import model.campaign.CampaignProfile;
import form.campaign.response.CampaignListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import restForm.CampaignCreateForm;
import exception.campaign.CampaignException;
import exception.campaign.CampaignFormatException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

@Component
public class CampaignService {
    @Autowired
    CampaignRepository campaignRepository;
    @Autowired
    CampaignPreviewRepository campaignPreviewRepository;
    @Autowired
    CampaignProfileRepository campaignProfileRepository;


    // Trouver les campagnes publicitaires d'un compte
    public List<CampaignListResponse> findAllForAuthor(Long accountId) {
        return campaignPreviewRepository.findAllByAccountId(accountId);
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

    public Campaign addCampaign(CampaignCreateForm newCampaign) throws CampaignException {
        DateFormat format = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
        try {
            Campaign campaign = new Campaign(
                    newCampaign.name,
                    newCampaign.imageHor,
                    newCampaign.imageVer,
                    newCampaign.imageMob,
                    newCampaign.redirectUrl,
                    format.parse(newCampaign.dateDebut),
                    format.parse(newCampaign.dateFin),
                    newCampaign.budget,
                    newCampaign.profileIds
            );
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
