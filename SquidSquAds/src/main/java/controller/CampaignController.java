package controller;

import javassist.NotFoundException;
import model.Campaign;
import model.dto.DTOCampaignListItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import service.CampaignService;
import utils.exceptions.ErrorInOperationException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Marc-Antoine Vézina
 * @Date_Of_Creation: 2018-02-01
 */

@RestController
@RequestMapping("/campagne")
public class CampaignController {
    public static final Logger logger = LoggerFactory.getLogger(CampaignController.class);

    @Autowired
    CampaignService campaignService;

    // -------------------Trouver une campagne par ID de compte---------------------------------------------

    @GetMapping(value = "/list")
    public ResponseEntity<List<DTOCampaignListItem>> findAllforAuthor(){
        // TODO find logged in account ID
        long accountID = 0;
        List<Campaign> campaigns = campaignService.findAllForAuthor(accountID);
        List<DTOCampaignListItem> results = new ArrayList<>();
        for(Campaign camp : campaigns){
            results.add(DTOCampaignListItem.fromCampaign(camp));
        }
        return ResponseEntity.ok().body(results);
    }

    // -------------------Trouver une Campagne---------------------------------------------

    @GetMapping(value = "/{id}")
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

    @PostMapping(value = "/create")
    public ResponseEntity<?> createCampaign(@Valid @RequestBody Campaign newCampaign, UriComponentsBuilder ucBuilder){
        logger.info("Création d'une campagne : {}", newCampaign);

        if (campaignService.findOneById(newCampaign.getCampaignId()) != null) {
            logger.error("Impossible de créer cette campagne, une campagne ayant l'identifiant {} existe déjà", newCampaign.getCampaignId());
            return new ResponseEntity(new ErrorInOperationException("Impossible de créer cette campagne, une campagne ayant " +
                    "l'identifiant "+newCampaign.getCampaignId()+" existe déjà"), HttpStatus.BAD_REQUEST);
        }
        Campaign campaign = campaignService.addCampaign(newCampaign);
        return new ResponseEntity<Campaign>(campaign, HttpStatus.CREATED);
    }

    // -------------------Update une Campagne---------------------------------------------

    @PutMapping(value = "/{id}")
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
