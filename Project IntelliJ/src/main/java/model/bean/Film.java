package model.bean;

import lombok.Data;
import org.json.JSONObject;
import utils.JsonSerializable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Questa classe rappresenta un film.
 */
@Data
public class Film implements JsonSerializable {

    /**
     * Rappresenta l'identificativo numerico del film.
     */
    private int id;

    /**
     * Rappresenta la durata del film.
     */
    private int length;

    /**
     * Rappresenta il genere (si veda {@link #toStringGenre()} per ulteriori informazioni).
     */
    private int genre;

    /**
     * Rappresenta il titolo del film.
     */
    private String title;

    /**
     * Rappresenta la trama del film.
     */
    private String plot;

    /**
     * Rappresenta la cover in 16:9 del film.
     */
    private String cover;

    /**
     * Rappresenta la locandina del film.
     */
    private String poster;

    /**
     * Rappresenta la data di pubblicazione del film.
     */
    private LocalDate datePublishing;

    /**
     * Rappresenta gli attori facenti parte del cast del film.
     */
    private List<Actor> actorList;

    /**
     * Rappresenta i registi che hanno preso parte al film.
     */
    private List<Director> directorList;

    /**
     * Rappresenta le case produttrici che hanno preso parte al film.
     */
    private List<HouseProduction> houseProductionList;

    /**
     * Rappresenta la produzione del film.
     */
    private List<Production> productionList;

    /**
     * Rappresenta le recensioni fatte al film.
     */
    private List<Review> reviewList;

    /**
     * Rappresenta gli spettacoli in cui viene proiettato il film.
     */
    private List<Show> showList;

    /**
     * Nel costruttore vuoto vengono istanziate le liste.
     */
    public Film() {
        actorList = new ArrayList<>();
        directorList = new ArrayList<>();
        houseProductionList = new ArrayList<>();
        productionList = new ArrayList<>();
        showList = new ArrayList<>();
    }

    public Film(int filmId) {
        id = filmId;
    }

    /**
     * Implementa la funzionalità che porta tutti i nomi e i cognomi dei registi in una riga.
     * @return stringa con tutti i nomi e cognomi dei registi.
     */
    public String toStringDirectors() {
        String string = "";
        for(Director director : directorList)
            string += director.getFirstname() + " " + director.getLastname() + ", ";

        string = string.substring(0, string.length() - 2);
        string += ".";
        return string;
    }

    /**
     * Implementa la funzionalità che porta tutti i nomi e i cognomi degli attori in una riga.
     * @return Stringa con tutti i nomi e cognomi degli attori.
     */
    public String toStringActors() {
        String string = "";
        for(Actor actor : actorList)
            string += actor.getFirstname() + " " + actor.getLastname() + ", ";

        string = string.substring(0, string.length() - 2);
        string += ".";
        return string;
    }

    /**
     * Implementa la funzionalità che converte il genere numerico del film in una stringa contenente il genere.
     * @return Stringa contenente il genere del film.
     */
    public String toStringGenre() {
        switch(this.genre) {
            case 0: return "Azione";
            case 1: return "Commedia";
            case 2: return "Romantico";
            case 3: return "Dramma";
        }
        return null;
    }


    @Override
    public JSONObject toJson() {
        JSONObject root = new JSONObject();
        root.put("id", id);
        root.put("title", title);
        return root;
    }
}
