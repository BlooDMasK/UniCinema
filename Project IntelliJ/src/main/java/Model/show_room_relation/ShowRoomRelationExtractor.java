package Model.show_room_relation;

import Model.api.ResultSetExtractor;
import Model.room.Room;
import Model.room.RoomDAO;
import Model.show.Show;
import Model.show.ShowDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ShowRoomRelationExtractor implements ResultSetExtractor<ShowRoomRelation> {
    @Override
    public ShowRoomRelation extract(ResultSet resultSet) throws SQLException {
        int roomID = resultSet.getInt("sr.id_room"),
            showID = resultSet.getInt("sr.id_show");

        ShowRoomRelation showRoomRelation = new ShowRoomRelation();
        Optional<Room> room = new RoomDAO().fetch(roomID);
        if(room.isPresent())
            showRoomRelation.setRoom(room.get());

        Optional<Show> show = new ShowDAO().fetch(showID);
        if(show.isPresent())
            showRoomRelation.setShow(show.get());

        return showRoomRelation;
    }
}
