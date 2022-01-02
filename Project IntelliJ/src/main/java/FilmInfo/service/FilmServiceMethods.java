package FilmInfo.service;


import model.bean.Film;
import model.dao.FilmDAO;

import java.sql.SQLException;
import java.util.*;

/**
 * Classe che implementa i metodi definiti nell'interfaccia service del sottosistema Film (Gestione Film)
 */
public class FilmServiceMethods implements FilmService {

    /**
     * Si occupa delle operazioni CRUD di un film.
     */
    FilmDAO filmDAO = new FilmDAO();

    /**
     * Implementa la funzionalità che permette di restituire un film, preso dal database, a partire dal suo id.
     * @param filmId identificativo numerico del film.
     * @return l'oggetto del film.
     * @throws SQLException
     */
    @Override
    public Optional<Film> fetch(int filmId) throws SQLException {
        return filmDAO.fetch(filmId);
    }

    /*@Override
    public Map<Integer, Film> doRetrieveOrderedFilmList(List<Show> showList) throws SQLException {
        Map<Integer, Film> filmMap = new LinkedHashMap<>();
        Optional<Film> filmOptional;
        int filmId;
        for (Show show : showList) {
            filmId = show.getFilm().getId();
            if (!filmMap.containsKey(filmId)) {
                filmOptional = filmDAO.fetch(filmId);
                if (filmOptional.isPresent()) {
                    filmOptional.get().getShowList().add(show);
                    filmMap.put(filmId, filmOptional.get());
                }
            } else
                filmMap.get(filmId).getShowList().add(show);

            Collections.sort(filmMap.get(filmId).getShowList());
        }
        return filmMap;
    }*/
}
