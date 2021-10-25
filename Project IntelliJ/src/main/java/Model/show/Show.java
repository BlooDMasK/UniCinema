package Model.show;

import Model.film.Film;
import Model.room.Room;
import Model.show_room_relation.ShowRoomRelation;
import Model.ticket.Ticket;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Show {
    private int id;
    private LocalDate date;
    private LocalTime hour;

    private List<Ticket> ticketList;
    private List<ShowRoomRelation> showRoomRelationList;
    private Film film;

    public Show(int id, LocalDate date, LocalTime hour, List<Ticket> ticketList, List<ShowRoomRelation> showRoomRelationList, Film film) {
        this.id = id;
        this.date = date;
        this.hour = hour;
        this.ticketList = ticketList;
        this.showRoomRelationList = showRoomRelationList;
        this.film = film;
    }

    public Show() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalTime getHour() {
        return hour;
    }

    public void setHour(LocalTime hour) {
        this.hour = hour;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Ticket> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    public List<ShowRoomRelation> getShowRoomRelationList() {
        return showRoomRelationList;
    }

    public void setShowRoomRelationList(List<ShowRoomRelation> showRoomRelationList) {
        this.showRoomRelationList = showRoomRelationList;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }
}
