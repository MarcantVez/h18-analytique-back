package com.squidsquads.controller;

import com.squidsquads.form.payment.response.CreateResponse;
import com.squidsquads.model.AdminType;
import com.squidsquads.service.PaymentService;
import com.squidsquads.service.RoyaltyService;
import com.squidsquads.utils.session.SessionAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController("PaymentController")
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @PostMapping("")
    @SessionAuthorize(AdminType.WEB)
    public ResponseEntity<?> createCampaign(@RequestHeader("Token") String token) {

        CreateResponse createResponse = paymentService.create(token);
        return ResponseEntity.status(createResponse.getStatus()).body(createResponse);
    }
}
