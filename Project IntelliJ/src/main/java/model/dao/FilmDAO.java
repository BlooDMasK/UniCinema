package model.dao;

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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Questa classe rappresenta il DAO di un film.
 */
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
    public Optional<Film> fetch(int id) throws SQLException {
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
                        film.getProductionList().add(production);
                    }

                    rs.close();
                    rs = houseProductionStatement.executeQuery();
                    while(rs.next()) {
                        HouseProduction houseProduction = houseProductionExtractor.extract(rs);
                        film.getHouseProductionList().add(houseProduction);
                    }

                    rs.close();
                    rs = directorStatement.executeQuery();
                    while(rs.next()) {
                        Director director = directorExtractor.extract(rs);
                        film.getDirectorList().add(director);
                    }

                    rs.close();
                    rs = actorStatement.executeQuery();
                    while(rs.next()) {
                        Actor actor = actorExtractor.extract(rs);
                        film.getActorList().add(actor);
                    }
                }
                rs.close();
                return Optional.ofNullable(film);
            }
        }
    }

    /**
     * Implementa la funzionalità di prendere la lista dei film in uscita.
     * @param n film in uscita
     * @return la lista dei film
     * @throws SQLException
     */
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

    /**
     * Implementa la funzionalità di prendere una lista degli utlimi film usciti.
     * @param n di film usciti
     * @return la lista dei film
     * @throws SQLException
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

    /**
     * Implementa la funzionalità di registrare un film nel database.
     * @param film da registrare
     * @return true se la registrazione va a buon fine, false altrimenti
     * @throws SQLException
     */
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
                ps.setDate(3, java.sql.Date.valueOf(film.getDatePublishing()));
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

    /**
     * Implementa la funzionalità di ricerca di un film all'interno del database.
     * @param conditions l'insieme delle {@link Condition} che permettono di effettuare la ricerca
     * @return la lista dei film
     * @throws SQLException
     */
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