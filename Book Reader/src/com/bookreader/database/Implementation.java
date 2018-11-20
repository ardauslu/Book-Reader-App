    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bookreader.database;

import com.bookreader.model.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ardau
 */
public class Implementation implements NewInterface {

    private Connection connection;

    @Override
    public boolean insert(User user) {

        
        try {
            String query = "INSERT INTO new_user values ('" + user.getUsername() + "','" + user.getName() + "','" + user.getLastName() + "','" + user.getPassword() + "','" + user.getCpassword() + "','" + user.getLocation() + "','" + user.getAge() + "','" + user.getUserid() + "')";
            Statement statement = this.connection.createStatement();
            statement.executeUpdate(query);
            statement.close();
        
            return true;
        } catch (SQLException ex) {
           return false;
        }
    }

    @Override
    public User getUser(String userName, String password) {

         try {
             String query = "SELECT * FROM new_user where username = '" + userName + "' and password = '" + password + "'";
           
             PreparedStatement preparedStatement = this.connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery();
    
             User user = null;

             if(resultSet.next()){
             
             user = new User();
             user.setUsername(resultSet.getString("username"));
             user.setName(resultSet.getString("name"));
             user.setLastName(resultSet.getString("lastname"));
             user.setUserid(resultSet.getString("userid"));
             user.setLocation(resultSet.getString("location"));
             user.setAge(resultSet.getString("age"));
             user.setCpassword(resultSet.getString("cpassword"));
             user.setPassword(resultSet.getString("password"));
      
             }
            
            preparedStatement.close();
            resultSet.close();
        
            return user;
        } catch (SQLException ex) {
           return null;
        }
    }

    public void openConnection() {

        try {

            Class.forName("com.mysql.jdbc.Driver");
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost/books","root","");
            System.out.println("Connection established successfully with the database server");

        } catch (Exception e) {

            System.out.println("Error:" + e.getMessage());
        }
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
