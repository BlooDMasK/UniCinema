package model.dao;

import utils.ConPool;
import utils.Paginator;
import utils.SqlMethods;
import utils.extractor.RoomExtractor;
import model.bean.Room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Questa classe rappresenta il DAO di una Sala.
 */
public class RoomDAO implements SqlMethods<Room> {

    /**
     * Implementa la funzionalità di prendere una lista delle sale.
     * @param paginator per gestire la paginazione
     * @return la lista delle sale
     * @throws SQLException
     */
    @Override
    public List<Room> fetchAll(Paginator paginator) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("SELECT * FROM room LIMIT ?,?")) {
                ps.setInt(1, paginator.getOffset());
                ps.setInt(2, paginator.getLimit());

                List<Room> roomList = new ArrayList<>();

                ResultSet rs = ps.executeQuery();
                RoomExtractor roomExtractor = new RoomExtractor();
                while(rs.next()) {
                    Room room = roomExtractor.extract(rs);
                    roomList.add(room);
                }
                rs.close();
                return roomList;
            }
        }
    }

    /**
     * Implementa la funzionalità di prendere una sala.
     * @param id rappresenta l'identificativo numerico della sala
     * @return la sala
     * @throws SQLException
     */
    @Override
    public Optional<Room> fetch(int id) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("SELECT * FROM room WHERE id = ?")) {
                ps.setInt(1, id);

                ResultSet rs = ps.executeQuery();
                Room room = null;

                if(rs.next()) {
                    RoomExtractor roomExtractor = new RoomExtractor();
                    room = roomExtractor.extract(rs);
                }
                rs.close();
                return Optional.ofNullable(room);
            }
        }
    }

    /**
     * Implementa la funzionalità di registrare una sala nel database.
     * @param room da registrare
     * @return true se la registrazione va a buon fine, false altrimenti
     * @throws SQLException
     */
    @Override
    public boolean insert(Room room) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("INSERT INTO room (n_rows, n_seats) VALUES (?,?)")) {
                ps.setInt(1, room.getN_rows());
                ps.setInt(2, room.getN_seats());

                int rows = ps.executeUpdate();
                return rows == 1;
            }
        }
    }

    /**
     * Implementa la funzionalità di aggiornare una sala.
     * @param room da aggiornare
     * @return true se l'aggiornamento va a buon fine, false altrimenti
     * @throws SQLException
     */
    @Override
    public boolean update(Room room) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("UPDATE room SET n_rows = ?, n_seats = ? WHERE id = ?")) {
                ps.setInt(1, room.getN_rows());
                ps.setInt(2, room.getN_seats());
                ps.setInt(3, room.getId());

                int rows = ps.executeUpdate();
                return rows == 1;
            }
        }
    }

    /**
     * Implementa la funzionalità di rimuovere una sala nel database.
     * @param id rappresenta l'identificativo numerico della sala
     * @return true se la rimozione va a buon fine, false altrimenti
     * @throws SQLException
     */
    @Override
    public boolean delete(int id) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("DELETE FROM room WHERE id = ?")) {
                ps.setInt(1, id);

                int rows = ps.executeUpdate();
                return rows == 1;
            }
        }
    }

    /**
     * Implementa la funzionalità di conteggio delle sale registrate nel database.
     * @return un intero che rappresenta il numero di sale registrate nel database
     * @throws SQLException
     */
    @Override
    public int countAll() throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try(PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) AS ct FROM room")) {
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
