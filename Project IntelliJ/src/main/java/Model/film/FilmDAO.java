package Model.film;

import Model.actors.ActorDAO;
import Model.api.*;
import Model.director.DirectorDAO;
import Model.houseproduction.HouseProductionDAO;
import Model.production.ProductionDAO;
import Model.review.ReviewDAO;
import Model.show.ShowDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FilmDAO implements SqlMethods<Film> {
    @Override
    public List<Film> fetchAll(Paginator paginator) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try(PreparedStatement ps = con.prepareStatement("SELECT * FROM film LIMIT ?,?")){
                ps.setInt(1, paginator.getOffset());
                ps.setInt(2, paginator.getLimit());

                List<Film> films = new ArrayList<>();

                ResultSet rs = ps.executeQuery();
                FilmExtractor filmExtractor = new FilmExtractor();
                while(rs.next()) {
                    Film film = filmExtractor.extract(rs);
                    film.setShowList(new ShowDAO().fetchAll(film));
                    film.setReviewList(new ReviewDAO().fetchAll(film));
                    films.add(film);
                }
                rs.close();
                return films;
            }
        }
    }

    @Override
    public Optional<Film> fetch(int id) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("SELECT * FROM film WHERE id = ?")) {
                ps.setInt(1,id);

                ResultSet rs = ps.executeQuery();
                Film film = null;
                if(rs.next()) {
                    FilmExtractor filmExtractor = new FilmExtractor();
                    film = filmExtractor.extract(rs);
                    rs.close();
                }
                return Optional.ofNullable(film);
            }
        }
    }

    @Override
    public boolean insert(Film film) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("INSERT INTO film (title, duration, date_publishing, genre, plot) VALUES(?,?,?,?,?)")) {
                ps.setString(1, film.getTitle());
                ps.setInt(2, film.getLength());
                ps.setDate(3, java.sql.Date.valueOf(film.getDatePublishing()));
                ps.setInt(4, film.getGenre());
                ps.setString(5, film.getPlot());

                int rows = ps.executeUpdate();
                return rows == 1;
            }
        }
    }

    @Override
    public boolean update(Film film) throws SQLException {
        try (Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("UPDATE film SET title = ?, duration = ?, date_publishing = ?, genre = ?, plot = ? WHERE id = ?")) {
                ps.setString(1, film.getTitle());
                ps.setInt(2, film.getLength());
                ps.setDate(3, java.sql.Date.valueOf(film.getDatePublishing()));
                ps.setInt(4, film.getGenre());
                ps.setString(5, film.getPlot());
                ps.setInt(6, film.getId());

                int rows = ps.executeUpdate();
                return rows == 1;
            }
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try(PreparedStatement ps =
                        con.prepareStatement("DELETE FROM film WHERE id = ?")) {

                ps.setInt(1, id);

                int rows = ps.executeUpdate();
                return rows == 1;
            }
        }
    }

    @Override
    public int countAll() throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try(PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) AS ct FROM film")) {
                ResultSet rs = ps.executeQuery();
                int ct = 0;
                if(rs.next())
                    ct = rs.getInt("ct");
                rs.close();
                return ct;
            }
        }
    }

    public List<Film> search(List<Condition> conditions) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            String query = "SELECT * FROM film WHERE " + Condition.searchConditions(conditions, "film");
            try(PreparedStatement ps = con.prepareStatement(query)) {
                for(int i = 0; i < conditions.size(); i++) {
                    if(conditions.get(i).getOperator() == Operator.MATCH)
                        ps.setObject(i+1, "%" + conditions.get(i).getValue() + "%");
                    else
                        ps.setObject(i+1, conditions.get(i).getValue());
                }

                ResultSet rs = ps.executeQuery();

                FilmExtractor filmExtractor = new FilmExtractor();

                List<Film> filmList = new ArrayList<>();
                while(rs.next()) {
                    Film film = filmExtractor.extract(rs);

                    film.setActorList(new ActorDAO().fetchAll(film));
                    film.setDirectorList(new DirectorDAO().fetchAll(film));
                    film.setHouseProductionList(new HouseProductionDAO().fetchAll(film));
                    film.setProductionList(new ProductionDAO().fetchAll(film));
                    film.setReviewList(new ReviewDAO().fetchAll(film));
                    film.setShowList(new ShowDAO().fetchAll(film));

                    filmList.add(film);
                }
                return filmList;
            }
        }
    }
}
