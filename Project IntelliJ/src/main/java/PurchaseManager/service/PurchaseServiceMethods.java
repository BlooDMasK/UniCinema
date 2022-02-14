package PurchaseManager.service;

import model.bean.Purchase;
import model.bean.Ticket;
import model.dao.purchase.PurchaseDAO;
import model.dao.purchase.PurchaseDAOMethods;
import model.dao.ticket.TicketDAO;
import model.dao.ticket.TicketDAOMethods;
import utils.Paginator;

import java.sql.SQLException;
import java.util.ArrayList;

public class PurchaseServiceMethods implements PurchaseService {

    TicketDAO ticketDAO;
    PurchaseDAO purchaseDAO;

    public PurchaseServiceMethods() throws SQLException {
        ticketDAO = new TicketDAOMethods();
        purchaseDAO = new PurchaseDAOMethods();
    }

    public void setTicketDAO(TicketDAOMethods ticketDAOMethods) {
        this.ticketDAO = ticketDAOMethods;
    }

    public void setPurchaseDAO(PurchaseDAOMethods purchaseDAOMethods) {
        this.purchaseDAO = purchaseDAOMethods;
    }

    @Override
    public ArrayList<Ticket> fetchTickets(int showId) throws SQLException {
        return ticketDAO.fetchAll(showId);
    }

    @Override
    public boolean findTicket(int showId, char row, int seat) throws SQLException {
        return ticketDAO.fetch(showId, row, seat);
    }

    @Override
    public boolean insert(ArrayList<Ticket> ticketList) throws SQLException {
        return ticketDAO.insert(ticketList);
    }

    @Override
    public int insert(Purchase purchase) throws SQLException {
        return purchaseDAO.insertAndReturnID(purchase);
    }

    @Override
    public ArrayList<Purchase> fetchAll(int accountId, Paginator paginator) throws SQLException {
        return purchaseDAO.fetchAll(accountId, paginator);
    }

    @Override
    public int countAll(int accountId) throws SQLException {
        return purchaseDAO.countAll(accountId);
    }

}
