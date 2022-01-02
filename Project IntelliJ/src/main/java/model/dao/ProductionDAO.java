package model.dao;

import utils.ConPool;
import utils.Paginator;
import utils.SqlMethods;
import utils.extractor.ProductionExtractor;
import model.bean.Film;
import model.bean.Production;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Questa classe rappresenta il DAO della Produzione.
 */
public class ProductionDAO implements SqlMethods<Production> {

    /**
     * Implementa la funzionalità di prendere una lista di produzione.
     * @param paginator per gestire la paginazione
     * @return la lista di produzione
     * @throws SQLException
     */
    @Override
    public List<Production> fetchAll(Paginator paginator) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("SELECT * FROM production AS prod LIMIT ?,?")) {
                ps.setInt(1, paginator.getOffset());
                ps.setInt(2, paginator.getLimit());

                List<Production> productionList = new ArrayList<>();

                ResultSet rs = ps.executeQuery();
                ProductionExtractor productionExtractor = new ProductionExtractor();
                while(rs.next()){
                    Production production = productionExtractor.extract(rs);
                    productionList.add(production);
                }
                rs.close();
                return productionList;
            }
        }
    }

    /**
     * Implementa la funzionalità di prendere una lista di produzione.
     * @return la lista di produzione
     * @throws SQLException
     */
    public List<Production> fetchAll() throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("SELECT * FROM production AS prod")) {

                List<Production> productionList = new ArrayList<>();

                ResultSet rs = ps.executeQuery();
                ProductionExtractor productionExtractor = new ProductionExtractor();
                while(rs.next()){
                    Production production = productionExtractor.extract(rs);
                    productionList.add(production);
                }
                rs.close();
                return productionList;
            }
        }
    }

    /**
     * Implementa la funzionalità di prendere una lista di produzione.
     * @param film a cui ha preso parte la produzione
     * @return la lista di produzione
     * @throws SQLException
     */
    public List<Production> fetchAll(Film film) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("SELECT * FROM production AS prod WHERE id_film = ?")) {
                ps.setInt(1, film.getId());

                List<Production> productionList = new ArrayList<>();

                ResultSet rs = ps.executeQuery();
                ProductionExtractor productionExtractor = new ProductionExtractor();
                while(rs.next()){
                    Production production = productionExtractor.extract(rs);
                    productionList.add(production);
                }
                rs.close();
                return productionList;
            }
        }
    }

    /**
     * Implementa la funzionalità di prendere una produzione.
     * @param id rappresenta l'identificativo numerico della produzione
     * @return la lista di produzione
     * @throws SQLException
     */
    @Override
    public Optional<Production> fetch(int id) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("SELECT * FROM production AS prod WHERE id = ?")) {
                ps.setInt(1, id);

                ResultSet rs = ps.executeQuery();
                Production production = null;

                if(rs.next()) {
                    ProductionExtractor productionExtractor = new ProductionExtractor();
                    production = productionExtractor.extract(rs);
                }
                rs.close();
                return Optional.ofNullable(production);
            }
        }
    }

    /**
     * Implementa la funzionalità di registrazione della produzione nel database.
     * @param production da registrare
     * @return true se la registrazione va a buon fine, false altrimenti
     * @throws SQLException
     */
    @Override
    public boolean insert(Production production) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("INSERT INTO production (firstname, lastname, id_film) VALUES (?,?,?)")) {
                ps.setString(1, production.getFirstname());
                ps.setString(2, production.getLastname());
                ps.setInt(3, production.getFilm().getId());

                int rows = ps.executeUpdate();
                return rows == 1;
            }
        }
    }

    /**
     * Implementa la funzionalità di aggiornamento della produzione.
     * @param production da aggiornare
     * @return true se l'aggiornamento va a buon fine, false altrimenti
     * @throws SQLException
     */
    @Override
    public boolean update(Production production) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("UPDATE production SET firstname = ?, lastname = ? WHERE id = ?")) {
                ps.setString(1, production.getFirstname());
                ps.setString(2, production.getLastname());
                ps.setInt(3, production.getFilm().getId());

                int rows = ps.executeUpdate();
                return rows == 1;
            }
        }
    }

    /**
     * Implementa la funzionalità di rimozione della produzione dal database.
     * @param id rappresenta l'identificativo numerico della produzione
     * @return true se la rimozione va a buon fine, false altrimenti
     * @throws SQLException
     */
    @Override
    public boolean delete(int id) throws SQLException {
        try (Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("DELETE FROM production WHERE id = ?")) {
                ps.setInt(1, id);

                int rows = ps.executeUpdate();
                return rows == 1;
            }
        }
    }

    /**
     * Implementa la funzionalità di conteggio delle produzioni nel database.
     * @return un intero che rappresenta il numero di produzioni nel database.
     * @throws SQLException
     */
    @Override
    public int countAll() throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) AS ct FROM production")) {
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
