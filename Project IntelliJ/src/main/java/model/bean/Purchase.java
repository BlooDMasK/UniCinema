package model.bean;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * Questa classe rappresenta l'acquisto effettuato da un Utente Registrato.
 */
@Data
public class Purchase {

    /**
     * Rappresenta l'identificativo dell'acquisto.
     */
    private int id;

    /**
     * Rappresenta la data dell'acquisto.
     */
    private LocalDate datePurchase;

    /**
     * Rappresenta l'Utente Registrato che ha effettuato l'acquisto.
     */
    private Account account;

    /**
     * Rappresenta la lista dei ticket acquistati.
     */
    private List<Ticket> ticketList;
}
