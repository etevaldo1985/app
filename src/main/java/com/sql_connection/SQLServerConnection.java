package com.sql_connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SQLServerConnection {

    private Connection connection;
    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=JavaProject;encrypt=true;trustServerCertificate=true";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "124860";
    private static final Logger LOG = Logger.getLogger(SQLServerConnection.class.getName());
   


    public SQLServerConnection() {

        try {
            this.connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            if (this.connection != null && !this.connection.isClosed()) {
                LOG.info("Connection successfully established");
            } else {
                LOG.warning("Failed to establish connection");
            }
        } catch (SQLException e) {
            LOG.log(Level.SEVERE,"Error connection with database", e.getMessage());
            e.printStackTrace();
        }
    }

    protected Connection getConnection() {
        return this.connection;
    }

    public void closeConnection() {
        if (this.connection != null) {
            try {
                this.connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
