package com.squidsquads.controller;

import com.squidsquads.model.account.WebSiteAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.squidsquads.service.account.WebSiteAdminService;

@RestController("WebSiteAdminController")
public class WebSiteAdminController {

    @Autowired
    WebSiteAdminService webSiteAdminService;

    public WebSiteAdmin findWebSiteAdminByAccountID(Long accountID)
    {
        return webSiteAdminService.findByAccountID(accountID);
    }

    public WebSiteAdmin addWebSiteAdmin(WebSiteAdmin webSiteAdmin)
    {
        return webSiteAdminService.addWebSiteAdmin(webSiteAdmin);
    }

//    @GetMapping(value = "test")
//    public ResponseEntity<?> test(@RequestParam Long id)
//    {
//        ResponseEntity<?> responseEntity;
//        WebSiteAdmin webSiteAdmin = webSiteAdminService.findByAccountID(id);
//        responseEntity = ResponseEntity.ok().body(webSiteAdmin);
//
//
//        return responseEntity;
//    }
}
