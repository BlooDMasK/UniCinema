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

    public Optional<Room> fetchFromShowId(int showId) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("SELECT * FROM room JOIN spectacle sp on room.id = sp.id_room WHERE sp.id = ?")) {
                ps.setInt(1, showId);

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

    @Override
    public boolean insert(Room room) throws SQLException {
        return false;
    }

    @Override
    public boolean update(Room room) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        return false;
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
