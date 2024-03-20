package org.example.Wordle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GetRandomWordFromDB {



    public GetRandomWordFromDB() {

    }

    public String getRandomWord() {
        String randomWord = "";
        try {
            // Use the singleton instance to get the database connection
            Connection conn = DBConn.getInstance().getConnection();
            if (conn != null) {
                PreparedStatement pstmt = conn.prepareStatement("SELECT word FROM word ORDER BY RANDOM() LIMIT 1");
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    randomWord = rs.getString("word");
                }
                pstmt.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(GetRandomWordFromDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return randomWord;
    }
}
