package utils;

import lombok.Generated;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.TimeZone;

/**
 * Questa classe offre le funzionalità di connessione al DBMS.
 */
@Generated
public class ConPool {

    //private static DataSource datasource;

    private static ConPool instance;
    private Connection connection;

    private String url = "jdbc:mysql://localhost:3307/cinema?useSSL=false&serverTimezone=" + TimeZone.getDefault().getID();
    private String username = "root";
    private String password = "Napoli*99";

    private ConPool() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            System.out.println("Connessione al DB fallita: " + e.getMessage());
        }
    }

    /**
     * Implementa la funzionalità che permette di restituire un oggetto di tipo Connection
     * @return l'oggetto di tipo Connection
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Implementa la funzionalità che permette di restituire l'instanza del ConPool
     * @return l'oggetto di tipo ConPool
     * @throws SQLException
     */
    public static ConPool getInstance() throws SQLException {
        if(instance == null) {
            instance = new ConPool();
        } else if(instance.getConnection().isClosed()) {
            instance = new ConPool();
        }
        return instance;
    }


}
