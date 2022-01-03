package PurchaseManager.service;

import model.bean.Purchase;
import model.bean.Ticket;
import utils.Paginator;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PurchaseService {

    ArrayList<Ticket> fetchTickets(int showId) throws SQLException;

    boolean findTicket(int showId, char row, int seat) throws SQLException;

    boolean insert(ArrayList<Ticket> ticketList) throws SQLException;

    int insert(Purchase purchase) throws SQLException;

    ArrayList<Purchase> fetchAll(int accountId, Paginator paginator) throws SQLException;

    int countAll(int accountId) throws SQLException;
}
