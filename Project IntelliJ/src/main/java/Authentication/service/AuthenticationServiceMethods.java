package Authentication.service;

import model.bean.Account;
import model.dao.account.AccountDAO;
import model.dao.account.AccountDAOMethods;

import java.sql.SQLException;




public class AuthenticationServiceMethods implements AuthenticationService{
    /**
     * Si occupa delle operazioni CRUD per un account.
     */
    private AccountDAO accountDAO;


    public AuthenticationServiceMethods() throws SQLException {
        accountDAO = new AccountDAOMethods();
    }

    public void setAccountDAO(AccountDAOMethods accountDAO) {
        this.accountDAO = accountDAO;
    }

    /**
     * Implementa la funzionalità di login per l'Utente Registrato.
     * @param account dell'utente da loggare.
     * @return dell'utente da loggato.
     * @throws SQLException
     */
    @Override
    public Account signin(String email, String pswrd) throws SQLException {
        return accountDAO.find(email, pswrd, false);
    }

    /**
     * Implementa la funzionalità che permette di restituire un account, preso dal database, a partire dalla sua email.
     * @param email dell'account da restituire.
     * @return true se la modifica avviene con successo, false altrimenti.
     * @throws SQLException
     */
    @Override
    public Account fetch(String email) throws SQLException {
        return accountDAO.fetch(email);
    }

    /**
     * Implementa la funzionalità di modifica dell'account.
     * @param account da modificare.
     * @return true se la modifica avviene con successo, false altrimenti.
     * @throws SQLException
     */
    @Override
    public boolean edit(Account account) throws SQLException {
        return accountDAO.update(account);
    }
}
