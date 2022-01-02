package Authentication.service;

import model.bean.Account;

import java.sql.SQLException;
import java.util.Optional;

public interface AuthenticationService {
    /**
     * Firma del metodo che implementa la funzione di login.
     * @param account dell'utente da loggare.
     * @return dell'utente da loggato.
     * @throws SQLException
     */
    Optional<Account> signin(Account account) throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità che permette di restituire un account, preso dal database, a partire dalla sua email.
     * @param email dell'account da restituire.
     * @return l'account da restituire.
     * @throws SQLException
     */
    Optional<Account> fetch(String email) throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità di modifica dell'account.
     * @param account da modificare.
     * @return true se la modifica avviene con successo, false altrimenti.
     * @throws SQLException
     */
    boolean edit(Account account) throws SQLException;
}
