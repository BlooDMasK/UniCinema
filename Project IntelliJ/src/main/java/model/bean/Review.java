package model.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import utils.JsonSerializable;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Locale;

/**
 * Questa classe rappresenta la recensione ad un film effettuata dall'Utente Registrato.
 */
@Generated
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review implements JsonSerializable {

    /**
     * Rappresenta l'Utente Registrato che ha effettuato la recensione.
     */
    private Account account;

    /**
     * Rappresenta il film a cui è stata fatta la recensione.
     */
    private Film film;

    /**
     * Rappresenta il testo della recensione.
     */
    private String description;

    /**
     * Rappresenta il titolo della recensione.
     */
    private String title;

    /**
     * Rappresenta la data in cui è stata pubblicata la recensione.
     */
    private LocalDate date;

    /**
     * Rappresenta l'orario in cui è stata pubblicata la recensione.
     */
    private LocalTime time;

    /**
     * Rappresenta la valutazione in stelle (da 1 a 5).
     */
    private int stars;

    public Review(String description, String title, LocalDate date, LocalTime time, int stars) {
        this.description = description;
        this.title = title;
        this.date = date;
        this.time = time;
        this.stars = stars;
    }

    /**
     * Implementa la funzionalità che permette di convertire la seguente classe in un file JSON, da passare alla response tramite chiamate Ajax.
     * @return object di tipo JSON, contenente i parametri della classe.
     */
    @Override
    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        object.put("accountId", account.getId());
        object.put("firstname", account.getFirstname());
        object.put("lastname", account.getLastname());
        object.put("description", description);
        object.put("title", title);
        object.put("dateDayOfWeek", date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ITALY).substring(0, 3).toUpperCase());
        object.put("dateDayMonth", date.getDayOfMonth() + " " + date.getMonth().getDisplayName(TextStyle.FULL, Locale.ITALIAN).toUpperCase());
        object.put("time", time.getHour() + ":" + ((time.getMinute() < 10) ? (time.getMinute() + "0") : time.getMinute()));
        object.put("isAdministratorReview", account.isAdministrator());
        object.put("stars", stars);
        return object;
    }
}
