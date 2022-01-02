package model.dao;

import utils.ConPool;
import utils.Paginator;
import utils.SqlMethods;
import utils.extractor.TicketExtractor;
import model.bean.Purchase;
import model.bean.Ticket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Questa classe rappresenta il DAO di un biglietto
 */
public class TicketDAO implements SqlMethods<Ticket> {
    /**
     * Implementa la funzionalità di prendere una lista di biglietti
     * @param paginator per gestire la paginazione
     * @return la lista dei biglietti
     * @throws SQLException
     */
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

    /**
     * Implementa la funzionalità di prendere una lista di biglietti
     * @param purchase dei biglietti
     * @return la lista dei biglietti
     * @throws SQLException
     */
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

    /**
     * Implementa la funzionalità di prendere un biglietto
     * @param id rappresenta l'identificativo numerico di un biglietto
     * @return il biglietto
     * @throws SQLException
     */
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

    /**
     * Implementa la funzionalità che permette di registrare un biglietto
     * @param ticket da registrare
     * @return true se la registrazione va a buon fine, false altrimenti
     * @throws SQLException
     */
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

    /**
     * Implementa la funzionalità che permette di aggiornare i dati di un biglietto
     * @param ticket da aggiornare
     * @return true se l'aggiornamento va a buon fine, false altrimenti
     * @throws SQLException
     */
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

    /**
     * Implementa la funzionalità che permette di rimuovere un biglietto.
     * @param id rappresenta l' identificativo numerico di un biglietto
     * @return true se la rimozione va a buon fine, false altrimenti
     * @throws SQLException
     */
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

    /**
     * Implementa la funzionalità di conteggio dei biglietti.
     * @return un intero che rappresenta il numero di biglietti registrati nel database
     * @throws SQLException
     */
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
