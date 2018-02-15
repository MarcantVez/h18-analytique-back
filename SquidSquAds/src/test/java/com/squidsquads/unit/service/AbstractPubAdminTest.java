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

public class AbstractPubAdminTest {

    private static AccountService accountService;
    private static WebSiteAdminService webSiteAdminService;

    private static AccountRepository accountRepository;
    private static WebSiteAdminRepository webSiteAdminRepository;

    private static Account account;
    private static WebSiteAdmin webSiteAdmin;

    @BeforeClass
    public static void init() {

        //TODO create the account that creates the profiles

        // Instance des services

        // Dummy account

        // Dummy web admin

        // Mock account repositery

        // Mock web site admin repositery

        // Assigner les mocks aux instances des services

    }


}
