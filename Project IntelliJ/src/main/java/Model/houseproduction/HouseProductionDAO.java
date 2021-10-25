package Model.houseproduction;

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

public class HouseProductionDAO implements SqlMethods<HouseProduction> {

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
