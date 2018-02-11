package com.squidsquads.service.campaign;

import com.squidsquads.form.campaign.request.CreateRequest;
import com.squidsquads.form.campaign.request.UpdateRequest;
import com.squidsquads.form.campaign.response.CampaignDeleteResponse;
import com.squidsquads.form.campaign.response.CampaignDetailResponse;
import com.squidsquads.form.campaign.response.CampaignListResponse;
import com.squidsquads.form.campaign.response.CampaignListResponseItem;
import com.squidsquads.form.validator.CampaignCreateValidator;
import com.squidsquads.model.account.AdminType;
import com.squidsquads.model.campaign.Campaign;
import com.squidsquads.model.campaign.CampaignProfile;
import com.squidsquads.repository.campaign.CampaignProfileRepository;
import com.squidsquads.repository.campaign.CampaignRepository;
import com.squidsquads.repository.userProfile.UserProfileRepository;
import com.squidsquads.utils.DateFormatter;
import com.squidsquads.utils.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class CampaignService {

    @Autowired
    CampaignRepository campaignRepository;
    @Autowired
    CampaignProfileRepository campaignProfileRepository;
    @Autowired
    UserProfileRepository profileRepository;

    /**
     * Trouver les campagnes publicitaires d'un compte
     */
    public CampaignListResponse findAllForAuthor(String token) {

        Long accountID = SessionManager.getInstance().getAccountIdForToken(token);
        AdminType adminType = SessionManager.getInstance().getAdminTypeForToken(token);

        if (accountID == SessionManager.NO_SESSION || adminType != AdminType.PUB) {
            return new CampaignListResponse().unauthorized();
        }

        List<Campaign> campaigns = campaignRepository.findByAccountId(accountID);
        List<CampaignListResponseItem> responseList = new ArrayList<>();

        for (Campaign c : campaigns) {
            responseList.add(
                    new CampaignListResponseItem(
                            c.getCampaignId(),
                            c.getName(),
                            DateFormatter.DateToString(c.getCreationDate())
                    )
            );
        }

        return new CampaignListResponse().ok(responseList);
    }

    /**
     * Trouver une campagne publicitaire par son identificateur
     */
    public CampaignDetailResponse findOneById(String token, Long campaingID) {

        Long accountID = SessionManager.getInstance().getAccountIdForToken(token);
        AdminType adminType = SessionManager.getInstance().getAdminTypeForToken(token);

        if (accountID == SessionManager.NO_SESSION || adminType != AdminType.PUB) {
            return new CampaignDetailResponse().unauthorized();
        }

        Campaign campaign = campaignRepository.findOne(campaingID);
        if (campaign == null || campaign.getAccountId() != accountID) {
            return new CampaignDetailResponse().notFound();
        }

        // GET linked profiles for campaign
        List<CampaignProfile> profiles = campaignProfileRepository.findAllByCampaignID(campaingID);
        Long[] profileIDs = new Long[profiles.size()];

        for (int i = 0; i < profiles.size(); i++) {
            profileIDs[i] = profiles.get(i).getProfileID();
        }
        campaign.setProfileIds(profileIDs);

        return new CampaignDetailResponse().ok(campaign);
    }

    /**
     * Modifier une campagne
     */
    @Transactional
    public CampaignDetailResponse updateCampaign(String token, Long campaignID, UpdateRequest updatedCampaign) {

        Long accountID = SessionManager.getInstance().getAccountIdForToken(token);
        AdminType adminType = SessionManager.getInstance().getAdminTypeForToken(token);

        if (accountID == SessionManager.NO_SESSION || adminType != AdminType.PUB) {
            return new CampaignDetailResponse().unauthorized();
        }

        if (campaignID != updatedCampaign.getCampaignId()) {
            return new CampaignDetailResponse().fieldsMissing();
        }

        Campaign campaign = new Campaign(
                updatedCampaign.getCampaignId(),
                accountID,
                updatedCampaign.getName(),
                updatedCampaign.getImageHor(),
                updatedCampaign.getImageVer(),
                updatedCampaign.getImageMob(),
                updatedCampaign.getRedirectUrl(),
                DateFormatter.StringToDate(updatedCampaign.getStartDate()),
                DateFormatter.StringToDate(updatedCampaign.getEndDate()),
                updatedCampaign.getBudget(),
                updatedCampaign.getProfileIds()
        );
        campaignProfileRepository.deleteAllByCampaignID(updatedCampaign.getCampaignId());

        for (long id : updatedCampaign.getProfileIds()) {
            campaignProfileRepository.save(new CampaignProfile(id, updatedCampaign.getCampaignId()));
        }

        Campaign updated = campaignRepository.save(campaign);

        return new CampaignDetailResponse().ok(updated);
    }

    /**
     * Créer une campagne
     */
    public CampaignDetailResponse addCampaign(String token, CreateRequest newCampaign) {

        Long accountID = SessionManager.getInstance().getAccountIdForToken(token);
        AdminType adminType = SessionManager.getInstance().getAdminTypeForToken(token);

        if (accountID == SessionManager.NO_SESSION || adminType != AdminType.PUB) {
            return new CampaignDetailResponse().unauthorized();
        }

        if (!CampaignCreateValidator.isCreateRequestComplete(newCampaign)) {
            return new CampaignDetailResponse().fieldsMissing();
        }

        Campaign campaign = new Campaign(
                null,
                accountID,
                newCampaign.getName(),
                newCampaign.getImageHor(),
                newCampaign.getImageVer(),
                newCampaign.getImageMob(),
                newCampaign.getRedirectUrl(),
                DateFormatter.StringToDate(newCampaign.getStartDate()),
                DateFormatter.StringToDate(newCampaign.getEndDate()),
                newCampaign.getBudget(),
                newCampaign.getProfileIds()
        );

        Campaign created = campaignRepository.save(campaign);

        for (long id : newCampaign.getProfileIds()) {
            campaignProfileRepository.save(new CampaignProfile(id, created.getCampaignId()));
        }
        created.setProfileIds(campaign.getProfileIds());

        return new CampaignDetailResponse().ok(created);
    }

    /**
     * Supprimer une campagne
     */
    public CampaignDeleteResponse deleteCampaignById(String token, Long campaignId) {

        Long accountID = SessionManager.getInstance().getAccountIdForToken(token);
        AdminType adminType = SessionManager.getInstance().getAdminTypeForToken(token);

        if (accountID == SessionManager.NO_SESSION || adminType != AdminType.PUB) {
            return new CampaignDeleteResponse().unauthorized();
        }

        Campaign campaign = campaignRepository.findOne(campaignId);

        if (campaign == null) {
            return new CampaignDeleteResponse().notFound();
        }

        if (!campaign.getAccountId().equals(accountID)) {
            return new CampaignDeleteResponse().unauthorized();
        }

        campaignRepository.delete(campaignId);

        return new CampaignDeleteResponse().ok();
    }
}
