package ShowManager.service;

import model.bean.Film;
import model.bean.Room;
import model.bean.Show;
import model.dao.RoomDAO;
import model.dao.ShowDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
    public List<Show> fetchAll() throws SQLException {
        return showDAO.fetchAll();
    }

    /**
     * Implementa la funzionalità che restituisce uno spettacolo.
     * @param id rappresenta l'identificativo numero dello spettacolo
     * @return uno spettacolo
     * @throws SQLException
     */
    @Override
    public Optional<Show> fetch(int id) throws SQLException {
        return showDAO.fetch(id);
    }

    /**
     * Implementa la funzionalità che restituisce la sala dove si tiene lo spettacolo.
     * @param showId che si tiene nella sala
     * @return una sala
     * @throws SQLException
     */
    @Override
    public Optional<Room> fetchRoom(int showId) throws SQLException {
        return roomDAO.fetchFromShowId(showId);
    }

}
