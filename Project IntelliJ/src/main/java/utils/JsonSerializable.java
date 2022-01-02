package utils;

import org.json.JSONObject;

/**
 * Questa interfaccia permette di convertire un oggetto che la implementa a oggetto JSON.
 */
public interface JsonSerializable {

    /**
     * Implementa la funzionalità che permette di convertire un oggetto in oggetto JSON.
     * @return l'oggetto convertito a JSON
     */
    JSONObject toJson();
}