package utils.extractor;

import lombok.Generated;
import utils.ResultSetExtractor;
import model.bean.Director;

import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * Questa classe permette di estrarre i dati del regista da una {@link ResultSet}.
 */
@Generated
public class DirectorExtractor implements ResultSetExtractor<Director> {
    /**
     * Implementa la funzionalità che permette di estrarre i dati del regista da una {@link ResultSet}
     * @param resultSet rappresenta l'insieme delle righe SQL
     * @return il regista
     * @throws SQLException
     */
    @Override
    public Director extract(ResultSet resultSet) throws SQLException {
        Director director = new Director();

        director.setFirstname(resultSet.getString("director.firstname"));
        director.setLastname(resultSet.getString("director.lastname"));
        director.setId(resultSet.getInt("director.id"));

        return director;
    }
}
