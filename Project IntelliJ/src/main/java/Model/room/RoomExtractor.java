package Model.room;

import Model.api.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomExtractor implements ResultSetExtractor<Room> {

    @Override
    public Room extract(ResultSet resultSet) throws SQLException {
        Room room = new Room();

        room.setId(resultSet.getInt("room.id"));
        room.setN_rows(resultSet.getInt("room.n_rows"));
        room.setN_seats(resultSet.getInt("room.n_seats"));

        return room;
    }
}
