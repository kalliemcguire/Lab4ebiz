package database;

import java.sql.Connection;
import java.sql.DriverManager;

public class PayrollSystemDA {
    
    private static Connection connection;
    
    public static void initialize() {
        try{
        connection = (Connection)  
        DriverManager.getConnection("jdbc:derby://localhost:1527/PayrollSystemDB","CIS640","cis640");
        System.out.println(connection);
        }
        catch (Exception e){}
    }
    
    public static Connection getConnection() {
        return connection;
    }
}
