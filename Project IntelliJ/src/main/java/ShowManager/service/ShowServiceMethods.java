package ShowManager.service;

import model.bean.Film;
import model.bean.Show;
import model.dao.ShowDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Classe che implementa i metodi definiti nell'interfaccia service del sottosistema Show (Gestione Spettacoli).
 */
public class ShowServiceMethods implements ShowService{

    /**
     * Si occupa delle operazioni CRUD per uno spettacolo.
     */
    ShowDAO showDAO = new ShowDAO();

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
}
