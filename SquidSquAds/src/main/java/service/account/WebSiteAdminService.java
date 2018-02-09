package service.account;

import model.account.WebSiteAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.account.WebSiteAdminRepository;

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
