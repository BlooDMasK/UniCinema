package model.bean;

import lombok.Data;

/**
 * Questa classe rappresenta la produzione del film.
 */
@Data
public class Production {

    /**
     * Rappresenta l'identificativo numerico della produzione.
     */
    int id;

    /**
     * Rappresenta il film a cui ha preso parte la produzione.
     */
    private Film film;

    /**
     * Rappresenta il nome della produzione del film.
     */
    private String firstname;

    /**
     * Rappresenta il cognome della produzione del film.
     */
    private String lastname;
}
