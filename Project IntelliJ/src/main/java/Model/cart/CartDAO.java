package Model.cart;

import Model.api.Paginator;
import Model.api.SqlMethods;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CartDAO implements SqlMethods<Cart> {
    @Override
    public List<Cart> fetchAll(Paginator paginator) throws SQLException {
        return null;
    }

    @Override
    public Optional<Cart> fetch(int id) throws SQLException {
        return Optional.empty();
    }

    @Override
    public boolean insert(Cart object) throws SQLException {
        return false;
    }

    @Override
    public boolean update(Cart object) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        return false;
    }

    @Override
    public int countAll() throws SQLException {
        return 0;
    }
}
