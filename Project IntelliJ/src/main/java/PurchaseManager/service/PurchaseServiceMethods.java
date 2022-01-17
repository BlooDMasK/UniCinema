package PurchaseManager.service;

import model.bean.Purchase;
import model.bean.Ticket;
import model.dao.PurchaseDAO;
import model.dao.TicketDAO;
import utils.Paginator;

import java.sql.SQLException;
import java.util.ArrayList;

public class PurchaseServiceMethods implements PurchaseService {

    TicketDAO ticketDAO = new TicketDAO();
    PurchaseDAO purchaseDAO = new PurchaseDAO();

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
