package com.squidsquads.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("VisitController")
@RequestMapping("/visit")
public class VisitController {

    @PostMapping("")
    public ResponseEntity<?> createVisit() {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(null);
    }

}
