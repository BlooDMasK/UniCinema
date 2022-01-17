package utils.extractor;

import model.bean.Film;
import model.bean.Room;
import utils.ResultSetExtractor;
import model.bean.Show;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Questa classe permette di estrarre i dati di uno Spettacolo da una {@link ResultSet}.
 */
public class ShowExtractor implements ResultSetExtractor<Show> {

    /**
     * Implementa la funzionalità che permette di estrarre i dati di uno Spettacolo da una {@link ResultSet}
     * @param resultSet rappresenta l'insieme delle righe SQL
     * @return lo spettacolo
     * @throws SQLException
     */
    @Override
    public Show extract(ResultSet resultSet) throws SQLException {
        Show show = new Show();

        show.setId(resultSet.getInt("sp.id"));
        show.setTime(resultSet.getTime("sp.show_hour").toLocalTime());
        show.setDate(resultSet.getDate("sp.show_date").toLocalDate());

        Room room = new Room();
        room.setId(resultSet.getInt("sp.id_room"));
        show.setRoom(room);

        Film film = new Film();
        film.setId(resultSet.getInt("sp.id_film"));
        show.setFilm(film);

        return show;
    }
}
