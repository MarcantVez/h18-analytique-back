package com.squidsquads.controller;

import com.squidsquads.form.campaign.request.CreateRequest;
import com.squidsquads.form.campaign.response.CampaignDeleteResponse;
import com.squidsquads.form.campaign.response.CampaignDetailResponse;
import com.squidsquads.form.campaign.response.CampaignListResponse;
import com.squidsquads.model.account.AdminType;
import com.squidsquads.utils.session.SessionAuthorize;
import javassist.NotFoundException;
import com.squidsquads.model.campaign.Campaign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.squidsquads.form.campaign.request.UpdateRequest;
import com.squidsquads.service.campaign.CampaignService;
import com.squidsquads.utils.exception.ErrorInOperationException;

import javax.validation.Valid;

/**
 * @author Marc-Antoine Vézina
 * @Date_Of_Creation: 2018-02-01
 */

@RestController("CampaignController")
public class CampaignController {
    @Autowired
    CampaignService campaignService;

    // -------------------Trouver une campagne par ID de compte---------------------------------------------

    @GetMapping("/campagne")
    @SessionAuthorize({AdminType.PUB})
    public ResponseEntity<CampaignListResponse> findAllForAuthor(@RequestHeader("Token") String token){
        CampaignListResponse campaignListResponseList = campaignService.findAllForAuthor(token);
        return ResponseEntity.status(campaignListResponseList.getStatus()).body(campaignListResponseList);
    }

    // -------------------Trouver une Campagne---------------------------------------------

    @GetMapping(value = "/campagne/{id}")
    @SessionAuthorize({AdminType.PUB})
    public ResponseEntity<CampaignDetailResponse> findById(@RequestHeader("Token") String token, @PathVariable(value = "id") Long id) {
        CampaignDetailResponse campaignDetail = campaignService.findOneById(token, id);
        return ResponseEntity.status(campaignDetail.getStatus()).body(campaignDetail);
    }

    // -------------------Ajouter une Campagne---------------------------------------------

    @PostMapping(value = "/campagne", consumes = MediaType.APPLICATION_JSON_VALUE)
    @SessionAuthorize({AdminType.PUB})
    public ResponseEntity<CampaignDetailResponse> createCampaign(@RequestHeader("Token") String token, @Valid @RequestBody CreateRequest newCampaign){
        CampaignDetailResponse campaignResponse = campaignService.addCampaign(token, newCampaign);
        return ResponseEntity.status(campaignResponse.getStatus()).body(campaignResponse);
    }

    // -------------------Update une Campagne---------------------------------------------

    @PutMapping(value = "campagne/{id}")
    @SessionAuthorize({AdminType.PUB})
    public ResponseEntity<CampaignDetailResponse> updateCampainById(@RequestHeader("Token") String token, @PathVariable(value = "id") Long campaignID, @Valid @RequestBody UpdateRequest updatedCampaign) {
        CampaignDetailResponse campaignResponse = campaignService.updateCampaign(token, campaignID, updatedCampaign);
        return ResponseEntity.status(campaignResponse.getStatus()).body(campaignResponse);
    }

    // -------------------Supprimer une Campagne---------------------------------------------

    @DeleteMapping(value = "campagne/{id}")
    @SessionAuthorize({AdminType.PUB})
    public ResponseEntity<?> deleteCampaignById(@RequestHeader("Token") String token, @PathVariable(value = "id") Long id) {
        CampaignDeleteResponse campaignDeleteResponse = campaignService.deleteCampaignById(token, id);
        return ResponseEntity.status(campaignDeleteResponse.getStatus()).body(null);
    }

}
