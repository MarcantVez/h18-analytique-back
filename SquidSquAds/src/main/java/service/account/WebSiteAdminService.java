package service.account;

import model.account.WebSiteAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.account.WebSiteAdminRepository;
import service.account.behavior.IWebSiteAdminService;

@Service
public class WebSiteAdminService implements IWebSiteAdminService{

    @Autowired
    WebSiteAdminRepository webSiteAdminRepository;

    @Override
    public WebSiteAdmin findByAccountID(Long accountID) {
        return webSiteAdminRepository.findByAccountID(accountID);
    }

    @Override
    public WebSiteAdmin addWebSiteAdmin(WebSiteAdmin webSiteAdmin) {
        return webSiteAdminRepository.save(webSiteAdmin);
    }
}
