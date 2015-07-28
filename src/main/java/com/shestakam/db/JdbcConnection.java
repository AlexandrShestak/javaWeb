package com.shestakam.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by alexandr on 17.7.15.
 */
public class JdbcConnection {

    private static Connection connection = null;
    private  final static Logger logger = LogManager.getLogger(JdbcConnection.class);

    public  static synchronized Connection getConnection() throws SQLException {

        if (connection==null || connection.isClosed()) {

            try {

                ResourceBundle resource = ResourceBundle.getBundle("database");
                String url = resource.getString("url");
                String driver = resource.getString("driver");
                String user = resource.getString("user");
                String pass = resource.getString("password");

                Class.forName(driver);
                logger.debug("driver ok");
                connection = DriverManager.getConnection(url, user, pass);

            } catch (Exception e) {
                logger.error("get connection error", e);
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                logger.debug("connection closed");
            } catch (SQLException e) {
                logger.error("close connection error",e);
            }
        }
    }
}
