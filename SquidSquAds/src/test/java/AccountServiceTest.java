import form.account.request.LoginRequest;
import form.account.response.AbstractLoginResponse;
import form.account.response.LoginFailedResponse;
import form.account.response.LoginSucceededResponse;
import model.account.Account;
import model.account.AdminType;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import service.account.AccountService;

import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AccountServiceTest {
    private static Account witnessAccount;

    @BeforeClass
    public static void init() {
        witnessAccount = mock(Account.class);
        when(witnessAccount.getAccountID()).thenReturn(1L);
        when(witnessAccount.getAdminType()).thenReturn(AdminType.WEB.toString());
        when(witnessAccount.getEmail()).thenReturn("test@test.com");
        when(witnessAccount.getPassword()).thenReturn("test1234");
        when(witnessAccount.getBankAccount()).thenReturn("0000 1111 2222 3333");
        when(witnessAccount.getCreatedDate()).thenReturn(new Date(0));
    }

    @Test
    public void noAccountTest() {

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("doesnt@exist.com");
        loginRequest.setPassword("123");

        AccountService asMock = mock(AccountService.class);
        when(asMock.login(loginRequest)).thenReturn(new LoginFailedResponse());

        AbstractLoginResponse loginResponse = asMock.login(loginRequest);

        assertTrue(loginResponse instanceof LoginFailedResponse);
        assertFalse(loginResponse.isAuthenticated());
        assertEquals(HttpStatus.UNAUTHORIZED, loginResponse.getStatus());
    }

    @Test
    public void wrongInformationTest() {

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@test.com");
        loginRequest.setPassword("12345");

        AccountService asMock = mock(AccountService.class);
        when(asMock.login(loginRequest)).thenReturn(new LoginFailedResponse());

        AbstractLoginResponse loginResponse = asMock.login(loginRequest);

        assertTrue(loginResponse instanceof LoginFailedResponse);
        assertFalse(loginResponse.isAuthenticated());
        assertEquals(HttpStatus.UNAUTHORIZED, loginResponse.getStatus());
    }

    @Test
    public void successfulLogin() {

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(witnessAccount.getEmail());
        loginRequest.setPassword(witnessAccount.getPassword());

        AccountService asMock = mock(AccountService.class);
        when(asMock.login(loginRequest))
                .thenReturn(new LoginSucceededResponse(1L, false, true, "", ""));

        AbstractLoginResponse loginResponse = asMock.login(loginRequest);

        assertTrue(loginResponse instanceof LoginSucceededResponse);
        assertTrue(loginResponse.isAuthenticated());
        assertTrue(((LoginSucceededResponse) loginResponse).isAdminWeb());
        assertFalse(((LoginSucceededResponse) loginResponse).isAdminPub());
        assertEquals(witnessAccount.getAccountID(), ((LoginSucceededResponse) loginResponse).getAccountId());
        assertEquals("", ((LoginSucceededResponse) loginResponse).getDisplayName());
        assertEquals("", ((LoginSucceededResponse) loginResponse).getToken());
        assertEquals(HttpStatus.OK, loginResponse.getStatus());
    }
}
