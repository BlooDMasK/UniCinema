package model.dao.account;

import model.bean.Account;
import utils.Paginator;

import java.sql.SQLException;
import java.util.List;

public interface AccountDAO {

    List<Account> fetchAll(Paginator paginator) throws SQLException;

    boolean insert(Account account) throws SQLException;

    Account fetch(int id) throws SQLException;

    Account fetch(String email) throws SQLException;

    boolean delete(int id) throws SQLException;

    boolean update(Account account) throws SQLException;

    Account find(String email, String password, boolean admin) throws SQLException;

    int countAll() throws SQLException;

}
