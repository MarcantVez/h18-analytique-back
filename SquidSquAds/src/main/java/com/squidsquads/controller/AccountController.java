package com.squidsquads.controller;

import com.squidsquads.utils.exception.account.AccountExceptionType;
import com.squidsquads.utils.exception.account.AccountNotFoundException;
import com.squidsquads.utils.exception.account.AddAccountException;
import com.squidsquads.form.account.response.AccountInfoResponse;
import com.squidsquads.form.account.response.AuthenticateResponse;
import com.squidsquads.form.account.response.MessageResponse;
import com.squidsquads.model.account.Account;
import com.squidsquads.model.account.AdminType;
import com.squidsquads.model.account.WebSiteAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.squidsquads.form.account.request.AccountAuthenticateRequest;
import com.squidsquads.form.account.request.AccountCreateRequest;
import com.squidsquads.service.account.AccountService;

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


    @GetMapping(value = "/getAccount")
    public ResponseEntity<?> getAccount(@RequestParam(required = true) String email)
    {
        ResponseEntity<?> responseEntity;
        try {
            Account account = accountService.findByEmail(email);
            if( account == null ) throw new AccountNotFoundException(AccountExceptionType.ACCOUNT_NOT_FOUND.toString());

            WebSiteAdmin webSiteAdmin = webSiteAdminController.findWebSiteAdminByAccountID(account.getAccountID());
            if( webSiteAdmin == null ) throw new AccountNotFoundException(AccountExceptionType.ACCOUNT_NOT_FOUND.toString()); //TODO: To change ?

            AccountInfoResponse accountInfoResponse = new AccountInfoResponse(
                    HttpStatus.OK.value(),
                    account.getAccountID(),
                    AdminType.valueOf(account.getAdminType()).equals(AdminType.ADS),
                    AdminType.valueOf(account.getAdminType()).equals(AdminType.WEB),
                    "HASH",
                    account.getEmail(),
                    account.getBankAccount(),
                    webSiteAdmin.getUrl()
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
}
