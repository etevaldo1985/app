package com;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

import java.util.Optional;

import com.jfoenix.controls.JFXButton;

public class DialogUtil {

    private DialogUtil() {
        throw new UnsupportedOperationException("This is an utility class and should be instantiated");
    }

    /**
     * Shows an information dialog
     *
     * @param title   The title of the dialog
     * @param message The message being exhibited
     */
    public static void showInfoDialog(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Shows an error dialog
     *
     * @param title   The title of the dialog
     * @param message The message being exhibited
     */
    public static void showErrorDialog(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Shows a confirmation dialog
     *
     *
     * @param title   The title of the dialog
     * @param message The message being exhibited
     * @return true if user clicks OK, false other way
     */
    public static boolean showConfirmationDialog(String title, String message) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        ButtonType okButton = new ButtonType("OK");
        ButtonType cancelButton = new ButtonType("Cancel");
        alert.getButtonTypes().setAll(okButton, cancelButton);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == okButton;
    }

    /**
     * Shows a warning dialog
     *
     * @param title   The title of the dialog
     * @param message The message being exhibited
     */
    public static void showWarningDialog(String title, String message) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText("Attention");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static TextField createTextField(String promptText) {
        TextField textField = new TextField();
        textField.setPromptText(promptText);
        textField.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #ccc;");
        return textField;
    }

    public static VBox createLabeledTextField(String labelText) {
        Label label = new Label(labelText);
        TextField textField = new TextField();
        textField.setPromptText(labelText);
        textField.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #ccc;");
        textField.setMinWidth(200);
        textField.setMaxWidth(Double.MAX_VALUE);
        return new VBox(5, label, textField);
    }

    public static VBox createLabeledPasswordField(String labelText) {
        Label label = new Label(labelText);
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText(labelText);
        passwordField.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #ccc;");
        passwordField.setMinWidth(200);
        passwordField.setMaxWidth(Double.MAX_VALUE);
        return new VBox(5, label, passwordField);
    }

    public static PasswordField createPasswordField(String promptText) {
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText(promptText);
        passwordField.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #ccc;");
        return passwordField;
    }

    public static ComboBox<String> createComboBox(String promptText, String... items) {
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(items);
        comboBox.setPromptText(promptText);
        return comboBox;
    }

    public static VBox createLabelComboBox(String promptText, String... items) {
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(items);
        comboBox.setPromptText(promptText);
        Label roleLabel = new Label(promptText);
        
        VBox roleBox = new VBox(5, roleLabel, comboBox);
        roleBox.setAlignment(Pos.CENTER_LEFT);
        return roleBox;
    }

    public static Label createTitle(String titleText) {
        Label title = new Label(titleText);
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333;");
        return title;
    }
    

    public static JFXButton createButton(String buttonText) {
        JFXButton button = new JFXButton(buttonText);
        button.setStyle(
                "-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10 20;");
        return button;
    }
}
