package com.registration;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import com.ValidatorRule;
import com.DialogUtil;
import com.jfoenix.controls.JFXButton;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RegistrationScreen {

        private Stage primaryStage;
        private static final String REGISTER = "Register";
        private RegistrationController registrationController = new RegistrationController();

        public RegistrationScreen(Stage primaryStage) {
                this.primaryStage = primaryStage;
        }

        public void showRegistrationScreen() {
                VBox vbox = new VBox(10);
                vbox.setPadding(new Insets(20));
                vbox.setAlignment(Pos.TOP_LEFT);
                vbox.setSpacing(10);
                vbox.setMinWidth(300);
                vbox.setMaxWidth(Double.MAX_VALUE);

                Label title = DialogUtil.createTitle(REGISTER);

                VBox titleBox = new VBox(5);
                titleBox.setAlignment(Pos.CENTER);
                titleBox.setPadding(new Insets(5,0,0,0));
                titleBox.getChildren().add(title);

                VBox usernameBox = DialogUtil.createLabeledTextField("Username");
                TextField usernameField = (TextField) usernameBox.getChildren().get(1);

                VBox passwordBox = DialogUtil.createLabeledPasswordField("Password");
                PasswordField passwordField = (PasswordField) passwordBox.getChildren().get(1);

                VBox repeatPasswordBox = DialogUtil.createLabeledPasswordField("Repeat Password");
                PasswordField repeatPasswordField = (PasswordField) repeatPasswordBox.getChildren().get(1);

                VBox firstNameBox = DialogUtil.createLabeledTextField("First Name");
                TextField firstNameField = (TextField) firstNameBox.getChildren().get(1);

                VBox lastNameBox = DialogUtil.createLabeledTextField("Last Name");
                TextField lastNameField = (TextField) lastNameBox.getChildren().get(1);

                DatePicker dobPicker = new DatePicker();
                dobPicker.setPromptText("Date of Birth");
                Label dobLabel = new Label("Date of Birth");

                VBox dobBox = new VBox(5, dobLabel, dobPicker);
                dobBox.setAlignment(Pos.CENTER_LEFT);

                VBox addressBox = DialogUtil.createLabeledTextField("Address");
                TextField addressField = (TextField) addressBox.getChildren().get(1);

                VBox postalCodeBox = DialogUtil.createLabeledTextField("Postal Code");
                TextField postalCodeField = (TextField) postalCodeBox.getChildren().get(1);

                VBox cityBox = DialogUtil.createLabeledTextField("City");
                TextField cityField = (TextField) cityBox.getChildren().get(1);

                VBox provinceBox = DialogUtil.createLabeledTextField("Province");
                TextField provinceField = (TextField) provinceBox.getChildren().get(1);

                VBox roleBox = DialogUtil.createLabelComboBox("Role", "Admin", "User", "Guest");
                ComboBox<String> roleComboBox = (ComboBox<String>) roleBox.getChildren().get(1);

                JFXButton registerButton = DialogUtil.createButton(REGISTER);

                registerButton.setOnAction(e -> checkFields(
                                usernameField.getText(),
                                passwordField.getText(),
                                repeatPasswordField.getText(),
                                firstNameField.getText(),
                                lastNameField.getText(),
                                dobPicker.getValue(),
                                addressField.getText(),
                                postalCodeField.getText(),
                                cityField.getText(),
                                provinceField.getText(),
                                roleComboBox.getValue()));

                addValidationListeners(usernameField);
                addValidationListeners(passwordField);
                addValidationListeners(repeatPasswordField);
                addValidationListeners(firstNameField);
                addValidationListeners(lastNameField);
                addValidationListeners(addressField);
                addValidationListeners(postalCodeField);
                addValidationListeners(cityField);
                addValidationListeners(provinceField);

                vbox.getChildren().addAll(
                                usernameBox,
                                passwordBox,
                                repeatPasswordBox,
                                firstNameBox,
                                lastNameBox,
                                dobBox,
                                addressBox,
                                postalCodeBox,
                                cityBox,
                                provinceBox,
                                roleBox,
                                registerButton);

                VBox mainLayout = new VBox(20);
                mainLayout.getChildren().addAll(titleBox, vbox);

                Scene scene = new Scene(mainLayout, 400, 780);
                primaryStage.setScene(scene);
                primaryStage.show();
        }

        private void addValidationListeners(Control control) {
                if (control instanceof TextField) {
                        TextField textField = (TextField) control;
                        textField.textProperty().addListener((obs, oldText, newText) -> validateField(textField));
                } else if (control instanceof PasswordField) {
                        PasswordField passwordField = (PasswordField) control;
                        passwordField.textProperty()
                                        .addListener((obs, oldText, newText) -> validateField(passwordField));
                }
        }

        private void validateField(Control field) {
                if (field instanceof TextField) {
                        TextField textField = (TextField) field;
                        if (textField.getText().trim().isEmpty()) {
                                textField.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: red;");
                        } else {
                                textField.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #ccc;");
                        }
                } else if (field instanceof PasswordField) {
                        PasswordField passwordField = (PasswordField) field;
                        if (passwordField.getText().trim().isEmpty()) {
                                passwordField.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: red;");
                        } else {
                                passwordField.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #ccc;");
                        }
                }
        }

        private void checkFields(String username, String password, String repeatPassword, String firstName,
                        String lastName,
                        LocalDate dob, String address, String postalCode, String city,
                        String province, String role) {

                boolean isValid = validateRegistrationForm(username, password, repeatPassword, firstName, lastName, dob,
                                address, postalCode, city, province, role);

                if (isValid) {
                        boolean isSuccess = registrationController.handleRegistration(username, password, firstName,
                                        lastName, dob, address,
                                        postalCode, city, province, role);
                        if (isSuccess) {
                                DialogUtil.showInfoDialog("Success!", "User has been registered successfully!");
                                primaryStage.close();
                        } else {
                                DialogUtil.showErrorDialog("Registration Error", "User has not been registered");
                        }

                }
        }

        private boolean validateRegistrationForm(String username, String password, String repeatPassword,
                        String firstName,
                        String lastName, LocalDate dob, String address,
                        String postalCode, String city, String province,
                        String role) {

                List<ValidatorRule> validationRules = Arrays.asList(
                                new ValidatorRule(username == null || username.trim().isEmpty(),
                                                "Username cannot be empty"),
                                new ValidatorRule(password == null || password.trim().isEmpty(),
                                                "Password cannot be empty"),
                                new ValidatorRule(repeatPassword == null || repeatPassword.trim().isEmpty(),
                                                "Repeat password cannot be empty"),
                                new ValidatorRule(firstName == null || firstName.trim().isEmpty(),
                                                "First name cannot be empty"),
                                new ValidatorRule(lastName == null || lastName.trim().isEmpty(),
                                                "Last name cannot be empty"),
                                new ValidatorRule(dob == null, "Date of birth must be provided"),
                                new ValidatorRule(address == null || address.trim().isEmpty(),
                                                "Address cannot be empty"),
                                new ValidatorRule(postalCode == null || postalCode.trim().isEmpty(),
                                                "Postal code cannot be empty"),
                                new ValidatorRule(city == null || city.trim().isEmpty(), "City cannot be empty"),
                                new ValidatorRule(province == null || province.trim().isEmpty(),
                                                "Province cannot be empty"),
                                new ValidatorRule(role == null || role.trim().isEmpty(), "Role cannot be empty"),
                                new ValidatorRule(password != null && !password.equals(repeatPassword),
                                                "Passwords do not match"),
                                new ValidatorRule(password != null && password.length() < 8,
                                                "Password must be at least 8 characters long"));

                for (ValidatorRule rule : validationRules) {
                        if (rule.isInvalid()) {
                                DialogUtil.showErrorDialog("Validation Error", rule.getMessage());
                                return false;
                        }
                }

                return true;
        }
}
