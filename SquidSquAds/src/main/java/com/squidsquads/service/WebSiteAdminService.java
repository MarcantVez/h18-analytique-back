package com.squidsquads.service;

import com.squidsquads.model.WebSiteAdmin;
import com.squidsquads.repository.WebSiteAdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WebSiteAdminService {

    @Autowired
    private WebSiteAdminRepository webSiteAdminRepository;

    public WebSiteAdmin findByAccountID(Integer accountID) {
        return webSiteAdminRepository.findByAccountID(accountID);
    }

    public WebSiteAdmin create(Integer accountID, String domain) {
        return webSiteAdminRepository.save(new WebSiteAdmin(accountID, domain));
    }

    public WebSiteAdmin findOne(Integer siteWebAdminID) {
        return webSiteAdminRepository.findOne(siteWebAdminID);
    }
}
