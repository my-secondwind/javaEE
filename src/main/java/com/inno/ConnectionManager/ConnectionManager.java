package com.inno.ConnectionManager;

import java.sql.Connection;

/**
 * ConnectionManager
 *
 * Interface for getting connection
 *
 * @author Ekaterina Belolipetskaya
 */
public interface ConnectionManager {
    Connection getConnection();
}
