package model.dao.show;

import model.bean.Film;
import model.bean.Show;
import utils.Paginator;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Questa interfaccia rappresenta il DAO di Show
 */
public interface ShowDAO {

    /**
     * Firma del metodo che permette di restituire la lista degli spettacoli
     * @param paginator {@link Paginator}
     * @return la lista degli spettacoli
     * @throws SQLException
     */
    List<Show> fetchAll(Paginator paginator) throws SQLException;

    /**
     * Firma del metodo che permette di restituire la lista degli spettacoli a partire da un film
     * @param film proiettato
     * @return la lista degli spettacoli
     * @throws SQLException
     */
    ArrayList<Show> fetchAll(Film film) throws SQLException;

    /**
     * Firma del metodo che permette di restituire la lista degli spettacoli giornalieri
     * @param roomId identificativo numerico della sala all'interno della quale viene proiettato lo spettacolo
     * @param date data dello spettacolo
     * @return lista degli spettacoli
     * @throws SQLException
     */
    ArrayList<Show> fetchDaily(int roomId, LocalDate date) throws SQLException;

    /**
     * Firma del metodo che permette di restituire la lista degli spettacoli giornalieri
     * @param roomId identificativo numerico della sala all'interno della quale viene proiettato lo spettacolo
     * @param date data dello spettacolo
     * @param excludingShow spettacolo da escludere
     * @return lista degli spettacoli
     * @throws SQLException
     */
    ArrayList<Show> fetchDaily(int roomId, LocalDate date, Show excludingShow) throws SQLException;

    /**
     * Firma del metodo che permette di restituire la lista degli spettacoli
     * @return lista degli spettacoli
     * @throws SQLException
     */
    ArrayList<Show> fetchAll() throws SQLException;

    /**
     * Firma del metodo che permette di restituire uno spettacolo a partire dal suo id
     * @param id identificativo numerico dello spettacolo
     * @return spettacolo
     * @throws SQLException
     */
    Show fetch(int id) throws SQLException;

    /**
     * Firma del metodo che permette di registrare uno spettacolo nel database
     * @param show da registrare
     * @return true se la registrazione va a buon fine, false altrimenti
     * @throws SQLException
     */
    boolean insert(Show show) throws SQLException;

    /**
     * Firma del metodo che permette di aggiornare uno spettacolo
     * @param show da aggiornare
     * @return true se l'aggiornamento va a buon fine, false altrimenti
     * @throws SQLException
     */
    boolean update(Show show) throws SQLException;

    /**
     * Firma del metodo che permette di rimuovere uno spettacolo a partire dal suo id
     * @param id identificativo numerico dello spettacolo da rimuovere
     * @return true se la rimozione va a buon fine, false altrimenti
     * @throws SQLException
     */
    boolean delete(int id) throws SQLException;

    /**
     * Firma del metodo che restituisce il numero di spettacoli registrati nel database
     */
    int countAll() throws SQLException;
}
