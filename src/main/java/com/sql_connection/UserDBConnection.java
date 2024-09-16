package com.sql_connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.models.User;

public class UserDBConnection extends SQLServerConnection {

    private static final Logger LOG = Logger.getLogger(UserDBConnection.class.getName());

    public boolean addUser(int userCredentialsId, User user, int personId) {
        StringBuilder queryBuilder = new StringBuilder();

        queryBuilder.append("INSERT INTO user_info (");

        queryBuilder.append("user_credentials_id, ");
        queryBuilder.append("username, ");
        queryBuilder.append("role, ");
        queryBuilder.append("person_id");

        queryBuilder.append(") VALUES (");
        queryBuilder.append("?,?,?,?");
        queryBuilder.append(")");

        boolean isSuccess = false;
        try (Connection connection = getConnection();
                PreparedStatement stmt = connection.prepareStatement(queryBuilder.toString())) {

            stmt.setInt(1, userCredentialsId);
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getRole());
            stmt.setInt(4, personId);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                isSuccess = true;
                LOG.log(Level.INFO, () -> "User added. Rows affected: " + rowsAffected);
            } else {
                LOG.log(Level.INFO, () -> " No user added. Rows affected: " + rowsAffected);
            }

        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Error while adding user", e.getMessage());
            e.printStackTrace();
            isSuccess = false;
        }
        return isSuccess;
    }

    public void getUserById(int userId) {
        String sql = "SELECT * FROM user WHERE user_id = ?";

        try (Connection connection = getConnection(); // Usa a conexão da classe base
                PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, userId);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {

                System.out.println("User ID: " + rs.getInt("user_id"));
                System.out.println("Username: " + rs.getString("username"));
                System.out.println("Role: " + rs.getString("role"));
                System.out.println("Person ID: " + rs.getInt("person_id"));
            }

        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Error while getting user", e.getMessage());
            e.printStackTrace();
        }
    }
}
