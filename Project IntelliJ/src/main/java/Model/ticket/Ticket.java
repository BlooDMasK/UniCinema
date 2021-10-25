package Model.ticket;

import Model.show.Show;
import Model.purchase.Purchase;

public class Ticket {
    private double price;
    private int id, seat;
    private String rowLetter;

    private Purchase purchase;
    private Show show;

    public Ticket(double price, int id, int seat, String rowLetter, Purchase purchase, Show show) {
        this.price = price;
        this.id = id;
        this.seat = seat;
        this.rowLetter = rowLetter;
        this.purchase = purchase;
        this.show = show;
    }

    public Ticket() {

    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public String getRowLetter() {
        return rowLetter;
    }

    public void setRowLetter(String rowLetter) {
        this.rowLetter = rowLetter;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Purchase getPurchase() {
        return purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }
}
