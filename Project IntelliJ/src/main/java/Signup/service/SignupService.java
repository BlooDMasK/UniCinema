package Signup.service;

import model.bean.Account;

import java.sql.SQLException;

public interface SignupService {

    /**
     * Firma del metodo che implementa la funzionalit√† di registrazione.
     * @param account da registrare.
     * @return true se la registrazione ha successo, altrimenti false.
     * @throws SQLException
     */
    boolean signup(Account account) throws SQLException;
}
