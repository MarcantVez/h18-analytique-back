package com.squidsquads.service.campaign;

import com.squidsquads.form.campaign.request.CreateRequest;
import com.squidsquads.form.campaign.request.UpdateRequest;
import com.squidsquads.form.campaign.response.*;
import com.squidsquads.form.validator.CampaignCreateValidator;
import com.squidsquads.model.campaign.Campaign;
import com.squidsquads.model.campaign.CampaignProfile;
import com.squidsquads.repository.campaign.CampaignProfileRepository;
import com.squidsquads.repository.campaign.CampaignRepository;
import com.squidsquads.repository.userProfile.UserProfileRepository;
import com.squidsquads.utils.DateFormatter;
import com.squidsquads.utils.session.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CampaignService {

    private static final Logger logger = LoggerFactory.getLogger(CampaignService.class);

    @Autowired
    CampaignRepository campaignRepository;

    @Autowired
    CampaignProfileRepository campaignProfileRepository;

    @Autowired
    UserProfileRepository profileRepository;

    /**
     * Trouver un profil utilisateur en fonction de son ID
     */
    private Campaign findByNameAndAccountID(String name, Long accountID) {
        return campaignRepository.findByNameAndAccountID(name, accountID);
    }

    /**
     * Trouver les campagnes publicitaires d'un compte
     */
    public ListResponse getAll(String token) {

        Long accountID = SessionManager.getInstance().getAccountIdForToken(token);

        // Si le compte n'a pas de session ici, c'est un probleme serveur
        if (accountID == SessionManager.NO_SESSION) {
            logger.error("Un compte basé sur un token de session est introuvable");
            return new ListResponse().failed();
        }

        List<Campaign> campaigns = campaignRepository.findByAccountID(accountID);
        List<ListResponseItem> responseList = new ArrayList<>();

        for (Campaign c : campaigns) {
            responseList.add(
                    new ListResponseItem(
                            c.getCampaignID(),
                            c.getName(),
                            DateFormatter.DateToString(c.getCreationDate())
                    )
            );
        }

        return new ListResponse().ok(responseList);
    }

    /**
     * Trouver une campagne publicitaire par son identificateur
     */
    public InfoResponse getByID(String token, Long campaingID) {

        Long accountID = SessionManager.getInstance().getAccountIdForToken(token);

        // Si le compte n'a pas de session ici, c'est un probleme serveur
        if (accountID == SessionManager.NO_SESSION) {
            logger.error("Un compte basé sur un token de session est introuvable");
            return new InfoResponse().failed();
        }

        Campaign campaign = campaignRepository.findOne(campaingID);
        if (campaign == null || campaign.getAccountID() != accountID) {
            return new InfoResponse().notFound();
        }

        // GET linked profiles for campaign
        List<CampaignProfile> profiles = campaignProfileRepository.findAllByCampaignID(campaingID);
        Long[] profileIDs = new Long[profiles.size()];

        for (int i = 0; i < profiles.size(); i++) {
            profileIDs[i] = profiles.get(i).getProfileID();
        }
        campaign.setProfileIds(profileIDs);

        return new InfoResponse().ok(campaign);
    }

    /**
     * Modifier une campagne
     */
    @Transactional
    public ModifyResponse modify(String token, Long campaignID, UpdateRequest updatedCampaign) {

        Long accountID = SessionManager.getInstance().getAccountIdForToken(token);

        // Si le compte n'a pas de session ici, c'est un probleme serveur
        if (accountID == SessionManager.NO_SESSION) {
            logger.error("Un compte basé sur un token de session est introuvable");
            return new ModifyResponse().failed();
        }

        if (campaignRepository.findByCampaignID(campaignID) == null) {
            return new ModifyResponse().notFound();
        }

        Campaign campaign = new Campaign(
                campaignID,
                accountID,
                updatedCampaign.getName(),
                updatedCampaign.getImgHorizontal(),
                updatedCampaign.getImgVertical(),
                updatedCampaign.getImgMobile(),
                updatedCampaign.getRedirectUrl(),
                DateFormatter.StringToDate(updatedCampaign.getStartDate()),
                DateFormatter.StringToDate(updatedCampaign.getEndDate()),
                updatedCampaign.getBudget(),
                updatedCampaign.getProfileIds()
        );
        campaignProfileRepository.deleteAllByCampaignID(campaignID);

        for (long id : updatedCampaign.getProfileIds()) {
            campaignProfileRepository.save(new CampaignProfile(id, campaignID));
        }

        campaignRepository.save(campaign);

        return new ModifyResponse().ok();
    }

    /**
     * Créer une campagne
     */
    @Transactional
    public CreateResponse create(String token, CreateRequest request) {

        Long accountID = SessionManager.getInstance().getAccountIdForToken(token);

        // Si le compte n'a pas de session ici, c'est un probleme serveur
        if (accountID == SessionManager.NO_SESSION) {
            logger.error("Un compte basé sur un token de session est introuvable");
            return new CreateResponse().failed();
        }

        if (!CampaignCreateValidator.isCreateRequestComplete(request)) {
            return new CreateResponse().fieldsMissing();
        }

        // Si le nom de profile existe déjà pour l'utilisateur courrant
        if (findByNameAndAccountID(request.getName(), accountID) != null) {
            return new CreateResponse().campaignAlreadyExists();
        }

        Campaign campaign = new Campaign(
                null,
                accountID,
                request.getName(),
                request.getImgHorizontal(),
                request.getImgVertical(),
                request.getImgMobile(),
                request.getRedirectUrl(),
                DateFormatter.StringToDate(request.getStartDate()),
                DateFormatter.StringToDate(request.getEndDate()),
                request.getBudget(),
                request.getProfileIds()
        );

        Campaign created = campaignRepository.save(campaign);

        for (long id : request.getProfileIds()) {
            // TODO : Valider si le profil ID existe
            campaignProfileRepository.save(new CampaignProfile(id, created.getCampaignID()));
        }

        return new CreateResponse().ok();
    }

    /**
     * Supprimer une campagne
     */
    @Transactional
    public DeleteResponse delete(String token, Long campaignId) {

        Long accountID = SessionManager.getInstance().getAccountIdForToken(token);

        // Si le compte n'a pas de session ici, c'est un probleme serveur
        if (accountID == SessionManager.NO_SESSION) {
            logger.error("Un compte basé sur un token de session est introuvable");
            return new DeleteResponse().failed();
        }

        Campaign campaign = campaignRepository.findOne(campaignId);

        if (campaign == null) {
            return new DeleteResponse().notFound();
        }

        if (!campaign.getAccountID().equals(accountID)) {
            return new DeleteResponse().failed();
        }

        campaignProfileRepository.deleteAllByCampaignID(campaignId);
        campaignRepository.delete(campaignId);

        return new DeleteResponse().ok();
    }
}
