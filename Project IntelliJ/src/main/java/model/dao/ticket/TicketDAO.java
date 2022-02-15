package model.dao.ticket;

import model.bean.Ticket;
import utils.Paginator;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Questa interfaccia rappresenta il DAO di TicketDAO
 */
public interface TicketDAO {

    /**
     * Firma del metodo che implementa la funzionalità che permette di restituire la lista dei Biglietti presenti
     * @param paginator {@link Paginator}
     * @return lista dei Biglietti
     * @throws SQLException
     */
    List<Ticket> fetchAll(Paginator paginator) throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità che permette di restituire la lista dei Biglietti relativi ad uno Spettacolo a partire da showId
     * @param showId identificativo numerico dello Spettacolo contenente i Biglietti
     * @return lista dei Biglietti
     * @throws SQLException
     */
    ArrayList<Ticket> fetchAll(int showId) throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità che permette di restituire un Biglietto, preso dal Db, a partire dal suo id
     * @param id identificativo numerico del Biglietto da restituire
     * @return Biglietto
     * @throws SQLException
     */
    Ticket fetch(int id) throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità che permette di verificare se un Biglietto è disponibile , preso dal Db, a partire da showId, row, seat
     * @param showId identificativo numerico dello Spettacolo
     * @param row carattere che rappresenta la fila
     * @param seat intero che rappresenta il posto in sala
     * @return true se il Ticket è disponibile, false altrimenti
     * @throws SQLException
     */
    boolean fetch(int showId, char row, int seat) throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità che permette di registrare una lista di Biglietti
     * @param ticketList lista di Biglietti da registrare
     * @return true se la lista di Biglietti viene registrata, false altrimenti
     * @throws SQLException
     */
    boolean insert(ArrayList<Ticket> ticketList) throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità che permette di rimuovere un Biglietto, preso dal Db, a partire dal suo id
     * @param id id identificativo numerico del Biglietto da rimuovere
     * @return true se il Biglietto viene rimosso, false altrimenti
     * @throws SQLException
     */
    boolean delete(int id) throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità che permette di contare quanti Biglietti sono presenti nel Db
     * @return intero
     * @throws SQLException
     */
    int countAll() throws SQLException;
}
