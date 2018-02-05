package service;
import model.Campaign;
import java.util.List;

public interface ICampaignService {
    List<Campaign> findAllForAuthor(Long accountId);

    Campaign findOneById(Long campaignID);

    Campaign updateCampaign(Campaign Campaign);

    Campaign addCampaign(Campaign campaign);

    void deleteCampaign(Campaign campaign);
}
