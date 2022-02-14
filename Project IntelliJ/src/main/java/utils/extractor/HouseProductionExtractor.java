package utils.extractor;

import lombok.Generated;
import utils.ResultSetExtractor;
import model.bean.HouseProduction;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Questa classe permette di estrarre i dati della Casa di Produzione da una {@link ResultSet}.
 */
@Generated
public class HouseProductionExtractor implements ResultSetExtractor<HouseProduction> {

    /**
     * Implementa la funzionalità che permette di estrarre i dati della Casa di Produzione da una {@link ResultSet}
     * @param resultSet rappresenta l'insieme delle righe SQL
     * @return la casa di produzione
     * @throws SQLException
     */
    @Override
    public HouseProduction extract(ResultSet resultSet) throws SQLException {
        HouseProduction houseProduction = new HouseProduction();

        houseProduction.setName(resultSet.getString("hp.name_house"));
        houseProduction.setId(resultSet.getInt("hp.id"));

        return houseProduction;
    }
}
