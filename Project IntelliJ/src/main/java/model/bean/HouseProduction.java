package model.bean;

import lombok.*;
import org.json.JSONObject;
import utils.JsonSerializable;

/**
 * Questa classe rappresenta la casa produttrice di un film.
 */
@Generated
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HouseProduction implements JsonSerializable {
    /**
     * Rappresenta l'identificativo numerico della casa produttrice.
     */
    int id;

    /**
     * Rappresenta il nome della casa produttrice.
     */
    private String name;

    /**
     * Rappresenta il film prodotto dalla casa produttrice.
     */
    private Film film;

    /**
     * metodo che converte la classe in un oggetto JSON
     * @return root, oggetto JSON
     */
    @Override
    public JSONObject toJson() {
        JSONObject root = new JSONObject();
        root.put("id", id);
        root.put("name", name);
        return root;
    }


    /**
     *
     * @param id rappresenta l'id della casa di produzione
     * @param name rappresenta il nome della casa di produzione
     */
    public HouseProduction(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
