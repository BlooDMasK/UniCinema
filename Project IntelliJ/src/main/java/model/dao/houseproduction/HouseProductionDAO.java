package model.dao.houseproduction;

import model.bean.Film;
import model.bean.HouseProduction;
import utils.Paginator;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * Questa interfaccia rappresenta il DAO di HouseProduction
 */
public interface HouseProductionDAO {

    /**
     * Firma del metodo che implementa la funzionalità che permette di restituire la lista delle Case di Produzione presenti
     * @param paginator {@link Paginator}
     * @return lista delle Case di Produzione
     * @throws SQLException
     */
    List<HouseProduction> fetchAll(Paginator paginator) throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità che permette di restituire la lista delle Case di Produzione presenti
     * @return lista delle Case di Produzione
     * @throws SQLException
     */
    List<HouseProduction> fetchAll() throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità che permette di restituire la lista delle Case di Produzione presenti in un film
     * @param film film contenente la lista di Case di Produzione
     * @return lista delle Case di Produzione
     * @throws SQLException
     */
    List<HouseProduction> fetchAll(Film film) throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità che permette di restituire la Casa di Produzione a partire dall'id
     * @param id identificativo numerico della Casa di Produzione
     * @return lista delle Case di Produzione
     * @throws SQLException
     */
    HouseProduction fetch(int id) throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità che permette di registrare una Casa di Produzione
     * @param houseProduction Casa di Produzione da registrare
     * @return true se la Casa di Produzione viene registrata, false altrimenti
     * @throws SQLException
     */
    boolean insert(HouseProduction houseProduction) throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità che permette di registrare una lista di Case di Produzione
     * @param houseProductionList lista di Case di Produzione da registrare
     * @return true se la  lista di Case di Produzione viene registrata, false altrimenti
     * @throws SQLException
     */
    boolean insert(ArrayList<HouseProduction> houseProductionList) throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità che permette di modificare una Casa di Produzione
     * @param houseProduction Casa di Produzione da modificare
     * @return true se la Casa di Produzione viene modificata, false altrimenti
     * @throws SQLException
     */
    boolean update(HouseProduction houseProduction) throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità che permette di modificare una lista di Case di Produzione
     * @param houseProductionList lista di Case di Produzione da modificare
     * @return true se la lista di Case di Produzione viene modificata, false altrimenti
     * @throws SQLException
     */
    boolean update(ArrayList<HouseProduction> houseProductionList, int filmId) throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità che permette di rimuovere una Casa di Produzione, preso dal Db, a partire dal suo id
     * @param id id identificativo numerico della Casa di Produzione da rimuovere
     * @return true se la Casa di Produzione viene rimossa, false altrimenti
     * @throws SQLException
     */
    boolean delete(int id) throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità che permette di contare quante Case di Produzione sono presenti nel Db
     * @return intero
     * @throws SQLException
     */
    int countAll() throws SQLException;
}
