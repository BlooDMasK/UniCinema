package Model.actors;

import Model.api.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ActorExtractor implements ResultSetExtractor<Actor> {

    @Override
    public Actor extract(ResultSet resultSet) throws SQLException {
        Actor actor = new Actor();

        actor.setFirstname(resultSet.getString("actor.firstname"));
        actor.setLastname(resultSet.getString("actor.lastname"));
        actor.setId(resultSet.getInt("actor.id"));

        return actor;
    }
}
