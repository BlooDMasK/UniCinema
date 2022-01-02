package model.dao;

import utils.ConPool;
import utils.Paginator;
import utils.SqlMethods;
import utils.extractor.ShowRoomRelationExtractor;
import model.bean.Show;
import model.bean.ShowRoomRelation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Questa classe rappresenta il DAO della relazione tra Spettacolo e Sala
 */
public class ShowRoomRelationDAO implements SqlMethods<ShowRoomRelation> {

    /**
     * Implementa la funzionalità di prendere una lista di relazioni spettacolo-sala.
     * @param paginator per gestire la paginazione
     * @return la lista di relazioni spettacolo-sala
     * @throws SQLException
     */
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

    /**
     * Implementa la funzionalità di prendere una lista di relazioni spettacolo-sala.
     * @param roomId rappresenta l'identificativo numerico della sala
     * @return la relazione spettacolo-sala
     * @throws SQLException
     */
    @Override
    public Optional<ShowRoomRelation> fetch(int roomId) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("SELECT * FROM show_room AS sr WHERE id_room = ?")) {
                ps.setInt(1, roomId);

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

    /**
     * Implementa la funzionalità di prendere una relazione spettacolo-sala.
     * @param show rappresenta lo spettacolo legato alla sala
     * @return la relazione spettacolo-sala
     * @throws SQLException
     */
    public Optional<ShowRoomRelation> fetchFromShow(Show show) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("SELECT * FROM show_room sr JOIN room on sr.id_room = room.id WHERE show_room.id_show = ?")) {
                ps.setInt(1, show.getId());

                ResultSet rs = ps.executeQuery();
                ShowRoomRelation showRoomRelation = null;
                if(rs.next()) {
                    ShowRoomRelationExtractor showRoomRelationExtractor = new ShowRoomRelationExtractor();
                    showRoomRelation = showRoomRelationExtractor.extractFromShow(rs, show);
                }
                rs.close();
                return Optional.ofNullable(showRoomRelation);
            }
        }
    }

    /**
     * Implementa la funzionalità di registrazione di una relazione spettacolo-sala.
     * @param showRoomRelation da registrare
     * @return true se la registrazione va a buon fine, false altrimenti
     * @throws SQLException
     */
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

    /**
     * Implementa la funzionalità di registrazione di una relazione spettacolo-sala.
     * @param roomId rappresenta l'identificativo numerico della sala in cui si tiene lo spettacolo
     * @param showId rappresenta l'identificativo numerico dello spettacolo
     * @return true se la registrazione va a buon fine, false altrimenti
     * @throws SQLException
     */
    public boolean insert(int roomId, int showId) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("INSERT INTO show_room (id_room, id_show) VALUES (?,?)")) {
                ps.setInt(1, roomId);
                ps.setInt(2, showId);

                int rows = ps.executeUpdate();
                return rows == 1;
            }
        }
    }

    @Override
    public boolean update(ShowRoomRelation showRoomRelation) throws SQLException {
        return false;
    }

    /**
     * Implementa la funzionalità di rimuovere una una relazione spettacolo-sala nel database.
     * @param roomId rappresenta l'identificativo numerico della sala
     * @return true se la rimozione va a buon fine, false altrimenti
     * @throws SQLException
     */
    @Override
    public boolean delete(int roomId) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("DELETE FROM show_room WHERE id_room = ?")) {
                ps.setInt(1, roomId);

                int rows = ps.executeUpdate();
                return rows == 1;
            }
        }
    }

    /**
     * Implementa la funzionalità di conteggio delle relazioni spettacolo-sala registrate nel database.
     * @return un intero che rappresenta il numero di relazioni spettacolo-sala registrate nel database
     * @throws SQLException
     */
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
