package com.squidsquads.service.account;

import com.squidsquads.model.account.WebSiteAdmin;
import com.squidsquads.repository.account.WebSiteAdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WebSiteAdminService {

    @Autowired
    public WebSiteAdminRepository webSiteAdminRepository;

    public WebSiteAdmin findByAccountID(Long accountID) {
        return webSiteAdminRepository.findByAccountID(accountID);
    }

    public WebSiteAdmin create(Long accountId, String domain) {
        return webSiteAdminRepository.save(new WebSiteAdmin(accountId, domain));
    }
}
