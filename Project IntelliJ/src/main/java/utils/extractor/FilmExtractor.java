package utils.extractor;

import lombok.Generated;
import model.bean.Film;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Questa classe permette di estrarre i dati del Film da una {@link ResultSet}.
 */
@Generated
public class FilmExtractor {

    /**
     * Implementa la funzionalità che permette di estrarre i dati del Film da una {@link ResultSet}
     * @param resultSet rappresenta l'insieme delle righe SQL
     * @return il film
     * @throws SQLException
     */
    public Film extract(ResultSet resultSet) throws SQLException {
        Film film = new Film();

        film.setId(resultSet.getInt("film.id"));
        film.setDatePublishing(resultSet.getDate("film.date_publishing").toLocalDate());
        film.setGenre(resultSet.getInt("film.genre"));
        film.setLength(resultSet.getInt("film.duration"));
        film.setPlot(resultSet.getString("film.plot"));
        film.setTitle(resultSet.getString("film.title"));
        film.setCover(resultSet.getString("film.cover"));
        film.setPoster(resultSet.getString("film.poster"));
        return film;
    }
}