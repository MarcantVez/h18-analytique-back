package controller;

import exception.account.AccountExceptionType;
import exception.account.AccountNotFoundException;
import exception.account.AddAccountException;
import form.account.response.AccountInfoResponse;
import form.account.response.AuthenticateResponse;
import form.account.response.MessageResponse;
import model.account.Account;
import model.account.AdminType;
import model.account.WebSiteAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import form.account.request.AccountAuthenticateRequest;
import form.account.request.AccountCreateRequest;
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


    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> authenticateAccount(@Valid @RequestBody AccountAuthenticateRequest af)
    {
        ResponseEntity<?> responseEntity;
        try {
            Account account = accountService.authenticate(af.email, af.password);
            AuthenticateResponse authenticateResponse = new AuthenticateResponse(
                    HttpStatus.OK.value(),
                    true,
                    account.getAccountID(),
                    AdminType.valueOf(account.getAdminType()).equals(AdminType.ADS),
                    AdminType.valueOf(account.getAdminType()).equals(AdminType.WEB),
                    "HASH"
                    );

            responseEntity = ResponseEntity.ok().body(authenticateResponse);
        } catch (Exception e) {
            MessageResponse messageResponse = new MessageResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    e.getMessage()
            );
            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageResponse);
        }

        return responseEntity;
    }


//    @GetMapping(value = "/getAccount")
//    public ResponseEntity<?> getAccount(@RequestParam(required = true) String email)
//    {
//        ResponseEntity<?> responseEntity;
//        try {
//            Account account = accountService.findByEmail(email);
//            if( account == null ) throw new AccountNotFoundException(AccountExceptionType.ACCOUNT_NOT_FOUND.toString());
//
//            WebSiteAdmin webSiteAdmin = webSiteAdminController.findWebSiteAdminByAccountID(account.getAccountID());
//            if( webSiteAdmin == null ) throw new AccountNotFoundException(AccountExceptionType.ACCOUNT_NOT_FOUND.toString()); //TODO: To change ?
//
//            AccountInfoResponse accountInfoResponse = new AccountInfoResponse(
//                    HttpStatus.OK.value(),
//                    account.getAccountID(),
//                    AdminType.valueOf(account.getAdminType()).equals(AdminType.ADS),
//                    AdminType.valueOf(account.getAdminType()).equals(AdminType.WEB),
//                    "HASH",
//                    account.getEmail(),
//                    account.getBankAccount(),
//                    webSiteAdmin.getUrl()
//            );
//
//            responseEntity = ResponseEntity.ok().body(accountInfoResponse);
//
//
//        } catch (Exception e) {
//            MessageResponse messageResponse = new MessageResponse(
//                    HttpStatus.BAD_REQUEST.value(),
//                    e.getMessage()
//            );
//            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageResponse);
//        }
//
//        return responseEntity;
//    }

    @GetMapping(value = "/getAccount/{id}")
    public ResponseEntity<?> getAccount(@PathVariable(value = "id") Long id)
    {
        ResponseEntity<?> responseEntity;
        try {
            Account account = accountService.findAccountByAcountID(id);
            if( account == null ) throw new AccountNotFoundException(AccountExceptionType.ACCOUNT_NOT_FOUND.toString());

            String url = "";
            if( AdminType.valueOf(account.getAdminType()).equals(AdminType.WEB) ) {
                WebSiteAdmin webSiteAdmin = webSiteAdminController.findWebSiteAdminByAccountID(account.getAccountID());
                if (webSiteAdmin == null) {
                    throw new AccountNotFoundException(AccountExceptionType.ACCOUNT_NOT_FOUND.toString());
                }
                url = webSiteAdmin.getUrl();
            }
            AccountInfoResponse accountInfoResponse = new AccountInfoResponse(
                    HttpStatus.OK.value(),
                    account.getAccountID(),
                    AdminType.valueOf(account.getAdminType()).equals(AdminType.ADS),
                    AdminType.valueOf(account.getAdminType()).equals(AdminType.WEB),
                    "HASH",
                    account.getEmail(),
                    account.getBankAccount(),
                    url
            );

            responseEntity = ResponseEntity.ok().body(accountInfoResponse);


        } catch (Exception e) {
            MessageResponse messageResponse = new MessageResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    e.getMessage()
            );
            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageResponse);
        }

        return responseEntity;
    }


    @PostMapping(value = "/addAccount")
    public ResponseEntity<?> addAccount(@Valid @RequestBody AccountCreateRequest acf)
    {
        ResponseEntity<?> responseEntity;

        Account account = accountService.addAccount(new Account(acf.adminType, acf.email, acf.password, acf.bank));
        WebSiteAdmin webSiteAdmin = new WebSiteAdmin(account.getAccountID(), acf.domain);
        webSiteAdminController.addWebSiteAdmin(webSiteAdmin);

        try {
            if(account == null) throw new AddAccountException(AccountExceptionType.ADD_ACCOUNT.toString());

            MessageResponse messageResponse = new MessageResponse(
                    HttpStatus.OK.value(),
                    "OK"
            );

            responseEntity = ResponseEntity.ok().body(messageResponse);
        } catch (Exception e) {
            MessageResponse messageResponse = new MessageResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    e.getMessage()
            );
            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageResponse);
        }

        return responseEntity;
    }


    // TEST ONLY
//    @GetMapping(value = "/test")
//    public ResponseEntity<?> test(@RequestParam Long id)
//    {
//        ResponseEntity<?> responseEntity;
//        Account account = accountService.test(id);
//        responseEntity = ResponseEntity.ok().body(account);
//
//        return responseEntity;
//    }
}
