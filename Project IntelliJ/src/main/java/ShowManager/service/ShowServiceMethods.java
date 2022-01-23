package ShowManager.service;

import model.bean.Film;
import model.bean.Room;
import model.bean.Show;
import model.dao.RoomDAO;
import model.dao.ShowDAO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Classe che implementa i metodi definiti nell'interfaccia service del sottosistema Show (Gestione Spettacoli).
 */
public class ShowServiceMethods implements ShowService{

    /**
     * Si occupa delle operazioni CRUD per uno spettacolo.
     */
    ShowDAO showDAO = new ShowDAO();

    /**
     *  Si occupa delle operazioni CRUD per una sala.
     */
    RoomDAO roomDAO = new RoomDAO();

    /**
     * Firma del metodo che implementa la funzionalità che restituisce tutti gli spettacoli che proiettano un dato film.
     * @param film proiettato.
     * @return lista degli spettacoli.
     * @throws SQLException
     */
    @Override
    public ArrayList<Show> fetchAll(Film film) throws SQLException {
        ArrayList<Show> showList = showDAO.fetchAll(film);
        Collections.sort(showList);
        return showList;
    }

    /**
     * Firma del metodo che implementa la funzionalità che restituisce tutti gli spettacoli.
     * @return lista di tutti gli spettacoli.
     * @throws SQLException
     */
    @Override
    public ArrayList<Show> fetchAll() throws SQLException {
        return showDAO.fetchAll();
    }

    @Override
    public ArrayList<Show> fetchDaily(int roomId, LocalDate date) throws SQLException {
        return showDAO.fetchDaily(roomId, date);
    }

    @Override
    public ArrayList<Show> fetchDaily(int roomId, LocalDate date, Show show) throws SQLException {
        return showDAO.fetchDaily(roomId, date, show);
    }

    /**
     * Implementa la funzionalità che restituisce uno spettacolo.
     * @param id rappresenta l'identificativo numero dello spettacolo
     * @return uno spettacolo
     * @throws SQLException
     */
    @Override
    public Show fetch(int id) throws SQLException {
        return showDAO.fetch(id);
    }

    /**
     * Implementa la funzionalità che restituisce la sala dove si tiene lo spettacolo.
     * @param showId che si tiene nella sala
     * @return una sala
     * @throws SQLException
     */
    @Override
    public Room fetchRoom(int showId) throws SQLException {
        return roomDAO.fetchFromShowId(showId);
    }

    @Override
    public boolean remove(int showId) throws SQLException {
        return showDAO.delete(showId);
    }

    @Override
    public boolean insert(Show show) throws SQLException {
        return showDAO.insert(show);
    }

    @Override
    public boolean update(Show show) throws SQLException {
        return showDAO.update(show);
    }


}
