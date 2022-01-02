package model.dao;

import utils.ConPool;
import utils.Paginator;
import utils.SqlMethods;
import utils.extractor.DirectorExtractor;
import model.bean.Film;
import model.bean.Director;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Questa classe rappresenta il DAO di un Regista.
 */
public class DirectorDAO implements SqlMethods<Director> {

    /**
     * Implementa la funzionalità di prendere una lista di registi.
     * @param paginator per gestire la paginazione
     * @return la lista dei registi
     * @throws SQLException
     */
    @Override
    public List<Director> fetchAll(Paginator paginator) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try(PreparedStatement ps = con.prepareStatement("SELECT * FROM director LIMIT ?,?")){
                ps.setInt(1, paginator.getOffset());
                ps.setInt(2, paginator.getLimit());

                List<Director> directorList = new ArrayList<>();

                ResultSet rs = ps.executeQuery();
                DirectorExtractor directorExtractor = new DirectorExtractor();
                while(rs.next()) {
                    Director director = directorExtractor.extract(rs);
                    directorList.add(director);
                }
                rs.close();
                return directorList;
            }
        }
    }

    /**
     * Implementa la funzionalità di prendere una lista di registi.
     * @return la lista dei registi
     * @throws SQLException
     */
    public List<Director> fetchAll() throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try(PreparedStatement ps = con.prepareStatement("SELECT * FROM director")){

                List<Director> directorList = new ArrayList<>();

                ResultSet rs = ps.executeQuery();
                DirectorExtractor directorExtractor = new DirectorExtractor();
                while(rs.next()) {
                    Director director = directorExtractor.extract(rs);
                    directorList.add(director);
                }
                rs.close();
                return directorList;
            }
        }
    }

    /**
     * Implementa la funzionalità di prendere una lista di registi.
     * @param film a cui hanno preso parte i registi
     * @return la lista dei registi
     * @throws SQLException
     */
    public List<Director> fetchAll(Film film) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try(PreparedStatement ps = con.prepareStatement("SELECT * FROM director WHERE id_film = ?")){
                ps.setInt(1, film.getId());

                List<Director> directorList = new ArrayList<>();

                ResultSet rs = ps.executeQuery();
                DirectorExtractor directorExtractor = new DirectorExtractor();
                while(rs.next()) {
                    Director director = directorExtractor.extract(rs);
                    directorList.add(director);
                }
                rs.close();
                return directorList;
            }
        }
    }

    /**
     * Implementa la funzionalità di prendere un regista.
     * @param id rappresenta l'identificativo numerico del regista
     * @return il regista
     * @throws SQLException
     */
    @Override
    public Optional<Director> fetch(int id) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("SELECT * FROM director WHERE id = ?")) {
                ps.setInt(1,id);

                ResultSet rs = ps.executeQuery();
                Director director = null;
                if(rs.next()) {
                    DirectorExtractor directorExtractor = new DirectorExtractor();
                    director = directorExtractor.extract(rs);
                }
                rs.close();
                return Optional.ofNullable(director);
            }
        }
    }

    /**
     * Implementa la funzionalità che registra un regista nel database.
     * @param director da registrare
     * @return true se la registrazione va a buon fine, false altrimenti
     * @throws SQLException
     */
    @Override
    public boolean insert(Director director) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("INSERT INTO director (firstname, lastname, id_film) VALUE (?,?,?)")) {
                ps.setString(1, director.getFirstname());
                ps.setString(2, director.getLastname());
                ps.setInt(3, director.getFilm().getId());

                int rows = ps.executeUpdate();
                return rows == 1;
            }
        }
    }

    /**
     * Implementa la funzionalità che aggiorna un regista nel database.
     * @param director da aggiornare
     * @return true se l'aggiornamento va a buon fine, false altrimenti
     * @throws SQLException
     */
    @Override
    public boolean update(Director director) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("UPDATE director SET firstname = ?, lastname = ? WHERE id = ?")) {
                ps.setString(1, director.getFirstname());
                ps.setString(2, director.getLastname());
                ps.setInt(3, director.getFilm().getId());
                ps.setInt(4, director.getId());

                int rows = ps.executeUpdate();
                return rows == 1;
            }
        }
    }

    /**
     * Implementa la funzionalità di rimozione del regista dal database.
     * @param id rappresenta l'identificativo numerico del regista
     * @return true se la rimozione va a buon fine, false altrimenti
     * @throws SQLException
     */
    @Override
    public boolean delete(int id) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("DELETE FROM director WHERE id = ?")) {
                ps.setInt(1, id);

                int rows = ps.executeUpdate();
                return rows == 1;
            }
        }
    }

    /**
     * Implementa la funzionalità di conteggio dei registi nel database.
     * @return un intero che rappresenta il numero di registi nel database.
     * @throws SQLException
     */
    @Override
    public int countAll() throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) AS ct FROM director")) {
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
