package model.dao.houseproduction;

import model.bean.Film;
import model.bean.HouseProduction;
import utils.Paginator;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface HouseProductionDAO {

    List<HouseProduction> fetchAll(Paginator paginator) throws SQLException;

    List<HouseProduction> fetchAll() throws SQLException;

    List<HouseProduction> fetchAll(Film film) throws SQLException;

    HouseProduction fetch(int id) throws SQLException;

    boolean insert(HouseProduction houseProduction) throws SQLException;

    boolean insert(ArrayList<HouseProduction> houseProductionList) throws SQLException;

    boolean update(HouseProduction houseProduction) throws SQLException;

    boolean update(ArrayList<HouseProduction> houseProductionList, int filmId) throws SQLException;

    boolean delete(int id) throws SQLException;

    int countAll() throws SQLException;
}
