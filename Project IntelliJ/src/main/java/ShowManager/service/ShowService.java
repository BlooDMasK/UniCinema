package ShowManager.service;

import model.bean.Film;
import model.bean.Show;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    List<Show> fetchAll() throws SQLException;



}
