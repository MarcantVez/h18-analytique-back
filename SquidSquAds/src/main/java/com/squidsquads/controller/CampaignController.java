package com.squidsquads.controller;

import com.squidsquads.form.campaign.request.CreateRequest;
import com.squidsquads.form.campaign.request.UpdateRequest;
import com.squidsquads.form.campaign.response.CampaignDeleteResponse;
import com.squidsquads.form.campaign.response.CampaignDetailResponse;
import com.squidsquads.form.campaign.response.CampaignListResponse;
import com.squidsquads.model.account.AdminType;
import com.squidsquads.service.campaign.CampaignService;
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
    public ResponseEntity<CampaignListResponse> findAllForAuthor(@RequestHeader("Token") String token) {

        CampaignListResponse campaignListResponseList = campaignService.findAllForAuthor(token);
        return ResponseEntity.status(campaignListResponseList.getStatus()).body(campaignListResponseList);
    }

    // --- Ajouter une campagne -------------------------------------------- //

    @PostMapping("")
    @SessionAuthorize(AdminType.PUB)
    public ResponseEntity<CampaignDetailResponse> createCampaign(@RequestHeader("Token") String token, @Valid @RequestBody CreateRequest newCampaign) {

        CampaignDetailResponse campaignResponse = campaignService.addCampaign(token, newCampaign);
        return ResponseEntity.status(campaignResponse.getStatus()).body(campaignResponse);
    }

    // --- Trouver une campagne -------------------------------------------- //

    @GetMapping("/{campaignID}")
    @SessionAuthorize(AdminType.PUB)
    public ResponseEntity<CampaignDetailResponse> findById(@RequestHeader("Token") String token, @PathVariable("campaignID") Long campaignID) {

        CampaignDetailResponse campaignDetail = campaignService.findOneById(token, campaignID);
        return ResponseEntity.status(campaignDetail.getStatus()).body(campaignDetail);
    }

    // --- Update une campagne --------------------------------------------- //

    @PutMapping("/{campaignID}")
    @SessionAuthorize(AdminType.PUB)
    public ResponseEntity<CampaignDetailResponse> updateCampainById(@RequestHeader("Token") String token, @PathVariable("campaignID") Long campaignID, @Valid @RequestBody UpdateRequest updatedCampaign) {

        CampaignDetailResponse campaignResponse = campaignService.updateCampaign(token, campaignID, updatedCampaign);
        return ResponseEntity.status(campaignResponse.getStatus()).body(campaignResponse);
    }

    // --- Supprimer une campagne ------------------------------------------ //

    @DeleteMapping("/{campaignID}")
    @SessionAuthorize(AdminType.PUB)
    public ResponseEntity<?> deleteCampaignById(@RequestHeader("Token") String token, @PathVariable("campaignID") Long campaignID) {

        CampaignDeleteResponse campaignDeleteResponse = campaignService.deleteCampaignById(token, campaignID);
        return ResponseEntity.status(campaignDeleteResponse.getStatus()).body(null);
    }

}
