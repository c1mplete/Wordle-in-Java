package org.example.Wordle;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class LOL extends JFrame  implements ActionListener {
    LoginPanel loginPanel = new LoginPanel();
    StartPanel startPanel = new StartPanel();
    GamePanel gamePanel = new GamePanel();
    public LOL(){
        startPanel.loginButton.addActionListener(this);
        loginPanel.loginButton.addActionListener(this);
        this.add(loginPanel);
        this.add(gamePanel);
        gamePanel.setVisible(false);
        loginPanel.setVisible(false);
        this.add(startPanel);
        this.setSize(750, 750);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == startPanel.loginButton){
            loginPanel.setVisible(true);
            startPanel.setVisible(false);
        }
        if(e.getSource() == loginPanel.loginButton){
            String psw;
            CheckForUserInDB checkForUserInDB = null;
            try {
                checkForUserInDB = new CheckForUserInDB();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            try {
                psw = checkForUserInDB.lookIfUserExists(loginPanel.usernameTextfield.getText());
                if (psw != null && psw.equals(loginPanel.pswTextfield.getText())) {
                    gamePanel.setVisible(true);
                    loginPanel.setVisible(false);
                } else {
                    loginPanel.wrongLoginLabel.setVisible(true);
                    System.out.println("Wrong username or password");
                }
            } catch (SQLException ex) {
                System.err.println("Database error: " + ex.getMessage());
            }
        }

    }

}
