package model.dao.purchase;

import model.bean.Purchase;
import utils.Paginator;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface PurchaseDAO {

    List<Purchase> fetchAll(Paginator paginator) throws SQLException;

    ArrayList<Purchase> fetchAll(int accountId, Paginator paginator) throws SQLException;

    Purchase fetch(int id) throws SQLException;

    int insertAndReturnID(Purchase purchase) throws SQLException;

    int countAll() throws SQLException;

    int countAll(int accountId) throws SQLException;


}
