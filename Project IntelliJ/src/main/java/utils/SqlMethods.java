package utils;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Questa interfaccia permette di generalizzare i metodi dei DAO.
 * @param <T> rappresenta il bean soggetto al DAO.
 */
public interface SqlMethods <T> {

    /**
     * Implementa la funzionalità di prendere una lista di Oggetti.
     * @param paginator per gestire la paginazione.
     * @return la lista degli oggetti registrati nel Database.
     * @throws SQLException
     */
    public List<T> fetchAll(Paginator paginator) throws SQLException;

    /**
     * Implementa la funzionalità di prendere un Oggetto.
     * @param id rappresenta l'identificativo numerico dell'Oggetto
     * @return l'Oggetto.
     * @throws SQLException
     */
    public Optional<T> fetch(int id) throws SQLException;

    /**
     * Implementa la funzionalità di registrare un Oggetto nel database.
     * @param object da registrare
     * @return true se la registrazione va a buon fine, false altrimenti.
     * @throws SQLException
     */
    public boolean insert(T object) throws SQLException;

    /**
     * Implementa la funzionalità di aggiornamento dati di un Oggetto.
     * @param object da aggiornare
     * @return true se l'aggiornamento va a buon fine, false altrimenti.
     * @throws SQLException
     */
    public boolean update(T object) throws SQLException;

    /**
     * Implementa la funzionalità di rimuovere un Oggetto.
     * @param id rappresenta l'identificativo numerico dell'Oggetto
     * @return true se la rimozione va a buon fine, false altrimenti.
     * @throws SQLException
     */
    public boolean delete(int id) throws SQLException;

    /**
     * Implementa la funzionalità di conteggio degli Oggetti.
     * @return un intero che rappresenta il numero di Oggetti registrati nel database
     * @throws SQLException
     */
    public int countAll() throws SQLException;
}
