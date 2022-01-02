package utils.extractor;

import model.bean.Account;
import model.dao.AccountDAO;
import utils.ResultSetExtractor;
import model.bean.Purchase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Questa classe permette di estrarre i dati di un Acquisto da una {@link ResultSet}.
 */
public class PurchaseExtractor implements ResultSetExtractor<Purchase>{

    /**
     * Implementa la funzionalità che permette di estrarre i dati di un Acquisto da una {@link ResultSet}
     * @param resultSet rappresenta l'insieme delle righe SQL
     * @return l'acquisto
     * @throws SQLException
     */
    public Purchase extract(ResultSet resultSet) throws SQLException {
        Purchase purchase = new Purchase();
        purchase.setId(resultSet.getInt("pur.id"));
        purchase.setDatePurchase(resultSet.getDate("pur.purchase_date").toLocalDate());

        return purchase;
    }

    /**
     * Implementa la funzionalità che permette di estrarre i dati di un Account a partire da una {@link ResultSet} contenente un Acquisto.
     * @param resultSet rappresenta l'insieme delle righe SQL
     * @return l'account
     * @throws SQLException
     */
    public Optional<Account> extractClient(ResultSet resultSet) throws SQLException {
        return new AccountDAO().fetch(resultSet.getInt("pur.id_client"));
    }
}
