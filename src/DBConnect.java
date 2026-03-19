/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.*;
        
public class DBConnect {
    private static final String URL = "jdbc:mariadb://localhost:3306/libdb";
    private static final String USER = "root";
    private static final String PASSWORD = "bazar12";
    
    public static Connection getConnection(){
        
        try{
            Connection conn = DriverManager.getConnection(URL,USER,PASSWORD);
            System.out.println("Database connected");
            return conn;
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }
    
}
