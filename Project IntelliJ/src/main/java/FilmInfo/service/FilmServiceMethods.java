package FilmInfo.service;


import model.bean.Film;
import model.dao.film.FilmDAO;
import model.dao.film.FilmDAOMethods;

import java.sql.SQLException;
import java.util.*;

/**
 * Classe che implementa i metodi definiti nell'interfaccia service del sottosistema Film (Gestione Film)
 */
public class FilmServiceMethods implements FilmService {

    /**
     * Si occupa delle operazioni CRUD di un film.
     */
    FilmDAO filmDAO;

    public FilmServiceMethods() throws SQLException {
        filmDAO = new FilmDAOMethods();
    }

    public void setFilmDAO(FilmDAOMethods filmDAOMethods) {
        this.filmDAO = filmDAOMethods;
    }

    /**
     * Implementa la funzionalità che permette di restituire un film, preso dal database, a partire dal suo id.
     * @param filmId identificativo numerico del film.
     * @return l'oggetto del film.
     * @throws SQLException
     */
    @Override
    public Film fetch(int filmId) throws SQLException {
        return filmDAO.fetch(filmId);
    }

    @Override
    public ArrayList<Film> search(String title) throws SQLException {
        return filmDAO.searchFromTitle(title);
    }

    @Override
    public ArrayList<Film> fetchLastReleases(int total) throws SQLException {
        return filmDAO.fetchLastReleases(total);
    }

    @Override
    public ArrayList<Film> fetchComingSoon(int total) throws SQLException {
        return filmDAO.fetchComingSoon(total);
    }
}
