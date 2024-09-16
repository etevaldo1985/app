package com.login;

import com.jfoenix.controls.JFXButton;
import com.registration.RegistrationScreen;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class LoginWindow extends Application {

    private Stage primaryStage;
    private LoginController loginController = new LoginController();
    private RegistrationScreen registrationScreen = new RegistrationScreen(new Stage());

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        showLoginScreen();
    }

    public void showLoginScreen() {
        VBox vbox = new VBox(15);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER);

        Label title = new Label("Login");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333;");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #ccc;");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #ccc;");

        JFXButton loginButton = new JFXButton("Login");
        loginButton.setStyle(
                "-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10 20;");

        loginButton.setOnAction(e -> loginController.handleLogin(usernameField.getText(), passwordField.getText()));

        // Register button
        JFXButton registerButton = new JFXButton("Register");
        registerButton.setStyle(
                "-fx-background-color: #FF9800; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10 20;");

        registerButton.setOnAction(e -> registrationScreen.showRegistrationScreen());

        vbox.getChildren().addAll(title, usernameField, passwordField, loginButton, registerButton);

        Scene scene = new Scene(vbox, 400, 250);
        scene.setFill(Color.WHITE);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Login");
        primaryStage.show();
    }
}
