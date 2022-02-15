package PurchaseManager.service;

import model.bean.Purchase;
import model.bean.Ticket;
import utils.Paginator;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Classe che implementa i metodi definiti nell'interfaccia service del sottosistema Purchase (PurchaseManager)
 */
public interface PurchaseService {

    /**
     * Firma del metodo che implementa la funzionalità che permette di restituire la lista dei biglietti a partire dal loro showId
     * @param showId identificativo numerico dello spettacolo associato ai biglietti
     * @return la lista dei biglietti
     * @throws SQLException
     */
    ArrayList<Ticket> fetchTickets(int showId) throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità che permette di verificare che un posto è stato prenotato
     * @param showId identificativo numerico dello spettacolo associato al biglietto
     * @param row lettera posto
     * @param seat numero del posto
     * @return true se il posto è occupato, false altrimenti
     * @throws SQLException
     */
    boolean findTicket(int showId, char row, int seat) throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità che permette di registrare un biglietto nel database
     * @param ticketList lista dei biglietti da registrare (la size andrà da 1 a 4)
     * @return true se la registrazione va a buon fine, false altrimenti
     * @throws SQLException
     */
    boolean insert(ArrayList<Ticket> ticketList) throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità che permette di registrare un acquisto nel database
     * @param purchase acquisto da registrare
     * @return identificativo numerico dell'acquisto registrato
     * @throws SQLException
     */
    int insert(Purchase purchase) throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità che permette di restituire la lista di tutti gli acquisti effettuati a partire dall'accountId
     * @param accountId che ha effettuato gli acquisti
     * @param paginator {@link Paginator}
     * @return lista degli acquisti di accountId
     * @throws SQLException
     */
    ArrayList<Purchase> fetchAll(int accountId, Paginator paginator) throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità che conta tutte gli acquisti effettuati da un accountId.
     * @param accountId che ha effettuato gli acquisti
     * @return un intero che rappresenta il conteggio degli acquisti.
     * @throws SQLException
     */
    int countAll(int accountId) throws SQLException;
}
