package com.squidsquads.controller;

import com.squidsquads.form.campaign.response.CampaignListResponse;
import com.squidsquads.model.account.Account;
import javassist.NotFoundException;
import com.squidsquads.model.campaign.Campaign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.squidsquads.form.campaign.request.CampaignCreateUpdateRequest;
import com.squidsquads.service.campaign.CampaignService;
import com.squidsquads.utils.exception.ErrorInOperationException;
import com.squidsquads.utils.exception.campaign.CampaignException;

import javax.validation.Valid;

/**
 * @author Marc-Antoine Vézina
 * @Date_Of_Creation: 2018-02-01
 */

@RestController("CampaignController")
public class CampaignController {
    private static final Logger logger = LoggerFactory.getLogger(CampaignController.class);

    @Autowired
    CampaignService campaignService;

    // -------------------Trouver une campagne par ID de compte---------------------------------------------

    @GetMapping("/campagne")
    public ResponseEntity<CampaignListResponse> findAllForAuthor(@RequestHeader("Token") String token){
        Account account =
        logger.info("Recherche des campagnes pour l'identifiant {}", accountId);
        CampaignListResponse campaignListResponseList = campaignService.findAllForAuthor(token);
        return ResponseEntity.ok().body(campaignListResponseList);
    }

    // -------------------Trouver une Campagne---------------------------------------------

    @GetMapping(value = "/campagne/{id}")
    public ResponseEntity<Campaign> findById(@PathVariable(value = "id") Long id) {
        logger.info("Recherche d'une campagne avec l'identifiant {}", id);
        Campaign campaign = campaignService.findOneById(id);
        if(campaign == null) {
            logger.error("Aucune campagne ayant l'identifiant {} n'a été trouvée.", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(campaign);
    }

    // -------------------Ajouter une Campagne---------------------------------------------

    @PostMapping(value = "/campagne", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createCampaign(@Valid @RequestBody CampaignCreateUpdateRequest newCampaign){
        logger.info("Création d'une campagne : {}", newCampaign);
        Campaign campaign = campaignService.addCampaign(newCampaign);
        return new ResponseEntity<Campaign>(campaign, HttpStatus.CREATED);
    }

    // -------------------Update une Campagne---------------------------------------------

    @PutMapping(value = "campagne/{id}")
    public ResponseEntity<?> updateCampainById(@PathVariable(value = "id") Long id, @Valid @RequestBody CampaignCreateUpdateRequest updatedCampaign) {
        logger.info("Mise à jour de la Campagne ayant l'identifiant {}", id);
        if (campaignService.findOneById(id) == null) {
            logger.error("Impossible de modifier. Aucune campagne ayant l'identifiant {} trouvée", id);
            return new ResponseEntity(new ErrorInOperationException("Impossible de modifier. Aucune campagne ayant " +
                    "l'identifiant "+ id +" trouvée"), HttpStatus.BAD_REQUEST);
        }
        Campaign updated = campaignService.updateCampaign(updatedCampaign);
        return new ResponseEntity<Campaign>(updated, HttpStatus.OK);
    }

    // -------------------Supprimer une Campagne---------------------------------------------

    @DeleteMapping(value = "campagne/{id}")
    public ResponseEntity<?> deleteCampaignById(@PathVariable(value = "id") Long id) {
        logger.info("Recherche et suppression de la Campagne #{}", id);
        try {
            campaignService.deleteCampaignById(id);
        } catch (NotFoundException e) {
            logger.error("Impossible de supprimer. La campagne #{} n'a pas été trouvée.", id);
            return new ResponseEntity(new ErrorInOperationException("Impossible de supprimer. La campagne #"+id
                    +" n'a pas été trouvée"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Campaign>(HttpStatus.OK);
    }

}
