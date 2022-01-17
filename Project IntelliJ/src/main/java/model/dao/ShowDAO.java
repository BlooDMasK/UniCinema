package model.dao;

import utils.ConPool;
import utils.Paginator;
import utils.SqlMethods;
import utils.extractor.FilmExtractor;
import utils.extractor.ShowExtractor;
import model.bean.Film;
import model.bean.Show;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Questa classe rappresenta il DAO di uno Spettacolo.
 */
public class ShowDAO implements SqlMethods<Show> {

    /**
     * Implementa la funzionalità di prendere una lista degli spettacoli.
     * @param paginator per gestire la paginazione
     * @return la lista degli spettacoli
     * @throws SQLException
     */
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

    /**
     * Implementa la funzionalità di prendere una lista degli spettacoli.
     * @param film proiettato durante lo spettacolo
     * @return la lista degli spettacoli
     * @throws SQLException
     */
    public ArrayList<Show> fetchAll(Film film) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("SELECT * FROM spectacle AS sp WHERE id_film = ? AND cast(concat(show_date, ' ', show_hour) as datetime) > NOW()")) {
                ps.setInt(1, film.getId());

                ArrayList<Show> showList = new ArrayList<>();

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

    public ArrayList<Show> fetchDaily(int roomId, LocalDate date) throws SQLException {
        try(Connection con = ConPool.getConnection()) {

            try (PreparedStatement ps = con.prepareStatement("SELECT * FROM spectacle AS sp JOIN film on film.id = sp.id_film WHERE sp.id_room = ? AND show_date = ?")) {
                ps.setInt(1, roomId);
                ps.setDate(2, Date.valueOf(date));

                ArrayList<Show> showList = new ArrayList<>();

                ResultSet rs = ps.executeQuery();
                ShowExtractor showExtractor = new ShowExtractor();
                FilmExtractor filmExtractor = new FilmExtractor();
                while(rs.next()) {
                    Show show = showExtractor.extract(rs);
                    Film film = filmExtractor.extract(rs);

                    show.setFilm(film);
                    showList.add(show);
                }

                rs.close();
                return showList;
            }
        }
    }

    public ArrayList<Show> fetchDaily(int roomId, LocalDate date, Show excludingShow) throws SQLException {
        try(Connection con = ConPool.getConnection()) {

            try (PreparedStatement ps = con.prepareStatement("SELECT * FROM spectacle AS sp JOIN film on film.id = sp.id_film WHERE sp.id_room = ? AND show_date = ? AND sp.id != ?")) {
                ps.setInt(1, roomId);
                ps.setDate(2, Date.valueOf(date));
                ps.setInt(3, excludingShow.getId());

                ArrayList<Show> showList = new ArrayList<>();

                ResultSet rs = ps.executeQuery();
                ShowExtractor showExtractor = new ShowExtractor();
                FilmExtractor filmExtractor = new FilmExtractor();
                while(rs.next()) {
                    Show show = showExtractor.extract(rs);
                    Film film = filmExtractor.extract(rs);

                    show.setFilm(film);
                    showList.add(show);
                }

                rs.close();
                return showList;
            }
        }
    }

    /**
     * Implementa la funzionalità di prendere una lista degli spettacoli.
     * @return la lista degli spettacoli
     * @throws SQLException
     */
    public ArrayList<Show> fetchAll() throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("SELECT * FROM spectacle AS sp WHERE cast(concat(show_date, ' ', show_hour) as datetime) > NOW()")) {
                ArrayList<Show> showList = new ArrayList<>();

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

    /**
     * Implementa la funzionalità di prendere uno spettacolo.
     * @param id rappresenta l'identificativo numerico dello spettacolo
     * @return lo spettacolo
     * @throws SQLException
     */
    @Override
    public Optional<Show> fetch(int id) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("SELECT * FROM spectacle AS sp JOIN film on sp.id_film = film.id WHERE sp.id = ?")) {
                ps.setInt(1, id);

                Show show = null;
                Film film;
                ResultSet rs = ps.executeQuery();
                if(rs.next()) {
                    ShowExtractor showExtractor = new ShowExtractor();
                    show = showExtractor.extract(rs);

                    FilmExtractor filmExtractor = new FilmExtractor();
                    film = filmExtractor.extract(rs);

                    show.setFilm(film);
                }
                rs.close();
                return Optional.ofNullable(show);
            }
        }
    }

    /**
     * Implementa la funzionalità di registrazione di uno spettacolo nel database.
     * @param show da registrare
     * @return true se la registrazione va a buon fine, false altrimenti
     * @throws SQLException
     */
    @Override
    public boolean insert(Show show) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("INSERT INTO spectacle (show_hour, show_date, id_film, id_room) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
                ps.setTime(1, Time.valueOf(show.getTime()));
                ps.setDate(2, Date.valueOf(show.getDate()));
                ps.setInt(3, show.getFilm().getId());
                ps.setInt(4, show.getRoom().getId());
                ps.executeUpdate();

                ResultSet rs = ps.getGeneratedKeys();
                if(rs.next()) {
                    int showId = rs.getInt(1);
                    show.setId(showId);
                    return true;
                } else
                    return false;
            }
        }
    }

    /**
     * Implementa la funzionalità di aggiornare uno spettacolo.
     * @param show da aggiornare
     * @return true se l'aggiornamento va a buon fine, false altrimenti
     * @throws SQLException
     */
    @Override
    public boolean update(Show show) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("UPDATE spectacle SET show_hour = ?, show_date = ? WHERE id = ?")) {
                ps.setTime(1, Time.valueOf(show.getTime()));
                ps.setDate(2, Date.valueOf(show.getDate()));
                ps.setInt(3, show.getId());

                int rows = ps.executeUpdate();
                return rows == 1;
            }
        }
    }

    /**
     * Implementa la funzionalità di rimuovere uno spettacolo nel database.
     * @param id rappresenta l'identificativo numerico dello spettacolo
     * @return true se la rimozione va a buon fine, false altrimenti
     * @throws SQLException
     */
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

    /**
     * Implementa la funzionalità di conteggio degli spettacoli registrati nel database.
     * @return un intero che rappresenta il numero di spettacoli registrati nel database
     * @throws SQLException
     */
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
