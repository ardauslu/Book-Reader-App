/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bookreader.ui;

import static com.bookreader.ui.BookList.bookTitleList;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;

/**
 *
 * @author ardau
 */
public class LastAdded extends JFrame {
    
    
    DefaultListModel model = new DefaultListModel();
    
    private JList list= new JList(model);
    
    
    
    
    public LastAdded() throws SQLException, ClassNotFoundException{
    
    
    setLayout(new BorderLayout());
    setBounds(300,300,300,300);
        
    add(list,BorderLayout.CENTER);
         
    
      ArrayList listOfBooks = new ArrayList();
      Class.forName("com.mysql.jdbc.Connection");
      Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/books", "root", "");

        Statement stmt = (Statement) con.createStatement();

        ResultSet rs = stmt.executeQuery("SELECT book_title FROM bx_books");

        while (rs.next()) {
            String bookName = rs.getString("book_title");
            listOfBooks.add(bookName);

        }
    
    
    
    model.addElement(listOfBooks.get(listOfBooks.size()-1));
    model.addElement(listOfBooks.get(listOfBooks.size()-2));
    model.addElement(listOfBooks.get(listOfBooks.size()-3));
    model.addElement(listOfBooks.get(listOfBooks.size()-4));
    model.addElement(listOfBooks.get(listOfBooks.size()-5));
   
 
    
    }
    
    
    
    
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
            
        
        LastAdded x = new LastAdded();
        x.setVisible(true);
        
        
        
    }
    
    
    
    
    
    
    
    
    
    
}
