package Model.account;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountExtractor {
    public Account extract(ResultSet resultSet) throws SQLException {
        Account account = new Account();

        account.setId(resultSet.getInt("acc.id"));
        account.setEmail(resultSet.getString("acc.email"));
        account.setFirstname(resultSet.getString("acc.firstname"));
        account.setLastname(resultSet.getString("acc.lastname"));
        account.setAdministrator(resultSet.getBoolean("acc.administrator"));

        return account;
    }
}