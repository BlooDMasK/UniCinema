package Model.actors;

import Model.api.ConPool;
import Model.api.Paginator;
import Model.api.SqlMethods;
import Model.film.Film;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ActorDAO implements SqlMethods<Actor> {
    @Override
    public List<Actor> fetchAll(Paginator paginator) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try(PreparedStatement ps = con.prepareStatement("SELECT * FROM actors LIMIT ?,?")){
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

    public List<Actor> fetchAll() throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try(PreparedStatement ps = con.prepareStatement("SELECT * FROM actors")){

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

    public List<Actor> fetchAll(Film film) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try(PreparedStatement ps = con.prepareStatement("SELECT * FROM actors WHERE id_film = ?")){
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

    @Override
    public Optional<Actor> fetch(int id) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("SELECT * FROM actors WHERE id = ?")) {
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

    @Override
    public boolean insert(Actor actor) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("INSERT INTO actors (firstname, lastname, id_film) VALUES(?,?,?)")) {
                ps.setString(1, actor.getFirstname());
                ps.setString(2, actor.getLastname());
                ps.setInt(3, actor.getFilm().getId());

                int rows = ps.executeUpdate();
                return rows == 1;
            }
        }
    }

    @Override
    public boolean update(Actor actor) throws SQLException {
        try (Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("UPDATE actors SET firstname=?, lastname=? WHERE id = ?")) {
                ps.setString(1, actor.getFirstname());
                ps.setString(2, actor.getLastname());
                ps.setInt(3, actor.getId());


                int rows = ps.executeUpdate();
                return rows == 1;
            }
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        try (Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("DELETE FROM actors WHERE id = ?")) {
                ps.setInt(1, id);

                int rows = ps.executeUpdate();
                return rows == 1;
            }
        }
    }

    @Override
    public int countAll() throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) AS ct FROM actors")) {
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
