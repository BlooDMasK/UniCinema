package Model.purchase;

import Model.account.Account;
import Model.account.AccountDAO;
import Model.api.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class PurchaseExtractor implements ResultSetExtractor<Purchase>{

    public Purchase extract(ResultSet resultSet) throws SQLException {
        Purchase purchase = new Purchase();
        purchase.setId(resultSet.getInt("pur.id"));
        purchase.setDatePurchase(resultSet.getDate("pur.purchase_date").toLocalDate());

        return purchase;
    }

    public Optional<Account> extractClient(ResultSet resultSet) throws SQLException {
        return new AccountDAO().fetch(resultSet.getInt("pur.id_client"));
    }
}
