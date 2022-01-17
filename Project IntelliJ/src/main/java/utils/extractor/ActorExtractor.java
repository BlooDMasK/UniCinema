package utils.extractor;

import utils.ResultSetExtractor;
import model.bean.Actor;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Questa classe permette di estrarre i dati dell'Attore da una {@link ResultSet}.
 */
public class ActorExtractor implements ResultSetExtractor<Actor> {

    /**
     * Implementa la funzionalità che permette di estrarre i dati dell'Attore da una {@link ResultSet}
     * @param resultSet rappresenta l'insieme delle righe SQL
     * @return l'attore
     * @throws SQLException
     */
    @Override
    public Actor extract(ResultSet resultSet) throws SQLException {
        Actor actor = new Actor();

        actor.setFirstname(resultSet.getString("actor.firstname"));
        actor.setLastname(resultSet.getString("actor.lastname"));
        actor.setId(resultSet.getInt("actor.id"));

        return actor;
    }
}
