package com.narval.stockms.dal;

import java.sql.*;

public class Dao {
    public static Connection con = null;
    public static String host = "localhost";
    public static String serverName = "DESKTOP-EFD0H";
    public static int port = 1433;
    public static String dbName = "StockManagementSystem";
    public static String properties = "encrypt=true;trustServerCertificate=true";
    public static String url = "jdbc:sqlserver://" + serverName + ":" + port + ";Database=" + dbName + ";" + properties;
    public static String user = "sa";
    public static String password = "123";

    public Dao() {}

    public static Connection connDb() {
        try {
            con = DriverManager.getConnection(url, user, password);
            return con;
        } catch (SQLException e) {
            System.out.println("oopss");
            e.printStackTrace();
            if (e.getErrorCode() == 0) { //Error Code 0: database server offline
                System.out.println("Error! Server is offline");
            }
            return null;
        }
    }
}
