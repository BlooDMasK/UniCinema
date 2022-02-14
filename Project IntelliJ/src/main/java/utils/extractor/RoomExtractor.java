package utils.extractor;

import lombok.Generated;
import utils.ResultSetExtractor;
import model.bean.Room;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Questa classe permette di estrarre i dati di una sala da una {@link ResultSet}.
 */
@Generated
public class RoomExtractor implements ResultSetExtractor<Room> {
    /**
     * Implementa la funzionalità che permette di estrarre i dati di una sala da una {@link ResultSet}
     * @param resultSet rappresenta l'insieme delle righe SQL
     * @return la sala
     * @throws SQLException
     */
    @Override
    public Room extract(ResultSet resultSet) throws SQLException {
        Room room = new Room();

        room.setId(resultSet.getInt("room.id"));
        room.setN_rows(resultSet.getInt("room.n_rows"));
        room.setN_seats(resultSet.getInt("room.n_seats"));

        return room;
    }
}
