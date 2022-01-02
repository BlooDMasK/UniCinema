package model.bean;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Questa classe rappresenta l'attore facente parte del cast di un film.
 */
@Data
public class Actor {

    /**
     * Rappresenta l'identificativo numerico dell'attore.
     */
    @Getter @Setter
    private int id;

    /**
     * Rappresenta il film a cui ha preso parte l'attore.
     */
    @Getter @Setter
    private Film film;

    /**
     * Rappresenta il nome dell'attore.
     */
    @Getter @Setter
    private String firstname;

    /**
     * Rappresenta il cognome dell'attore.
     */
    @Getter @Setter
    private String lastname;
}
