package Model.houseproduction;

import Model.api.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HouseProductionExtractor implements ResultSetExtractor<HouseProduction> {

    @Override
    public HouseProduction extract(ResultSet resultSet) throws SQLException {
        HouseProduction houseProduction = new HouseProduction();

        houseProduction.setName(resultSet.getString("hp.name"));
        houseProduction.setId(resultSet.getInt("hp.id"));

        return houseProduction;
    }
}
