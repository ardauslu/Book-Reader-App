/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bookreader.ui;

import static com.bookreader.ui.Login.passwordLabel;
import static com.bookreader.ui.Login.usernameLabel;
import static com.bookreader.ui.ReadingTheBook.window;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
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
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.*;
import javax.swing.border.BevelBorder;

public class BookList extends JFrame {

    public static ArrayList<String> bookTitleList = new ArrayList<>();

    private JLabel label = new JLabel();
    private JTextField searchTxt = new JTextField();
    private JScrollPane sp = new JScrollPane();
    private JScrollPane sp2 = new JScrollPane();
    private JScrollPane sp3 = new JScrollPane();
    private JButton rate = new JButton("Rate The Book");
    private JButton lastAdded = new JButton("Last Added 5 Books!");
    private Label headerLabel = new Label();
    DefaultListModel defaultListModel = new DefaultListModel();
    DefaultListModel model = new DefaultListModel();
    DefaultListModel avgModel = new DefaultListModel();
    JList l = new JList(defaultListModel);
    public static JButton read = new JButton("Read The Selected Book");
    public static ReadingTheBook x = new ReadingTheBook();
    public static String selected;
    public static boolean pages = false;
    private JList popularJList = new JList(model);
    private JList averageJList = new JList(avgModel);

    public static boolean isNumeric(String strNum) {
        return strNum.matches("[A-Za-z0-9]+");
    }

    private void searchTxtActionPerformed(java.awt.event.ActionEvent evt) {
        searchTxtActionPerformed(evt);
    }

    private void myJListMouseClicked(java.awt.event.MouseEvent evt) {

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
        l.setModel(defaultListModel);

    }

    private void bindData() throws SQLException, ClassNotFoundException {
        getStars().stream().forEach((star) -> {
            defaultListModel.addListDataListener((ListDataListener) star);
        });

        l.setModel(defaultListModel);
        l.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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

    public BookList() throws ClassNotFoundException, SQLException, MalformedURLException, IOException {

        ArrayList isbnBooks = new ArrayList();
        Class.forName("com.mysql.jdbc.Connection");
        Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/books", "root", "");

        Statement stmt = (Statement) con.createStatement();

        ResultSet rs = stmt.executeQuery("SELECT book_title,isbn FROM bx_books");

        while (rs.next()) {
            String bookName = rs.getString("book_title");
            bookTitleList.add(bookName);
            String is = rs.getString("isbn");
            isbnBooks.add(is);

        }

        setLayout(new BorderLayout());

        l.getSelectionModel().addListSelectionListener(
                new Listener()
        );

        ArrayList pop = new ArrayList();
        ArrayList countPop = new ArrayList();
        ArrayList tenPopular = new ArrayList();

        Statement stmtx = (Statement) con.createStatement();

        ResultSet rsx = stmtx.executeQuery("SELECT isbn ,COUNT(isbn) AS co FROM bx_book_ratings GROUP BY isbn ORDER BY COUNT(isbn) DESC limit 11");

        while (rsx.next()) {
            String isbnValue = rsx.getString("isbn");
            Integer c = rsx.getInt("co");
            pop.add(isbnValue);
            countPop.add(c);

        }

        int j = 0;
        int i = 0;
        for (j = 0; j < isbnBooks.size(); j++) {

            for (i = 0; i < pop.size(); i++) {

                if (isbnBooks.get(j).equals(pop.get(i))) {

                    tenPopular.add(bookTitleList.get(j));

                }

            }

        }

        popularJList.add(sp2);
        model.addElement(tenPopular.get(0));
        model.addElement(tenPopular.get(1));
        model.addElement(tenPopular.get(2));
        model.addElement(tenPopular.get(3));
        model.addElement(tenPopular.get(4));
        model.addElement(tenPopular.get(5));
        model.addElement(tenPopular.get(6));
        model.addElement(tenPopular.get(7));
        model.addElement(tenPopular.get(8));
        model.addElement(tenPopular.get(9));

        ArrayList bestAv = new ArrayList();
        ArrayList average = new ArrayList();

        Statement stmty = (Statement) con.createStatement();

        ResultSet rsy = stmty.executeQuery("SELECT isbn,AVG(book_rating) FROM bx_book_ratings GROUP BY isbn ORDER BY AVG(book_rating) DESC limit 30 ");
        int itarator;
        while (rsy.next()) {

            String av = rsy.getString("isbn");
            if (isNumeric(av) == true) {

                average.add(av);

            }

        }

        int b = 0;
        int k = 0;
        int w;
        for (k = 0; k < bookTitleList.size(); k++) {

            for (w = 0; w < average.size(); w++) {

                if (isbnBooks.get(k).equals(average.get(w))) {

                    bestAv.add(bookTitleList.get(k));

                    
                }

            }

        }

       

        averageJList.add(sp3);

        avgModel.addElement(bestAv.get(0));
        avgModel.addElement(bestAv.get(1));
        avgModel.addElement(bestAv.get(2));
        avgModel.addElement(bestAv.get(3));
        avgModel.addElement(bestAv.get(4));
        avgModel.addElement(bestAv.get(5));
        avgModel.addElement(bestAv.get(6));
        avgModel.addElement(bestAv.get(7));
        avgModel.addElement(bestAv.get(8));
        avgModel.addElement(bestAv.get(9));

        read.setFont(new Font("Showcard Gothic", Font.BOLD | Font.ITALIC, 24));
        read.setBackground(Color.LIGHT_GRAY);
        read.setForeground(Color.BLACK);

        rate.setFont(new Font("Showcard Gothic", Font.BOLD | Font.ITALIC, 24));
        rate.setBackground(Color.LIGHT_GRAY);
        rate.setForeground(Color.BLACK);
        
        lastAdded.setFont(new Font("Showcard Gothic", Font.BOLD | Font.ITALIC, 24));
        lastAdded.setBackground(Color.LIGHT_GRAY);
        lastAdded.setForeground(Color.BLACK);
        
        
        JLabel tenBest = new JLabel("Highest Rated Books");
        tenBest.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        JLabel mostPopular = new JLabel("Most Popular Books");
        mostPopular.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        Panel rightPanel = new Panel(new FlowLayout());
        Panel centerPanel = new Panel(new FlowLayout());
        add(centerPanel, BorderLayout.SOUTH);
        add(rightPanel, BorderLayout.EAST);
        centerPanel.add(usernameLabel);
        centerPanel.add(read);
        rightPanel.add(mostPopular);
        rightPanel.add(tenBest);
        centerPanel.add(lastAdded);
        centerPanel.add(rate);
      
        rightPanel.add(popularJList);
        rightPanel.add(averageJList);
        add(sp, BorderLayout.CENTER);
        add(label,BorderLayout.WEST);
        add(searchTxt, BorderLayout.NORTH);
        
        sp.setViewportView(l);

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

        ArrayList imageLargeList = new ArrayList();

        Class.forName("com.mysql.jdbc.Connection");

        Statement stmt1 = (Statement) con.createStatement();

        ResultSet rs1 = stmt1.executeQuery("SELECT image_url_l FROM bx_books");

        while (rs1.next()) {
            String imageS = rs1.getString("image_url_l");
            imageLargeList.add(imageS);

        }

        BufferedImage img = ImageIO.read(new URL((String) imageLargeList.get(1)));

        label.setIcon(new ImageIcon(img));

        read.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                window.setVisible(pages);

            }

        });

        lastAdded.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                
            LastAdded fiveBooks = null;
                try {
                    fiveBooks = new LastAdded();
                } catch (SQLException ex) {
                    Logger.getLogger(BookList.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(BookList.class.getName()).log(Level.SEVERE, null, ex);
                }
            fiveBooks.setVisible(true);
                
                

            }

        });
        
        
        
        
        rate.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                RateABook rate = new RateABook();
                rate.setVisible(true);

            }

        });

        read.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the contents of the JTextArea component.

                String a = usernameLabel.getText();
                String b = passwordLabel.getText();
                String idList = null;
                ArrayList userId = new ArrayList();
                ArrayList count = new ArrayList();

                com.mysql.jdbc.Connection conn = null;
                try {
                    conn = (com.mysql.jdbc.Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/books", "root", "");
                } catch (SQLException ex) {
                    Logger.getLogger(BookList.class.getName()).log(Level.SEVERE, null, ex);
                }

                com.mysql.jdbc.Statement statement = null;
                try {
                    statement = (com.mysql.jdbc.Statement) conn.createStatement();
                } catch (SQLException ex) {
                    Logger.getLogger(BookList.class.getName()).log(Level.SEVERE, null, ex);
                }
                ResultSet resultSet = null;
                try {
                    resultSet = statement.executeQuery("SELECT user_id,COUNT(user_id) AS co FROM bx_book_ratings GROUP BY user_id");
                } catch (SQLException ex) {
                    Logger.getLogger(BookList.class.getName()).log(Level.SEVERE, null, ex);
                }

                try {
                    while (resultSet.next()) {
                        String id = resultSet.getString("user_id");
                        int ct = resultSet.getInt("co");
                        userId.add(id);
                        count.add(ct);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(BookList.class.getName()).log(Level.SEVERE, null, ex);
                }

                PreparedStatement stmt6 = null;
                try {
                    stmt6 = con.prepareStatement("SELECT userid FROM new_user WHERE username=? AND password =?");
                } catch (SQLException ex) {
                    Logger.getLogger(RateABook.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    stmt6.setString(1, a);
                } catch (SQLException ex) {
                    Logger.getLogger(RateABook.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    stmt6.setString(2, b);
                } catch (SQLException ex) {
                    Logger.getLogger(RateABook.class.getName()).log(Level.SEVERE, null, ex);
                }
                ResultSet rs6 = null;
                try {
                    rs6 = stmt6.executeQuery();
                } catch (SQLException ex) {
                    Logger.getLogger(RateABook.class.getName()).log(Level.SEVERE, null, ex);
                }

                try {
                    while (rs6.next()) {

                        idList = rs6.getString("userid");
                        System.out.println("" + idList);

                    }
                } catch (SQLException ex) {
                    Logger.getLogger(RateABook.class.getName()).log(Level.SEVERE, null, ex);
                }

                int i = 0;

                for (i = 0; i < userId.size() - 1; i++) {

                    if ((int) count.get(i) >= 10 && userId.get(i).equals(idList)) {

                        pages = true;
                        window.setVisible(pages);

                    } else {

                        read.setText("You havent rated 10 books!");

                    }

                }

            }
        });

    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                BookList app = null;
                try {
                    app = new BookList();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(BookList.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(BookList.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(BookList.class.getName()).log(Level.SEVERE, null, ex);
                }
                app.setSize((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth()), (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight()));
                app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                app.setVisible(true);
            }
        });
    }

    class Listener implements ListSelectionListener {

        public Listener() {

        }

        @Override
        public void valueChanged(ListSelectionEvent e) {

            ListSelectionModel lsm = (ListSelectionModel) e.getSource();

            if (!lsm.isSelectionEmpty()) {

                try {

                    selected = l.getSelectedValue().toString();

                    ArrayList bookImage = new ArrayList();
                    ArrayList bookList = new ArrayList();
                    ArrayList ISBNList = new ArrayList();
                    Class.forName("com.mysql.jdbc.Connection");

                    Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/books", "root", "");

                    Statement stmt = (Statement) con.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM bx_books");

                    while (rs.next()) {
                        String bookTitle = rs.getString("book_title");
                        String imageURL = rs.getString("image_url_l");
                        String isbnList = rs.getString("isbn");
                        bookList.add(bookTitle);
                        bookImage.add(imageURL);
                        ISBNList.add(isbnList);
                    }

                    int i;

                    {

                        for (i = 0; i < bookList.size() - 1; i++) {

                            String where = (String) bookList.get(i);

                            if (where.equals(selected)) {
                                BufferedImage img = ImageIO.read(new URL((String) bookImage.get(i)));

                                label.setIcon(new ImageIcon(img));

                                System.out.println("" + selected);
                                break;
                            }

                        }

                    }

                    System.out.println("" + ISBNList.get(1));

                } catch (ClassNotFoundException | SQLException | IOException ex) {
                    Logger.getLogger(Listener.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        }

    }

}
