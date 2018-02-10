package com.squidsquads.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.squidsquads.service.account.WebSiteAdminService;

@RestController("WebSiteAdminController")
public class WebSiteAdminController {

    @Autowired
    WebSiteAdminService webSiteAdminService;

}
