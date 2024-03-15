package org.example.Wordle;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckForUserInDB {
    DBConn db = DBConn.getInstance();
    ResultSet rs;

    public CheckForUserInDB() throws SQLException {
    }

    public String lookIfUserExists(String username) throws SQLException {
        String sql = "SELECT psw FROM userdata WHERE username LIKE ?";
        PreparedStatement stmt = db.getConnection().prepareStatement(sql);
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getString("psw");
        }
        return null;
    }



}
