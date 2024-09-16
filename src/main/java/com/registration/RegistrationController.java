package com.registration;

import java.time.LocalDate;
import java.util.logging.Logger;

import com.DialogUtil;
import com.models.Credentials;
import com.models.Person;
import com.models.User;
import com.sql_connection.CredentialsDBConnection;
import com.sql_connection.PersonDBConnection;
import com.sql_connection.UserDBConnection;

public class RegistrationController {

    private static final Logger LOG = Logger.getLogger(RegistrationController.class.getName());

    public RegistrationController() {

    }

    public boolean handleRegistration(String username, String password, String fname, String lname,
            LocalDate dateOfBirth,
            String address, String postalCode, String city, String province, String role) {

        Person person = new Person(fname, lname, dateOfBirth, address, postalCode, city, province);
        Credentials credentials = new Credentials(username, password);
        User user = new User(username, role);
        boolean isSuccess = false;

        int personId = registerPerson(person);
        if (personId > 0) {
            person.setId(personId);
            int credentialsId = registerCredentials(credentials, person);
            if (credentialsId > 0) {
                if (registerUser(credentialsId, user, personId)) {
                    isSuccess = true;
                } else {
                    DialogUtil.showErrorDialog("Error", "Error registering user");
                }
            }
        }
        return isSuccess;

    }

    public int registerPerson(Person person) {
        PersonDBConnection personDBConnection = new PersonDBConnection();
        return personDBConnection.addPersonAndGetId(person);

    }

    public int registerCredentials(Credentials credentials, Person person) {
        CredentialsDBConnection credDBConnection = new CredentialsDBConnection();
        return credDBConnection.addCredentialsAndGetId(credentials, person);

    }

    public boolean registerUser(int credentialsId, User user, int personId) {
        UserDBConnection userDBConnection = new UserDBConnection();
        return userDBConnection.addUser(credentialsId, user, personId);

    }

}
