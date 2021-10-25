package Model.purchase;

import Model.account.Account;
import Model.ticket.Ticket;

import java.time.LocalDate;
import java.util.List;

public class Purchase {
    private int id;
    private LocalDate datePurchase;

    private Account account;
    private List<Ticket> ticketList;

    public Purchase(){ }

    public Purchase(int id, LocalDate datePurchase) {
        this.id = id;
        this.datePurchase = datePurchase;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<Ticket> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDatePurchase() {
        return datePurchase;
    }

    public void setDatePurchase(LocalDate datePurchase) {
        this.datePurchase = datePurchase;
    }
}
