package controller;

import exception.account.AccountExceptionType;
import exception.account.AccountNotFoundException;
import model.account.Account;
import model.account.AdminType;
import model.account.WebSiteAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import restForm.utilisateur.AccountAuthenticateForm;
import restForm.utilisateur.AccountCreateForm;
import restForm.utilisateur.AccountIdentificationForm;
import service.account.AccountService;

import javax.validation.Valid;


/**
 * @author: Dulce Cayetano
 * @Date_Of_Creation: 2018-01-22
 * @Last_modified_by:
 * @Date_of_last_modification:
 **/
@RestController("AccountController")
public class AccountController {

    @Autowired
    AccountService accountService;

    @Autowired
    WebSiteAdminController webSiteAdminController;

    /*
        Authenticate REST API

        JSON request
        {
            "email": "test@test.com",
            "password": "test"
        }

        JSON response
        {
            "isAuthenticated": "true",
            "accountID": "2",
            "isAdminPub": "true",
            "isAdminWeb": "false",
            "displayName": "Moi",
            "token": "HASH"
        }
     */
    @PostMapping(value = "/authenticate")
    public String authenticateAccount(@Valid @RequestBody AccountAuthenticateForm af)
    {
        String response = "{";
        try {
            Account account = accountService.authenticate(af.email, af.password);

            // To generate with Swagger2 I guess ? Not enough time to look at swagger
            response += "\"isAuthenticated\": \"true\"," +
                    "\"accountID\": \"" + account.getAccountID() + "\"," +
                    "\"isAdminPub\": \"" + account.getAdminType().equals(AdminType.ADS.toString()) + "\"," +
                    "\"isAdminWeb\": \"" + account.getAdminType().equals(AdminType.WEB.toString()) + "\"," +
                    //"\"displayName\": \"" + accountProfile.getName() + "\"," +
                    "\"token\": \"HASH\"" +
                    "}";
        } catch (Exception e) {
            response += "\"isAuthenticated\": \"false\"," +
                    "\"reason\": \"" + e.getMessage() + "\"" +
                    "}";
        }

        return response;
    }

    /*
        Get Account REST API

        HTTP GET Request
        email=google2@google.com

        JSON response
        {
            "accountID": "10",
            "isAdminPub": "true",
            "isAdminWeb": "false",
            "email": "google2@google.com",
            "bank": "1111 2222 3333 4444",
            "webSiteAdminID": "11",
            "url": "google2.com"
        }
     */
    @GetMapping(value = "/getAccount")
    public String getAccount(@RequestParam(required = true) String email)
    {
        String response = "{";
        try {
            Account account = accountService.findByEmail(email);
            if( account == null ) throw new AccountNotFoundException(AccountExceptionType.ACCOUNT_NOT_FOUND.toString());

            WebSiteAdmin webSiteAdmin = webSiteAdminController.findWebSiteAdminByAccountID(account.getAccountID());
            if( webSiteAdmin == null ) throw new AccountNotFoundException(AccountExceptionType.ACCOUNT_NOT_FOUND.toString()); //TODO: To change ?

            response += "\"accountID\": \"" + account.getAccountID() + "\"," +
                    "\"isAdminPub\": \"" + account.getAdminType().equals(AdminType.ADS.toString()) + "\"," +
                    "\"isAdminWeb\": \"" + account.getAdminType().equals(AdminType.WEB.toString()) + "\"," +
                    "\"email\": \"" + account.getEmail() + "\"," +
                    "\"bank\": \"" + account.getBankAccount() + "\"," +
                    "\"webSiteAdminID\": \"" + webSiteAdmin.getWebSiteAdminID() + "\"," +
                    "\"url\": \"" + webSiteAdmin.getUrl() + "\" }";
        } catch (Exception e) {
            response += "\"error\": \"true\"," +
                    "\"reason\": \"" + e.getMessage() + "\"" +
                    "}";
        }
        return response;
    }

    /*
      Add account REST API

      JSON Request
     {
        "adminType": "ADS",
        "email": "google2@google.com",
        "domain": "google2.com",
        "bank": "1111 2222 3333 4444",
        "password": "gogol",
        "confirmPassword": "gogol"
    }

      JSON response
      { "created" : "true" }
   */
    @PostMapping(value = "/addAccount")
    public String addAccount(@Valid @RequestBody AccountCreateForm acf)
    {
        String response = "{ \"created\" : \"";

        Account account = accountService.addAccount(new Account(acf.adminType, acf.email, acf.password, acf.bank));
        WebSiteAdmin webSiteAdmin = new WebSiteAdmin(account.getAccountID(), acf.domain);
        webSiteAdminController.addWebSiteAdmin(webSiteAdmin);

        response += (account != null  & webSiteAdmin != null) ? true : false ;
        response += "\" }";

        return response;
    }


    ///// TODO FOLLOWING FOR LATER SPRINT !!!!!


    @PostMapping(value = "/deleteAccount")
    public String deleteAccount(@Valid @RequestBody AccountIdentificationForm aif)
    {
        String response = "{ \"deleted\" : \"";

        try {
            accountService.deleteByEmail(aif.email);
            response += true + "\"";
        } catch (Exception e) {
            response += false + "\"";
        }

        return response;
    }

    @PostMapping(value = "/updateAccount")
    public String updateAccount(@Valid @RequestBody AccountCreateForm acf)
    {
        String response = "{ \"updated\" : \"";

        try {
            accountService.updateAccount(new Account(acf.adminType, acf.email, acf.password, acf.bank));
            response +=  true ;
        } catch (Exception e) {
            response += false;
        }

        response += "\"";

        return response;
    }
}
