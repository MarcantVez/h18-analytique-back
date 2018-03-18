package com.squidsquads.controller;

import com.squidsquads.form.visit.request.VisitRequest;
import com.squidsquads.form.visit.response.CookieCreationResponse;
import com.squidsquads.form.visit.response.VisitResponse;
import com.squidsquads.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController("VisitController")
@RequestMapping("/visit")
public class VisitController {

    @Autowired
    VisitService visitService;

    // --------------------------------------------------------------------- //
    @GetMapping("")
    public ResponseEntity<VisitResponse> logVisit(@RequestParam Integer userid) {
        VisitResponse response = visitService.processVisit(userid);
        return ResponseEntity.status(response.getStatus()).build();
    }

    // --------------------------------------------------------------------- //
    @PostMapping("")
    public ResponseEntity<CookieCreationResponse> createIdentity(HttpServletResponse httpResponse, @RequestBody VisitRequest request) {

        CookieCreationResponse response = visitService.createIdentity(request);

        // Création du Cookie
        Cookie cookie = new Cookie(VisitService.SQUIDSQUADS_COOKIE, response.getFingerprint());
        cookie.setMaxAge(2678400); // 1 mois
        cookie.setPath("/");
        cookie.setSecure(false);
        cookie.setHttpOnly(false);
        httpResponse.addCookie(cookie);

        // Renvoyer Set-Cookie dans le header + fingerprint dans le body
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    // --------------------------------------------------------------------- //
    @PostMapping("/leave")
    public ResponseEntity<VisitResponse> leaveVisit(@RequestParam Integer userid) {
        visitService.leave(userid);
        // Cette requête est utilisée comme beacon, la réponse est ignorée
        return ResponseEntity.ok().build();
    }

}
