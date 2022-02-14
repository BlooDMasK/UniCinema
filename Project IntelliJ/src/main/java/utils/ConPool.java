package utils;

import lombok.Generated;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.TimeZone;

/**
 * Questa classe offre le funzionalità di connessione al DBMS.
 */
@Generated
public class ConPool {

    /**
     * Rappresenta l'oggetto che permette di restituire la connessione.
     */
    private static DataSource datasource;

    /**
     * Implementa la funzionalità che permette di impostare e restituire la connessione al DBMS.
     * @return la connessione
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        if (datasource == null) {
            PoolProperties p = new PoolProperties();
            p.setUrl("jdbc:mysql://localhost:3307/cinema?serverTimezone=" + TimeZone.getDefault().getID());
            p.setDriverClassName("com.mysql.cj.jdbc.Driver");
            p.setUsername("root");
            p.setPassword("studentiTSW_2021");
            p.setMaxActive(100);
            p.setInitialSize(10);
            p.setMinIdle(10);
            p.setRemoveAbandonedTimeout(60);
            p.setRemoveAbandoned(true);
            datasource = new DataSource();
            datasource.setPoolProperties(p);
        }
        return datasource.getConnection();
    }
}
