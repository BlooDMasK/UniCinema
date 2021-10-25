package Model.ticket;

import Model.api.ConPool;
import Model.api.Paginator;
import Model.api.SqlMethods;
import Model.purchase.Purchase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TicketDAO implements SqlMethods<Ticket> {
    @Override
    public List<Ticket> fetchAll(Paginator paginator) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("SELECT * FROM ticket LIMIT ?,?")) {
                ps.setInt(1, paginator.getOffset());
                ps.setInt(2, paginator.getLimit());

                List<Ticket> ticketList = new ArrayList<>();

                ResultSet rs = ps.executeQuery();
                TicketExtractor ticketExtractor = new TicketExtractor();
                while(rs.next()) {
                    Ticket ticket = ticketExtractor.extract(rs);
                    ticketList.add(ticket);
                }
                rs.close();
                return ticketList;
            }
        }
    }

    public List<Ticket> fetchAll(Purchase purchase) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("SELECT * FROM ticket WHERE id_purchase = ?")) {
                ps.setInt(1, purchase.getId());

                List<Ticket> ticketList = new ArrayList<>();

                ResultSet rs = ps.executeQuery();
                TicketExtractor ticketExtractor = new TicketExtractor();
                while(rs.next()) {
                    Ticket ticket = ticketExtractor.extract(rs);
                    ticketList.add(ticket);
                }
                rs.close();
                return ticketList;
            }
        }
    }


    @Override
    public Optional<Ticket> fetch(int id) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("SELECT * FROM ticket WHERE id = ?")) {
                ps.setInt(1, id);

                ResultSet rs = ps.executeQuery();
                Ticket ticket = null;

                if(rs.next()) {
                    TicketExtractor ticketExtractor = new TicketExtractor();
                    ticket = ticketExtractor.extract(rs);
                }
                rs.close();
                return Optional.ofNullable(ticket);
            }
        }
    }

    @Override
    public boolean insert(Ticket ticket) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("INSERT INTO ticket (price, seat, rowletter) VALUES (?,?,?)")) {
                ps.setDouble(1, ticket.getPrice());
                ps.setInt(2, ticket.getSeat());
                ps.setString(3, ticket.getRowLetter());

                int rows = ps.executeUpdate();
                return rows == 1;
            }
        }
    }

    @Override
    public boolean update(Ticket ticket) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("UPDATE ticket SET price = ?, seat = ?, rowletter = ? WHERE id = ?")) {
                ps.setDouble(1, ticket.getPrice());
                ps.setInt(2, ticket.getSeat());
                ps.setString(3, ticket.getRowLetter());
                ps.setInt(4, ticket.getId());

                int rows = ps.executeUpdate();
                return rows == 1;
            }
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("DELETE FROM ticket WHERE id = ?")) {
                ps.setInt(1, id);

                int rows = ps.executeUpdate();
                return rows == 1;
            }
        }
    }

    @Override
    public int countAll() throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try(PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) AS ct FROM ticket")) {
                ResultSet rs = ps.executeQuery();
                int ct = 0;
                if(rs.next())
                    ct = rs.getInt("ct");
                rs.close();
                return ct;
            }
        }
    }
}
