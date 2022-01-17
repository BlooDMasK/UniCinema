package FilmManager.service;

import model.bean.Film;

import java.sql.SQLException;

public interface FilmManagerService {

    boolean removeFilm(int filmId) throws SQLException;

    boolean insert(Film film) throws SQLException;

    boolean update(Film film) throws SQLException;
}
