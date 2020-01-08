package com.inno.ConnectionManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * ConnectionManagerJdbcImpl
 * <p>
 * Implements connection manager for JDBC.
 *
 * @author Ekaterina Belolipetskaya
 */
@EJB
public class ConnectionManagerJdbcImpl implements ConnectionManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionManagerJdbcImpl.class);
    private static ConnectionManager connectionManager;
    private static final String POSTGRES_DRIVER = "org.postgresql.Driver";
    public static final String POSTGRES_URL_USERS = "jdbc:postgresql://127.0.0.1:5432/mobile";
    public static final String POSTGRES_USER = "postgres";
    public static final String POSTGRES_PASSWORD = "qwerty123";

    private ConnectionManagerJdbcImpl() {
    }

    public static ConnectionManager getInstance() {
        if (connectionManager == null) {
            connectionManager = new ConnectionManagerJdbcImpl();
        }
        return connectionManager;
    }

    @Override
    public Connection getConnection() {
        Connection connection = null;
        try {
            LOGGER.debug("Getting connection to DB");
            Class.forName(POSTGRES_DRIVER);
            connection = DriverManager.getConnection(
                    POSTGRES_URL_USERS,
                    POSTGRES_USER,
                    POSTGRES_PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            LOGGER.error("Error during getting connection", e);
        }
        return connection;
    }
}
