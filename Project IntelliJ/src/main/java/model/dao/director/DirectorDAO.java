package model.dao.director;

import model.bean.Director;
import model.bean.Film;
import utils.Paginator;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface DirectorDAO {

    List<Director> fetchAll(Paginator paginator) throws SQLException;

    List<Director> fetchAll() throws SQLException;

    List<Director> fetchAll(Film film) throws SQLException;

    Director fetch(int id) throws SQLException;

    boolean insert(Director director) throws SQLException;

    boolean insert(ArrayList<Director> directorList) throws SQLException;

    boolean update(Director director) throws SQLException;

    boolean update(ArrayList<Director> directorList, int filmId) throws SQLException;

    boolean delete(int id) throws SQLException;

    int countAll() throws SQLException;
}
