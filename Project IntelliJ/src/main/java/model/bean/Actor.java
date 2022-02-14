package model.bean;

import lombok.*;
import org.json.JSONObject;
import utils.JsonSerializable;

/**
 * Questa classe rappresenta l'attore facente parte del cast di un film.
 */
@Generated
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Actor implements JsonSerializable {

    /**
     * Rappresenta l'identificativo numerico dell'attore.
     */
    private int id;

    /**
     * Rappresenta il nome dell'attore.
     */
    private String firstname;

    /**
     * Rappresenta il cognome dell'attore.
     */
    private String lastname;

    /**
     * Rappresenta il film a cui ha preso parte l'attore.
     */
    private Film film;

    @Override
    public JSONObject toJson() {
        JSONObject root = new JSONObject();
        root.put("id", id);
        root.put("firstname", firstname);
        root.put("lastname", lastname);
        return root;
    }

    public Actor(int id, String firstname, String lastname) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
    }
}
