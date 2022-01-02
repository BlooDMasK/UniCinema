package model.bean;

import lombok.Data;

/**
 * Questa classe rappresenta la casa produttrice di un film.
 */
@Data
public class HouseProduction {
    /**
     * Rappresenta l'identificativo numerico della casa produttrice.
     */
    int id;

    /**
     * Rappresenta il film prodotto dalla casa produttrice.
     */
    private Film film;

    /**
     * Rappresenta il nome della casa produttrice.
     */
    private String name;
}
