package utils.extractor;

import lombok.Generated;
import utils.ResultSetExtractor;
import model.bean.Review;

import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * Questa classe permette di estrarre i dati di una recensione da una {@link ResultSet}.
 */
@Generated
public class ReviewExtractor implements ResultSetExtractor<Review> {
    /**
     * Implementa la funzionalità che permette di estrarre i dati di una recensione da una {@link ResultSet}
     * @param resultSet rappresenta l'insieme delle righe SQL
     * @return la recensione
     * @throws SQLException
     */
    @Override
    public Review extract(ResultSet resultSet) throws SQLException {
        Review review = new Review();

        review.setTitle(resultSet.getString("rev.title"));
        review.setDescription(resultSet.getString("rev.caption"));
        review.setStars(resultSet.getInt("rev.stars"));
        review.setDate(resultSet.getDate("rev.rev_date").toLocalDate());
        review.setTime(resultSet.getTime("rev.rev_time").toLocalTime());

        return review;
    }
}
