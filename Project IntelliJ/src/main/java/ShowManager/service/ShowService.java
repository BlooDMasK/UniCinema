package ShowManager.service;

import model.bean.Film;
import model.bean.Room;
import model.bean.Show;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Interfaccia per i metodi del sottosistema Show (Gestione Spettacoli).
 */
public interface ShowService {

    /**
     * Firma del metodo che implementa la funzionalità che restituisce tutti gli spettacoli che proiettano un dato film.
     * @param film proiettato.
     * @return lista degli spettacoli.
     * @throws SQLException
     */
    ArrayList<Show> fetchAll(Film film) throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità che restituisce tutti gli spettacoli.
     * @return lista di tutti gli spettacoli.
     * @throws SQLException
     */
    ArrayList<Show> fetchAll() throws SQLException;

    ArrayList<Show> fetchDaily(int roomId, LocalDate date) throws SQLException;

    /**
     * Quando devo aggiornare, devo escludere dalla ricerca l'oggetto show
     * @param roomId
     * @param date
     * @param show
     * @return
     * @throws SQLException
     */
    ArrayList<Show> fetchDaily(int roomId, LocalDate date, Show show) throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità che restituisce uno spettacolo.
     * @param id rappresenta l'identificativo numero dello spettacolo
     * @return uno spettacolo
     * @throws SQLException
     */
    Show fetch(int id) throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità che restituisce la sala dove si tiene lo spettacolo.
     * @param showId che si tiene nella sala
     * @return una sala
     * @throws SQLException
     */
    Room fetchRoom(int showId) throws SQLException;

    boolean remove(int showId) throws SQLException;

    boolean insert(Show show) throws SQLException;

    boolean update(Show show) throws SQLException;
}
