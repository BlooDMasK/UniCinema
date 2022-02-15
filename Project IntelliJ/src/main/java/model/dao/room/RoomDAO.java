package model.dao.room;

import model.bean.Room;
import utils.Paginator;

import java.sql.SQLException;
import java.util.List;

/**
 * Questa interfaccia rappresenta il DAO di Room
 */
public interface RoomDAO {

    /**
     * Firma del metodo che implementa la funzionalità che permette di restituire la lista delle Sale presenti
     * @param paginator {@link Paginator}
     * @return lista delle Sale
     * @throws SQLException
     */
    List<Room> fetchAll(Paginator paginator) throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità che permette di restituire una Sala, presa dal Db, a partire dal suo id
     * @param id identificativo numerico della Sala da restituire
     * @return Sala
     * @throws SQLException
     */
    Room fetch(int id) throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità che permette di restituire la Sala in cui viene effettuato uno Spettacolo, a partire da showId
     * @param showId identificativo numerico dello Spettacolo ospitato dalla Sala
     * @return Sala
     * @throws SQLException
     */
    Room fetchFromShowId(int showId) throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità che permette di contare quante Sale sono presenti nel Db
     * @return intero
     * @throws SQLException
     */
    int countAll() throws SQLException;

}
