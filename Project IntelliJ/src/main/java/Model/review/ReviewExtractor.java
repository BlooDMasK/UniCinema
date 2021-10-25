package Model.review;

import Model.api.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReviewExtractor implements ResultSetExtractor<Review> {

    @Override
    public Review extract(ResultSet resultSet) throws SQLException {
        Review review = new Review();

        review.setDescription(resultSet.getString("rev.caption"));
        review.setStars(resultSet.getInt("rev.stars"));

        return review;
    }
}
