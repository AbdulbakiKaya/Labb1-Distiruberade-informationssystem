package se.kth.webshop.database_db;

import java.sql.*;

public class UserDAO {

    public boolean register(String username, String password) {
        String sql = "INSERT INTO USERS(username, password) VALUES(?, ?)";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean validate(String username, String password) {
        String sql = "SELECT password FROM USERS WHERE username = ?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return false;
                String stored = rs.getString(1);
                return stored.equals(password);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}