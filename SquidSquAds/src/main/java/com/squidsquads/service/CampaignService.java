package com.squidsquads.service;

import com.squidsquads.form.campaign.request.CreateRequest;
import com.squidsquads.form.campaign.request.UpdateRequest;
import com.squidsquads.form.campaign.response.*;
import com.squidsquads.form.validator.CampaignValidator;
import com.squidsquads.model.Campaign;
import com.squidsquads.model.CampaignProfile;
import com.squidsquads.repository.CampaignProfileRepository;
import com.squidsquads.repository.CampaignRepository;
import com.squidsquads.repository.UserProfileRepository;
import com.squidsquads.utils.DateFormatter;
import com.squidsquads.utils.session.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CampaignService {

    private static final Logger logger = LoggerFactory.getLogger(CampaignService.class);

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private CampaignProfileRepository campaignProfileRepository;

    @Autowired
    private UserProfileService userProfileService;

    /**
     * Trouver un profil utilisateur en fonction de son ID
     */
    private Campaign findByNameAndAccountID(String name, Integer accountID) {
        return campaignRepository.findByNameAndAccountID(name, accountID);
    }

    /**
     * Retourne une campagne publicitaire aléatoire
     */
    public Campaign getRandom(List<Campaign> activeCampaignList) {

        return activeCampaignList.size() > 0 ? activeCampaignList.get(0) : null;
    }

    /**
     * Trouver les campagnes publicitaires d'un compte
     */
    public ListResponse getAll(String token) {

        Integer accountID = SessionManager.getInstance().getAccountIdForToken(token);

        // Si le compte n'a pas de session ici, c'est un probleme serveur
        if (SessionManager.NO_SESSION.equals(accountID)) {
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
    public InfoResponse getByID(String token, Integer campaignID) {

        Integer accountID = SessionManager.getInstance().getAccountIdForToken(token);

        // Si le compte n'a pas de session ici, c'est un probleme serveur
        if (SessionManager.NO_SESSION.equals(accountID)) {
            logger.error("Un compte basé sur un token de session est introuvable");
            return new InfoResponse().failed();
        }

        Campaign campaign = campaignRepository.findOne(campaignID);
        if (campaign == null || !accountID.equals(campaign.getAccountID())) {
            return new InfoResponse().notFound();
        }

        // GET linked profiles for campaign
        List<CampaignProfile> profiles = campaignProfileRepository.findAllByCampaignID(campaignID);
        Integer[] profileIDs = new Integer[profiles.size()];

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
    public ModifyResponse modify(String token, Integer campaignID, UpdateRequest updatedCampaign) {

        Integer accountID = SessionManager.getInstance().getAccountIdForToken(token);
        // Si le compte n'a pas de session ici, c'est un probleme serveur
        if (SessionManager.NO_SESSION.equals(accountID)) {
            logger.error("Un compte basé sur un token de session est introuvable");
            return new ModifyResponse().failed();
        }
        if (campaignRepository.findOne(campaignID) == null) {
            return new ModifyResponse().notFound();
        }

        if (!CampaignValidator.isUpdateRequestComplete(updatedCampaign)) {
            return new ModifyResponse().fieldsMissing();
        }

        try {
            Date startDate = DateFormatter.StringToDate(updatedCampaign.getStartDate());
            Date endDate = DateFormatter.StringToDate(updatedCampaign.getEndDate());
            if (startDate == null || endDate == null) {
                return new ModifyResponse().invalidDateFormat();
            }

            Campaign campaign = new Campaign(
                    campaignID,
                    accountID,
                    updatedCampaign.getName(),
                    updatedCampaign.getImgHorizontal(),
                    updatedCampaign.getImgVertical(),
                    updatedCampaign.getImgMobile(),
                    updatedCampaign.getRedirectUrl(),
                    startDate,
                    endDate,
                    updatedCampaign.getBudget(),
                    updatedCampaign.getProfileIds()
            );
            campaignProfileRepository.deleteAllByCampaignID(campaignID);


            for (Integer id : updatedCampaign.getProfileIds()) {
                campaignProfileRepository.save(new CampaignProfile(id, campaignID));
            }

            campaignRepository.save(campaign);

        } catch (NullPointerException npe) {
            return new ModifyResponse().fieldsMissing();
        }


        return new ModifyResponse().ok();
    }

    /**
     * Créer une campagne
     */
    @Transactional
    public CreateResponse create(String token, CreateRequest request) {

        Integer accountID = SessionManager.getInstance().getAccountIdForToken(token);

        // Si le compte n'a pas de session ici, c'est un probleme serveur
        if (SessionManager.NO_SESSION.equals(accountID)) {
            logger.error("Un compte basé sur un token de session est introuvable");
            return new CreateResponse().failed();
        }

        if (!CampaignValidator.isCreateRequestComplete(request)) {
            return new CreateResponse().fieldsMissing();
        }

        // Si le nom de profile existe déjà pour l'utilisateur courrant
        if (findByNameAndAccountID(request.getName(), accountID) != null) {
            return new CreateResponse().campaignAlreadyExists();
        }
        Date startDate = DateFormatter.StringToDate(request.getStartDate());
        Date endDate = DateFormatter.StringToDate(request.getEndDate());
        if (startDate == null || endDate == null) {
            return new CreateResponse().invalidDateFormat();
        }

        Campaign campaign = new Campaign(
                null,
                accountID,
                request.getName(),
                request.getImgHorizontal(),
                request.getImgVertical(),
                request.getImgMobile(),
                request.getRedirectUrl(),
                startDate,
                endDate,
                request.getBudget(),
                request.getProfileIds()
        );

        Campaign created = campaignRepository.save(campaign);

        for (Integer id : request.getProfileIds()) {
            if (userProfileService.findByProfileIDAndAccountID(id, accountID) != null) {
                campaignProfileRepository.save(new CampaignProfile(id, created.getCampaignID()));
            }
        }

        return new CreateResponse().ok();
    }

    /**
     * Supprimer une campagne
     */
    @Transactional
    public DeleteResponse delete(String token, Integer campaignId) {

        Integer accountID = SessionManager.getInstance().getAccountIdForToken(token);

        // Si le compte n'a pas de session ici, c'est un probleme serveur
        if (SessionManager.NO_SESSION.equals(accountID)) {
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
