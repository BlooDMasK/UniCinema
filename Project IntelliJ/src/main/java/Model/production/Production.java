package Model.production;

import Model.film.Film;

public class Production {
    int id;
    private Film film;
    private String firstname, lastname;

    public Production(int id, Film film, String firstname, String lastname) {
        this.id = id;
        this.film = film;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Production() {

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
