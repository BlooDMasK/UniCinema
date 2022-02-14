package model.bean;

import lombok.Data;
import lombok.Generated;

/**
 * Questa classe rappresenta la sala in cui si tengono gli spettacoli.
 */
@Generated
@Data
public class Room {
    /**
     * Rappresenta il numero di file presenti in sala.
     */
    private int n_rows;

    /**
     * Rappresenta il numero di posti per fila presenti in sala.
     */
    private int n_seats;

    /**
     * Rappresenta il numero identificativo della sala.
     */
    private int id;

    /**
     * Il totale dei posti sarà calcolato facendo n_rows * n_seats.
     * La fila A sta in basso, la fila finale sta in alto.
     * Lo schermo in una sala sta in basso (quindi la fila A è quella più vicina).
     */
}
