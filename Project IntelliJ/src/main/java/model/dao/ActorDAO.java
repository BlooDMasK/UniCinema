package model.dao;

import utils.SqlMethods;
import utils.extractor.ActorExtractor;
import utils.ConPool;
import utils.Paginator;
import model.bean.Film;
import model.bean.Actor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Questa classe rappresenta il DAO di un Attore.
 */
public class ActorDAO implements SqlMethods<Actor> {

    /**
     * Implementa la funzionalità di prendere una lista di attori.
     * @param paginator per gestire la paginazione
     * @return la lista degli attori
     * @throws SQLException
     */
    @Override
    public List<Actor> fetchAll(Paginator paginator) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try(PreparedStatement ps = con.prepareStatement("SELECT * FROM actor LIMIT ?,?")){
                ps.setInt(1, paginator.getOffset());
                ps.setInt(2, paginator.getLimit());

                List<Actor> actorList = new ArrayList<>();

                ResultSet rs = ps.executeQuery();
                ActorExtractor actorExtractor = new ActorExtractor();
                while(rs.next()) {
                    Actor actor = actorExtractor.extract(rs);
                    actorList.add(actor);
                }
                rs.close();
                return actorList;
            }
        }
    }

    /**
     * Implementa la funzionalità di prendere una lista di attori.
     * @return la lista degli attori
     * @throws SQLException
     */
    public List<Actor> fetchAll() throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try(PreparedStatement ps = con.prepareStatement("SELECT * FROM actor")){

                List<Actor> actorList = new ArrayList<>();

                ResultSet rs = ps.executeQuery();
                ActorExtractor actorExtractor = new ActorExtractor();
                while(rs.next()) {
                    Actor actor = actorExtractor.extract(rs);
                    actorList.add(actor);
                }
                rs.close();
                return actorList;
            }
        }
    }

    /**
     * Implementa la funzionalità di prendere una lista di attori.
     * @param film a cui hanno preso parte gli attori
     * @return la lista degli attori
     * @throws SQLException
     */
    public List<Actor> fetchAll(Film film) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try(PreparedStatement ps = con.prepareStatement("SELECT * FROM actor WHERE id_film = ?")){
                ps.setInt(1, film.getId());

                List<Actor> actorList = new ArrayList<>();

                ResultSet rs = ps.executeQuery();
                ActorExtractor actorExtractor = new ActorExtractor();
                while(rs.next()) {
                    Actor actor = actorExtractor.extract(rs);
                    actorList.add(actor);
                }
                rs.close();
                return actorList;
            }
        }
    }

    /**
     * Implementa la funzionalità di prendere un attore.
     * @param id rappresenta l'identificativo numerico di un attore
     * @return l'attore
     * @throws SQLException
     */
    @Override
    public Optional<Actor> fetch(int id) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("SELECT * FROM actor WHERE id = ?")) {
                ps.setInt(1,id);

                ResultSet rs = ps.executeQuery();
                Actor actor = null;
                if(rs.next()) {
                    ActorExtractor actorExtractor = new ActorExtractor();
                    actor = actorExtractor.extract(rs);
                }
                rs.close();
                return Optional.ofNullable(actor);
            }
        }
    }

    /**
     * Implementa la funzionalità che permette di registrare un attore.
     * @param actor da registrare
     * @return true se la registrazione va a buon fine, false altrimenti
     * @throws SQLException
     */
    @Override
    public boolean insert(Actor actor) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("INSERT INTO actor (firstname, lastname, id_film) VALUES(?,?,?)")) {
                ps.setString(1, actor.getFirstname());
                ps.setString(2, actor.getLastname());
                ps.setInt(3, actor.getFilm().getId());

                int rows = ps.executeUpdate();
                return rows == 1;
            }
        }
    }

    /**
     * Implementa la funzionalità che permette di aggiornare i dati di un attore.
     * @param actor da aggiornare
     * @return true se l'aggiornamento va a buon fine, false altrimenti
     * @throws SQLException
     */
    @Override
    public boolean update(Actor actor) throws SQLException {
        try (Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("UPDATE actor SET firstname=?, lastname=? WHERE id = ?")) {
                ps.setString(1, actor.getFirstname());
                ps.setString(2, actor.getLastname());
                ps.setInt(3, actor.getId());


                int rows = ps.executeUpdate();
                return rows == 1;
            }
        }
    }

    /**
     * Implementa la funzionalità che permette di rimuovere un attore.
     * @param id rappresenta l'identificativo numerico di un attore
     * @return true se la rimozione va a buon fine, false altrimenti
     * @throws SQLException
     */
    @Override
    public boolean delete(int id) throws SQLException {
        try (Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("DELETE FROM actor WHERE id = ?")) {
                ps.setInt(1, id);

                int rows = ps.executeUpdate();
                return rows == 1;
            }
        }
    }

    /**
     * Implementa la funzionalità di conteggio degli attori.
     * @return un intero che rappresenta il numero di attori registrati nel database
     * @throws SQLException
     */
    @Override
    public int countAll() throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) AS ct FROM actor")) {
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
