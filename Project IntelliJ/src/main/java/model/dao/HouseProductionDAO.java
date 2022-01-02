package model.dao;

import utils.ConPool;
import utils.Paginator;
import utils.SqlMethods;
import utils.extractor.HouseProductionExtractor;
import model.bean.Film;
import model.bean.HouseProduction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
/**
 * Questa classe rappresenta il DAO di una Casa di produzione.
 */
public class HouseProductionDAO implements SqlMethods<HouseProduction> {

    /**
     * Implementa la funzionalità di prendere una lista di case di produzione.
     * @param paginator per gestire la paginazione
     * @return la lista delle case di produzione
     * @throws SQLException
     */
    @Override
    public List<HouseProduction> fetchAll(Paginator paginator) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try(PreparedStatement ps = con.prepareStatement("SELECT * FROM house_production AS hp LIMIT ?,?")) {
                ps.setInt(1, paginator.getOffset());
                ps.setInt(2, paginator.getLimit());

                List<HouseProduction> houseProductionList = new ArrayList<>();

                ResultSet rs = ps.executeQuery();
                HouseProductionExtractor houseProductionExtractor = new HouseProductionExtractor();
                while(rs.next()) {
                    HouseProduction houseProduction = houseProductionExtractor.extract(rs);
                    houseProductionList.add(houseProduction);
                }
                rs.close();
                return houseProductionList;
            }
        }
    }
    /**
     * Implementa la funzionalità di prendere una lista di case di produzione.
     * @return la lista delle case di produzione
     * @throws SQLException
     */
    public List<HouseProduction> fetchAll() throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try(PreparedStatement ps = con.prepareStatement("SELECT * FROM house_production AS hp")) {

                List<HouseProduction> houseProductionList = new ArrayList<>();

                ResultSet rs = ps.executeQuery();
                HouseProductionExtractor houseProductionExtractor = new HouseProductionExtractor();
                while(rs.next()) {
                    HouseProduction houseProduction = houseProductionExtractor.extract(rs);
                    houseProductionList.add(houseProduction);
                }
                rs.close();
                return houseProductionList;
            }
        }
    }

    /**
     * Implementa la funzionalità di prendere una lista di case di produzione
     * @param film a cui la casa produttrice ha preso parte
     * @return la lista delle case di produzione
     * @throws SQLException
     */
    public List<HouseProduction> fetchAll(Film film) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try(PreparedStatement ps = con.prepareStatement("SELECT * FROM house_production AS hp WHERE id_film = ?")) {
                ps.setInt(1, film.getId());

                List<HouseProduction> houseProductionList = new ArrayList<>();

                ResultSet rs = ps.executeQuery();
                HouseProductionExtractor houseProductionExtractor = new HouseProductionExtractor();
                while(rs.next()) {
                    HouseProduction houseProduction = houseProductionExtractor.extract(rs);
                    houseProductionList.add(houseProduction);
                }
                rs.close();
                return houseProductionList;
            }
        }
    }

    /**
     * Implementa la funzionalità di prendere una casa di produzione.
     * @param id rappresenta l'identificativo numerico della casa di produzione
     * @return la casa di produzione
     * @throws SQLException
     */
    @Override
    public Optional<HouseProduction> fetch(int id) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try(PreparedStatement ps = con.prepareStatement("SELECT * FROM house_production AS hp WHERE id = ?")) {
                ps.setInt(1, id);

                ResultSet rs = ps.executeQuery();
                HouseProduction houseProduction = null;
                if(rs.next()){
                    HouseProductionExtractor houseProductionExtractor = new HouseProductionExtractor();
                    houseProduction = houseProductionExtractor.extract(rs);
                }
                rs.close();

                return Optional.ofNullable(houseProduction);
            }
        }
    }

    /**
     *Implementa la funzionalità di registrare una casa di produzione del DataBase
     * @param houseProduction da registrare
     * @return true se la registrazione va a buon fine, false altrimenti
     * @throws SQLException
     */
    @Override
    public boolean insert(HouseProduction houseProduction) throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("INSERT INTO house_production (name_house, id_film) VALUES (?, ?)")){
                ps.setString(1, houseProduction.getName());
                ps.setInt(2, houseProduction.getFilm().getId());

                int rows = ps.executeUpdate();
                return rows == 1;
            }
        }
    }

    /**
     *Implementa la funzionalità che permette di aggiornare i dati di una casa produttrice
     * @param houseProduction da aggiornare
     * @return true se l'aggiornamento va a buon fine, false altrimenti
     * @throws SQLException
     */
    @Override
    public boolean update(HouseProduction houseProduction) throws SQLException {
        try (Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("UPDATE house_production SET name_house = ? WHERE id = ?")) {
                ps.setString(1, houseProduction.getName());
                ps.setInt(2, houseProduction.getId());

                int rows = ps.executeUpdate();
                return rows == 1;
            }
        }
    }

    /**
     *Implementa la funzionalità che permette di rimuovere una casa produttrice
     * @param id rappresenta l'identificativo numericodi una casa di produzione
     * @return true se la rimozione va a buon fine, false altrimenti
     * @throws SQLException
     */
    @Override
    public boolean delete(int id) throws SQLException {
        try (Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("DELETE FROM house_production WHERE id = ?")) {
                ps.setInt(1, id);

                int rows = ps.executeUpdate();
                return rows == 1;
            }
        }
    }

    /**
     * Implementa la funzionalità di conteggio delle case di produzione
     * @return un intero che rappresenta il numero delle case di produzione registrate nel DataBase
     * @throws SQLException
     */
    @Override
    public int countAll() throws SQLException {
        try(Connection con = ConPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) AS ct FROM house_production")) {
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
