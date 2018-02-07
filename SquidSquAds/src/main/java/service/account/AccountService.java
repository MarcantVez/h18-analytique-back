package service.account;

import exception.account.AccountNotFoundException;
import exception.account.WrongPasswordException;
import exception.account.AccountExceptionType;
import model.account.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.account.AccountRepository;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    public Iterable<Account> findAll() {
        return accountRepository.findAll();
    }

    public Account findByEmail(String email) {
        Account account = accountRepository.findByEmail(email);
        return account;
    }

    public Account addAccount(Account account) {
        return accountRepository.save(account);
    }

    public void deleteByEmail(String email) throws Exception {
        Account account = findByEmail(email);
        if( account == null )
        {
            throw new AccountNotFoundException(AccountExceptionType.ACCOUNT_NOT_FOUND.toString());
        }

        accountRepository.delete(account);
    }

    public Account updateAccount(Account account) throws Exception {
        Account oldAccount = accountRepository.findByEmail(account.getEmail());
        if( account == null )
        {
            throw new AccountNotFoundException(AccountExceptionType.ACCOUNT_NOT_FOUND.toString());
        }

        account.setAccountID(oldAccount.getAccountID());
        return accountRepository.save(account);
    }

    public Account authenticate(String email, String password) throws Exception {

        Account account = findByEmail(email);
        if( account == null )
        {
            throw new AccountNotFoundException(AccountExceptionType.ACCOUNT_NOT_FOUND.toString());
        }
        if( account.getPassword().equals(password) == false )
        {
            throw new WrongPasswordException(AccountExceptionType.WRONG_PASSWORD.toString());
        }

        return account;
    }

    public Account findAccountByAcountID(Long accountID)
    {
        return accountRepository.findOne(accountID);
    }
}
