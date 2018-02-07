package service.account;

import model.account.WebSiteAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.account.WebSiteAdminRepository;

@Service
public class WebSiteAdminService {

    @Autowired
    WebSiteAdminRepository webSiteAdminRepository;

    public WebSiteAdmin findByAccountID(Long accountID) {
        return webSiteAdminRepository.findByAccountID(accountID);
    }

    public WebSiteAdmin addWebSiteAdmin(WebSiteAdmin webSiteAdmin) {
        return webSiteAdminRepository.save(webSiteAdmin);
    }
}
