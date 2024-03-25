package org.example.Wordle;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel {
    String word2;
    JTextField charFields[][] = new JTextField[6][5];
    JButton submitButton;
    int currentRow = 0;
    String wordToGuess = "";
    GetRandomWordFromDB word = new GetRandomWordFromDB();
    List<Character> wordToGuessChars = new ArrayList<>();
    boolean[] rowLocked = new boolean[6];

    public GamePanel() {
        generateNewWord(); // Initial word generation

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
                    public void removeUpdate(DocumentEvent e) {
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                    }
                });

                add(charFields[i][j]);
            }
            // Initially, only the first row is unlocked
            if (i != 0) {
                lockRow(i);
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
                handleSubmission();
            }
        });
    }

    private void handleSubmission() {
        String inputWord = "";
        for (int i = 0; i < charFields[currentRow].length; i++) {
            inputWord += charFields[currentRow][i].getText();
        }

        // Check if the input word matches the word to guess
        if (inputWord.equals(wordToGuess)) {
            // Clear text fields
            for (int i = 0; i < charFields[currentRow].length; i++) {
                charFields[currentRow][i].setText("");
            }
            // Redraw the UI with a new word
            redrawUI();
            // Reset current row
            currentRow = 0;
            // Reset UI
            resetUI();
        }

        // Move to the next row if not at the last row
        if (currentRow < 5) {
            currentRow++;
        } else {
            // Check if all rows are locked
            boolean allRowsLocked = true;
            for (boolean locked : rowLocked) {
                if (!locked) {
                    allRowsLocked = false;
                    break;
                }
            }
            if (allRowsLocked) {
                gameOver();
            }
        }
    }



    private void unlockRow(int row) {
        for (int i = 0; i < charFields[row].length; i++) {
            charFields[row][i].setEnabled(true); // Enable text fields in the row
        }
    }

    private void lockRow(int row) {
        for (int i = 0; i < charFields[row].length; i++) {
            charFields[row][i].setEnabled(false); // Disable text fields in the row
        }
    }
    private void generateNewWord() {
        String newWord = null;
        do {
            newWord = word.getRandomWord();
            System.out.println(newWord);
        } while (newWord.equals(wordToGuess)); // Repeat until a different word is generated
        wordToGuess = newWord;
        System.out.println(wordToGuess);
        wordToGuessChars.clear();
        for (char c : wordToGuess.toCharArray()) {
            wordToGuessChars.add(c);
        }
    }

    private void resetUI() {
        // Clear background colors of text fields
        for (int i = 0; i < charFields.length; i++) {
            for (int j = 0; j < charFields[i].length; j++) {
                charFields[i][j].setBackground(null);
            }
        }
        // Lock all rows except the first one
        for (int i = 0; i < rowLocked.length; i++) {
            if (i == 0) {
                rowLocked[i] = false;
                unlockRow(i);
            } else {
                rowLocked[i] = true;
                lockRow(i);
            }
        }
    }

    private void redrawUI() {
        // Clear all text fields
        for (int i = 0; i < charFields.length; i++) {
            for (int j = 0; j < charFields[i].length; j++) {
                charFields[i][j].setText("");
            }
        }
        // Clear backgrounds of text fields
        for (int i = 0; i < charFields[currentRow].length; i++) {
            charFields[currentRow][i].setBackground(null);
        }
    }


    private void gameOver() {
        JOptionPane.showMessageDialog(null,
                "Game Over! The correct word was: " + wordToGuess,
                "Game Over", JOptionPane.INFORMATION_MESSAGE);
        resetUI();
        generateNewWord();
        currentRow = 0;
    }
}



