package com.sql_connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.microsoft.sqlserver.jdbc.SQLServerStatement;
import com.models.Person;

public class PersonDBConnection extends SQLServerConnection {

    private static final Logger LOG = Logger.getLogger(PersonDBConnection.class.getName());
    private static final int DELETED = 0;

    public PersonDBConnection() {
        super();
    }

    public boolean addPerson(Person person) {

        StringBuilder queryBuilder = new StringBuilder();

        queryBuilder.append("INSERT INTO person (");

        queryBuilder.append("first_name, ");
        queryBuilder.append("last_name, ");
        queryBuilder.append("date_of_birth, ");
        queryBuilder.append("address, ");
        queryBuilder.append("postal_code, ");
        queryBuilder.append("city, ");
        queryBuilder.append("province, ");
        queryBuilder.append("deleted, ");
        queryBuilder.append("created_date, ");
        queryBuilder.append("last_modified");

        queryBuilder.append(") VALUES (");
        queryBuilder.append("?,?,?,?,?,?,?,?,?,?");
        queryBuilder.append(")");

        boolean isSuccess = false;

        try (Connection connection = getConnection();
                PreparedStatement stmt = connection.prepareStatement(queryBuilder.toString())) {

            Date createdDate = new Date(Calendar.getInstance().getTimeInMillis());
            Date sqlDate = Date.valueOf(person.getDateOfBirth());

            stmt.setString(1, person.getFirstName());
            stmt.setString(2, person.getLastName());
            stmt.setDate(3, sqlDate);
            stmt.setString(4, person.getAddress());
            stmt.setString(5, person.getPostalCode());
            stmt.setString(6, person.getCity());
            stmt.setString(7, person.getProvince());
            stmt.setInt(8, DELETED);
            stmt.setDate(9, createdDate);
            stmt.setDate(10, createdDate);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                isSuccess = true;
            }

        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Failed to add Person", e.getMessage());
        }
        return isSuccess;

    }

    public int addPersonAndGetId(Person person) {

        StringBuilder queryBuilder = new StringBuilder();

        queryBuilder.append("INSERT INTO person (");

        queryBuilder.append("first_name, ");
        queryBuilder.append("last_name, ");
        queryBuilder.append("date_of_birth, ");
        queryBuilder.append("address, ");
        queryBuilder.append("postal_code, ");
        queryBuilder.append("city, ");
        queryBuilder.append("province, ");
        queryBuilder.append("deleted, ");
        queryBuilder.append("created_date, ");
        queryBuilder.append("last_modified");

        queryBuilder.append(") VALUES (");
        queryBuilder.append("?,?,?,?,?,?,?,?,?,?");
        queryBuilder.append(")");

        int result = 0;

        try (Connection connection = getConnection();
                PreparedStatement stmt = connection.prepareStatement(queryBuilder.toString(), SQLServerStatement.RETURN_GENERATED_KEYS)) {

            Date createdDate = new Date(Calendar.getInstance().getTimeInMillis());
            Date sqlDate = Date.valueOf(person.getDateOfBirth());

            stmt.setString(1, person.getFirstName());
            stmt.setString(2, person.getLastName());
            stmt.setDate(3, sqlDate);
            stmt.setString(4, person.getAddress());
            stmt.setString(5, person.getPostalCode());
            stmt.setString(6, person.getCity());
            stmt.setString(7, person.getProvince());
            stmt.setInt(8, DELETED);
            stmt.setDate(9, createdDate);
            stmt.setDate(10, createdDate);

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

        } catch (Exception e) {
            e.printStackTrace();
            LOG.log(Level.SEVERE, "Failed to add Person", e.getMessage());
            result = -1;
        }
        return result;
    }
}
