package Model.actors;

import Model.film.Film;

public class Actor {
    private int id;
    private Film film;
    private String firstname, lastname;

    public Actor(int id, Film film, String firstname, String lastname) {
        this.id = id;
        this.film = film;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Actor() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
