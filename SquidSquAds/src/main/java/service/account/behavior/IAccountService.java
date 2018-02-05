package service.account.behavior;

import model.account.Account;


public interface IAccountService {
    Iterable<Account> findAll();

    Account findByEmail(String email);

    Account addAccount(Account account);

    Account authenticate(String email, String password) throws Exception;

    void deleteByEmail(String email) throws Exception;

    Account updateAccount(Account account) throws Exception;
}
