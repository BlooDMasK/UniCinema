package utils;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Questa interfaccia permette di generalizzare l'estrazione dei dati da una {@link ResultSet}
 * @param <B> rappresenta l'oggetto a cui assegnare i dati estratti
 */
public interface ResultSetExtractor <B>{

    /**
     * Implementa la funzionalità che permette di estrarre i dati dalla ResultSet
     * @param resultSet insieme dei dati
     * @return l'oggetto i cui dati saranno estratti dalla ResultSet
     * @throws SQLException
     */
    B extract(ResultSet resultSet) throws SQLException;
}
