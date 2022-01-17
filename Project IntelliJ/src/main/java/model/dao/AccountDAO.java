package model.dao;

import utils.SqlMethods;
import utils.extractor.AccountExtractor;
import utils.ConPool;
import utils.Paginator;
import model.bean.Account;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static utils.validator.RequestValidator.isNull;

/**
 * Questa classe rappresenta il DAO di un Account.
 */
public class AccountDAO implements SqlMethods<Account> {

    /**
     * Implementa la funzionalità di prendere una lista di Account.
     * @param paginator per gestire la paginazione.
     * @return la lista degli account registrati.
     * @throws SQLException
     */
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

    /**
     * Implementa la funzionalità di registrare un account nel database.
     * @param account da registrare
     * @return true se la registrazione va a buon fine, false altrimenti.
     * @throws SQLException
     */
    @Override
    public boolean insert(Account account) throws SQLException {
        try (Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("INSERT INTO customer (email, pswrd, firstname, lastname, administrator) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {

                ps.setString(1, account.getEmail());
                ps.setString(2, account.getPswrd());
                ps.setString(3, account.getFirstname());
                ps.setString(4, account.getLastname());
                ps.setBoolean(5, account.isAdministrator());

                int rows = ps.executeUpdate();
                if(rows == 1) {
                    ResultSet rs = ps.getGeneratedKeys();
                    rs.next();
                    account.setId(rs.getInt(1));
                    return true;
                }
                return false;
            }
        }
    }

    /**
     * Implementa la funzionalità di prendere un account.
     * @param id rappresenta l'identificativo numerico dell'account
     * @return l'account.
     * @throws SQLException
     */
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

    /**
     * Implementa la funzionalità di prendere un account.
     * @param email rappresenta l'identificativo alfabetico dell'account
     * @return l'account.
     * @throws SQLException
     */
    public Optional<Account> fetch(String email) throws SQLException {
        try (Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps =
                         con.prepareStatement("SELECT * FROM customer AS acc WHERE email = ?")) {
                ps.setString(1, email);

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

    /**
     * Implementa la funzionalità di rimuovere un account.
     * @param id rappresenta l'identificativo numerico dell'account
     * @return true se la rimozione va a buon fine, false altrimenti.
     * @throws SQLException
     */
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

    /**
     * Implementa la funzionalità di aggiornamento dati di un account.
     * @param account da aggiornare
     * @return true se l'aggiornamento va a buon fine, false altrimenti.
     * @throws SQLException
     */
    @Override
    public boolean update(Account account) throws SQLException {
        boolean updatePswrd = !isNull(account.getPswrd());

        try(Connection con = ConPool.getConnection()) {
            try(PreparedStatement ps =
                        con.prepareStatement("UPDATE customer SET firstname = ?, lastname = ?, administrator = ?, email = ?"+ (updatePswrd ? ", pswrd = ?" : "") +" WHERE id = ?")) {

                ps.setString(1, account.getFirstname());
                ps.setString(2, account.getLastname());
                ps.setBoolean(3, account.isAdministrator());
                ps.setString(4, account.getEmail());
                if(updatePswrd) {
                    ps.setString(5, account.getPswrd());
                    ps.setInt(6, account.getId());
                } else
                    ps.setInt(5, account.getId());

                int rows = ps.executeUpdate();
                return rows == 1;
            }
        }
    }

    /**
     * Implementa la funzionalità di ricerca di un account.
     * @param email rappresenta l'identificativo alfabetico dell'account
     * @param password rappresenta la password dell'account (che può essere anche vuota)
     * @param admin rappresenta il ruolo amministrativo che ricopre l'account
     * @return
     * @throws SQLException
     */
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

    /**
     * Implementa la funzionalità di conteggio degli account.
     * @return un intero che rappresenta il numero di account registrati nel database
     * @throws SQLException
     */
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