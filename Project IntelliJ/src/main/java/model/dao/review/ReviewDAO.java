package model.dao.review;

import model.bean.Film;
import model.bean.Review;
import utils.Paginator;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * Questa interfaccia rappresenta il DAO di Review
 */
public interface ReviewDAO {

    /**
     * Firma del metodo che implementa la funzionalità che permette di restituire la lista delle Recensioni presenti
     * @param paginator {@link Paginator}
     * @return lista delle Recensioni
     * @throws SQLException
     */
    List<Review> fetchAll(Paginator paginator) throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità che permette di restituire la lista delle Recensioni presenti
     * @return lista delle Recensioni
     * @throws SQLException
     */
    ArrayList<Review> fetchAll() throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità che permette di restituire la lista delle Recensioni presenti in un film a partire da filmId
     * @param filmId identificativo numerico del Film
     * @return lista delle Recensioni
     * @throws SQLException
     */
    ArrayList<Review> fetchAll(int filmId) throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità che permette di restituire la lista delle Recensioni presenti in un film
     * @param film film contenente la lista delle recensione
     * @param paginator {@link Paginator}
     * @return lista delle Recensioni
     * @throws SQLException
     */
    ArrayList<Review> fetchAll(Film film, Paginator paginator) throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità che permette di restituire una Recensione fatta da un Account a partire da accountId
     * @param accountId identificativo numerico dell'Account che ha effettuato la Recensione
     * @return Review
     * @throws SQLException
     */
    Review fetch(int accountId) throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità che permette di restituire una Recensione fatta da un Account ad un Film a partire da accountId e filmId
     * @param accountId identificativo numerico dell'Account che ha effettuato la Recensione
     * @param filmId identificativo numerico del film contenente la recensione effettuata da Account
     * @return Review
     * @throws SQLException
     */
    Review fetch(int accountId, int filmId) throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità che permette di registrare una Recensione
     * @param review Recensione da registrare
     * @return true se la Recensione viene registrata, false altrimenti
     * @throws SQLException
     */
    boolean insert(Review review) throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità che permette di modificare una Recensione
     * @param review Recensione da modificare
     * @return true se la Recensione viene modificata, false altrimenti
     * @throws SQLException
     */
    boolean update(Review review) throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità che permette di rimuovere una Recensione fatta da un Account, presa dal Db, a partire da accountId
     * @param accountId id identificativo numerico dell'Account che ha effettuato la Recensione da rimuovere
     * @return true se la Recensione viene rimossa, false altrimenti
     * @throws SQLException
     */
    boolean delete(int accountId) throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità che permette di contare quante Recensioni sono presenti nel Db
     * @return intero
     * @throws SQLException
     */
    int countAll() throws SQLException;


    /**
     * Firma del metodo che implementa la funzionalità che permette di contare quante Recensioni sono presenti in un Film a partire da filmId
     * @param film film contenente le recensioni
     * @return intero
     * @throws SQLException
     */
    int countAll(Film film) throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità che permette di contare quante Recensioni ha Effettuato un Account a partire da accountId
     * @param accountId identificativo numerico dell'Account che ha effettuato le Recensioni
     * @return intero
     * @throws SQLException
     */
    int countByAccountId(int accountId) throws SQLException;
}
