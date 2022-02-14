package model.dao.ticket;

import model.bean.Ticket;
import utils.Paginator;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface TicketDAO {

    List<Ticket> fetchAll(Paginator paginator) throws SQLException;

    ArrayList<Ticket> fetchAll(int showId) throws SQLException;

    Ticket fetch(int id) throws SQLException;

    boolean fetch(int showId, char row, int seat) throws SQLException;

    boolean insert(ArrayList<Ticket> ticketList) throws SQLException;

    boolean delete(int id) throws SQLException;

    int countAll() throws SQLException;
}
