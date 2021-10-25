package Model.director;

import Model.api.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DirectorExtractor implements ResultSetExtractor<Director> {

    @Override
    public Director extract(ResultSet resultSet) throws SQLException {
        Director director = new Director();

        director.setFirstname(resultSet.getString("director.firstname"));
        director.setLastname(resultSet.getString("director.lastname"));
        director.setId(resultSet.getInt("director.id"));

        return director;
    }
}
