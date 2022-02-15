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

/**
 * Classe che implementa i metodi definiti nell'interfaccia service del sottosistema Purchase (PurchaseManager)
 */
public class PurchaseServiceMethods implements PurchaseService {

    TicketDAO ticketDAO;
    PurchaseDAO purchaseDAO;

    public PurchaseServiceMethods() throws SQLException {
        ticketDAO = new TicketDAOMethods();
        purchaseDAO = new PurchaseDAOMethods();
    }

    /**
     * Metodo che permette di settare il ticketDAO con la propria implementazione
     * @param ticketDAOMethods
     */
    public void setTicketDAO(TicketDAOMethods ticketDAOMethods) {
        this.ticketDAO = ticketDAOMethods;
    }

    /**
     * Metodo che permette di settare il purchaseDAO con la propria implementazione
     * @param purchaseDAOMethods
     */
    public void setPurchaseDAO(PurchaseDAOMethods purchaseDAOMethods) {
        this.purchaseDAO = purchaseDAOMethods;
    }

    /**
     * Implementa la funzionalità che permette di restituire la lista dei biglietti a partire dal loro showId
     * @param showId identificativo numerico dello spettacolo associato ai biglietti
     * @return la lista dei biglietti
     * @throws SQLException
     */
    @Override
    public ArrayList<Ticket> fetchTickets(int showId) throws SQLException {
        return ticketDAO.fetchAll(showId);
    }

    /**
     * Implementa la funzionalità che permette di verificare che un posto è stato prenotato
     * @param showId identificativo numerico dello spettacolo associato al biglietto
     * @param row lettera posto
     * @param seat numero del posto
     * @return true se il posto è occupato, false altrimenti
     * @throws SQLException
     */
    @Override
    public boolean findTicket(int showId, char row, int seat) throws SQLException {
        return ticketDAO.fetch(showId, row, seat);
    }

    /**
     * Implementa la funzionalità che permette di registrare un biglietto nel database
     * @param ticketList lista dei biglietti da registrare (1 <= size <= 4)
     * @return true se la registrazione va a buon fine, false altrimenti
     * @throws SQLException
     */
    @Override
    public boolean insert(ArrayList<Ticket> ticketList) throws SQLException {
        return ticketDAO.insert(ticketList);
    }

    /**
     * Implementa la funzionalità che permette di registrare un acquisto nel database
     * @param purchase acquisto da registrare
     * @return identificativo numerico dell'acquisto registrato
     * @throws SQLException
     */
    @Override
    public int insert(Purchase purchase) throws SQLException {
        return purchaseDAO.insertAndReturnID(purchase);
    }

    /**
     * Implementa la funzionalità che permette di restituire la lista di tutti gli acquisti effettuati a partire dall'accountId
     * @param accountId che ha effettuato gli acquisti
     * @param paginator {@link Paginator}
     * @return lista degli acquisti di accountId
     * @throws SQLException
     */
    @Override
    public ArrayList<Purchase> fetchAll(int accountId, Paginator paginator) throws SQLException {
        return purchaseDAO.fetchAll(accountId, paginator);
    }

    /**
     * Implementa la funzionalità che conta tutte gli acquisti effettuati da un accountId.
     * @param accountId che ha effettuato gli acquisti
     * @return un intero che rappresenta il conteggio degli acquisti.
     * @throws SQLException
     */
    @Override
    public int countAll(int accountId) throws SQLException {
        return purchaseDAO.countAll(accountId);
    }

}
