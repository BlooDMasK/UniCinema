package model.dao.ticket;

import lombok.Generated;
import utils.ConPool;
import utils.Paginator;
import utils.SqlMethods;
import utils.extractor.TicketExtractor;
import model.bean.Ticket;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Questa classe rappresenta il DAO di un biglietto
 */
@Generated
public class TicketDAOMethods implements TicketDAO {

    private ConPool conPool = ConPool.getInstance();
    private Connection con = conPool.getConnection();

    public TicketDAOMethods() throws SQLException {
    }

    /**
     * Implementa la funzionalità di prendere una lista di biglietti
     * @param paginator per gestire la paginazione
     * @return la lista dei biglietti
     * @throws SQLException
     */
    @Override
    public List<Ticket> fetchAll(Paginator paginator) throws SQLException {

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

    /**
     * Implementa la funzionalità di prendere una lista di biglietti
     * @param showId rappresenta l'identificativo numerico dello spettacolo
     * @return la lista dei biglietti
     * @throws SQLException
     */
    public ArrayList<Ticket> fetchAll(int showId) throws SQLException {

        try (PreparedStatement ps = con.prepareStatement("SELECT * FROM ticket WHERE id_spectacle = ?")) {
            ps.setInt(1, showId);

            ArrayList<Ticket> ticketList = new ArrayList<>();

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

    /**
     * Implementa la funzionalità di prendere un biglietto
     * @param id rappresenta l'identificativo numerico di un biglietto
     * @return il biglietto
     * @throws SQLException
     */
    @Override
    public Ticket fetch(int id) throws SQLException {

        try (PreparedStatement ps = con.prepareStatement("SELECT * FROM ticket WHERE id = ?")) {
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            Ticket ticket = null;

            if(rs.next()) {
                TicketExtractor ticketExtractor = new TicketExtractor();
                ticket = ticketExtractor.extract(rs);
            }
            rs.close();
            return ticket;
        }

    }

    /**
     * Metodo che implementa la funzionalità di verificare se un Biglietto è disponibile a partire da showId, row, seat
     * @param showId identificativo numerico dello Spettacolo
     * @param row carattere che rappresenta la fila
     * @param seat intero che rappresenta il posto in sala
     * @return true se il Biglietto è disponibile, false altrimenti
     * @throws SQLException
     */
    public boolean fetch(int showId, char row, int seat) throws SQLException {

        try (PreparedStatement ps = con.prepareStatement("SELECT * FROM ticket WHERE id_spectacle = ? AND rowletter = ? AND seat = ?")) {
            ps.setInt(1, showId);
            ps.setString(2, String.valueOf(row));
            ps.setInt(3, seat);

            ResultSet rs = ps.executeQuery();
            boolean result = rs.next();

            rs.close();
            return result;
        }

    }

    /**
     * Implementa la funzionalità che permette di registrare uno o più biglietti
     * @param ticketList da registrare
     * @return true se la registrazione va a buon fine, false altrimenti
     * @throws SQLException
     */
    public boolean insert(ArrayList<Ticket> ticketList) throws SQLException {

        String query = "INSERT INTO ticket(price, seat, rowletter, id_spectacle, id_purchase) VALUES ";
        for(Ticket ticket : ticketList)
            query += "("+ticket.getPrice()+","+ticket.getSeat()+",'"+ticket.getRowLetter()+"',"+ticket.getShow().getId()+","+ticket.getPurchase().getId()+"),";

        query = query.substring(0, query.length()-1);

        try (PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            int rows = ps.executeUpdate();
            if(rows >= 1 && rows <= 4) {
                ResultSet rs = ps.getGeneratedKeys();
                for(int i = 0; i < rows && rs.next(); i++)
                    ticketList.get(i).setId(rs.getInt(1));

                return true;
            } else
                return false;
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

        try (PreparedStatement ps = con.prepareStatement("DELETE FROM ticket WHERE id = ?")) {
            ps.setInt(1, id);

            int rows = ps.executeUpdate();
            return rows == 1;
        }
    }

    /**
     * Implementa la funzionalità di conteggio dei biglietti.
     * @return un intero che rappresenta il numero di biglietti registrati nel database
     * @throws SQLException
     */
    @Override
    public int countAll() throws SQLException {

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
