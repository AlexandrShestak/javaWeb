package com.shestakam.dao;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by alexandr on 17.7.15.
 */
public class JdbcConnection {

    private static Connection connection= null;
    private final static Logger logger = Logger.getLogger(JdbcConnection.class.getName());

    public  static synchronized Connection getConnection() {
        if (connection==null) {
            ResourceBundle resource = ResourceBundle.getBundle("database.properties");
            String url = resource.getString("url");
            String driver = resource.getString("driver");
            String user = resource.getString("user");
            String pass = resource.getString("password");

            try {
                Class.forName(driver);
                logger.debug("driver ok");
                connection = DriverManager.getConnection(url, user, pass);

            } catch (Exception e) {
                logger.error("get connection error",e);
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                logger.debug("connection closed");
            } catch (SQLException e) {
                logger.error("close connection error",e);
            }
        }
    }
}
