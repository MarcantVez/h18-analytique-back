package controller;

import model.account.WebSiteAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import service.account.WebSiteAdminService;

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
}
