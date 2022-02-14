package model.dao.actor;

import lombok.Generated;
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

/**
 * Questa classe rappresenta il DAO di un Attore.
 */
@Generated
public class ActorDAOMethods implements ActorDAO {

    private ConPool conPool = ConPool.getInstance();
    private Connection con = conPool.getConnection();

    public ActorDAOMethods() throws SQLException {
    }

    /**
     * Implementa la funzionalità di prendere una lista di attori.
     * @param paginator per gestire la paginazione
     * @return la lista degli attori
     * @throws SQLException
     */
    @Override
    public List<Actor> fetchAll(Paginator paginator) throws SQLException {

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

    /**
     * Implementa la funzionalità di prendere una lista di attori.
     * @return la lista degli attori
     * @throws SQLException
     */
    public List<Actor> fetchAll() throws SQLException {

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

    /**
     * Implementa la funzionalità di prendere una lista di attori.
     * @param film a cui hanno preso parte gli attori
     * @return la lista degli attori
     * @throws SQLException
     */
    public List<Actor> fetchAll(Film film) throws SQLException {

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

    /**
     * Implementa la funzionalità di prendere un attore.
     * @param id rappresenta l'identificativo numerico di un attore
     * @return l'attore
     * @throws SQLException
     */
    @Override
    public Actor fetch(int id) throws SQLException {

        try (PreparedStatement ps = con.prepareStatement("SELECT * FROM actor WHERE id = ?")) {
            ps.setInt(1,id);

            ResultSet rs = ps.executeQuery();
            Actor actor = null;
            if(rs.next()) {
                ActorExtractor actorExtractor = new ActorExtractor();
                actor = actorExtractor.extract(rs);
            }
            rs.close();
            return actor;
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

        try (PreparedStatement ps = con.prepareStatement("INSERT INTO actor (firstname, lastname, id_film) VALUES(?,?,?)")) {
            ps.setString(1, actor.getFirstname());
            ps.setString(2, actor.getLastname());
            ps.setInt(3, actor.getFilm().getId());

            int rows = ps.executeUpdate();
            return rows == 1;
        }
    }

    public boolean insert(ArrayList<Actor> actorList) throws SQLException {

        String query = "INSERT INTO actor (firstname, lastname, id_film) VALUE ";
        for(Actor actor : actorList)
            query += "('"+actor.getFirstname()+"','"+actor.getLastname()+"',"+actor.getFilm().getId()+"),";

        query = query.substring(0, query.length()-1);

        try (PreparedStatement ps = con.prepareStatement(query)) {
            int rows = ps.executeUpdate();
            return rows == actorList.size();
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
        try (PreparedStatement ps = con.prepareStatement("UPDATE actor SET firstname=?, lastname=? WHERE id = ?")) {
            ps.setString(1, actor.getFirstname());
            ps.setString(2, actor.getLastname());
            ps.setInt(3, actor.getId());


            int rows = ps.executeUpdate();
            return rows == 1;
        }
    }

    /*
    1 3 5 7 attori
    1 3 5 7 - - id

     */
    public boolean update(ArrayList<Actor> actorList, int filmId) throws SQLException {

        int deleteCount = 0,
            updateCount = 0,
            insertCount = 0;

        String deleteQuery;
        ArrayList<String> updateQueryList = new ArrayList<>();
        String insertQuery = "INSERT INTO actor(firstname, lastname, id_film) VALUES ";


        if(actorList.isEmpty()) {
            deleteQuery = "DELETE FROM actor WHERE id_film = " + filmId;
        } else {
            //Elimino tutte le righe non presenti nella nuova lista di attori
            deleteQuery = "DELETE FROM actor WHERE id_film = "+filmId+" AND id NOT IN (";
            for (Actor actor : actorList)
                if (actor.getId() != 0) {
                    deleteQuery += actor.getId() + ",";
                    deleteCount++;
                }

            deleteQuery = deleteQuery.substring(0, deleteQuery.length() - 1);
            deleteQuery += ")";

            //Aggiorno tutte le righe che sono nella lista
            for (Actor actor : actorList)
                if (actor.getId() != 0) {
                    updateQueryList.add("UPDATE actor SET firstname = '" + actor.getFirstname() + "', lastname = '" + actor.getLastname() + "' WHERE id=" + actor.getId());
                    updateCount++;
                }

            //Inserisco tutte le entità che non hanno id (non avendo id significa che sono state appena inserite nella lista)
            for (Actor actor : actorList)
                if (actor.getId() == 0) {
                    insertQuery += "('" + actor.getFirstname() + "','" + actor.getLastname() + "'," + filmId + "),";
                    insertCount++;
                }

            insertQuery = insertQuery.substring(0, insertQuery.length() - 1);
        }

        try (PreparedStatement deletePS = con.prepareStatement(deleteQuery)) {
            deletePS.executeUpdate();
        }

        if (updateCount > 0) {
            for (String updateQuery : updateQueryList)
                try (PreparedStatement updatePS = con.prepareStatement(updateQuery)) {
                    updatePS.executeUpdate();
                }
        }

        if (insertCount > 0) {
            try (PreparedStatement insertPS = con.prepareStatement(insertQuery)) {
                insertPS.executeUpdate();
            }
        }
        return true;
    }

    /**
     * Implementa la funzionalità che permette di rimuovere un attore.
     * @param id rappresenta l'identificativo numerico di un attore
     * @return true se la rimozione va a buon fine, false altrimenti
     * @throws SQLException
     */
    @Override
    public boolean delete(int id) throws SQLException {

        try (PreparedStatement ps = con.prepareStatement("DELETE FROM actor WHERE id = ?")) {
            ps.setInt(1, id);

            int rows = ps.executeUpdate();
            return rows == 1;
        }
    }

    /**
     * Implementa la funzionalità di conteggio degli attori.
     * @return un intero che rappresenta il numero di attori registrati nel database
     * @throws SQLException
     */
    @Override
    public int countAll() throws SQLException {

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
