package Model.film;

import Model.film.Film;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FilmExtractor {
    public Film extract(ResultSet resultSet) throws SQLException {
        Film film = new Film();

        film.setId(resultSet.getInt("film.id"));
        film.setDatePublishing(resultSet.getDate("film.date_publishing").toLocalDate());
        film.setGenre(resultSet.getInt("film.genre"));
        film.setLength(resultSet.getInt("film.duration"));
        film.setPlot(resultSet.getString("film.plot"));
        film.setTitle(resultSet.getString("film.title"));
        film.setCover(resultSet.getString("film.cover"));

        return film;
    }
}