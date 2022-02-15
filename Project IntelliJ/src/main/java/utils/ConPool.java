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

    private static ConPool instance;
    private Connection connection;

    private String url = "jdbc:mysql://localhost:3307/cinema?useSSL=false&serverTimezone=" + TimeZone.getDefault().getID();
    private String username = "root";
    private String password = "studentiTSW_2021";

    private ConPool() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            System.out.println("Connessione al DB fallita: " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public static ConPool getInstance() throws SQLException {
        if(instance == null) {
            instance = new ConPool();
        } else if(instance.getConnection().isClosed()) {
            instance = new ConPool();
        }
        return instance;
    }


}
