package Model.ticket;

import Model.api.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TicketExtractor implements ResultSetExtractor<Ticket> {

    @Override
    public Ticket extract(ResultSet resultSet) throws SQLException {
        Ticket ticket = new Ticket();

        ticket.setId(resultSet.getInt("ticket.id"));
        ticket.setPrice(resultSet.getDouble("ticket.price"));
        ticket.setSeat(resultSet.getInt("ticket.seat"));
        ticket.setRowLetter(resultSet.getString("ticket.rowletter"));

        return ticket;
    }
}
