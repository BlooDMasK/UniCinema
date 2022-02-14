package model.bean;

import lombok.*;
import org.json.JSONObject;
import utils.JsonSerializable;

/**
 * Questa classe rappresenta la produzione del film.
 */
@Generated
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Production implements JsonSerializable {

    /**
     * Rappresenta l'identificativo numerico della produzione.
     */
    int id;

    /**
     * Rappresenta il nome della produzione del film.
     */
    private String firstname;

    /**
     * Rappresenta il cognome della produzione del film.
     */
    private String lastname;

    /**
     * Rappresenta il film a cui ha preso parte la produzione.
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

    public Production(int id, String firstname, String lastname) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
    }
}
