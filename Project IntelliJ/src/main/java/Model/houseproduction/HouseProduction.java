package Model.houseproduction;

import Model.film.Film;

public class HouseProduction {
    int id;
    private Film film;
    private String name;

    public HouseProduction(int id, Film film, String name) {
        this.id = id;
        this.film = film;
        this.name = name;
    }

    public HouseProduction() {

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
