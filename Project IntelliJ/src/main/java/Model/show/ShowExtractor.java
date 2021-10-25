package Model.show;

import Model.api.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ShowExtractor implements ResultSetExtractor<Show> {
    @Override
    public Show extract(ResultSet resultSet) throws SQLException {
        Show show = new Show();

        show.setId(resultSet.getInt("sp.id"));
        show.setHour(resultSet.getTime("sp.show_hour").toLocalTime());
        show.setDate(resultSet.getDate("sp.show_date").toLocalDate());

        return show;
    }
}
