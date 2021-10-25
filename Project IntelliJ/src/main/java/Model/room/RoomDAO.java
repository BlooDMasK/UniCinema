package Model.room;

import Model.api.ConPool;
import Model.api.Paginator;
import Model.api.SqlMethods;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RoomDAO implements SqlMethods<Room> {
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
