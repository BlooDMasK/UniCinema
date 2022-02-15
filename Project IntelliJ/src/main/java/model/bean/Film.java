package model.bean;

import lombok.Data;
import lombok.Generated;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.JsonSerializable;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Questa classe rappresenta un film.
 */
@Generated
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
    private ArrayList<Actor> actorList;

    /**
     * Rappresenta i registi che hanno preso parte al film.
     */
    private ArrayList<Director> directorList;

    /**
     * Rappresenta le case produttrici che hanno preso parte al film.
     */
    private ArrayList<HouseProduction> houseProductionList;

    /**
     * Rappresenta la produzione del film.
     */
    private ArrayList<Production> productionList;

    /**
     * Rappresenta le recensioni fatte al film.
     */
    private ArrayList<Review> reviewList;

    /**
     * Rappresenta gli spettacoli in cui viene proiettato il film.
     */
    private ArrayList<Show> showList;

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


    /**
     *
     * @param id Rappresenta l'identificativo numerico del film.
     * @param length Rappresenta la durata del film.
     * @param genre Rappresenta il genere (si veda {@link #toStringGenre()} per ulteriori informazioni).
     * @param title Rappresenta il titolo del film.
     * @param plot Rappresenta la trama del film.
     * @param cover Rappresenta la cover in 16:9 del film.
     * @param poster Rappresenta la locandina del film.
     * @param datePublishing Rappresenta la data di pubblicazione del film.
     * @param actorList Rappresenta i registi che hanno preso parte al film.
     * @param directorList Rappresenta i registi che hanno preso parte al film.
     * @param houseProductionList Rappresenta le case produttrici che hanno preso parte al film.
     * @param productionList Rappresenta la produzione del film.
     */
    public Film(int id, int length, int genre, String title, String plot, String cover, String poster, LocalDate datePublishing,
                ArrayList<Actor> actorList, ArrayList<Director> directorList, ArrayList<HouseProduction> houseProductionList,
                ArrayList<Production> productionList) {
        this.id = id;
        this.length = length;
        this.genre = genre;
        this.title = title;
        this.plot = plot;
        this.cover = cover;
        this.poster = poster;
        this.datePublishing = datePublishing;
        this.actorList = actorList;
        this.directorList = directorList;
        this.houseProductionList = houseProductionList;
        this.productionList = productionList;
    }

    /**
     *
     * @param id Rappresenta l'identificativo numerico del film.
     * @param length Rappresenta la durata del film.
     * @param genre Rappresenta il genere (si veda {@link #toStringGenre()} per ulteriori informazioni).
     * @param title Rappresenta il titolo del film.
     * @param plot Rappresenta la trama del film.
     * @param cover Rappresenta la cover in 16:9 del film.
     * @param poster Rappresenta la locandina del film.
     * @param datePublishing Rappresenta la data di pubblicazione del film.
     */
    public Film(int id, int length, int genre, String title, String plot, String cover, String poster, LocalDate datePublishing) {
        this.id = id;
        this.length = length;
        this.genre = genre;
        this.title = title;
        this.plot = plot;
        this.cover = cover;
        this.poster = poster;
        this.datePublishing = datePublishing;
    }

    /**
     *
     * @param filmId  Rappresenta l'identificativo numerico del film.
     */
    public Film(int filmId) {
        id = filmId;
    }


    /**
     * implementa l'aggiunta di un attore alla lista degli attori
     * @param actor rappresenta l'attore da aggiungere
     */
    public void addActor(Actor actor) {
        actorList.add(actor);
    }

    /**
     * implementa l'aggiunta di un regista alla lista dei registi
     * @param director rappresenta il regista da aggiungere
     */
    public void addDirector(Director director) {
        directorList.add(director);
    }


    /**
     * implementa l'aggiunta di una casa di produzione alla lista delle case di produzione
     * @param houseProduction rappresenta la casa di produzione da aggiungere
     */
    public void addHouseProduction(HouseProduction houseProduction) {
        houseProductionList.add(houseProduction);
    }


    /**
     * implementa l'aggiunta di un produttore alla lista dei produttori
     * @param production rappresenta il produttore da aggiungere
     */
    public void addProduction(Production production) {
        productionList.add(production);
    }

    /**
     * implementa l'aggiunta di uno spettacolo alla lista degli spettacoli
     * @param show rappresenta lo spettacolo da aggiungere
     */
    public void addShow(Show show) {
        showList.add(show);
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
            case 1: return "Animazione";
            case 2: return "Avventura";
            case 3: return "Azione";
            case 4: return "Biografico";
            case 5: return "Commedia";
            case 6: return "Documentario";
            case 7: return "Drammatico";
            case 8: return "Fantascienza";
            case 9: return "Fantasy/Fantastico";
            case 10: return "Guerra";
            case 11: return "Horror";
            case 12: return "Musica";
            case 13: return "Storico";
            case 14: return "Thriller";
            case 15: return "Western";
        }
        return null;
    }

    /**
     * metodo che converte la classe in un oggetto JSON
     * @return root, oggetto JSON
     */
    @Override
    public JSONObject toJson() {
        JSONObject root = new JSONObject();
        root.put("id", id);
        root.put("title", title);

        JSONArray actorListJSON = new JSONArray();
        for(Actor actor : actorList)
            actorListJSON.put(actor.toJson());

        root.put("actorList", actorListJSON);

        JSONArray directorListJSON = new JSONArray();
        for(Director director : directorList)
            directorListJSON.put(director.toJson());

        root.put("directorList", directorListJSON);

        JSONArray productionListJSON = new JSONArray();
        for(Production production : productionList)
            productionListJSON.put(production.toJson());

        root.put("productionList", productionListJSON);

        JSONArray houseProductionListJSON = new JSONArray();
        for(HouseProduction houseProduction : houseProductionList)
            houseProductionListJSON.put(houseProduction.toJson());

        root.put("houseProductionList", houseProductionListJSON);

        return root;
    }


    /**
     * metodo che permette di scrivere file(immagini) all'interno della path designata
     * @param uploadPath rappresenta la path designata
     * @param stream rappresenta il file da caricare
     */
    public void writeCover(String uploadPath, Part stream) {
        try(InputStream fileStream = stream.getInputStream()) {
            File file = new File(uploadPath + cover);
            Files.copy(fileStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ignored) {

        }
    }

    /**
     * metodo che permette di scrivere file(immagini) all'interno della path designata
     * @param uploadPath rappresenta la path designata
     * @param stream rappresenta il file da caricare
     */

    public void writePoster(String uploadPath, Part stream) {
        try(InputStream fileStream = stream.getInputStream()) {
            File file = new File(uploadPath + poster);
            Files.copy(fileStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ignored) {

        }
    }
}
