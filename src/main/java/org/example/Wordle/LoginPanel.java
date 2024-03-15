package org.example.Wordle;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {
    JTextField usernameTextfield, pswTextfield;
    JLabel usernameLabel, pswLabel, wrongLoginLabel;
    JButton loginButton;
    public LoginPanel(){

            usernameTextfield = new JTextField();
            usernameTextfield.setBounds(225, 150, 150, 50);
            usernameLabel = new JLabel("Username");
            usernameLabel.setBounds(125, 150, 100, 50);
            usernameLabel.setLabelFor(usernameTextfield);
            usernameLabel.setForeground(Color.WHITE);

            pswTextfield = new JTextField();
            pswTextfield.setBounds(225, 310, 150, 50);
            pswLabel = new JLabel("Password");
            pswLabel.setBounds(125, 310, 150, 50);
            pswLabel.setLabelFor(pswTextfield);
            pswLabel.setForeground(Color.WHITE);


            loginButton = new JButton("Login");
            loginButton.setBounds(225, 375, 100, 25);

            wrongLoginLabel = new JLabel("Wrong username or password");
            wrongLoginLabel.setBounds(225, 400, 200, 50);
            wrongLoginLabel.setForeground(Color.RED);
            wrongLoginLabel.setVisible(false);

            this.setLayout(null);
            this.setBackground(Color.BLACK);
            this.add(usernameTextfield);
            this.add(wrongLoginLabel);
            this.add(pswTextfield);
            this.add(loginButton);
            this.add(pswLabel);
            this.add(usernameLabel);
            this.setBounds(0,0,750, 750);


    }

}
