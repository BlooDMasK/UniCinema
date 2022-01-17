package Signup.service;

import model.bean.Account;
import model.dao.AccountDAO;

import java.sql.SQLException;

public class SignupServiceMethods implements SignupService{

    AccountDAO accountDAO = new AccountDAO();

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
