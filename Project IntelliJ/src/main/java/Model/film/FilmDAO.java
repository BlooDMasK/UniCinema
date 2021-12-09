package Model.film;

import Model.actors.Actor;
import Model.actors.ActorDAO;
import Model.actors.ActorExtractor;
import Model.api.*;
import Model.director.Director;
import Model.director.DirectorDAO;
import Model.director.DirectorExtractor;
import Model.houseproduction.HouseProduction;
import Model.houseproduction.HouseProductionDAO;
import Model.houseproduction.HouseProductionExtractor;
import Model.production.Production;
import Model.production.ProductionDAO;
import Model.production.ProductionExtractor;
import Model.review.ReviewDAO;
import Model.show.ShowDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

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

    public List<Film> fetchComingSoon(int n) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try(PreparedStatement ps = con.prepareStatement("SELECT * FROM film JOIN production prod on film.id = prod.id_film JOIN house_production hp on film.id = hp.id_film JOIN director on film.id = director.id_film JOIN actor on film.id = actor.id_film WHERE DATE(date_publishing) > DATE(NOW())")) {
                ResultSet rs = ps.executeQuery();

                FilmExtractor filmExtractor = new FilmExtractor();
                ActorExtractor actorExtractor = new ActorExtractor();
                DirectorExtractor directorExtractor = new DirectorExtractor();
                HouseProductionExtractor houseProductionExtractor = new HouseProductionExtractor();
                ProductionExtractor productionExtractor = new ProductionExtractor();

                Map<Integer, Film> filmMap = new LinkedHashMap<>();
                int count = 0;
                while(rs.next() && count < n) {
                    int filmId = rs.getInt("film.id");
                    if(!filmMap.containsKey(filmId)) {
                        //Aggiungo l'oggetto film alla mappa se non esiste con quella chiave
                        Film film = filmExtractor.extract(rs);

                        film.setActorList(new ArrayList<>());
                        film.setDirectorList(new ArrayList<>());
                        film.setHouseProductionList(new ArrayList<>());
                        film.setProductionList(new ArrayList<>());

                        filmMap.put(filmId, film);
                        count++;
                    }

                    Actor actor = actorExtractor.extract(rs);
                    Director director = directorExtractor.extract(rs);
                    HouseProduction houseProduction = houseProductionExtractor.extract(rs);
                    Production production = productionExtractor.extract(rs);

                    filmMap.get(filmId).getActorList().add(actor);
                    filmMap.get(filmId).getDirectorList().add(director);
                    filmMap.get(filmId).getHouseProductionList().add(houseProduction);
                    filmMap.get(filmId).getProductionList().add(production);
                }

                rs.close();
                return new ArrayList<>(filmMap.values());
            }
        }
    }

    /*
    Restituisce gli ultimi N film.
     */
    public List<Film> fetchLastReleases(int n) throws SQLException{
        try(Connection con = ConPool.getConnection()) {
            try(PreparedStatement ps = con.prepareStatement("SELECT * FROM film JOIN production prod on film.id = prod.id_film JOIN house_production hp on film.id = hp.id_film JOIN director on film.id = director.id_film JOIN actor on film.id = actor.id_film WHERE DATE(date_publishing) <= DATE(NOW())")) {
                ResultSet rs = ps.executeQuery();

                FilmExtractor filmExtractor = new FilmExtractor();
                ActorExtractor actorExtractor = new ActorExtractor();
                DirectorExtractor directorExtractor = new DirectorExtractor();
                HouseProductionExtractor houseProductionExtractor = new HouseProductionExtractor();
                ProductionExtractor productionExtractor = new ProductionExtractor();

                Map<Integer, Film> filmMap = new LinkedHashMap<>();
                int count = 0;
                while(rs.next() && count < n) {
                    int filmId = rs.getInt("film.id");
                    if(!filmMap.containsKey(filmId)) {
                        //Aggiungo l'oggetto film alla mappa se non esiste con quella chiave
                        Film film = filmExtractor.extract(rs);

                        film.setActorList(new ArrayList<>());
                        film.setDirectorList(new ArrayList<>());
                        film.setHouseProductionList(new ArrayList<>());
                        film.setProductionList(new ArrayList<>());

                        filmMap.put(filmId, film);
                        count++;
                    }

                    Actor actor = actorExtractor.extract(rs);
                    Director director = directorExtractor.extract(rs);
                    HouseProduction houseProduction = houseProductionExtractor.extract(rs);
                    Production production = productionExtractor.extract(rs);

                    filmMap.get(filmId).getActorList().add(actor);
                    filmMap.get(filmId).getDirectorList().add(director);
                    filmMap.get(filmId).getHouseProductionList().add(houseProduction);
                    filmMap.get(filmId).getProductionList().add(production);
                }

                rs.close();
                return new ArrayList<>(filmMap.values());
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
            try (PreparedStatement ps = con.prepareStatement("UPDATE film SET title = ?, duration = ?, date_publishing = ?, genre = ?, plot = ?, cover = ? WHERE id = ?")) {
                ps.setString(1, film.getTitle());
                ps.setInt(2, film.getLength());
                ps.setDate(3, java.sql.Date.valueOf(film.getDatePublishing()));
                ps.setInt(4, film.getGenre());
                ps.setString(5, film.getPlot());
                ps.setString(6, film.getCover());
                ps.setInt(7, film.getId());

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
            String query = "SELECT * FROM film JOIN production prod on film.id = prod.id_film JOIN house_production hp on film.id = hp.id_film JOIN director on film.id = director.id_film JOIN actor on film.id = actor.id_film WHERE " + Condition.searchConditions(conditions, "film");
            try(PreparedStatement ps = con.prepareStatement(query)) {
                for(int i = 0; i < conditions.size(); i++) {
                    if(conditions.get(i).getOperator() == Operator.MATCH)
                        ps.setObject(i+1, "%" + conditions.get(i).getValue() + "%");
                    else
                        ps.setObject(i+1, conditions.get(i).getValue());
                }

                ResultSet rs = ps.executeQuery();

                FilmExtractor filmExtractor = new FilmExtractor();
                ActorExtractor actorExtractor = new ActorExtractor();
                DirectorExtractor directorExtractor = new DirectorExtractor();
                HouseProductionExtractor houseProductionExtractor = new HouseProductionExtractor();
                ProductionExtractor productionExtractor = new ProductionExtractor();

                Map<Integer, Film> filmMap = new LinkedHashMap<>();
                while(rs.next()) {
                    int filmId = rs.getInt("film.id");
                    if(!filmMap.containsKey(filmId)) {
                        //Aggiungo l'oggetto film alla mappa se non esiste con quella chiave
                        Film film = filmExtractor.extract(rs);

                        film.setActorList(new ArrayList<>());
                        film.setDirectorList(new ArrayList<>());
                        film.setHouseProductionList(new ArrayList<>());
                        film.setProductionList(new ArrayList<>());

                        filmMap.put(filmId, film);
                    }

                    Actor actor = actorExtractor.extract(rs);
                    Director director = directorExtractor.extract(rs);
                    HouseProduction houseProduction = houseProductionExtractor.extract(rs);
                    Production production = productionExtractor.extract(rs);

                    filmMap.get(filmId).getActorList().add(actor);
                    filmMap.get(filmId).getDirectorList().add(director);
                    filmMap.get(filmId).getHouseProductionList().add(houseProduction);
                    filmMap.get(filmId).getProductionList().add(production);
                }
                return new ArrayList<>(filmMap.values());
            }
        }
    }
}
