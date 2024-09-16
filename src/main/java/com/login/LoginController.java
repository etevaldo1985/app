package com.login;

import java.util.logging.Logger;

import com.DialogUtil;
import com.sql_connection.CredentialsDBConnection;

public class LoginController {

    private static final Logger LOG = Logger.getLogger(LoginController.class.getName());

    public LoginController() {

    }

    public void handleLogin(String username, String password) {

        CredentialsDBConnection credentialsDBConnection = new CredentialsDBConnection();

        if (credentialsDBConnection.getUserCredentials(username, password) != null) {

            DialogUtil.showInfoDialog("Success", "Found your user");
        }else {
            DialogUtil.showErrorDialog("Error", "Username or password incorrect or not registered");
        }

        

    }

}
