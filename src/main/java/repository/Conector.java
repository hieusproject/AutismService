/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import entity.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author AnNguyen
 */
public class Conector {
    public static Connection getConnection(){
        Connection connection = null;
         try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your MySQL JDBC Driver?");
            e.printStackTrace();
            return null;
        }
        
        try {
            connection =  DriverManager.getConnection("jdbc:mysql://localhost:3306/autismsvdb?serverTimezone=UTC", "root","1");
            System.out.println("connect successfull");
           
        } catch (Exception e) {
            System.out.println("conection failed");
            connection = null;
        }
        return connection;
    }
    
    public static void main(String[] args) throws SQLException {
    }
}
