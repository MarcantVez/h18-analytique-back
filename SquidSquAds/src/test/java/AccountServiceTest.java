import form.account.request.LoginRequest;
import form.account.response.AbstractLoginResponse;
import form.account.response.LoginFailedResponse;
import form.account.response.LoginSucceededResponse;
import model.account.Account;
import model.account.AdminType;
import model.account.WebSiteAdmin;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import repository.account.AccountRepository;
import repository.account.WebSiteAdminRepository;
import service.account.AccountService;
import service.account.WebSiteAdminService;

import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AccountServiceTest {

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
        account.setPassword("123");
        account.setBankAccount("0000 1111 2222 3333");
        account.setCreatedDate(new Date());

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

    @Test
    public void loginNoAccountTest() {

        // Repositery retourne null comme s'il ne l'a pas trouvé
        when(accountRepository.findByEmail(account.getEmail())).thenReturn(null);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("mauvais@email.com");
        loginRequest.setPassword("123");

        AbstractLoginResponse loginResponse = accountService.login(loginRequest);

        assertTrue(loginResponse instanceof LoginFailedResponse);
        assertFalse(loginResponse.isAuthenticated());
        assertEquals(HttpStatus.UNAUTHORIZED, loginResponse.getStatus());
        assertEquals("Courriel ou mot de passe incorrect", ((LoginFailedResponse) loginResponse).getMessage());
    }

    @Test
    public void loginWrongInformationTest() {

        // Repositery retourne le bon account mais le mot de passe est mauvais
        when(accountRepository.findByEmail(account.getEmail())).thenReturn(account);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(account.getEmail());
        loginRequest.setPassword("mauvais mot de passe");

        AbstractLoginResponse loginResponse = accountService.login(loginRequest);

        assertTrue(loginResponse instanceof LoginFailedResponse);
        assertFalse(loginResponse.isAuthenticated());
        assertEquals(HttpStatus.UNAUTHORIZED, loginResponse.getStatus());
        assertEquals("Courriel ou mot de passe incorrect", ((LoginFailedResponse) loginResponse).getMessage());
    }

    @Test
    public void loginSuccessful() {

        // Repositery retourne le bon account
        when(accountRepository.findByEmail(account.getEmail())).thenReturn(account);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(account.getEmail());
        loginRequest.setPassword(account.getPassword());

        AbstractLoginResponse loginResponse = accountService.login(loginRequest);

        assertTrue(loginResponse instanceof LoginSucceededResponse);
        assertTrue(loginResponse.isAuthenticated());
        assertEquals(HttpStatus.OK, loginResponse.getStatus());
        assertTrue(((LoginSucceededResponse) loginResponse).isAdminWeb());
        assertFalse(((LoginSucceededResponse) loginResponse).isAdminPub());
        assertEquals(account.getAccountID(), ((LoginSucceededResponse) loginResponse).getAccountId());
        assertEquals(account.getEmail(), ((LoginSucceededResponse) loginResponse).getDisplayName());
        assertEquals(32, ((LoginSucceededResponse) loginResponse).getToken().length());
    }
}
