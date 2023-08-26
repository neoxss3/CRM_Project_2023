/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crm22.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author anhbs
 */
public class MySQLConfig {
    public static Connection getConnection() throws ClassNotFoundException{
        Connection con = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3308/tfosrebyC_CRM", "root", "12345");
        }catch(ClassNotFoundException | SQLException e){
            System.out.println("Loi connect" + e.getMessage());
        }
        return con;
    }
}
