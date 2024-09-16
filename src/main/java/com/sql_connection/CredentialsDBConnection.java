package com.sql_connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.PasswordUtils;
import com.microsoft.sqlserver.jdbc.SQLServerStatement;
import com.models.Credentials;
import com.models.Person;
import com.models.User;

public class CredentialsDBConnection extends SQLServerConnection {

    private static final Logger LOG = Logger.getLogger(CredentialsDBConnection.class.getName());

    public CredentialsDBConnection() {
        super();
    }

    public User getUserCredentials(String username, String password) {
        User user = null;

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT password_hash, salt FROM user_credentials WHERE username = ? ");

        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(queryBuilder.toString())) {

            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String storedHash = resultSet.getString(1);
                String storedSalt = resultSet.getString(2);

                if (PasswordUtils.verifyPassword(password, storedHash, storedSalt)) {
                    user = new User(username, storedHash);
                }
            }

        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Error executing the query", e);
            e.printStackTrace();
        }

        return user;
    }

    public boolean addCredentials(Credentials credentials, Person person) {
        // To be done
        return false;
    }

    public int addCredentialsAndGetId(Credentials credentials, Person person) {
        int result = 0;
        StringBuilder queryBuilder = new StringBuilder();

        String[] hashAndSalt = PasswordUtils.encodePassword(credentials.getPassword());
        if (Arrays.toString(hashAndSalt).isEmpty()) {
            return result = -1;
        }
        String passwordHash = hashAndSalt[0];
        String salt = hashAndSalt[1];

        queryBuilder.append("INSERT INTO user_credentials (");
        queryBuilder.append("username, ");
        queryBuilder.append("password_hash, ");
        queryBuilder.append("person_id, ");
        queryBuilder.append("salt");

        queryBuilder.append(") VALUES (");
        queryBuilder.append("?,?,?,?");
        queryBuilder.append(")");

        try (Connection connection = getConnection();
                PreparedStatement stmt = connection.prepareStatement(queryBuilder.toString(),
                        SQLServerStatement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, credentials.getUsername());
            stmt.setString(2, passwordHash);
            stmt.setInt(3, person.getId());
            stmt.setString(4, salt);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKey = stmt.getGeneratedKeys()) {
                    if (generatedKey.next()) {
                        result = generatedKey.getInt(1);

                    } else {
                        throw new SQLException("Failed to obtain person ID.");
                    }

                }

            }

        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Error adding credentials", e);
            e.printStackTrace();
            result = -1;
        }
        return result;
    }
}
