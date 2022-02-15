package model.dao.film;

import model.bean.Film;
import utils.Paginator;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * Questa interfaccia rappresenta il DAO di Film
 */
public interface FilmDAO {

    /**
     * Firma del metodo che implementa la funzionalità che permette di restituire la lista dei Film presenti
     * @param paginator {@link Paginator}
     * @return lista degli Account
     * @throws SQLException
     */
    List<Film> fetchAll(Paginator paginator) throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità che permette di restituire un Film, preso dal Db, a partire dal suo id
     * @param id identificativo numerico del Film da restituire
     * @return Film
     * @throws SQLException
     */
    Film fetch(int id) throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità di restituire la lista degli n film in uscita
     * @param n numero dei Film in uscita
     * @return lista di Film
     * @throws SQLException
     */
    ArrayList<Film> fetchComingSoon(int n) throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità di restituire la lista degli ultimi n film usciti
     * @param n numero degli ultimi n Film usciti
     * @return lista di Film
     * @throws SQLException
     */
    ArrayList<Film> fetchLastReleases(int n) throws SQLException;


    /**
     * Firma del metodo che restituisce una lista di Film a partire dal titolo
     * @param title titolo del Film da cercare
     * @return lista di film
     * @throws SQLException
     */
    ArrayList<Film> searchFromTitle(String title) throws SQLException;

    /**
     * Firma del metodo che permette di registrare un film nel Db e restituire il suo id
     * @param film da aggiungere
     * @return id del film aggiunto
     * @throws SQLException
     */
    int insertAndReturnID(Film film) throws SQLException;
    /**
     * Firma del metodo che implementa la funzionalità che permette di modificare un Film
     * @param film film da modificare
     * @return true se il film viene modificato, false altrimenti
     * @throws SQLException
     */
    boolean update(Film film) throws SQLException;
    /**
     * Firma del metodo che implementa la funzionalità che permette di rimuovere un Film, preso dal Db, a partire dal suo id
     * @param id id identificativo numerico del Film da rimuovere
     * @return true se il Film viene rimosso, false altrimenti
     * @throws SQLException
     */
    boolean delete(int id) throws SQLException;
    /**
     * Firma del metodo che implementa la funzionalità che permette di contare quanti Film sono presenti nel Db
     * @return intero
     * @throws SQLException
     */
    int countAll() throws SQLException;
}
