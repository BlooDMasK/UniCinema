package utils.extractor;

import utils.ResultSetExtractor;
import model.bean.Room;
import model.dao.RoomDAO;
import model.bean.Show;
import model.dao.ShowDAO;
import model.bean.ShowRoomRelation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Questa classe permette di estrarre i dati di una relazione Spettacolo-Sala da una {@link ResultSet}.
 */
public class ShowRoomRelationExtractor implements ResultSetExtractor<ShowRoomRelation> {

    /**
     * Implementa la funzionalità che permette di estrarre i dati di una relazione Spettacolo-Sala da una {@link ResultSet}
     * @param resultSet rappresenta l'insieme delle righe SQL
     * @return la relazione Spettacolo-Sala
     * @throws SQLException
     */
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

    /**
     * Implementa la funzionalità che permette di estrarre i dati di una relazione Spettacolo-Sala da una {@link ResultSet}
     * @param resultSet rappresenta l'insieme delle righe SQL
     * @param show rappresenta lo spettacolo che ti svolge nella sala
     * @return la relazione Spettacolo-Sala
     * @throws SQLException
     */
    public ShowRoomRelation extractFromShow(ResultSet resultSet, Show show) throws SQLException {
        ShowRoomRelation showRoomRelation = new ShowRoomRelation();

        RoomExtractor roomExtractor = new RoomExtractor();
        Room room = roomExtractor.extract(resultSet);

        showRoomRelation.setShow(show);
        showRoomRelation.setRoom(room);

        return showRoomRelation;
    }
}
