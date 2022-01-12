package FilmInfo.service;

import model.bean.Film;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Interfaccia per i metodi del sottosistema Film (Gestione Film)
 */
public interface FilmService {

    /**
     * Firma del metodo che implementa la funzionalità che permette di restituire un film, preso dal database, a partire dal suo id.
     * @param filmId identificativo numerico del film.
     * @return l'oggetto film.
     * @throws SQLException
     */
    Optional<Film> fetch(int filmId) throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità che permette di restituire la lista dei film contenenti il carattere immesso nella searchbar
     * @param title del film da ricercare
     * @return lista dei film
     */
    ArrayList<Film> search(String title) throws SQLException;

    //Map<Integer, Film> doRetrieveOrderedFilmList(List<Show> showList) throws SQLException;
}
