package Model.account;

import Model.api.ConPool;
import Model.api.Paginator;
import Model.api.SqlMethods;
import Model.purchase.Purchase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccountDAO implements SqlMethods<Account> {

    @Override
    public List<Account> fetchAll(Paginator paginator) throws SQLException {

        try (Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("SELECT * FROM customer AS acc LIMIT ?, ?")) {

                ps.setInt(1, paginator.getOffset());
                ps.setInt(2, paginator.getLimit());

                List<Account> accounts = new ArrayList<>();

                ResultSet rs = ps.executeQuery();
                AccountExtractor accountExtractor = new AccountExtractor();
                while (rs.next()) {
                    Account account = accountExtractor.extract(rs);
                    accounts.add(account);
                }

                rs.close();
                return accounts;
            }
        }
    }

    @Override
    public boolean insert(Account account) throws SQLException {
        try (Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("INSERT INTO customer (email, pswrd, firstname, lastname, administrator) VALUES (?, ?, ?, ?, ?)")) {

                ps.setString(1, account.getEmail());
                ps.setString(2, account.getPswrd());
                ps.setString(3, account.getFirstname());
                ps.setString(4, account.getLastname());
                ps.setBoolean(5, account.isAdministrator());

                int rows = ps.executeUpdate();
                return rows == 1;
            }
        }
    }

    @Override
    public Optional<Account> fetch(int id) throws SQLException {
        try (Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps =
                         con.prepareStatement("SELECT * FROM customer AS acc WHERE id = ?")) {
                ps.setInt(1, id);

                ResultSet rs = ps.executeQuery();
                AccountExtractor accountExtractor = new AccountExtractor();
                Account account = null;
                if(rs.next())
                    account = accountExtractor.extract(rs);

                rs.close();
                return Optional.ofNullable(account);
            }
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try(PreparedStatement ps =
                        con.prepareStatement("DELETE FROM customer WHERE id = ?")) {

                ps.setInt(1, id);

                int rows = ps.executeUpdate();
                return rows == 1;
            }
        }
    }

    @Override
    public boolean update(Account account) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try(PreparedStatement ps =
                        con.prepareStatement("UPDATE customer SET firstname = ?, lastname = ?, administrator = ? WHERE id = ?")) {

                ps.setString(1, account.getFirstname());
                ps.setString(2, account.getLastname());
                ps.setBoolean(3, account.isAdministrator());
                ps.setString(4, account.getEmail());

                int rows = ps.executeUpdate();
                return rows == 1;
            }
        }
    }

    public Optional<Account> find(String email, String password, boolean admin) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try(PreparedStatement ps = con.prepareStatement("SELECT * FROM customer AS acc WHERE (email = ? AND pswrd = ?" + (admin ? " AND administrator = ?)" : ")"))) {
                ps.setString(1, email);
                ps.setString(2, password);
                if(admin)
                    ps.setBoolean(3, admin);
                ResultSet rs = ps.executeQuery();
                Account account = null;
                if(rs.next())
                    account = new AccountExtractor().extract(rs);
                return Optional.ofNullable(account);
            }
        }
    }

    @Override
    public int countAll() throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try(PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) AS ct FROM customer")) {
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