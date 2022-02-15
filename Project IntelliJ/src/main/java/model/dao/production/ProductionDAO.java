package model.dao.production;

import model.bean.Film;
import model.bean.Production;
import utils.Paginator;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Questa interfaccia rappresenta il DAO di Production
 */
public interface ProductionDAO {

    /**
     * Firma del metodo che implementa la funzionalità che permette di restituire la lista delle Produzioni presenti
     * @param paginator {@link Paginator}
     * @return lista deglle Produzioni
     * @throws SQLException
     */
    List<Production> fetchAll(Paginator paginator) throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità che permette di restituire la lista delle Produzioni presenti
     * @return lista deglle Produzioni
     * @throws SQLException
     */
    List<Production> fetchAll() throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità che permette di restituire la lista delle Produzioni presenti in un film
     * @param film contenente la lista delle Produzioni
     * @return lista deglle Produzioni
     * @throws SQLException
     */
    List<Production> fetchAll(Film film) throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità che permette di restituire una Produzione, presa dal Db, a partire dal suo id
     * @param id identificativo numerico della Produzione da restituire
     * @return Produzione
     * @throws SQLException
     */
    Production fetch(int id) throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità che permette di registrare una Produzione
     * @param production Produzione da registrare
     * @return true se la Produzione viene registrata, false altrimenti
     * @throws SQLException
     */
    boolean insert(Production production) throws SQLException;


    /**
     * Firma del metodo che implementa la funzionalità che permette di registrare una lista di Produzioni
     * @param productionList lista di Produzioni da registrare
     * @return true se la lista di Produzioni viene registrata, false altrimenti
     * @throws SQLException
     */
    boolean insert(ArrayList<Production> productionList) throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità che permette di modificare una Produzione
     * @param production Produzione da modificare
     * @return true se la Produzione viene modificata, false altrimenti
     * @throws SQLException
     */
    boolean update(Production production) throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità che permette di modificare una lista di Produzioni di un film
     * @param productionList lista di Produzioni da modificare
     * @param filmId id del film contenente la lista di Produzioni da modificare
     * @return true se la lista di Produzioni viene modificata, false altrimenti
     * @throws SQLException
     */
    boolean update(ArrayList<Production> productionList, int filmId) throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità che permette di rimuovere una Produzione, presa dal Db, a partire dal suo id
     * @param id id identificativo numerico della Produzione da rimuovere
     * @return true se la Produzione viene rimossa, false altrimenti
     * @throws SQLException
     */
    boolean delete(int id) throws SQLException;


    /**
     * Firma del metodo che implementa la funzionalità che permette di contare quante Produzioni sono presenti nel Db
     * @return intero
     * @throws SQLException
     */
    int countAll() throws SQLException;
}
