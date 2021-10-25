package Model.api;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface SqlMethods <T> {
    public List<T> fetchAll(Paginator paginator) throws SQLException;

    public Optional<T> fetch(int id) throws SQLException;

    public boolean insert(T object) throws SQLException;

    public boolean update(T object) throws SQLException;

    public boolean delete(int id) throws SQLException;

    public int countAll() throws SQLException;
}
