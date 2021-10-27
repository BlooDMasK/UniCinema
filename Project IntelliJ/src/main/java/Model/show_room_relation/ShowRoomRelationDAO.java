package Model.show_room_relation;

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

public class ShowRoomRelationDAO implements SqlMethods<ShowRoomRelation> {
    @Override
    public List<ShowRoomRelation> fetchAll(Paginator paginator) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("SELECT * FROM show_room AS sr LIMIT ?,?")) {
                ps.setInt(1, paginator.getOffset());
                ps.setInt(2, paginator.getLimit());

                List<ShowRoomRelation> showRoomRelationList = new ArrayList<>();

                ResultSet rs = ps.executeQuery();
                ShowRoomRelationExtractor showRoomRelationExtractor = new ShowRoomRelationExtractor();
                while(rs.next()) {
                    ShowRoomRelation showRoomRelation = showRoomRelationExtractor.extract(rs);
                    showRoomRelationList.add(showRoomRelation);
                }
                rs.close();
                return showRoomRelationList;
            }
        }
    }

    @Override
    public Optional<ShowRoomRelation> fetch(int id_room) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("SELECT * FROM show_room AS sr WHERE id_room = ?")) {
                ps.setInt(1, id_room);

                ResultSet rs = ps.executeQuery();
                ShowRoomRelation showRoomRelation = null;
                if(rs.next()) {
                    ShowRoomRelationExtractor showRoomRelationExtractor = new ShowRoomRelationExtractor();
                    showRoomRelation = showRoomRelationExtractor.extract(rs);
                }
                rs.close();
                return Optional.ofNullable(showRoomRelation);
            }
        }
    }

    @Override
    public boolean insert(ShowRoomRelation showRoomRelation) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("INSERT INTO show_room (id_room, id_show) VALUES (?,?)")) {
                ps.setInt(1, showRoomRelation.getRoom().getId());
                ps.setInt(2, showRoomRelation.getShow().getId());

                int rows = ps.executeUpdate();
                return rows == 1;
            }
        }
    }

    public boolean insert(int roomID, int showID) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("INSERT INTO show_room (id_room, id_show) VALUES (?,?)")) {
                ps.setInt(1, roomID);
                ps.setInt(2, showID);

                int rows = ps.executeUpdate();
                return rows == 1;
            }
        }
    }

    @Override
    public boolean update(ShowRoomRelation showRoomRelation) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(int roomID) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("DELETE FROM show_room WHERE id_room = ?")) {
                ps.setInt(1, roomID);

                int rows = ps.executeUpdate();
                return rows == 1;
            }
        }
    }

    @Override
    public int countAll() throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try(PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) AS ct FROM show_room")) {
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
