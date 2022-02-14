package model.dao.show;

import model.bean.Film;
import model.bean.Show;
import utils.Paginator;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface ShowDAO {

    List<Show> fetchAll(Paginator paginator) throws SQLException;

    ArrayList<Show> fetchAll(Film film) throws SQLException;

    ArrayList<Show> fetchDaily(int roomId, LocalDate date) throws SQLException;

    ArrayList<Show> fetchDaily(int roomId, LocalDate date, Show excludingShow) throws SQLException;

    ArrayList<Show> fetchAll() throws SQLException;

    Show fetch(int id) throws SQLException;

    boolean insert(Show show) throws SQLException;

    boolean update(Show show) throws SQLException;

    boolean delete(int id) throws SQLException;

    int countAll() throws SQLException;
}
