package model.bean;

import lombok.*;
import org.json.JSONObject;
import utils.JsonSerializable;

/**
 * Questa classe rappresenta il regista di un film.
 */
@Generated
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Director implements JsonSerializable {

    /**
     * Rappresenta l'identificativo numerico del regista.
     */
    int id;

    /**
     * Rappresenta il nome del regista.
     */
    private String firstname;

    /**
     * Rappresenta il cognome del regista.
     */
    private String lastname;

    /**
     * Rappresenta il film a cui ha preso parte il regista.
     */
    private Film film;

    /**
     *
     * @param id Rappresenta l'identificativo numerico del regista.
     * @param firstname Rappresenta il nome del regista.
     * @param lastname Rappresenta il cognome del regista.
     */

    public Director(int id, String firstname, String lastname) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
    }


    /**
     * metodo che converte la classe in un oggetto JSON
     * @return root, oggetto JSON
     */
    @Override
    public JSONObject toJson() {
        JSONObject root = new JSONObject();
        root.put("id", id);
        root.put("firstname", firstname);
        root.put("lastname", lastname);
        return root;
    }
}
