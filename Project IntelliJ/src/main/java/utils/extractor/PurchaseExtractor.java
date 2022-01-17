package utils.extractor;

import utils.ResultSetExtractor;
import model.bean.Purchase;

import java.sql.ResultSet;
import java.sql.SQLException;

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
        purchase.setDatePurchase(resultSet.getDate("pur.date_purchase").toLocalDate());

        return purchase;
    }
}
