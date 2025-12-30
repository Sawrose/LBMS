package com.lbms.dao;

import com.lbms.db.DBConnection;
import com.lbms.model.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;

public class UserDAO {

    // Return users object if login succeeds, otherwise null
    public User login(String username, String password) {
        String sql = "SELECT id, username, password_hash, role FROM users WHERE username=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password_hash");
                if (BCrypt.checkpw(password, storedHash)) {
                    return new User(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("role")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insertUserLibrarian(String username, String password, String role) {
        String sql = "INSERT INTO users(username, password_hash, role) VALUES (?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

            ps.setString(1, username);
            ps.setString(2, hashedPassword);
            ps.setString(3, role);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Username already exists!");
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
