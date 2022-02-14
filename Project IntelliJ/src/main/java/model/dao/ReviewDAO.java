package model.dao;

import lombok.Generated;
import model.bean.Account;
import utils.SqlMethods;
import utils.extractor.ReviewExtractor;
import utils.extractor.AccountExtractor;
import utils.ConPool;
import utils.Paginator;
import model.bean.Film;
import utils.extractor.FilmExtractor;
import model.bean.Review;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Questa classe rappresenta il DAO di una recensione
 */
@Generated
public class ReviewDAO implements SqlMethods<Review> {
    /**
     * Implementa la funzionalità di prendere una lista di recensioni
     * @param paginator per gestire la paginazione
     * @return la lista delle recensioni
     * @throws SQLException
     */
    @Override
    public List<Review> fetchAll(Paginator paginator) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("SELECT * FROM review AS rev JOIN film on rev.id_film = film.id JOIN customer acc on rev.id_customer = acc.id ORDER BY rev.id DESC LIMIT ?,?")) {
                ps.setInt(1, paginator.getOffset());
                ps.setInt(2, paginator.getLimit());

                List<Review> reviewList = new ArrayList<>();

                ResultSet rs = ps.executeQuery();
                ReviewExtractor reviewExtractor = new ReviewExtractor();
                AccountExtractor accountExtractor = new AccountExtractor();
                FilmExtractor filmExtractor = new FilmExtractor();
                while(rs.next()) {
                    Review review = reviewExtractor.extract(rs);
                    review.setFilm(filmExtractor.extract(rs));
                    review.setAccount(accountExtractor.extract(rs));

                    reviewList.add(review);
                }
                rs.close();
                return reviewList;
            }
        }
    }

    /**
     * Implementa la funzionalità di prendere una lista di recensioni
     * @return la lista delle recensioni
     * @throws SQLException
     */
    public ArrayList<Review> fetchAll() throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("SELECT * FROM review AS rev")) {

                ArrayList<Review> reviewList = new ArrayList<>();

                ResultSet rs = ps.executeQuery();
                ReviewExtractor reviewExtractor = new ReviewExtractor();
                while(rs.next()) {
                    Review review = reviewExtractor.extract(rs);
                    reviewList.add(review);
                }
                rs.close();
                return reviewList;
            }
        }
    }

    /**
     * Implementa la funzionalità di prendere una lista di recensioni
     * @param filmId soggetto a recensione
     * @return la lista delle recensioni
     * @throws SQLException
     */
    public ArrayList<Review> fetchAll(int filmId) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("SELECT * FROM review AS rev JOIN customer acc ON rev.id_customer = acc.id WHERE id_film = ?")) {
                ps.setInt(1, filmId);

                ArrayList<Review> reviewList = new ArrayList<>();

                ResultSet rs = ps.executeQuery();
                ReviewExtractor reviewExtractor = new ReviewExtractor();
                AccountExtractor accountExtractor = new AccountExtractor();
                while(rs.next()) {
                    Review review = reviewExtractor.extract(rs);
                    Account account = accountExtractor.extract(rs);
                    review.setAccount(account);

                    reviewList.add(review);
                }
                rs.close();
                return reviewList;
            }
        }
    }

    /**
     * Implementa la funzionalità di prendere una lista di recensioni
     * @param film soggetto a recensioni
     * @param paginator per gestire la paginazione
     * @return la lista delle recensioni
     * @throws SQLException
     */
    public ArrayList<Review> fetchAll(Film film, Paginator paginator) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("SELECT * FROM review AS rev JOIN customer acc ON rev.id_customer = acc.id WHERE id_film = ? ORDER BY cast(concat(rev.rev_date, ' ', rev.rev_time) as datetime) DESC LIMIT ?,?")) {
                ps.setInt(1, film.getId());
                ps.setInt(2, paginator.getOffset());
                ps.setInt(3, paginator.getLimit());

                ArrayList<Review> reviewList = new ArrayList<>();

                ResultSet rs = ps.executeQuery();
                ReviewExtractor reviewExtractor = new ReviewExtractor();
                AccountExtractor accountExtractor = new AccountExtractor();
                while(rs.next()) {
                    Review review = reviewExtractor.extract(rs);
                    Account account = accountExtractor.extract(rs);
                    review.setAccount(account);

                    reviewList.add(review);
                }
                rs.close();
                return reviewList;
            }
        }
    }

    /**
     * Implementa la funzionalità di prendere una recensione
     * @param accountId rappresenta l'identificativo numerico del cliente che sottomette la recensione
     * @return la recensione
     * @throws SQLException
     */
    @Override
    public Review fetch(int accountId) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("SELECT * FROM review AS rev JOIN film on rev.id_film = film.id JOIN customer acc on rev.id_customer = acc.id WHERE id_customer = ?")) {
                ps.setInt(1, accountId);

                ResultSet rs = ps.executeQuery();
                Review review = null;
                if(rs.next()) {
                    AccountExtractor accountExtractor = new AccountExtractor();
                    FilmExtractor filmExtractor = new FilmExtractor();
                    ReviewExtractor reviewExtractor = new ReviewExtractor();
                    review = reviewExtractor.extract(rs);
                    review.setAccount(accountExtractor.extract(rs));
                    review.setFilm(filmExtractor.extract(rs));
                }
                rs.close();
                return review;
            }
        }
    }

    /**
     * Implementa la funzionalità di prendere una recensione
     * @param accountId rappresenta l'identificativo numerico del cliente che sottomette la recensione
     * @return la recensione
     * @throws SQLException
     */
    public Review fetch(int accountId, int filmId) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("SELECT * FROM review AS rev JOIN film on rev.id_film = film.id JOIN customer acc on rev.id_customer = acc.id WHERE id_customer = ? AND id_film = ?")) {
                ps.setInt(1, accountId);
                ps.setInt(2, filmId);

                ResultSet rs = ps.executeQuery();
                Review review = null;
                if(rs.next()) {
                    AccountExtractor accountExtractor = new AccountExtractor();
                    FilmExtractor filmExtractor = new FilmExtractor();
                    ReviewExtractor reviewExtractor = new ReviewExtractor();
                    review = reviewExtractor.extract(rs);
                    review.setAccount(accountExtractor.extract(rs));
                    review.setFilm(filmExtractor.extract(rs));
                }
                rs.close();
                return review;
            }
        }
    }

    /**
     * Implementa la funzionalità che permette di registrare una recensione nel DataBAse
     * @param review da registrare
     * @return  true se la registrazione va a buon fine, false altrimenti
     * @throws SQLException
     */
    @Override
    public boolean insert(Review review) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("INSERT INTO review (id_customer, id_film, title, caption, stars, rev_date, rev_time) VALUES (?,?,?,?,?,?,?)")) {
                ps.setInt(1, review.getAccount().getId());
                ps.setInt(2, review.getFilm().getId());
                ps.setString(3, review.getTitle());
                ps.setString(4, review.getDescription());
                ps.setInt(5, review.getStars());
                ps.setDate(6, Date.valueOf(review.getDate()));
                ps.setTime(7, Time.valueOf(review.getTime()));

                int rows = ps.executeUpdate();
                return rows == 1;
            }
        }
    }

    /**
     * Implementa la funzionalità che permette di aggiornare i dati di una recensione
     * @param review da aggiornare
     * @return true se l'aggiornamento va a buon fine, false altrimenti
     * @throws SQLException
     */
    @Override
    public boolean update(Review review) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("UPDATE review SET caption = ?, stars = ? WHERE id_customer = ?")) {
                ps.setString(1, review.getDescription());
                ps.setInt(2, review.getStars());
                ps.setInt(3, review.getAccount().getId());

                int rows = ps.executeUpdate();
                return rows == 1;
            }
        }
    }

    /**
     *Implementa la funzionalità che permette di rimuovere una recensione
     * @param accountId rappresenta l'identificativo numerico di un account
     * @return true se la rimozione va a buon fine, false altrimenti
     * @throws SQLException
     */
    @Override
    public boolean delete(int accountId) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("DELETE FROM review WHERE id_customer = ?")) {
                ps.setInt(1, accountId);

                int rows = ps.executeUpdate();
                return rows == 1;
            }
        }
    }

    /**
     * Implementa la funzionalità di conteggio delle recensioni
     * @return un intero che rappresenta il numero di recensioni registrate nel database
     * @throws SQLException
     */
    @Override
    public int countAll() throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try(PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) AS ct FROM review")) {
                ResultSet rs = ps.executeQuery();
                int ct = 0;
                if(rs.next())
                    ct = rs.getInt("ct");
                rs.close();
                return ct;
            }
        }
    }

    /**
     * Implementa la funzionalità di conteggio delle recensioni
     * @param film soggetto a recensioni
     * @return un intero che rappresenta il numero di recensioni registrate nel database
     * @throws SQLException
     */
    public int countAll(Film film) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try(PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) AS ct FROM review WHERE id_film = ?")) {
                ps.setInt(1, film.getId());

                ResultSet rs = ps.executeQuery();
                int ct = 0;
                if(rs.next())
                    ct = rs.getInt("ct");
                rs.close();
                return ct;
            }
        }
    }

    /**
     * Implementa la funzionalità di conteggio delle recensioni effettuate da un determinato account
     * @param accountId rappresenta l'identificativo numerico diun account
     * @return un intero che rappresenta il numero dir ecensioni effettuate da un account
     * @throws SQLException
     */
    public int countByAccountId(int accountId) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try(PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) AS ct FROM review WHERE id_customer = ?")) {
                ps.setInt(1, accountId);

                ResultSet rs = ps.executeQuery();
                int ct = 0;
                if(rs.next())
                    ct = rs.getInt("ct");
                rs.close();
                return ct;
            }
        }
    }
}
