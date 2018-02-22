package com.squidsquads.controller;

import com.squidsquads.form.visit.response.VisitResponse;
import com.squidsquads.service.visit.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@RestController("VisitController")
@RequestMapping("/visit")
public class VisitController {

    @Autowired
    VisitService visitService;

    @GetMapping("")
    public ResponseEntity<VisitResponse> createVisit() {
        VisitResponse response = visitService.processVisit();
        return ResponseEntity.status(response.getStatus()).body(null);
    }

}
