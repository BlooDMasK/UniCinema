package Model.production;

import Model.api.ConPool;
import Model.api.Paginator;
import Model.api.SqlMethods;
import Model.director.Director;
import Model.director.DirectorExtractor;
import Model.film.Film;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductionDAO implements SqlMethods<Production> {

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
