package model.dao;

import lombok.Generated;
import utils.*;
import utils.extractor.FilmExtractor;
import model.bean.Actor;
import utils.extractor.ActorExtractor;
import model.bean.Director;
import utils.extractor.DirectorExtractor;
import model.bean.HouseProduction;
import utils.extractor.HouseProductionExtractor;
import model.bean.Production;
import utils.extractor.ProductionExtractor;
import model.bean.Film;

import java.sql.*;
import java.sql.Date;
import java.util.*;

/**
 * Questa classe rappresenta il DAO di un film.
 */
@Generated
public class FilmDAO implements SqlMethods<Film> {

    /**
     * Implementa la funzionalità di prendere una lista di film.
     * @param paginator per gestire la paginazione
     * @return la lista dei film
     * @throws SQLException
     */
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

    /**
     * Implementa la funzionalità di prendere un film.
     * @param id rappresenta l'identificativo numerico del film
     * @return il film
     * @throws SQLException
     */
    @Override
    public Film fetch(int id) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try(PreparedStatement filmStatement = con.prepareStatement("SELECT * FROM film WHERE id = ?");
                PreparedStatement productionStatement = con.prepareStatement("SELECT * FROM production prod WHERE prod.id_film = ?");
                PreparedStatement houseProductionStatement = con.prepareStatement("SELECT * FROM house_production hp WHERE hp.id_film = ?");
                PreparedStatement directorStatement = con.prepareStatement("SELECT * FROM director WHERE director.id_film = ?");
                PreparedStatement actorStatement = con.prepareStatement("SELECT * FROM actor WHERE actor.id_film = ?")) {

                filmStatement.setInt(1, id);
                productionStatement.setInt(1, id);
                houseProductionStatement.setInt(1, id);
                directorStatement.setInt(1, id);
                actorStatement.setInt(1, id);

                FilmExtractor filmExtractor = new FilmExtractor();
                ProductionExtractor productionExtractor = new ProductionExtractor();
                HouseProductionExtractor houseProductionExtractor = new HouseProductionExtractor();
                DirectorExtractor directorExtractor = new DirectorExtractor();
                ActorExtractor actorExtractor = new ActorExtractor();

                ResultSet rs = filmStatement.executeQuery();

                Film film = null;
                if(rs.next()) { //Istanzio il film
                    film = filmExtractor.extract(rs);

                    rs.close();
                    rs = productionStatement.executeQuery();
                    while(rs.next()) {
                        Production production = productionExtractor.extract(rs);
                        film.addProduction(production);
                    }

                    rs.close();
                    rs = houseProductionStatement.executeQuery();
                    while(rs.next()) {
                        HouseProduction houseProduction = houseProductionExtractor.extract(rs);
                        film.addHouseProduction(houseProduction);
                    }

                    rs.close();
                    rs = directorStatement.executeQuery();
                    while(rs.next()) {
                        Director director = directorExtractor.extract(rs);
                        film.addDirector(director);
                    }

                    rs.close();
                    rs = actorStatement.executeQuery();
                    while(rs.next()) {
                        Actor actor = actorExtractor.extract(rs);
                        film.addActor(actor);
                    }
                }
                rs.close();
                return film;
            }
        }
    }

    /**
     * Implementa la funzionalità di prendere la lista dei film in uscita.
     * @param n film in uscita
     * @return la lista dei film
     * @throws SQLException
     */
    public ArrayList<Film> fetchComingSoon(int n) throws SQLException {
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

                    filmMap.get(filmId).addActor(actor);
                    filmMap.get(filmId).addDirector(director);
                    filmMap.get(filmId).addHouseProduction(houseProduction);
                    filmMap.get(filmId).addProduction(production);
                }

                rs.close();
                return new ArrayList<>(filmMap.values());
            }
        }
    }

    /**
     * Implementa la funzionalità di prendere una lista degli utlimi film usciti.
     * @param n di film usciti
     * @return la lista dei film
     * @throws SQLException
     */
    public ArrayList<Film> fetchLastReleases(int n) throws SQLException{
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

                    filmMap.get(filmId).addActor(actor);
                    filmMap.get(filmId).addDirector(director);
                    filmMap.get(filmId).addHouseProduction(houseProduction);
                    filmMap.get(filmId).addProduction(production);
                }

                rs.close();
                return new ArrayList<>(filmMap.values());
            }
        }
    }

    /**
     * Implementa la funzionalità di prendere una lista di film che contengono il carattere immesso nella searchbar.
     * @param title del film
     * @return la lista dei film
     * @throws SQLException
     */
    public ArrayList<Film> searchFromTitle(String title) throws SQLException{
        if(!title.isEmpty() && !title.isBlank() && title != null) {
            try (Connection con = ConPool.getConnection()) {
                String query = "SELECT * FROM film WHERE title LIKE '%%" + title + "%%'";
                try (PreparedStatement ps = con.prepareStatement(query)) {
                    ArrayList<Film> filmList = new ArrayList<>();

                    ResultSet rs = ps.executeQuery();
                    FilmExtractor filmExtractor = new FilmExtractor();
                    while (rs.next()) {
                        Film film = filmExtractor.extract(rs);
                        filmList.add(film);
                    }
                    rs.close();
                    return filmList;
                }
            }
        } else
            return null;
    }

    /**
     * Implementa la funzionalità di registrare un film nel database.
     * @param film da registrare
     * @return true se la registrazione va a buon fine, false altrimenti
     * @throws SQLException
     */
    @Override
    public boolean insert(Film film) {
        return false;
    }

    public int insertAndReturnID(Film film) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("INSERT INTO film (title, duration, date_publishing, genre, plot, cover, poster) VALUES(?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, film.getTitle());
                ps.setInt(2, film.getLength());
                ps.setDate(3, Date.valueOf(film.getDatePublishing()));
                ps.setInt(4, film.getGenre());
                ps.setString(5, film.getPlot());
                ps.setString(6, film.getCover());
                ps.setString(7, film.getPoster());

                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();
                if(rs.next()) {
                    int filmId = rs.getInt(1);
                    film.setId(filmId);
                    return filmId;
                } else
                    return 0;
            }
        }
    }

    /**
     * Implementa la funzionalità di aggiornamento dati di un film.
     * @param film da aggiornare
     * @return true se l'aggiornamento va a buon fine, false altrimenti
     * @throws SQLException
     */
    @Override
    public boolean update(Film film) throws SQLException {
        try (Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("UPDATE film SET title = ?, duration = ?, date_publishing = ?, genre = ?, plot = ?, cover = ?, poster = ? WHERE id = ?")) {
                ps.setString(1, film.getTitle());
                ps.setInt(2, film.getLength());
                ps.setDate(3, Date.valueOf(film.getDatePublishing()));
                ps.setInt(4, film.getGenre());
                ps.setString(5, film.getPlot());
                ps.setString(6, film.getCover());
                ps.setString(7, film.getPoster());
                ps.setInt(8, film.getId());

                int rows = ps.executeUpdate();
                return rows == 1;
            }
        }
    }

    /**
     * Implementa la funzionalità di rimozione di un film dal database.
     * @param id rappresenta l'identificativo numerico del film
     * @return true se la rimozione va a buon fine, false altrimenti
     * @throws SQLException
     */
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

    /**
     * Implementa la funzionalità di conteggio dei film registrati nel database.
     * @return un intero che rappresenta il numero di film registrati nel database
     * @throws SQLException
     */
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
}
