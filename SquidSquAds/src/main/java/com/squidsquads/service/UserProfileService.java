package com.squidsquads.service;

import com.squidsquads.form.userProfile.request.CreateModifyRequest;
import com.squidsquads.form.userProfile.response.*;
import com.squidsquads.form.validator.UserProfileValidator;
import com.squidsquads.model.Account;
import com.squidsquads.model.UserProfile;
import com.squidsquads.model.Site;
import com.squidsquads.repository.AccountRepository;
import com.squidsquads.repository.SiteRepository;
import com.squidsquads.repository.UserProfileRepository;
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
public class UserProfileService {

    private static final Logger logger = LoggerFactory.getLogger(UserProfileService.class);

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private SiteRepository siteRepository;

    @Autowired
    private AccountService accountService;

    /**
     * Trouver un profil utilisateur en fonction de son nom et du ID du compte utilisateur
     */
    private UserProfile findByNameAndAccountID(String name, Integer accountID) {
        return userProfileRepository.findByNameAndAccountID(name, accountID);
    }

    /**
     * Trouver un profil utilisateur en fonction de son ID
     */
    public UserProfile findByProfileIDAndAccountID(Integer profileID, Integer accountID) {
        return userProfileRepository.findByProfileIDAndAccountID(profileID, accountID);
    }

    /**
     * Créer une profil utilisateur
     */
    @Transactional
    public CreateResponse create(String token, CreateModifyRequest request) {

        Integer accountID = SessionManager.getInstance().getAccountIdForToken(token);

        // Si le compte n'existe pas ici, c'est un probleme serveur
        if (SessionManager.NO_SESSION.equals(accountID)) {
            logger.error("Un compte basé sur un token de session est introuvable");
            return new CreateResponse().failed();
        }

        // Si la requête est incomplète
        if (!UserProfileValidator.isUserProfileRequestComplete(request)) {
            return new CreateResponse().fieldsMissing();
        }

        // Si le nom de profile existe déjà pour l'utilisateur courrant
        if (findByNameAndAccountID(request.getName(), accountID) != null) {
            return new CreateResponse().profileAlreadyExists();
        }

        // Valider les URLs fournis
        for (int i = 0; i < request.getUrls().length; i++) {
            if (!UserProfileValidator.isURLValid(request.getUrls()[i])) {
                return new CreateResponse().invalidURL();
            }
        }

        // Sinon on crée le profil utilisateur
        UserProfile userProfile = userProfileRepository.save(new UserProfile(accountID, request.getName(), request.getDescription()));

        if(userProfile == null)
        {
            return new CreateResponse().failed();
        }

        // Créer les sites pour le profil
        for (int i = 0; i < request.getUrls().length; i++) {

            Site site = siteRepository.save(new Site(userProfile.getProfileID(), request.getUrls()[i]));
            if(site == null)
            {
                return new CreateResponse().failed();
            }
        }

        return new CreateResponse().ok();
    }

    /**
     * Obtenir tous les profils utilisateur d'un admin de publicité
     */
    public ListResponse getAll(String token) {

        Integer accountID = SessionManager.getInstance().getAccountIdForToken(token);

        // Si le compte n'existe pas ici, c'est un probleme serveur
        if (SessionManager.NO_SESSION.equals(accountID)) {
            logger.error("Un compte basé sur un token de session est introuvable");
            return new ListResponse().unauthorized();
        }

        List<UserProfile> userProfiles = userProfileRepository.findByAccountID(accountID);
        List<ListResponseItem> responseList = new ArrayList<>(userProfiles.size());

        for (UserProfile u : userProfiles) {
            responseList.add(
                    new ListResponseItem(
                            u.getProfileID(),
                            u.getName(),
                            DateFormatter.DateToString(u.getDateCreated())
                    )
            );
        }

        return new ListResponse().ok(responseList);
    }

    /**
     * Obtenir un profil utilisateur en fonction de son ID
     */
    public InfoResponse getByID(String token, Integer profileID) {

        Integer accountID = SessionManager.getInstance().getAccountIdForToken(token);

        // Si le compte n'existe pas ici, c'est un probleme serveur
        if (SessionManager.NO_SESSION.equals(accountID)) {
            logger.error("Un compte basé sur un token de session est introuvable");
            return new InfoResponse().failed();
        }

        UserProfile userProfile = userProfileRepository.findByProfileIDAndAccountID(profileID, accountID);

        // Si le ID du profil n'existe pas
        if (userProfile == null) {
            return new InfoResponse().notFound();
        }

        List<Site> sites = siteRepository.findByUserProfileID(userProfile.getProfileID());

        return new InfoResponse().ok(userProfile, sites);
    }

    /**
     * Modifier un profil utilisateur
     */
    @Transactional
    public ModifyResponse modify(String token, Integer profileID, CreateModifyRequest request) {

        Integer accountID = SessionManager.getInstance().getAccountIdForToken(token);
        Account account = accountService.findByAccountID(accountID);

        // Si le compte n'existe pas ici, c'est un probleme serveur
        if (account == null) {
            logger.error("Un compte basé sur un token de session est introuvable");
            return new ModifyResponse().failed();
        }

        UserProfile userProfile = findByProfileIDAndAccountID(profileID, accountID);

        // Si le ID du profil n'existe pas
        if (userProfile == null) {
            return new ModifyResponse().notFound();
        }

        // Si la requête est incomplète
        if (!UserProfileValidator.isUserProfileRequestComplete(request)) {
            return new ModifyResponse().fieldsMissing();
        }

        // Valider les URLs fournis
        for (int i = 0; i < request.getUrls().length; i++) {
            if (!UserProfileValidator.isURLValid(request.getUrls()[i])) {
                return new ModifyResponse().invalidURL();
            }
        }

        // Mettre à jour le profil
        userProfile.setName(request.getName());
        userProfile.setDescription(request.getDescription());
        userProfileRepository.save(userProfile);

        // Retirer les anciens sites
        List<Site> anciensSites = siteRepository.findByUserProfileID(userProfile.getProfileID());
        siteRepository.delete(anciensSites);

        // Ajouter les nouveaux sites
        for (int i = 0; i < request.getUrls().length; i++) {
            siteRepository.save(new Site(userProfile.getProfileID(), request.getUrls()[i]));
        }

        return new ModifyResponse().ok();
    }

    /**
     * Deletes existing user profile
     */
    @Transactional
    public DeleteResponse delete(String token, Integer profileID) {

        Integer accountID = SessionManager.getInstance().getAccountIdForToken(token);

        // Si le compte n'existe pas ici, c'est un probleme serveur
        if (SessionManager.NO_SESSION.equals(accountID)) {
            logger.error("Un compte basé sur un token de session est introuvable");
            return new DeleteResponse().failed();
        }

        UserProfile userProfile = findByProfileIDAndAccountID(profileID, accountID);

        // Si le ID du profil n'existe pas
        if (userProfile == null) {
            return new DeleteResponse().notFound();
        }

        List<Site> sites = siteRepository.findByUserProfileID(userProfile.getProfileID());
        siteRepository.delete(sites);

        userProfileRepository.delete(userProfile.getProfileID());

        return new DeleteResponse().ok();
    }


}
