package Model.room;

import Model.show.Show;
import Model.show_room_relation.ShowRoomRelation;

public class Room {
    private int n_rows, n_seats, id;

    private ShowRoomRelation showRoomRelation;

    public Room(int n_rows, int n_seats, int id, ShowRoomRelation showRoomRelation) {
        this.n_rows = n_rows;
        this.n_seats = n_seats;
        this.id = id;
        this.showRoomRelation = showRoomRelation;
    }

    public Room() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getN_rows() {
        return n_rows;
    }

    public void setN_rows(int n_rows) {
        this.n_rows = n_rows;
    }

    public int getN_seats() {
        return n_seats;
    }

    public void setN_seats(int n_seats) {
        this.n_seats = n_seats;
    }

    public ShowRoomRelation getShowRoomRelation() {
        return showRoomRelation;
    }

    public void setShowRoomRelation(ShowRoomRelation showRoomRelation) {
        this.showRoomRelation = showRoomRelation;
    }
}
