package Model.purchase;

import Model.account.Account;
import Model.account.AccountDAO;
import Model.api.ConPool;
import Model.api.Paginator;
import Model.api.SqlMethods;
import Model.ticket.TicketDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class PurchaseDAO implements SqlMethods<Purchase> {
    @Override
    public List<Purchase> fetchAll(Paginator paginator) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try(PreparedStatement ps = con.prepareStatement("SELECT * FROM purchase AS pur LIMIT ?,?")) {
                ps.setInt(1, paginator.getOffset());
                ps.setInt(2, paginator.getLimit());

                List<Purchase> purchases = new ArrayList<>();

                ResultSet rs = ps.executeQuery();
                PurchaseExtractor purchaseExtractor = new PurchaseExtractor();
                while(rs.next()) {
                    Purchase purchase = purchaseExtractor.extract(rs);
                    Optional<Account> account = purchaseExtractor.extractClient(rs);
                    if(account.isPresent())
                            purchase.setAccount(account.get());
                    purchase.setTicketList(new TicketDAO().fetchAll(purchase));

                    purchases.add(purchase);
                }
                rs.close();
                return purchases;
            }
        }
    }

    @Override
    public Optional<Purchase> fetch(int id) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("SELECT * FROM purchase AS pur WHERE id = ?")) {
                ps.setInt(1,id);

                ResultSet rs = ps.executeQuery();
                PurchaseExtractor purchaseExtractor = new PurchaseExtractor();
                Purchase purchase = purchaseExtractor.extract(rs);
                rs.close();
                return Optional.ofNullable(purchase);
            }
        }
    }

    @Override
    public boolean insert(Purchase purchase) throws SQLException {
        return false;
    }

    public int insertAndReturnID(Purchase purchase) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("INSERT INTO purchase(date_purchase, id_client) VALUES(?,?)", new String[] {"IDPurchase"})) {
                ps.setDate(1, java.sql.Date.valueOf(purchase.getDatePurchase()));
                ps.setInt(2, purchase.getAccount().getId());

                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();
                if(rs.next())
                    return rs.getInt(1);
                else
                    return 0;
            }
        }
    }

    @Override
    public boolean update(Purchase object) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        return false;
    }

    @Override
    public int countAll() throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) AS ct FROM purchase")) {
                ResultSet rs = ps.executeQuery();
                int ct = 0;
                if(rs.next())
                    ct = rs.getInt("ct");
                rs.close();
                return ct;
            }
        }
    }
}
