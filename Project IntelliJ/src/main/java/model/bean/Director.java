package model.bean;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Questa classe rappresenta il regista di un film.
 */
@Data
public class Director {

    /**
     * Rappresenta l'identificativo numerico del regista.
     */
    @Getter @Setter
    int id;

    /**
     * Rappresenta il film a cui ha preso parte il regista.
     */
    @Getter @Setter
    private Film film;

    /**
     * Rappresenta il nome del regista.
     */
    @Getter @Setter
    private String firstname;

    /**
     * Rappresenta il cognome del regista.
     */
    @Getter @Setter
    private String lastname;
}
