package Model.review;

import Model.account.Account;
import Model.film.Film;

public class Review {
    private Account account;
    private Film film;
    private String description;
    private int stars;

    public Review(Account account, Film film, String description, int stars) {
        this.account = account;
        this.film = film;
        this.description = description;
        this.stars = stars;
    }

    public Review() {

    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }
}
