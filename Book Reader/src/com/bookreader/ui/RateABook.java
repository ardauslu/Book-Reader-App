/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bookreader.ui;

import com.bookreader.model.User;
import com.bookreader.helper.Controller;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.bookreader.helper.Controller;
import com.bookreader.model.User;
import com.bookreader.model.UserInteractionListener;
import static com.bookreader.ui.BookList.bookTitleList;
import static com.bookreader.ui.BookList.selected;
import static com.bookreader.ui.Login.passwordLabel;
import static com.bookreader.ui.Login.usernameLabel;
import com.sun.xml.internal.bind.v2.model.core.ID;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author ardau
 */
public class RateABook extends javax.swing.JFrame {

    public ArrayList rateList = new ArrayList();

    public static String rateValue;
    public static int clickSave = 0;

    /**
     * Creates new form RateABook
     */
    public RateABook() {
        initComponents();

        saveRate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the contents of the JTextArea component.
                String a = usernameLabel.getText();
                String b = passwordLabel.getText();

                String idList = null;
                String isbnList = null;

                try {
                    Class.forName("com.mysql.jdbc.Connection");
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(RateABook.class.getName()).log(Level.SEVERE, null, ex);
                }

                Connection con = null;
                try {
                    con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/books", "root", "");
                } catch (SQLException ex) {
                    Logger.getLogger(RateABook.class.getName()).log(Level.SEVERE, null, ex);
                }

                PreparedStatement stmt4 = null;
                try {
                    stmt4 = con.prepareStatement("SELECT userid FROM new_user WHERE username=? AND password =?");
                } catch (SQLException ex) {
                    Logger.getLogger(RateABook.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    stmt4.setString(1, a);
                } catch (SQLException ex) {
                    Logger.getLogger(RateABook.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    stmt4.setString(2, b);
                } catch (SQLException ex) {
                    Logger.getLogger(RateABook.class.getName()).log(Level.SEVERE, null, ex);
                }
                ResultSet rs4 = null;
                try {
                    rs4 = stmt4.executeQuery();
                } catch (SQLException ex) {
                    Logger.getLogger(RateABook.class.getName()).log(Level.SEVERE, null, ex);
                }

                try {
                    while (rs4.next()) {

                        idList = rs4.getString("userid");

                    }
                } catch (SQLException ex) {
                    Logger.getLogger(RateABook.class.getName()).log(Level.SEVERE, null, ex);
                }

                PreparedStatement stmt5 = null;
                try {
                    stmt5 = con.prepareStatement("SELECT * FROM bx_books WHERE book_title = ?");
                } catch (SQLException ex) {
                    Logger.getLogger(RateABook.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    stmt5.setString(1, selected);
                } catch (SQLException ex) {
                    Logger.getLogger(RateABook.class.getName()).log(Level.SEVERE, null, ex);
                }
                ResultSet rs5 = null;
                try {
                    rs5 = stmt5.executeQuery();
                } catch (SQLException ex) {
                    Logger.getLogger(RateABook.class.getName()).log(Level.SEVERE, null, ex);
                }

                try {
                    while (rs5.next()) {

                        isbnList = rs5.getString("isbn");

                    }

                    int rateSpin = (int) rateSpinner.getValue();

                    String query = "INSERT INTO bx_book_ratings values ('" + idList + "','" + isbnList + "','" + rateSpin + "')";
                    try (Statement statement = con.createStatement()) {
                        statement.executeUpdate(query);
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(RateABook.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        buttonGroup4 = new javax.swing.ButtonGroup();
        rateSpinner = new javax.swing.JSpinner();
        rateTxt = new javax.swing.JLabel();
        saveRate = new javax.swing.JButton();
        ratedBooksLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        rateTxt.setText("Please rate selected book and save");

        saveRate.setText("Save");
        saveRate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                saveRateMouseClicked(evt);
            }
        });

        ratedBooksLabel.setText("Rated Books:");
        ratedBooksLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ratedBooksLabelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rateTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ratedBooksLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(rateSpinner)
                    .addComponent(saveRate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rateSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rateTxt))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(saveRate)
                        .addContainerGap(23, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ratedBooksLabel)
                        .addContainerGap())))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void saveRateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveRateMouseClicked

        clickSave++;
        ratedBooksLabel.setText("Rated Books:" + clickSave);

        if (clickSave == 10) {

            ratedBooksLabel.setText("Now you rated 10 books!You can read as your wish...");

        }


    }//GEN-LAST:event_saveRateMouseClicked

    private void ratedBooksLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ratedBooksLabelMouseClicked


    }//GEN-LAST:event_ratedBooksLabelMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(RateABook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RateABook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RateABook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RateABook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RateABook().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.JSpinner rateSpinner;
    private javax.swing.JLabel rateTxt;
    private javax.swing.JLabel ratedBooksLabel;
    private javax.swing.JButton saveRate;
    // End of variables declaration//GEN-END:variables
}
