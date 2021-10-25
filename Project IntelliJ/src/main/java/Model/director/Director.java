package Model.director;

import Model.film.Film;

import java.util.ArrayList;

public class Director {
    int id;
    private Film film;
    private String firstname, lastname;

    public Director(int id, Film film, String firstname, String lastname) {
        this.id = id;
        this.film = film;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Director() {

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
