package model.dao.production;

import model.bean.Film;
import model.bean.Production;
import utils.Paginator;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ProductionDAO {

    List<Production> fetchAll(Paginator paginator) throws SQLException;

    List<Production> fetchAll() throws SQLException;

    List<Production> fetchAll(Film film) throws SQLException;

    Production fetch(int id) throws SQLException;

    boolean insert(Production production) throws SQLException;

    boolean insert(ArrayList<Production> productionList) throws SQLException;

    boolean update(Production production) throws SQLException;

    boolean update(ArrayList<Production> productionList, int filmId) throws SQLException;

    boolean delete(int id) throws SQLException;

    int countAll() throws SQLException;
}
