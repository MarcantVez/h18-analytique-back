
import exception.account.AccountExceptionType;
import exception.account.AccountNotFoundException;
import exception.account.AccountWrongPasswordException;
import model.account.Account;
import model.account.AdminType;
import service.account.AccountService;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Date;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

/**
 * Created by AL on 07/02/2018.
 */


public class AccountServiceTest
{
    private static Account witnessAccount;

    @BeforeClass
    public static void init()
    {
        witnessAccount = mock(Account.class);
        when(witnessAccount.getAccountID()).thenReturn(1L);
        when(witnessAccount.getAdminType()).thenReturn(AdminType.WEB.toString());
        when(witnessAccount.getEmail()).thenReturn("test@test.com");
        when(witnessAccount.getPassword()).thenReturn("test1234");
        when(witnessAccount.getBankAccount()).thenReturn("0000 1111 2222 3333");
        when(witnessAccount.getCreatedDate()).thenReturn(new Date(0));
    }

    @Test (expected = AccountNotFoundException.class)
    public void noAccountTest() throws Exception
    {
        AccountService asMock = mock(AccountService.class);
        when(asMock.authenticate(witnessAccount.getEmail(), witnessAccount.getPassword()))
                .thenThrow(new AccountNotFoundException(AccountExceptionType.ACCOUNT_NOT_FOUND.toString()));

        asMock.authenticate(witnessAccount.getEmail(), witnessAccount.getPassword());
    }

    @Test (expected = AccountWrongPasswordException.class)
    public void authenticateFailTest() throws Exception
    {
        AccountService asMock = mock(AccountService.class);
        when(asMock.authenticate(witnessAccount.getEmail(), witnessAccount.getPassword()))
                .thenThrow(new AccountWrongPasswordException(AccountExceptionType.WRONG_PASSWORD.toString()));

        asMock.authenticate(witnessAccount.getEmail(), witnessAccount.getPassword());

        assertTrue(false);
    }

    @Test
    public void authenticateTest()
    {
        AccountService asMock = mock(AccountService.class);
        try {
            when(asMock.authenticate(witnessAccount.getEmail(), witnessAccount.getPassword()))
                    .thenReturn(witnessAccount);

            Account account = asMock.authenticate("test@test.com", "test1234");
            assertTrue(AdminType.valueOf(account.getAdminType()).equals(AdminType.WEB));
            assertTrue(account.getEmail().equals("test@test.com"));
            assertTrue(account.getPassword().equals("test1234"));
            assertTrue(account.getBankAccount().equals("0000 1111 2222 3333"));
        } catch (Exception e) {
            assertTrue(false);
        }
    }
}
