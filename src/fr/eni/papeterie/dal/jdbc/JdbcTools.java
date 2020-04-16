package fr.eni.papeterie.dal.jdbc;

import fr.eni.papeterie.dal.Settings;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcTools {
    private static Connection c;

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName(Settings.getProperty("driverjdbc"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (c == null){
            c = DriverManager.getConnection(Settings.getProperty("url"), Settings.getProperty("user"), Settings.getProperty("password"));
        }
        return c;
    }

    public static void closeConnection(Connection c) throws SQLException {
        if(c !=null){
            c.close();
            c=null;
        }
    }

    public static void closeStatement(Statement stmt) throws SQLException{
        if(c !=null){
            c.close();
            c=null;
        }
    }
}
