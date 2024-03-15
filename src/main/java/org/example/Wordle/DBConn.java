package org.example.Wordle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConn {
    private static DBConn eineDatenbankInstanz;
    private Connection connection;
    String jdbcURL = "jdbc:postgresql://localhost:5432/wordle";
    String username = "postgres";
    String psw = "admin";

    private DBConn() {
        try {
            this.connection = DriverManager.getConnection(jdbcURL, username, psw);
        } catch (SQLException ex) {
            Logger.getLogger(DBConn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public static synchronized DBConn getInstance() throws SQLException {
        if (eineDatenbankInstanz == null || eineDatenbankInstanz.getConnection().isClosed()) {
            eineDatenbankInstanz = new DBConn();
        }
        return eineDatenbankInstanz;
    }
}

