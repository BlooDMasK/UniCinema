package model.dao.production;

import lombok.Generated;
import model.bean.Production;
import utils.ConPool;
import utils.Paginator;
import utils.SqlMethods;
import utils.extractor.ProductionExtractor;
import model.bean.Film;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Questa classe rappresenta il DAO della Produzione.
 */
@Generated
public class ProductionDAOMethods implements ProductionDAO {

    private ConPool conPool = ConPool.getInstance();
    private Connection con = conPool.getConnection();;

    public ProductionDAOMethods() throws SQLException {
    }

    /**
     * Implementa la funzionalità di prendere una lista di produzione.
     * @param paginator per gestire la paginazione
     * @return la lista di produzione
     * @throws SQLException
     */
    @Override
    public List<Production> fetchAll(Paginator paginator) throws SQLException {

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

    /**
     * Implementa la funzionalità di prendere una lista di produzione.
     * @return la lista di produzione
     * @throws SQLException
     */
    public List<Production> fetchAll() throws SQLException {

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

    /**
     * Implementa la funzionalità di prendere una lista di produzione.
     * @param film a cui ha preso parte la produzione
     * @return la lista di produzione
     * @throws SQLException
     */
    public List<Production> fetchAll(Film film) throws SQLException {

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

    /**
     * Implementa la funzionalità di prendere una produzione.
     * @param id rappresenta l'identificativo numerico della produzione
     * @return la lista di produzione
     * @throws SQLException
     */
    @Override
    public Production fetch(int id) throws SQLException {

        try (PreparedStatement ps = con.prepareStatement("SELECT * FROM production AS prod WHERE id = ?")) {
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            Production production = null;

            if(rs.next()) {
                ProductionExtractor productionExtractor = new ProductionExtractor();
                production = productionExtractor.extract(rs);
            }
            rs.close();
            return production;
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

        try (PreparedStatement ps = con.prepareStatement("INSERT INTO production (firstname, lastname, id_film) VALUES (?,?,?)")) {
            ps.setString(1, production.getFirstname());
            ps.setString(2, production.getLastname());
            ps.setInt(3, production.getFilm().getId());

            int rows = ps.executeUpdate();
            return rows == 1;
        }

    }

    public boolean insert(ArrayList<Production> productionList) throws SQLException {

        String query = "INSERT INTO production (firstname, lastname, id_film) VALUE ";
        for(Production production : productionList)
            query += "('"+production.getFirstname()+"','"+production.getLastname()+"',"+production.getFilm().getId()+"),";

        query = query.substring(0, query.length()-1);

        try (PreparedStatement ps = con.prepareStatement(query)) {
            int rows = ps.executeUpdate();
            return rows == productionList.size();
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

        try (PreparedStatement ps = con.prepareStatement("UPDATE production SET firstname = ?, lastname = ? WHERE id = ?")) {
            ps.setString(1, production.getFirstname());
            ps.setString(2, production.getLastname());
            ps.setInt(3, production.getFilm().getId());

            int rows = ps.executeUpdate();
            return rows == 1;
        }

    }

    public boolean update(ArrayList<Production> productionList, int filmId) throws SQLException {

        int deleteCount = 0,
                updateCount = 0,
                insertCount = 0;

        String deleteQuery;
        ArrayList<String> updateQueryList = new ArrayList<>();
        String insertQuery = "INSERT INTO production(firstname, lastname, id_film) VALUES ";


        if (productionList.isEmpty()) {
            deleteQuery = "DELETE FROM production WHERE id_film = " + filmId;
        } else {
            //Elimino tutte le righe non presenti nella nuova lista di attori
            deleteQuery = "DELETE FROM production WHERE id_film = " + filmId + " AND id NOT IN (";
            for (Production production : productionList)
                if (production.getId() != 0) {
                    deleteQuery += production.getId() + ",";
                    deleteCount++;
                }

            deleteQuery = deleteQuery.substring(0, deleteQuery.length() - 1);
            deleteQuery += ")";

            //Aggiorno tutte le righe che sono nella lista
            for (Production production : productionList)
                if (production.getId() != 0) {
                    updateQueryList.add("UPDATE production SET firstname = '" + production.getFirstname() + "', lastname = '" + production.getLastname() + "' WHERE id=" + production.getId());
                    updateCount++;
                }

            //Inserisco tutte le entità che non hanno id (non avendo id significa che sono state appena inserite nella lista)
            for (Production production : productionList)
                if (production.getId() == 0) {
                    insertQuery += "('" + production.getFirstname() + "','" + production.getLastname() + "'," + filmId + "),";
                    insertCount++;
                }

            insertQuery = insertQuery.substring(0, insertQuery.length() - 1);
        }

        try (PreparedStatement deletePS = con.prepareStatement(deleteQuery)) {
            deletePS.executeUpdate();
        }

        if (updateCount > 0) {
            for (String updateQuery : updateQueryList)
                try (PreparedStatement updatePS = con.prepareStatement(updateQuery)) {
                    updatePS.executeUpdate();
                }
        }

        if (insertCount > 0) {
            try (PreparedStatement insertPS = con.prepareStatement(insertQuery)) {
                insertPS.executeUpdate();
            }
        }
        return true;
    }

    /**
     * Implementa la funzionalità di rimozione della produzione dal database.
     * @param id rappresenta l'identificativo numerico della produzione
     * @return true se la rimozione va a buon fine, false altrimenti
     * @throws SQLException
     */
    @Override
    public boolean delete(int id) throws SQLException {

        try (PreparedStatement ps = con.prepareStatement("DELETE FROM production WHERE id = ?")) {
            ps.setInt(1, id);

            int rows = ps.executeUpdate();
            return rows == 1;
        }

    }

    /**
     * Implementa la funzionalità di conteggio delle produzioni nel database.
     * @return un intero che rappresenta il numero di produzioni nel database.
     * @throws SQLException
     */
    @Override
    public int countAll() throws SQLException {

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
