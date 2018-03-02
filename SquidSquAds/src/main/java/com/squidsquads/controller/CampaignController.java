package com.squidsquads.controller;

import com.squidsquads.form.campaign.request.CreateRequest;
import com.squidsquads.form.campaign.request.UpdateRequest;
import com.squidsquads.form.campaign.response.*;
import com.squidsquads.model.AdminType;
import com.squidsquads.service.CampaignService;
import com.squidsquads.utils.session.SessionAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController("CampaignController")
@RequestMapping("/campagne")
public class CampaignController {

    @Autowired
    CampaignService campaignService;

    // --- Trouver les campagnes par ID de compte -------------------------- //

    @GetMapping("")
    @SessionAuthorize(AdminType.PUB)
    public ResponseEntity<?> findAllForAuthor(@RequestHeader("Token") String token) {

        ListResponse listResponseList = campaignService.getAll(token);
        return ResponseEntity.status(listResponseList.getStatus()).body(listResponseList);
    }

    // --- Ajouter une campagne -------------------------------------------- //

    @PostMapping("")
    @SessionAuthorize(AdminType.PUB)
    public ResponseEntity<?> createCampaign(@RequestHeader("Token") String token, @Valid @RequestBody CreateRequest newCampaign) {

        CreateResponse createResponse = campaignService.create(token, newCampaign);
        return ResponseEntity.status(createResponse.getStatus()).body(createResponse);
    }

    // --- Trouver une campagne -------------------------------------------- //

    @GetMapping("/{campaignID}")
    @SessionAuthorize(AdminType.PUB)
    public ResponseEntity<?> findById(@RequestHeader("Token") String token, @PathVariable("campaignID") Integer campaignID) {

        InfoResponse infoResponse = campaignService.getByID(token, campaignID);
        return ResponseEntity.status(infoResponse.getStatus()).body(infoResponse);
    }

    // --- Update une campagne --------------------------------------------- //

    @PutMapping("/{campaignID}")
    @SessionAuthorize(AdminType.PUB)
    public ResponseEntity<?> updateCampainById(@RequestHeader("Token") String token, @PathVariable("campaignID") Integer campaignID, @Valid @RequestBody UpdateRequest updatedCampaign) {

        ModifyResponse modifyResponse = campaignService.modify(token, campaignID, updatedCampaign);
        return ResponseEntity.status(modifyResponse.getStatus()).body(modifyResponse);
    }

    // --- Supprimer une campagne ------------------------------------------ //

    @DeleteMapping("/{campaignID}")
    @SessionAuthorize(AdminType.PUB)
    public ResponseEntity<?> deleteCampaignById(@RequestHeader("Token") String token, @PathVariable("campaignID") Integer campaignID) {

        DeleteResponse deleteResponse = campaignService.delete(token, campaignID);
        return ResponseEntity.status(deleteResponse.getStatus()).body(deleteResponse);
    }

}
