package org.web.server.web_server_on_servlet.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbConnector {
    private static Connection connection;
    private static String dbName = "web_server";
    private static String username = "postgres";
    private static String password = "1111";

    public static Connection connectionDB() {
        try {
            if (connection == null) {
                System.out.println("Connected!");
                Class.forName("org.postgresql.Driver");
                return DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + dbName, username, password);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("Connection already initialized!");
        return connection;
    }

    public static Connection connectionDB(String dbName, String username, String password) {
        try {
            if (connection == null) {
                System.out.println("Connected!");
                Class.forName("org.postgresql.Driver");
                return DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + dbName, username, password);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("Connection already initialized!");
        return connection;
    }
}
