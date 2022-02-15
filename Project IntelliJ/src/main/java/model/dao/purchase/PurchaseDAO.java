package model.dao.purchase;

import model.bean.Purchase;
import utils.Paginator;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Questa interfaccia rappresenta il DAO di Purchase
 */
public interface PurchaseDAO {

    /**
     * Firma del metodo che implementa la funzionalità che permette di restituire la lista degli Ordini presenti
     * @param paginator {@link Paginator}
     * @return lista degli Ordini
     * @throws SQLException
     */
    List<Purchase> fetchAll(Paginator paginator) throws SQLException;


    /**
     * Firma del metodo che implementa la funzionalità che permette di restituire la lista degli Ordini di un Account, a partire dal suo accountId
     * @param paginator {@link Paginator}
     * @param accountId identificativo numerico che rappresenta l'id dell'Account
     * @return lista degli Ordini
     * @throws SQLException
     */
    ArrayList<Purchase> fetchAll(int accountId, Paginator paginator) throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità che permette di restituire un Ordine, preso dal Db, a partire dal suo id
     * @param id identificativo numerico dell'Ordine da restituire
     * @return Purchase
     * @throws SQLException
     */
    Purchase fetch(int id) throws SQLException;

    /**
     *Firma del metodo che permette di registrare un Ordine e di restituire il suo identificativo
     * @param purchase Ordine da registrare
     * @return intero identificativo dell'Ordine aggiunto
     * @throws SQLException
     */
    int insertAndReturnID(Purchase purchase) throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità che permette di contare quanti Ordini sono presenti nel Db
     * @return intero
     * @throws SQLException
     */
    int countAll() throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità che permette di contare quanti Ordini di un Account sono presenti a partire da accountId
     * @param accountId identificativo numerico dell'Account
     * @return intero
     * @throws SQLException
     */
    int countAll(int accountId) throws SQLException;


}
