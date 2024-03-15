package org.example.Wordle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WordDatabase {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/wordle";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "admin";

    public static void main(String[] args) {
        try {
            addWordsFromFileToDatabase("C:\\Users\\KevTom\\Desktop\\Pong\\wordle2.0\\src\\main\\java\\org\\example\\Wordle/words.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addWordsFromFileToDatabase(String filePath) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        BufferedReader br = null;

        try {
            // Load the database driver
            Class.forName("org.postgresql.Driver");

            // Establish a connection to the database
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Prepare the SQL statement for inserting a word
            String sql = "INSERT INTO word (word) VALUES (?)";
            pstmt = conn.prepareStatement(sql);

            // Open the file and read it line by line
            br = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = br.readLine()) != null) {
                // Assuming each line contains a single word
                pstmt.setString(1, line.trim());
                pstmt.executeUpdate();
            }

            // Close resources
            br.close();
            pstmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Ensure resources are closed in case of an exception
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

