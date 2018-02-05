package service.account.behavior;

import model.account.WebSiteAdmin;

public interface IWebSiteAdminService {
    WebSiteAdmin findByAccountID(Long accountID);

    WebSiteAdmin addWebSiteAdmin(WebSiteAdmin webSiteAdmin);
}
