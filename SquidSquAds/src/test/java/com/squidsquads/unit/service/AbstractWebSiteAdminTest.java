package com.squidsquads.unit.service;

import com.squidsquads.model.account.Account;
import com.squidsquads.model.account.AdminType;
import com.squidsquads.model.account.WebSiteAdmin;
import com.squidsquads.repository.account.AccountRepository;
import com.squidsquads.repository.account.WebSiteAdminRepository;
import com.squidsquads.service.account.AccountService;
import com.squidsquads.service.account.WebSiteAdminService;
import org.junit.BeforeClass;

import java.util.Date;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class AbstractWebSiteAdminTest {

    private static AccountService accountService;
    private static WebSiteAdminService webSiteAdminService;

    private static AccountRepository accountRepository;
    private static WebSiteAdminRepository webSiteAdminRepository;

    private static Account account;
    private static WebSiteAdmin webSiteAdmin;

    @BeforeClass
    public static void init() {

        // Instance des services
        accountService = new AccountService();
        webSiteAdminService = new WebSiteAdminService();

        // Dummy account
        account = new Account();
        account.setAccountID(1);
        account.setAdminType(AdminType.WEB.toString());
        account.setEmail("test@test.com");
        account.setPassword("$2a$10$s.oRmPd1ATjGOHh0caIBHOXh26N9ft7/XYOdYEapBuuTGr/7UEiRi");
        account.setBankAccount("0000 1111 2222 3333");
        account.setDateCreated(new Date());

        // Dummy web admin
        webSiteAdmin = new WebSiteAdmin(account.getAccountID(), "http://wwww.google.ca");

        // Mock account repositery
        accountRepository = mock(AccountRepository.class);
        when(accountRepository.findByAccountID(account.getAccountID())).thenReturn(account);
        when(accountRepository.findByEmail(account.getEmail())).thenReturn(account);

        // Mock web site admin repositery
        webSiteAdminRepository = mock(WebSiteAdminRepository.class);
        when(webSiteAdminRepository.findByAccountID(account.getAccountID())).thenReturn(webSiteAdmin);
        when(webSiteAdminRepository.save(webSiteAdmin)).thenReturn(webSiteAdmin);

        // Assigner les mocks aux instances des services
        accountService.accountRepository = accountRepository;
        accountService.webSiteAdminService = webSiteAdminService;
        webSiteAdminService.webSiteAdminRepository = webSiteAdminRepository;
    }

    public static AccountService getAccountService() {
        return accountService;
    }

    public static WebSiteAdminService getWebSiteAdminService() {
        return webSiteAdminService;
    }

    public static AccountRepository getAccountRepository() {
        return accountRepository;
    }

    public static WebSiteAdminRepository getWebSiteAdminRepository() {
        return webSiteAdminRepository;
    }

    public static Account getAccount() {
        return account;
    }

    public static WebSiteAdmin getWebSiteAdmin() {
        return webSiteAdmin;
    }
}
