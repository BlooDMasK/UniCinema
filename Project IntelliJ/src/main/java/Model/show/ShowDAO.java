package Model.show;

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

public class ShowDAO implements SqlMethods<Show> {
    @Override
    public List<Show> fetchAll(Paginator paginator) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("SELECT * FROM spectacle AS sp LIMIT ?,?")) {
                ps.setInt(1, paginator.getOffset());
                ps.setInt(2, paginator.getLimit());

                List<Show> showList = new ArrayList<>();

                ResultSet rs = ps.executeQuery();
                ShowExtractor showExtractor = new ShowExtractor();
                while(rs.next()) {
                    Show show = showExtractor.extract(rs);
                    showList.add(show);
                }

                rs.close();
                return showList;
            }
        }
    }

    public List<Show> fetchAll(Film film) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("SELECT * FROM spectacle AS sp WHERE id_film = ?")) {
                ps.setInt(1, film.getId());

                List<Show> showList = new ArrayList<>();

                ResultSet rs = ps.executeQuery();
                ShowExtractor showExtractor = new ShowExtractor();
                while(rs.next()) {
                    Show show = showExtractor.extract(rs);
                    showList.add(show);
                }

                rs.close();
                return showList;
            }
        }
    }

    @Override
    public Optional<Show> fetch(int id) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("SELECT * FROM spectacle AS sp WHERE id = ?")) {
                ps.setInt(1, id);

                Show show = null;
                ResultSet rs = ps.executeQuery();
                if(rs.next()) {
                    ShowExtractor showExtractor = new ShowExtractor();
                    show = showExtractor.extract(rs);
                }
                rs.close();
                return Optional.ofNullable(show);
            }
        }
    }

    @Override
    public boolean insert(Show show) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("INSERT INTO spectacle (id, show_hour, show_date) VALUES (?, ?, ?)")) {
                ps.setInt(1, show.getId());
                ps.setTime(2, java.sql.Time.valueOf(show.getHour()));
                ps.setDate(3, java.sql.Date.valueOf(show.getDate()));

                int rows = ps.executeUpdate();
                return rows == 1;
            }
        }
    }

    @Override
    public boolean update(Show show) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("UPDATE spectacle SET show_hour = ?, show_date = ? WHERE id = ?")) {
                ps.setTime(1, java.sql.Time.valueOf(show.getHour()));
                ps.setDate(2, java.sql.Date.valueOf(show.getDate()));
                ps.setInt(3, show.getId());

                int rows = ps.executeUpdate();
                return rows == 1;
            }
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("DELETE FROM spectacle WHERE id = ?")) {
                ps.setInt(1, id);

                int rows = ps.executeUpdate();
                return rows == 1;
            }
        }
    }

    @Override
    public int countAll() throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try(PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) AS ct FROM spectacle")) {
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
