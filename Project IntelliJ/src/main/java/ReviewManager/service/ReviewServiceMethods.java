package ReviewManager.service;

import model.bean.Film;
import model.bean.Review;
import model.dao.ReviewDAO;
import utils.Paginator;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Classe che implementa i metodi definiti nell'interfaccia service del sottosistema Review (Gestione Recensioni)
 */
public class ReviewServiceMethods implements ReviewService{

    /**
     * Si occupa delle operazioni CRUD per una recensione.
     */
    ReviewDAO reviewDAO = new ReviewDAO();

    /**
     * Implementa la funzionalità che conta tutte le recensioni effettuate per un film.
     * @param film soggetto a recensioni.
     * @return un intero che rappresenta il conteggio delle recensioni.
     * @throws SQLException
     */
    @Override
    public int countAll(Film film) throws SQLException {
        return reviewDAO.countAll(film);
    }

    /**
     * Implementa la funzionalità che permette di restituire una lista contenete le recensioni effettuate per un film.
     * @param film soggetto a recensioni.
     * @param paginator {@link Paginator}
     * @return lista di recensioni.
     * @throws SQLException
     */
    @Override
    public ArrayList<Review> fetchAll(Film film, Paginator paginator) throws SQLException {
        return reviewDAO.fetchAll(film, paginator);
    }

    @Override
    public ArrayList<Review> fetchAll(int filmId) throws SQLException {
        return reviewDAO.fetchAll(filmId);
    }

    /**
     * Implementa la funzionalità che calcola la valutazione media in base alle stelle.
     * @param reviewList lista delle recensioni su cui effettuare il calcolo.
     * @param stars parametro che indica per quali stelle effettuare la media (Esempio: se si inserisce 1, farà la media per tutte le recensioni aventi valutazione 1).
     *              Il parametro 0 permette di calcolare la media per tutte le recensioni, indipendentemente dalla loro valutazione.
     * @return media per arrotondamento matematico.
     */
    @Override
    public double averageStars(ArrayList<Review> reviewList, int stars) {
        int size = reviewList.size();
        if(size == 0)
            return 0;

        double avg = 0.0;
        for(Review review : reviewList)
            if(stars == 0 || stars == review.getStars())
                avg += review.getStars();

        return avg / size;
    }

    /**
     * Implementa la funzionalità che calcola la percentuale delle valutazioni in base alle stelle.
     * @param reviewList lista delle recensioni su cui effettuare il calcolo.
     * @param stars è un parametro che indica per quali stelle calcolare la percentuale (Esempio: se si inserisce 1, calcolerà la percentuale per tutte le recensioni aventi valutazione 1).
     *              Il parametro 0 permette di calcolare la percentuale per tutte le recensioni, indipendentemente dalla loro valutazione.
     * @return percentuale non arrotondata.
     */
    @Override
    public double percentageStars(ArrayList<Review> reviewList, int stars) {
        double size = reviewList.size();
        if(size == 0.0)
            return 0.0;

        double count = 0.0;
        for(Review review : reviewList)
            if (stars == 0.0 || stars == review.getStars())
                count++;



        return (count/size)*100.0;
    }

    /**
     * Implementa la funzionalità che permette di registrare una recensione nel database.
     * @param review da registrare
     * @return true se la registrazione va a buon fine, false altrimenti
     * @throws SQLException
     */
    public boolean insert(Review review) throws SQLException {
        return reviewDAO.insert(review);
    }

    /**
     * Implementa la funzionalità che permette di prendere una Recensione dal database.
     * @param accountId che ha scritto la recensione
     * @return
     * @throws SQLException
     */
    @Override
    public Optional<Review> fetch(int accountId, int filmId) throws SQLException {
        return reviewDAO.fetch(accountId, filmId);
    }

    /**
     * Implementa la funzionalità che permette di rimuovere una recensione dal database.
     * @param accountId che ha scritto la recensione
     * @return true se la rimozione va a buon fine, false altrimenti
     * @throws SQLException
     */
    @Override
    public boolean delete(int accountId) throws SQLException {
        return reviewDAO.delete(accountId);
    }

    @Override
    public int countByAccountId(int id) throws SQLException {
        return reviewDAO.countByAccountId(id);
    }
}
