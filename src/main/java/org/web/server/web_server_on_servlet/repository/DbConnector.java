package org.web.server.web_server_on_servlet.repository;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbConnector {
    public static Connection connection = null;
    private static String dbName = "web_server";
    private static String username = "postgres";
    private static String password = "1111";

    public static Connection connectionDB() {
        try {
            if (connection == null) {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + dbName, username, password);
                if (connection != null) {
                    System.out.println("Connection Successful!");
                } else {
                    System.out.println("Connection Failed!");
                }
            } else {
                return connection;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return connection;
    }

    public static Connection connectionDB(String dbName, String username, String password) {
        try {
            if (connection == null) {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + dbName, username, password);
                if (connection != null) {
                    System.out.println("Connected");
                } else {
                    System.out.println("Connection Failed!");
                }
            } else {
                return connection;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return connection;
    }
}
