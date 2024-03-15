package org.example.Wordle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartPanel extends JPanel{
    JButton loginButton;
    private JLabel nameLabel;
    public StartPanel(){
        loginButton = new JButton("Login");


        nameLabel = new JLabel("WORDLE");
        nameLabel.setLabelFor(loginButton);

        this.setLayout(new GridBagLayout());
        this.setBackground(Color.BLACK);
        this.add(loginButton);
        this.add(nameLabel);
        this.setBounds(0,0,750, 750);
    }

}
