package controller;

import javassist.NotFoundException;
import model.campaign.Campaign;
import form.campaign.response.CampaignListResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restForm.CampaignCreateForm;
import service.campaign.CampaignService;
import utils.exceptions.ErrorInOperationException;
import exception.campaign.CampaignException;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Marc-Antoine Vézina
 * @Date_Of_Creation: 2018-02-01
 */

@RestController
public class CampaignController {
    public static final Logger logger = LoggerFactory.getLogger(CampaignController.class);

    @Autowired
    CampaignService campaignService;

    // -------------------Trouver une campagne par ID de compte---------------------------------------------

    @GetMapping("/campagne")
    public ResponseEntity<List<CampaignListResponse>> findAllForAuthor(){
        // TODO find logged in account ID
        long accountID = 0;
        List<CampaignListResponse> campaignListResponseList = campaignService.findAllForAuthor(accountID);
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

    @PostMapping(value = "campagne")
    public ResponseEntity<?> createCampaign(@Valid @RequestBody CampaignCreateForm newCampaign){
        logger.info("Création d'une campagne : {}", newCampaign);
        try{
            Campaign campaign = campaignService.addCampaign(newCampaign);
            return new ResponseEntity<Campaign>(campaign, HttpStatus.CREATED);
        } catch (CampaignException campExcept) {
            logger.error("Le format de campagne est ");
            return new ResponseEntity(new ErrorInOperationException("Impossible de créer cette campagne, une campagne portant le même nom existe déjà"), HttpStatus.BAD_REQUEST);
        }
    }

    // -------------------Update une Campagne---------------------------------------------

    @PutMapping(value = "campagne/{id}")
    public ResponseEntity<Campaign> updateCampainById(@PathVariable(value = "id") Long id, @RequestBody Campaign campaign) {
        logger.info("Mise à jour de la Campagne ayant l'identifiant {}", id);
        Campaign targetCamaign = campaignService.findOneById(id);

        if (targetCamaign == null) {
            logger.error("Impossible de modifier. Aucune campagne ayant l'identifiant {} trouvée", id);
            return new ResponseEntity(new ErrorInOperationException("Impossible de modifier. Aucune campagne ayant " +
                    "l'identifiant "+ id +" trouvée"), HttpStatus.BAD_REQUEST);
        }
        Campaign updated = campaignService.updateCampaign(campaign);
        return new ResponseEntity<Campaign>(updated, HttpStatus.OK);
    }

    // -------------------Supprimer une Campagne---------------------------------------------

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Campaign> deleteCampaignById(@PathVariable(value = "id") Long id) {
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
