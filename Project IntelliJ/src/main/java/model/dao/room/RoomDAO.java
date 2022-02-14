package model.dao.room;

import model.bean.Room;
import utils.Paginator;

import java.sql.SQLException;
import java.util.List;

public interface RoomDAO {

    List<Room> fetchAll(Paginator paginator) throws SQLException;

    Room fetch(int id) throws SQLException;

    Room fetchFromShowId(int showId) throws SQLException;

    int countAll() throws SQLException;

}
