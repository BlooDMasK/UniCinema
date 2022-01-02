package utils.extractor;

import model.bean.Account;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Questa classe permette di estrarre i dati dell'Account da una {@link ResultSet}.
 */
public class AccountExtractor {

    /**
     * Implementa la funzionalità che permette di estrarre i dati dell'Account da una {@link ResultSet}
     * @param resultSet rappresenta l'insieme delle righe SQL
     * @return l'account
     * @throws SQLException
     */
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