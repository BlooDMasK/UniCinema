package utils;

import lombok.Data;

import java.util.List;
import java.util.StringJoiner;

/**
 * Questa classe permette di gestire i parametri di ricerca SQL
 */
@Data
public class Condition {

    /**
     * Rappresenta il nome TODO: si può togliere?
     */
    private final String name;

    /**
     * Rappresenta l'operatore di ricerca.
     */
    private final Operator operator;

    /**
     * Rappresenta l'oggetto.
     */
    private final Object value;

    /**
     * Implementa la funzionalità di costruzione dei parametri della query per effettuare la ricerca condizionata.
     * @param conditions della query
     * @param alias della tabella su cui deve agire
     * @return stringa contenente le condizioni
     */
    public static String searchConditions(List<Condition> conditions, String alias) {
        StringJoiner conditionJoiner = new StringJoiner(" AND ");
        for(Condition cn : conditions) {
            String tmp = alias + "." + cn.toString() + "?";
            conditionJoiner.add(tmp);
        }
        return conditionJoiner.toString();
    }
}
