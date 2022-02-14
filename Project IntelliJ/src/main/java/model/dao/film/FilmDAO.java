package model.dao.film;

import model.bean.Film;
import utils.Paginator;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface FilmDAO {

    List<Film> fetchAll(Paginator paginator) throws SQLException;

    Film fetch(int id) throws SQLException;

    ArrayList<Film> fetchComingSoon(int n) throws SQLException;

    ArrayList<Film> fetchLastReleases(int n) throws SQLException;

    ArrayList<Film> searchFromTitle(String title) throws SQLException;

    int insertAndReturnID(Film film) throws SQLException;

    boolean update(Film film) throws SQLException;

    boolean delete(int id) throws SQLException;

    int countAll() throws SQLException;
}
