/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bookreader.ui;

import static com.bookreader.ui.BookList.selected;
import static com.bookreader.ui.Login.usernameLabel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author ardau
 */
public class Admin extends JFrame {

    public static String adminSelected;
    private JLabel label = new JLabel();
    private JTextField searchTxt = new JTextField();
    private JScrollPane bookSp = new JScrollPane();
    private JScrollPane userSp = new JScrollPane();
    private JButton addBook = new JButton("Add a Book to Library");
    private JButton deleteBook = new JButton("Delete selected book");
    private JButton deleteUser = new JButton("Delete selected user");
    private Label headerLabel = new Label();
    private static DefaultListModel defaultListModel = new DefaultListModel();
    private static DefaultListModel userModel = new DefaultListModel();
    JList bookList = new JList(defaultListModel);
    JList userList = new JList(userModel);
    public static String bookSelected;

    private void searchTxtActionPerformed(java.awt.event.ActionEvent evt) {
        searchTxtActionPerformed(evt);
    }

    private void searchTxtKeyReleased(java.awt.event.KeyEvent evt) throws SQLException, ClassNotFoundException {

        searchFilter(searchTxt.getText());

    }

    private void searchFilter(String searchTerm) throws SQLException, ClassNotFoundException {
        DefaultListModel filteredItems = new DefaultListModel();
        ArrayList stars = getStars();

        stars.stream().forEach((star) -> {

            String starName = star.toString().toLowerCase();
            if (starName.contains(searchTerm.toLowerCase())) {

                filteredItems.addElement(star);
            }
        });

        defaultListModel = filteredItems;
        bookList.setModel(defaultListModel);

    }

    private void bindData() throws SQLException, ClassNotFoundException {
        getStars().stream().forEach((star) -> {
            defaultListModel.addListDataListener((ListDataListener) star);
        });

        bookList.setModel(defaultListModel);
        bookList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    public ArrayList getStars() throws SQLException,
            ClassNotFoundException {

        ArrayList stars = new ArrayList();

        com.mysql.jdbc.Statement statement;
        ResultSet resultSet;
        try (com.mysql.jdbc.Connection conn = (com.mysql.jdbc.Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/books", "root", "")) {
            statement = (com.mysql.jdbc.Statement) conn.createStatement();
            resultSet = statement.executeQuery("SELECT book_title FROM bx_books");
            while (resultSet.next()) {
                String bookName = resultSet.getString("book_title");
                stars.add(bookName);

            }
        }
        resultSet.close();
        statement.close();

        return stars;

    }

    public Admin() throws ClassNotFoundException, SQLException {

        ArrayList userArray = new ArrayList();
        Class.forName("com.mysql.jdbc.Connection");
        Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/books", "root", "");

        Statement stmt = (Statement) con.createStatement();

        ResultSet rs = stmt.executeQuery("SELECT username FROM new_user");

        while (rs.next()) {

            String us = rs.getString("username");
            userArray.add(us);

        }

        userList.setModel(userModel);
        userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        for (int i = 0; i < userArray.size(); i++) {

            userModel.addElement(userArray.get(i));

        }

        setLayout(new BorderLayout());

        bookList.getSelectionModel().addListSelectionListener(
                new Listener()
        );

        userList.getSelectionModel().addListSelectionListener(
                new Admin.Listener()
        );

        setLayout(new BorderLayout());
        Panel rightPanel = new Panel(new FlowLayout());
        Panel downPanel = new Panel(new FlowLayout());
        Panel leftPanel = new Panel(new FlowLayout());
        add(downPanel, BorderLayout.SOUTH);
        add(rightPanel, BorderLayout.EAST);
        add(leftPanel, BorderLayout.WEST);
        leftPanel.add(addBook);
        leftPanel.add(deleteBook);
        leftPanel.add(deleteUser);
        rightPanel.add(userSp);
        add(bookSp, BorderLayout.CENTER);

        add(searchTxt, BorderLayout.NORTH);

        bookSp.setViewportView(bookList);
        userSp.setViewportView(userList);
        
        deleteUser.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                try {
                    Class.forName("com.mysql.jdbc.Driver");
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
                }
                Connection con = null;
                try {
                    con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/books", "root", "");
                } catch (SQLException ex) {
                    Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
                }

                PreparedStatement st = null;
                try {
                    st = con.prepareStatement("DELETE FROM new_user WHERE username = '" + adminSelected + "'");
                } catch (SQLException ex) {
                    Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    st.executeUpdate();
                } catch (SQLException ex) {
                    Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        });

        deleteUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the contents of the JTextArea component.

                userModel.removeElement(adminSelected);

                userSp.validate();
                userSp.repaint();

                userList.validate();
                userList.repaint();

                rightPanel.validate();
                rightPanel.repaint();

                JOptionPane.showMessageDialog(Admin.this, adminSelected + " is deleted");

            }

        });

        deleteBook.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                try {
                    Class.forName("com.mysql.jdbc.Driver");
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
                }
                Connection con = null;
                try {
                    con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/books", "root", "");
                } catch (SQLException ex) {
                    Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
                }
                PreparedStatement st2 = null;
                try {
                    st2 = con.prepareStatement("DELETE FROM bx_books WHERE book_title = '" + bookSelected + "'");
                } catch (SQLException ex) {
                    Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    st2.executeUpdate();
                } catch (SQLException ex) {
                    Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
                }

                JOptionPane.showMessageDialog(Admin.this, bookSelected + " is deleted");

            }

        });

        searchTxt.addActionListener((java.awt.event.ActionEvent evt) -> {
            searchTxtActionPerformed(evt);
        });
        searchTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                try {
                    searchTxtKeyReleased(evt);
                } catch (SQLException | ClassNotFoundException ex) {
                    Logger.getLogger(BookList.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        addBook.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                BookAdding add = new BookAdding();
                add.setVisible(true);

            }

        });

    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        Admin admin = new Admin();
        admin.setVisible(true);

    }

    class Listener implements ListSelectionListener {

        public Listener() {

        }

        @Override
        public void valueChanged(ListSelectionEvent e) {

            ListSelectionModel lsm = (ListSelectionModel) e.getSource();

            if (!lsm.isSelectionEmpty()) {

                {

                    bookSelected = bookList.getSelectedValue().toString();
                    adminSelected = userList.getSelectedValue().toString();

                }

            }
        }
    }
}
