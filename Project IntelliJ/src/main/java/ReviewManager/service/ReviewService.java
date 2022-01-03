package ReviewManager.service;

import model.bean.Film;
import model.bean.Review;
import utils.Paginator;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Interfaccia per i metodi del sottosistema Review (Gestione Recensioni)
 */
public interface ReviewService {

    /**
     * Firma del metodo che implementa la funzionalità che conta tutte le recensioni effettuate per un film.
     * @param film soggetto a recensioni.
     * @return un intero che rappresenta il conteggio delle recensioni.
     * @throws SQLException
     */
    int countAll(Film film) throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità che permette di restituire una lista contenete le recensioni effettuate per un film.
     * @param film soggetto a recensioni.
     * @param paginator {@link Paginator}
     * @return lista di recensioni.
     * @throws SQLException
     */
    ArrayList<Review> fetchAll(Film film, Paginator paginator) throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità che permette di restituire una lista contenete le recensioni effettuate per un film.
     * @return lista di recensioni.
     * @throws SQLException
     */
    ArrayList<Review> fetchAll(int filmId) throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità che calcola la valutazione media in base alle stelle.
     * @param reviewList lista delle recensioni su cui effettuare il calcolo.
     * @param stars parametro che indica per quali stelle effettuare la media (Esempio: se si inserisce 1, farà la media per tutte le recensioni aventi valutazione 1).
     *              Il parametro 0 permette di calcolare la media per tutte le recensioni, indipendentemente dalla loro valutazione.
     * @return media per arrotondamento matematico.
     */
    double averageStars(ArrayList<Review> reviewList, int stars);

    /**
     * Firma del metodo che implementa la funzionalità che calcola la percentuale delle valutazioni in base alle stelle.
     * @param reviewList lista delle recensioni su cui effettuare il calcolo.
     * @param stars parametro che indica per quali stelle calcolare la percentuale (Esempio: se si inserisce 1, calcolerà la percentuale per tutte le recensioni aventi valutazione 1).
     *              Il parametro 0 permette di calcolare la percentuale per tutte le recensioni, indipendentemente dalla loro valutazione.
     * @return percentuale non arrotondata.
     */
    double percentageStars(ArrayList<Review> reviewList, int stars);

    /**
     * Firma del metodo che implementa la funzionalità che permette di registrare una recensione nel database.
     * @param review da registrare
     * @return true se la registrazione va a buon fine, false altrimenti
     * @throws SQLException
     */
    boolean insert(Review review) throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità che permette di prendere una Recensione dal database.
     * @param accountId che ha scritto la recensione
     * @return recensione
     * @throws SQLException
     */
    Optional<Review> fetch(int accountId, int filmId) throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità che permette di rimuovere una recensione dal database.
     * @param accountId che ha scritto la recensione
     * @return true se la rimozione avviene con successo, false altrimenti
     * @throws SQLException
     */
    boolean delete(int accountId) throws SQLException;

    int countByAccountId(int id) throws SQLException;
}
