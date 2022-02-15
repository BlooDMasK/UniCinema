package FilmManager.service;

import model.bean.Film;

import java.sql.SQLException;

/**
 * Interfaccia per i metodi del sottosistema Film (FilmManager)
 */
public interface FilmManagerService {

    /**
     * Firma del metodo che implementa la funzionalità che permette di rimuovere un film
     * @param filmId identificativo numerico del film.
     * @return true se la rimozione va a buon fine, false altrimenti
     * @throws SQLException
     */
    boolean removeFilm(int filmId) throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità che permette di registrare un film nel database.
     * @param film da registrare
     * @return true se la registrazione va a buon fine, false altrimenti
     * @throws SQLException
     */
    boolean insert(Film film) throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità che permette di aggiornare i valori di un film
     * @param film da aggiornare
     * @return true se l'aggiornamento va a buon fine, false altrimenti
     * @throws SQLException
     */
    boolean update(Film film) throws SQLException;
}
