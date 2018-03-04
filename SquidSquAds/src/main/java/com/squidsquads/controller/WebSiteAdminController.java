package com.squidsquads.controller;

import com.squidsquads.service.WebSiteAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController("WebSiteAdminController")
public class WebSiteAdminController {

    @Autowired
    WebSiteAdminService webSiteAdminService;

}
