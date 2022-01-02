package model.bean;

import lombok.Data;

/**
 * Questa classe rappresenta la sala in cui si tengono gli spettacoli.
 */
@Data
public class Room {
    /**
     * Rappresenta il numero di file presenti in sala.
     */
    private int n_rows;

    /**
     * Rappresenta il numero di posti presenti in sala. TODO: per fila o per sala?
     */
    private int n_seats;

    /**
     * Rappresenta il numero identificativo della sala.
     */
    private int id;

    /**
     * Rappresenta la relazione tra la Sala e lo Spettacolo.
     */
    private ShowRoomRelation showRoomRelation;
}
