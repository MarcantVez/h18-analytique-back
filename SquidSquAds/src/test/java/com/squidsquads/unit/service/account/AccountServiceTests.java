package com.squidsquads.unit.service.account;

import com.squidsquads.form.account.request.CreateRequest;
import com.squidsquads.form.account.request.LoginRequest;
import com.squidsquads.form.account.request.ResetPasswordRequest;
import com.squidsquads.form.account.response.*;
import com.squidsquads.model.Account;
import com.squidsquads.model.Banner;
import com.squidsquads.model.WebSiteAdmin;
import com.squidsquads.unit.service.AbstractServiceTests;
import com.squidsquads.utils.session.SessionManager;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.junit.Test;
import org.mockito.stubbing.Answer;
import org.springframework.http.HttpStatus;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;

public class AccountServiceTests extends AbstractServiceTests {

    private AccountTestHelper helper = new AccountTestHelper();

    ///////////
    // Login //
    ///////////

    @Test
    public void loginFailsWhenEmailIsNull() {

        LoginRequest req = helper.getLoginRequestWhereEmailIsNull();
        AbstractLoginResponse res = getAccountService().login(req);

        assertLoginFailure(res);
    }

    @Test
    public void loginFailsWhenPasswordIsNull() {

        LoginRequest req = helper.getLoginRequestWherePasswordIsNull();
        AbstractLoginResponse res = getAccountService().login(req);

        assertLoginFailure(res);
    }

    @Test
    public void loginFailsWhenEmailDoesNotExist() {

        when(getAccountRepository().findByEmail(anyString())).thenReturn(null);

        LoginRequest req = helper.getLoginRequest();
        AbstractLoginResponse res = getAccountService().login(req);

        assertLoginFailure(res);
    }

    @Test
    public void loginFailsWhenPasswordIsWrong() {

        when(getAccountRepository().findByEmail(anyString())).thenReturn(helper.getAccountPub());

        LoginRequest req = helper.getLoginRequestWherePasswordIsWrong();
        AbstractLoginResponse res = getAccountService().login(req);

        assertLoginFailure(res);
    }

    @Test
    public void loginSucceedsWhenEmailAndPasswordMatches() {

        Account acc = helper.getAccountPub();
        when(getAccountRepository().findByEmail(anyString())).thenReturn(acc);

        LoginRequest req = helper.getLoginRequest();
        AbstractLoginResponse res = getAccountService().login(req);

        assertNotNull(res);
        assertTrue(res instanceof LoginSucceededResponse);
        assertTrue(res.isAuthenticated());
        assertEquals(HttpStatus.OK, res.getStatus());
        assertTrue(((LoginSucceededResponse) res).isAdminPub());
        assertFalse(((LoginSucceededResponse) res).isAdminWeb());
        assertEquals(acc.getAccountID(), ((LoginSucceededResponse) res).getAccountId());
        assertEquals(acc.getEmail(), ((LoginSucceededResponse) res).getDisplayName());
        assertNotNull(((LoginSucceededResponse) res).getToken());
        assertEquals(32, ((LoginSucceededResponse) res).getToken().length());
    }

    private void assertLoginFailure(AbstractLoginResponse res) {

        assertNotNull(res);
        assertTrue(res instanceof LoginFailedResponse);
        assertFalse(res.isAuthenticated());
        assertEquals(HttpStatus.UNAUTHORIZED, res.getStatus());
        assertEquals("Courriel ou mot de passe incorrect", ((LoginFailedResponse) res).getMessage());
    }

    ////////////
    // Create //
    ////////////

    @Test
    public void createFailsWhenAdminTypeIsNotValid() {

        CreateRequest req = helper.getCreateRequestWhereAdminTypeIsNotValid();
        CreateResponse res = getAccountService().create(req);

        assertNotNull(res);
        assertEquals(HttpStatus.BAD_REQUEST, res.getStatus());
        assertEquals("Type d'administrateur invalide", res.getMessage());
    }

    @Test
    public void createFailsWhenOneParameterIsNullOrEmpty() {

        CreateRequest req = helper.getCreateRequestWhereOneParameterIsNullOrEmpty();
        CreateResponse res = getAccountService().create(req);

        assertNotNull(res);
        assertEquals(HttpStatus.BAD_REQUEST, res.getStatus());
        assertEquals("Tous les champs requis doivent être remplis", res.getMessage());
    }

    @Test
    public void createFailsWhenAdminTypeIsWebAndDomainIsEmpty() {

        CreateRequest req = helper.getCreateRequestWhereAdminTypeIsWebAndDomainIsEmpty();
        CreateResponse res = getAccountService().create(req);

        assertNotNull(res);
        assertEquals(HttpStatus.BAD_REQUEST, res.getStatus());
        assertEquals("Tous les champs requis doivent être remplis", res.getMessage());
    }

    @Test
    public void createFailsWhenBankFormatIsWrong() {

        CreateRequest req = helper.getCreateRequestWhereBankFormatIsWrong();
        CreateResponse res = getAccountService().create(req);

        assertNotNull(res);
        assertEquals(HttpStatus.BAD_REQUEST, res.getStatus());
        assertEquals("Numéro de compte de banque invalide", res.getMessage());
    }

    @Test
    public void createFailsWhenPasswordsDoNotMatch() {

        CreateRequest req = helper.getCreateRequestWherePasswordsDoNotMatch();
        CreateResponse res = getAccountService().create(req);

        assertNotNull(res);
        assertEquals(HttpStatus.BAD_REQUEST, res.getStatus());
        assertEquals("Mots de passe ne sont pas identiques", res.getMessage());
    }

    @Test
    public void createFailsWhenEmailIsAlreadyUsed() {

        when(getAccountRepository().findByEmail(anyString())).thenReturn(helper.getAccountWeb());

        CreateRequest req = helper.getCreateRequestForWeb();
        CreateResponse res = getAccountService().create(req);

        assertNotNull(res);
        assertEquals(HttpStatus.CONFLICT, res.getStatus());
        assertEquals("Courriel déjà utilisé", res.getMessage());
    }

    @Test
    public void createSucceedsForAdminPub() {

        when(getAccountRepository().save(any(Account.class))).thenReturn(helper.getAccountPub());

        CreateRequest req = helper.getCreateRequestForPub();
        CreateResponse res = getAccountService().create(req);

        assertNotNull(res);
        assertEquals(HttpStatus.OK, res.getStatus());
        assertEquals("Compte créé", res.getMessage());
    }

    @Test
    public void createSucceedsForAdminWebAndCreatesAdditionnalEntities() {

        // A little dirty but useful to check for answers when saving entities
        final MutableBoolean webSiteAdminCreated = new MutableBoolean(false);
        final MutableBoolean atLeastOneBannerCreated = new MutableBoolean(false);

        when(getAccountRepository().save(any(Account.class))).thenReturn(helper.getAccountWeb());
        when(getWebSiteAdminRepository().save(any(WebSiteAdmin.class))).thenAnswer((Answer<WebSiteAdmin>) invocation -> {
            webSiteAdminCreated.setTrue();
            return null;
        });
        when(getBannerRepository().save(any(Banner.class))).thenAnswer((Answer<Banner>) invocation -> {
            atLeastOneBannerCreated.setTrue();
            return null;
        });

        CreateRequest req = helper.getCreateRequestForWeb();
        CreateResponse res = getAccountService().create(req);

        assertNotNull(res);
        assertEquals(HttpStatus.OK, res.getStatus());
        assertEquals("Compte créé", res.getMessage());
        assertTrue(webSiteAdminCreated.booleanValue());
        assertTrue(atLeastOneBannerCreated.booleanValue());
    }

    //////////////
    // Get Info //
    //////////////

    @Test
    public void getInfoFailsWhenSessionNotFound() {

        when(getAccountRepository().findByAccountID(anyInt())).thenReturn(null);

        InfoResponse res = getAccountService().getInfo("invalid token");

        assertNotNull(res);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, res.getStatus());
        assertEquals("Un problème est survenu", res.getMessage());
    }

    @Test
    public void getInfoSucceedsForAdminPub() {

        Account pub = helper.getAccountPub();
        String token = SessionManager.getInstance().addSession(pub);

        when(getAccountRepository().findByAccountID(anyInt())).thenReturn(pub);

        InfoResponse res = getAccountService().getInfo(token);

        assertNotNull(res);
        assertEquals(HttpStatus.OK, res.getStatus());
        assertEquals("OK", res.getMessage());
        assertEquals(pub.getEmail(), res.getEmail());
        assertEquals(pub.getBankAccount(), res.getBankAccount());
        assertEquals("", res.getDomain());
    }

    @Test
    public void getInfoSucceedsForAdminWeb() {

        Account web = helper.getAccountWeb();
        WebSiteAdmin webSiteAdmin = helper.getWebSiteAdmin();
        String token = SessionManager.getInstance().addSession(web);

        when(getAccountRepository().findByAccountID(anyInt())).thenReturn(web);
        when(getWebSiteAdminRepository().findByAccountID(anyInt())).thenReturn(webSiteAdmin);

        InfoResponse res = getAccountService().getInfo(token);

        assertNotNull(res);
        assertEquals(HttpStatus.OK, res.getStatus());
        assertEquals("OK", res.getMessage());
        assertEquals(web.getEmail(), res.getEmail());
        assertEquals(web.getBankAccount(), res.getBankAccount());
        assertEquals(webSiteAdmin.getUrl(), res.getDomain());
    }

    ////////////////////
    // Reset Password //
    ////////////////////

    @Test
    public void resetPasswordFailsWhenSessionNotFound() {

        when(getAccountRepository().findByAccountID(anyInt())).thenReturn(null);

        ResetPasswordResponse res = getAccountService().resetPassword("invalid token", null);

        assertNotNull(res);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, res.getStatus());
        assertEquals("Un problème est survenu", res.getMessage());
    }

    @Test
    public void resetPasswordFailsWhenOldPasswordIsWrong() {

        Account pub = helper.getAccountPub();
        String token = SessionManager.getInstance().addSession(pub);

        when(getAccountRepository().findByAccountID(anyInt())).thenReturn(pub);

        ResetPasswordRequest req = helper.getResetPasswordRequestWhereOldPasswordIsWrong();
        ResetPasswordResponse res = getAccountService().resetPassword(token, req);

        assertNotNull(res);
        assertEquals(HttpStatus.BAD_REQUEST, res.getStatus());
        assertEquals("Ancien mot de passe n'est pas valide", res.getMessage());
    }

    @Test
    public void resetPasswordFailsWhenNewPasswordsAreNotTheSame() {

        Account pub = helper.getAccountPub();
        String token = SessionManager.getInstance().addSession(pub);

        when(getAccountRepository().findByAccountID(anyInt())).thenReturn(pub);

        ResetPasswordRequest req = helper.getResetPasswordRequestWhereNewPasswordsAreNotTheSame();
        ResetPasswordResponse res = getAccountService().resetPassword(token, req);

        assertNotNull(res);
        assertEquals(HttpStatus.BAD_REQUEST, res.getStatus());
        assertEquals("Nouveaux mots de passe ne sont pas identiques", res.getMessage());
    }

    @Test
    public void resetPasswordSucceeds() {

        Account pub = helper.getAccountPub();
        String token = SessionManager.getInstance().addSession(pub);

        when(getAccountRepository().findByAccountID(anyInt())).thenReturn(pub);

        ResetPasswordRequest req = helper.getResetPasswordRequest();
        ResetPasswordResponse res = getAccountService().resetPassword(token, req);

        assertNotNull(res);
        assertEquals(HttpStatus.OK, res.getStatus());
        assertEquals("Mot de passe modifié", res.getMessage());
    }
}
