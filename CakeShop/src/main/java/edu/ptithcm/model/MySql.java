package edu.ptithcm.model;

import java.sql.*;

public class MySql{
    // Thong tin ket noi mac dinh
    private static final String defaultURL = "jdbc:mysql://localhost:3306/";
    private static final String defaultDBName = "CakeShop";
    private static String defaultUserName = "root";
    private static String defaultPasswd = "";

    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(defaultURL + defaultDBName, defaultUserName, defaultPasswd);
    }

    // setter/getter
    public static String getDefaultURL() {
        return defaultURL;
    }

    public static String getDefaultDBName() {
        return defaultDBName;
    }

    public static String getDefaultPasswd() {
        return defaultPasswd;
    }

    public static void setDefaultPasswd(String defaultPasswd) {
        MySql.defaultPasswd = defaultPasswd;
    }

    public static String getDefaultUserName() {
        return defaultUserName;
    }

    public static void setDefaultUserName(String defaultUserName) {
        MySql.defaultUserName = defaultUserName;
    }
}