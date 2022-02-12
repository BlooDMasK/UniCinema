package model.bean;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;
import utils.JsonSerializable;

/**
 * Questa classe rappresenta il biglietto per lo spettacolo.
 */
@Data
@NoArgsConstructor
public class Ticket implements JsonSerializable {
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
    private char rowLetter;

    /**
     * Rappresenta l'acquisto effettuato.
     */
    private Purchase purchase;

    /**
     * Rappresenta lo spettacolo da vedere.
     */
    private Show show;

    public Ticket(double price, int seat, char rowLetter, Show show, Purchase purchase) {
        this.price = price;
        this.seat = seat;
        this.rowLetter = rowLetter;
        this.show = show;
        this.purchase = purchase;
    }

    public Ticket(double price, int seat, char rowLetter) {
        this.price = price;
        this.seat = seat;
        this.rowLetter = rowLetter;
    }

    public String generateUniqueCode() {
        return "TK"+id+"-RW"+rowLetter+"-ST"+seat+"-SH"+show.getId();
    }

    @Override
    public JSONObject toJson() {
        JSONObject root = new JSONObject();
        root.put("price", price+"€");
        root.put("id", id);
        root.put("seat", seat);
        root.put("rowLetter", Character.toString(rowLetter));
        root.put("show", show.toJson());
        root.put("uniqueCode", generateUniqueCode());
        return root;
    }
}
