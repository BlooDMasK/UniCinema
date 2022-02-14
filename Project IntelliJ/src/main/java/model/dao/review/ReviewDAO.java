package model.dao.review;

import model.bean.Film;
import model.bean.Review;
import utils.Paginator;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ReviewDAO {

    List<Review> fetchAll(Paginator paginator) throws SQLException;

    ArrayList<Review> fetchAll() throws SQLException;

    ArrayList<Review> fetchAll(int filmId) throws SQLException;

    ArrayList<Review> fetchAll(Film film, Paginator paginator) throws SQLException;

    Review fetch(int accountId) throws SQLException;

    Review fetch(int accountId, int filmId) throws SQLException;

    boolean insert(Review review) throws SQLException;

    boolean update(Review review) throws SQLException;

    boolean delete(int accountId) throws SQLException;

    int countAll() throws SQLException;

    int countAll(Film film) throws SQLException;

    int countByAccountId(int accountId) throws SQLException;
}
