package model.dao.account;

import model.bean.Account;
import utils.Paginator;

import java.sql.SQLException;
import java.util.List;


/**
 * Questa interfaccia rappresenta il DAO di Account
 */
public interface AccountDAO {


    /**
     * Firma del metodo che implementa la funzionalità che permette di restituire la lista degli Account presenti
     * @param paginator {@link Paginator}
     * @return lista degli Account
     * @throws SQLException
     */
    List<Account> fetchAll(Paginator paginator) throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità che permette di registrare un Account
     * @param account Account da registrare
     * @return true se l'Account viene registrato, false altrimenti
     * @throws SQLException
     */
    boolean insert(Account account) throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità che permette di restituire un Account, preso dal Db, a partire dal suo id
     * @param id identificativo numerico dell'Account da restituire
     * @return Account
     * @throws SQLException
     */
    Account fetch(int id) throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalitò che permette di restituire un Account, preso dal Db, a partire dalla sua email
     * @param email email dell'Account da restituire
     * @return Account
     * @throws SQLException
     */
    Account fetch(String email) throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità che permette di rimuovere un Account, preso dal Db, a partire dal suo id
     * @param id id identificativo numerico dell'Account da rimuovere
     * @return true se l'Account viene rimosso, false altrimenti
     * @throws SQLException
     */
    boolean delete(int id) throws SQLException;


    /**
     * Firma del metodo che implementa la funzionalità che permette di modificare un Account
     * @param account account da modificare
     * @return true se l'Account viene modificato, false altrimenti
     * @throws SQLException
     */
    boolean update(Account account) throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità che permette di ricerare un Account, a partire dalla sua email, passowrd, admin
     * @param email email dell'account da ricercare
     * @param password password dell'account da ricercare
     * @param admin true se l'account da ricercare è admin, false altrimenti
     * @return Account
     * @throws SQLException
     */
    Account find(String email, String password, boolean admin) throws SQLException;


    /**
     * Firma del metodo che implementa la funzionalità che permette di contare quanti Account sono presenti nel Db
     * @return intero
     * @throws SQLException
     */
    int countAll() throws SQLException;

}
