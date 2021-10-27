package Model.review;

import Model.account.AccountExtractor;
import Model.api.ConPool;
import Model.api.Paginator;
import Model.api.SqlMethods;
import Model.film.Film;
import Model.film.FilmExtractor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReviewDAO implements SqlMethods<Review> {

    @Override
    public List<Review> fetchAll(Paginator paginator) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("SELECT * FROM review AS rev JOIN film on rev.id_film = film.id JOIN clients acc on rev.id_client = acc.id LIMIT ?,?")) {
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

    public List<Review> fetchAll() throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("SELECT * FROM review AS rev")) {

                List<Review> reviewList = new ArrayList<>();

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

    public List<Review> fetchAll(Film film) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("SELECT * FROM review AS rev WHERE id_film = ?")) {
                ps.setInt(1, film.getId());

                List<Review> reviewList = new ArrayList<>();

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

    @Override
    public Optional<Review> fetch(int id_client) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("SELECT * FROM review AS rev JOIN film on rev.id_film = film.id JOIN clients acc on rev.id_client = acc.id WHERE id_client = ?")) {
                ps.setInt(1, id_client);

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
                return Optional.ofNullable(review);
            }
        }
    }

    @Override
    public boolean insert(Review review) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("INSERT INTO review (id_client, id_film, caption, stars) VALUES (?,?,?,?)")) {
                ps.setInt(1, review.getAccount().getId());
                ps.setInt(2, review.getFilm().getId());
                ps.setString(3, review.getDescription());
                ps.setInt(4, review.getStars());

                int rows = ps.executeUpdate();
                return rows == 1;
            }
        }
    }

    @Override
    public boolean update(Review review) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("UPDATE review SET caption = ?, stars = ? WHERE id_client = ?")) {
                ps.setString(1, review.getDescription());
                ps.setInt(2, review.getStars());
                ps.setInt(3, review.getAccount().getId());

                int rows = ps.executeUpdate();
                return rows == 1;
            }
        }
    }

    @Override
    public boolean delete(int id_client) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("DELETE FROM review WHERE id_client = ?")) {
                ps.setInt(1, id_client);

                int rows = ps.executeUpdate();
                return rows == 1;
            }
        }
    }

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
}
