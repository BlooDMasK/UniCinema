package model.dao.actor;

import model.bean.Account;
import model.bean.Actor;
import model.bean.Film;
import utils.Paginator;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Questa interfaccia rappresenta il DAO di Actor
 */
public interface ActorDAO {

    /**
     * Firma del metodo che implementa la funzionalità che permette di restituire la lista degli Attori presenti
     * @param paginator {@link Paginator}
     * @return lista degli Attori
     * @throws SQLException
     */
    List<Actor> fetchAll(Paginator paginator) throws SQLException;


    /**
     * Firma del metodo che implementa la funzionalità che permette di restituire la lista degli Attori presenti
     * @return lista degli Attori
     * @throws SQLException
     */
    List<Actor> fetchAll() throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità che permette di restituire gli attori presenti nel film
     * @param film film contenente gli attori da restituire
     * @return lista degli attori
     * @throws SQLException
     */
    List<Actor> fetchAll(Film film) throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalitò che permette di restituire un Attore, preso dal Db, a partire dal suo id
     * @param id identificativo numerico dell'Attore da restituire
     * @return Actor
     * @throws SQLException
     */
    Actor fetch(int id) throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità che permette di aggiungere un Attore
     * @param actor Attore da inserire
     * @return true se l'Attore viene aggiunto, false altrimenti
     * @throws SQLException
     */
    boolean insert(Actor actor) throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità che permette di aggiungere una lista di Attori
     * @param actorList lista di attori da aggiungere
     * @return true se la lista vine aggiunta, false altrimenti
     * @throws SQLException
     */
    boolean insert(ArrayList<Actor> actorList) throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità che permette di modificare un Attore
     * @param actor attore da modificare
     * @return true se la modifica va a buon fine, false altrimenti
     * @throws SQLException
     */
    boolean update(Actor actor) throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalità che permette di modificare una lista di Attori, a partire dall'id del film
     * @param actorList lista di attori da modificare
     * @param filmId id del film contenente la lista degli attori da modificare
     * @return true se la modifica va a buon fine, false altrimenti
     * @throws SQLException
     */
    boolean update(ArrayList<Actor> actorList, int filmId) throws SQLException;

    /**
     * Firma del metodo che implementa la funzionalitò che permette di rimuovere un Attore, preso dal Db, a partire dal suo id
     * @param id id identificativo numerico dell'Attore da rimuovere
     * @return true se l'Attore viene rimosso, false altrimenti
     * @throws SQLException
     */
    boolean delete(int id) throws SQLException;


    /**
     * Firma del metodo che implementa la funzionalitò che permette di contare quanti Attori sono presenti nel Db
     * @return intero
     * @throws SQLException
     */
    int countAll() throws SQLException;

}
