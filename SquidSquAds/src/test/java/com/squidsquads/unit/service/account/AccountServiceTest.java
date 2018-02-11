package com.squidsquads.unit.service.account;

import com.squidsquads.form.account.request.LoginRequest;
import com.squidsquads.form.account.response.AbstractLoginResponse;
import com.squidsquads.form.account.response.LoginFailedResponse;
import com.squidsquads.form.account.response.LoginSucceededResponse;
import com.squidsquads.model.account.Account;
import com.squidsquads.model.account.AdminType;
import com.squidsquads.model.account.WebSiteAdmin;
import com.squidsquads.repository.account.AccountRepository;
import com.squidsquads.repository.account.WebSiteAdminRepository;
import com.squidsquads.service.account.AccountService;
import com.squidsquads.service.account.WebSiteAdminService;
import com.squidsquads.unit.service.AbstractWebSiteAdminTest;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AccountServiceTest extends AbstractWebSiteAdminTest {

    @Test
    public void loginNoAccountTest() {

        // Repositery retourne null comme s'il ne l'a pas trouvé
        when(super.getAccountRepository().findByEmail(super.getAccount().getEmail())).thenReturn(null);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("mauvais@email.com");
        loginRequest.setPassword("123");

        AbstractLoginResponse loginResponse = super.getAccountService().login(loginRequest);

        assertTrue(loginResponse instanceof LoginFailedResponse);
        assertFalse(loginResponse.isAuthenticated());
        assertEquals(HttpStatus.UNAUTHORIZED, loginResponse.getStatus());
        assertEquals("Courriel ou mot de passe incorrect", ((LoginFailedResponse) loginResponse).getMessage());
    }

    @Test
    public void loginWrongInformationTest() {

        // Repositery retourne le bon account mais le mot de passe est mauvais
        when(super.getAccountRepository().findByEmail(super.getAccount().getEmail())).thenReturn(super.getAccount());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(super.getAccount().getEmail());
        loginRequest.setPassword("mauvais mot de passe");

        AbstractLoginResponse loginResponse = super.getAccountService().login(loginRequest);

        assertTrue(loginResponse instanceof LoginFailedResponse);
        assertFalse(loginResponse.isAuthenticated());
        assertEquals(HttpStatus.UNAUTHORIZED, loginResponse.getStatus());
        assertEquals("Courriel ou mot de passe incorrect", ((LoginFailedResponse) loginResponse).getMessage());
    }

    @Test
    public void loginSuccessful() {

        // Repositery retourne le bon account
        when(super.getAccountRepository().findByEmail(super.getAccount().getEmail())).thenReturn(super.getAccount());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(super.getAccount().getEmail());
        loginRequest.setPassword(super.getAccount().getPassword());

        AbstractLoginResponse loginResponse = super.getAccountService().login(loginRequest);

        assertTrue(loginResponse instanceof LoginSucceededResponse);
        assertTrue(loginResponse.isAuthenticated());
        assertEquals(HttpStatus.OK, loginResponse.getStatus());
        assertTrue(((LoginSucceededResponse) loginResponse).isAdminWeb());
        assertFalse(((LoginSucceededResponse) loginResponse).isAdminPub());
        assertEquals(super.getAccount().getAccountID(), ((LoginSucceededResponse) loginResponse).getAccountId());
        assertEquals(super.getAccount().getEmail(), ((LoginSucceededResponse) loginResponse).getDisplayName());
        assertEquals(32, ((LoginSucceededResponse) loginResponse).getToken().length());
    }
}
