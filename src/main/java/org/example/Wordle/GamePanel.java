package org.example.Wordle;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePanel extends JPanel {
    int i
    JTextField charFields[][] = new JTextField[6][5];
    JButton submitButton;
    int currentRow = 0;
    String wordToGuess = "";
    GetRandomWordFromDB word = new GetRandomWordFromDB();

    public GamePanel() {
        wordToGuess = word.getRandomWord();
        System.out.println(wordToGuess);

        for (int i = 0; i < charFields.length; i++) {
            for (int j = 0; j < charFields[i].length; j++) {
                charFields[i][j] = new JTextField(1);
                charFields[i][j].setFont(new Font("Arial", Font.PLAIN, 40));
                charFields[i][j].setBounds(0 + (j * 50), 0 + (i * 50), 50, 50);

                // Limit input to a single character and prevent extra characters
                charFields[i][j].getDocument().addDocumentListener(new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        SwingUtilities.invokeLater(() -> {
                            if (e.getDocument().getLength() > 1) {
                                try {
                                    e.getDocument().remove(1, e.getDocument().getLength() - 1);
                                } catch (BadLocationException ex) {
                                    throw new RuntimeException(ex);
                                }
                            }
                        });
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {}

                    @Override
                    public void changedUpdate(DocumentEvent e) {}
                });

                add(charFields[i][j]);
            }
        }

        submitButton = new JButton("Submit");
        submitButton.setBounds(0, 300, 100, 25);
        add(submitButton);
        this.setLayout(new BorderLayout());
        this.setBounds(0, 0, 750, 750);

        // Request focus on the first JTextField after the GUI is fully initialized
        SwingUtilities.invokeLater(() -> charFields[0][0].requestFocus());

        // Add an ActionListener to the Submit button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputtedWord = "";
                for (int i = 0; i < charFields.length; i++) {
                    for (int j = 0; j < charFields[i].length; j++) {
                        inputtedWord += charFields[i][j].getText();
                    }
                }

                compareWords(inputtedWord);
                moveToNextRow();
            }
        });
    }

    private void compareWords(String inputtedWord) {
        for (int i = 0; i < charFields.length; i++) {
            for (int j = 0; j < charFields[i].length; j++) {
                char inputChar = inputtedWord.charAt(j);
                char targetChar = wordToGuess.charAt(j);

                // Only set the background color for the specific text field
                charFields[i][j].setBackground(null); // Reset to default
                if (inputChar == targetChar && ) {
                    charFields[i][j].setBackground(Color.GREEN);
                } else if (wordToGuess.contains(String.valueOf(inputChar))) {
                    charFields[i][j].setBackground(Color.YELLOW);
                } else {
                    charFields[i][j].setBackground(Color.GRAY);
                }
            }
        }
    }


    private void moveToNextRow() {
        // Disable the current row
        for (int j = 0; j < charFields[currentRow].length; j++) {
            charFields[currentRow][j].setEditable(false);
        }

        // Enable the next row
        if (currentRow < charFields.length - 1) {
            currentRow++;
            for (int j = 0; j < charFields[currentRow].length; j++) {
                charFields[currentRow][j].setEditable(true);
            }
        }
    }
}
