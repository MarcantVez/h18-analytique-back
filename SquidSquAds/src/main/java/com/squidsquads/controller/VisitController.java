package com.squidsquads.controller;

import com.squidsquads.form.visit.request.VisitRequest;
import com.squidsquads.form.visit.response.CookieCreationResponse;
import com.squidsquads.form.visit.response.VisitResponse;
import com.squidsquads.service.visit.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;

@RestController("VisitController")
@RequestMapping("/visit")
public class VisitController {

    @Autowired
    VisitService visitService;

    @GetMapping("")
    public ResponseEntity<VisitResponse> logVisit() {
        VisitResponse response = visitService.processVisit();
        return ResponseEntity.status(response.getStatus()).body(null);
    }

    @PostMapping("")
    public ResponseEntity<CookieCreationResponse> createIdentity(HttpServletResponse httpResponse, @RequestBody VisitRequest request) {
        CookieCreationResponse response = visitService.createIdentity(request);
        Cookie cookie = new Cookie("_squidsquads", response.getFingerprint());
        cookie.setMaxAge(2678400); // 1 mois
        httpResponse.addCookie(cookie);
        return ResponseEntity.status(response.getStatus()).body(response);
        // renvoyer setCookie in header + fingerprint dans le body
    }

}
