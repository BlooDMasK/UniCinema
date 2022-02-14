package utils;

import lombok.Data;
import lombok.Generated;
import org.json.JSONObject;

import java.util.List;

/**
 * Questa classe permette di mostrare gli Alert all'interno delle JSP
 */
@Generated
@Data
public class Alert implements JsonSerializable {

    /**
     * Rappresenta la lista dei messaggi che devono comparire nell'Alert
     */
    private final List<String> messages;

    /**
     * Rappresenta il tipo di alert che deve comparire (success, danger, warning ecc...)
     */
    private final String type;

    /**
     * Questo metodo permette di convertire, tramite {@link JsonSerializable}, un oggetto Alert in un oggetto {@link JSONObject},
     * da mandare alla response di una richiesta Http.
     * @return oggetto JSON
     */
    @Override
    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        object.put("messages", messages);
        object.put("type", type);
        return object;
    }
}
