package model.bean;

import lombok.Data;

/**
 * Questa classe rappresenta il biglietto per lo spettacolo.
 */
@Data
public class Ticket {
    /**
     * Rappresenta il prezzo del biglietto
     */
    private double price;

    /**
     * Rappresenta l'identificativo numerico del biglietto.
     */
    private int id;

    /**
     * Rappresenta l'identificativo numerico del posto in cui sedersi.
     */
    private int seat;

    /**
     * Rappresenta l'identificativo alfabetico della fila in cui si trova il posto.
     */
    private String rowLetter;

    /**
     * Rappresenta l'acquisto effettuato.
     */
    private Purchase purchase;

    /**
     * Rappresenta lo spettacolo da vedere.
     */
    private Show show;
}
