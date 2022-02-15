package model.dao.director;

import model.bean.Director;
import model.bean.Film;
import utils.Paginator;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Questa interfaccia rappresenta il DAO di Director
 */
public interface DirectorDAO {


    /**
     * Firma del metodo che implementa la funzionalità che permette di restituire la lista dei Registi  presenti
     * @param paginator {@link Paginator}
     * @return lista dei Registi
     * @throws SQLException
     */
    List<Director> fetchAll(Paginator paginator) throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità che permette di restituire la lista dei Registi presenti
     * @return lista dei Registi
     * @throws SQLException
     */
    List<Director> fetchAll() throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità che permette di restituire la lista dei Registi  presenti in film
     * @param film in cui la lista dei Registi è presente
     * @return lista dei Registi
     * @throws SQLException
     */
    List<Director> fetchAll(Film film) throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità che permette di restituire un Regista, preso dal Db, a partire dal suo id
     * @param id identificativo numerico del Regista da restituire
     * @return Director
     * @throws SQLException
     */
    Director fetch(int id) throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità che permette di inserire un Regista
     * @param director Account da registrare
     * @return true se il Regista viene aggiunto, false altrimenti
     * @throws SQLException
     */
    boolean insert(Director director) throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità che permette di inserire una lista di Registi
     * @param directorList lista di Registi da inserire
     * @return true se la lista Registi viene aggiunta, false altrimenti
     * @throws SQLException
     */
    boolean insert(ArrayList<Director> directorList) throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità che permette di modificare un Regista
     * @param director Regista da modificare
     * @return true se il Regista viene modificato, false altrimenti
     * @throws SQLException
     */
    boolean update(Director director) throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità che permette di modificare una lista di Registi a partire da filmId
     * @param directorList lista di Registi da modificare
     * @param filmId id del film in cui è presenta la lista dei registi
     * @return true se la lista Registi viene modificata, false altrimenti
     * @throws SQLException
     */
    boolean update(ArrayList<Director> directorList, int filmId) throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità che permette di rimuovere un Regista, preso dal Db, a partire dal suo id
     * @param id id identificativo numerico del Regista da rimuovere
     * @return true se il Regista viene rimosso, false altrimenti
     * @throws SQLException
     */
    boolean delete(int id) throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità che permette di contare quanti Registi sono presenti nel Db
     * @return intero
     * @throws SQLException
     */
    int countAll() throws SQLException;
}
