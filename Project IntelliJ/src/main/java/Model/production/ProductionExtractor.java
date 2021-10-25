package Model.production;

import Model.api.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductionExtractor implements ResultSetExtractor<Production> {

    @Override
    public Production extract(ResultSet resultSet) throws SQLException {
        Production production = new Production();

        production.setFirstname(resultSet.getString("prod.firstname"));
        production.setLastname(resultSet.getString("prod.lastname"));
        production.setId(resultSet.getInt("prod.id"));

        return production;
    }
}
