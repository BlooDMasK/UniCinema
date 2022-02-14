package Signup.service;

import model.bean.Account;
import model.dao.account.AccountDAO;
import model.dao.account.AccountDAOMethods;

import java.sql.SQLException;

public class SignupServiceMethods implements SignupService{

    AccountDAO accountDAO;

    public SignupServiceMethods() throws SQLException {
        accountDAO = new AccountDAOMethods();
    }

    public void setAccountDAO(AccountDAOMethods accountDAO) {
        this.accountDAO = accountDAO;
    }

    /**
     * Implementa la funzionalità di registrazione per l'Ospite.
     * @param account da registrare.
     * @return true se la registrazione ha successo, altrimenti false.
     * @throws SQLException
     */
    @Override
    public boolean signup(Account account) throws SQLException {
        return accountDAO.insert(account);
    }
}
