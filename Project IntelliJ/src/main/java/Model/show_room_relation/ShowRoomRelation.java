package Model.show_room_relation;

import Model.room.Room;
import Model.show.Show;

public class ShowRoomRelation {
    private Room room;
    private Show show;

    public ShowRoomRelation(Room room, Show show) {
        this.room = room;
        this.show = show;
    }

    public ShowRoomRelation() {
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }
}
