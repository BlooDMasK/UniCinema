package utils.extractor;

import lombok.Generated;
import utils.ResultSetExtractor;
import model.bean.Production;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Questa classe permette di estrarre i dati della Produzione da una {@link ResultSet}.
 */
@Generated
public class ProductionExtractor implements ResultSetExtractor<Production> {

    /**
     * Implementa la funzionalità che permette di estrarre i dati della Produzione da una {@link ResultSet}
     * @param resultSet rappresenta l'insieme delle righe SQL
     * @return la produzione
     * @throws SQLException
     */
    @Override
    public Production extract(ResultSet resultSet) throws SQLException {
        Production production = new Production();

        production.setFirstname(resultSet.getString("prod.firstname"));
        production.setLastname(resultSet.getString("prod.lastname"));
        production.setId(resultSet.getInt("prod.id"));

        return production;
    }
}
