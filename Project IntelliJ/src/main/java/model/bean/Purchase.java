package model.bean;

import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.JsonSerializable;

import java.time.LocalDate;
import java.util.List;

/**
 * Questa classe rappresenta l'acquisto effettuato da un Utente Registrato.
 */
@Generated
@Data
@NoArgsConstructor
public class Purchase implements JsonSerializable {

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

    /**
     *
     * @param account rappresenta l'Utente Registrato che ha effettuato l'acquisto
     */
    public Purchase(Account account) {
        this.account = account;
        this.datePurchase = LocalDate.now();
    }

    /**
     *
     * @param id Rappresenta l'identificativo dell'acquisto.
     * @param date Rappresenta la data dell'acquisto.
     */
    public Purchase(int id, LocalDate date) {
        this.id = id;
        this.datePurchase = date;
    }

    /**
     * metodo che converte la classe in un oggetto JSON
     * @return root, oggetto JSON
     */
    @Override
    public JSONObject toJson() {
        JSONObject root = new JSONObject();
        root.put("id", id);
        root.put("datePurchase", datePurchase.toString());

        JSONArray list = new JSONArray();
        for(Ticket ticket : ticketList)
            list.put(ticket.toJson());

        root.put("ticketList", list);
        return root;
    }
}
