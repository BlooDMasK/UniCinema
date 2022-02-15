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

    /**
     * Metodo che permette di settare il filmDAO con la propria implementazione.
     * @param filmDAOMethods
     */
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

    /**
     * Implementa la funzionalità che permette di restituire la lista dei film contenenti il carattere immesso nella searchbar
     * @param title del film da ricercare
     * @return lista dei film
     * @throws SQLException
     */
    @Override
    public ArrayList<Film> search(String title) throws SQLException {
        return filmDAO.searchFromTitle(title);
    }

    /**
     * Implementa la funzionalità che permette di restituire la lista degli ultimi film usciti al cinema.
     * @param total numero di film da restituire
     * @return lista dei film
     * @throws SQLException
     */
    @Override
    public ArrayList<Film> fetchLastReleases(int total) throws SQLException {
        return filmDAO.fetchLastReleases(total);
    }

    /**
     * Implementa la funzionalità che permette di restituire la lista dei film prossimi all'uscita
     * @param total numero di film da restituire
     * @return lista dei film
     * @throws SQLException
     */
    @Override
    public ArrayList<Film> fetchComingSoon(int total) throws SQLException {
        return filmDAO.fetchComingSoon(total);
    }
}
