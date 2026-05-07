package com.example.mini_project2.controllers;

import com.example.mini_project2.db.AccountStore;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmField;

    @FXML
    private Label msgLabel;

    private final AccountStore accountStore = new AccountStore();

    @FXML
    private void handleRegister() {
        String u = usernameField.getText().trim();
        String p = passwordField.getText().trim();
        String c = confirmField.getText().trim();

        if (u.isEmpty() || p.isEmpty() || c.isEmpty()) {
            msgLabel.setText("Please fill all fields.");
            return;
        }

        if (!p.equals(c)) {
            msgLabel.setText("Passwords do not match.");
            return;
        }

        if (accountStore.userExists(u)) {
            msgLabel.setText("User already exists.");
            return;
        }

        boolean ok = accountStore.registerUser(u, p);
        if (ok) {
            msgLabel.setStyle("-fx-text-fill: green;");
            msgLabel.setText("Registered! Returning to login...");

            try {
                backToLogin();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            msgLabel.setText("Error registering user.");
        }
    }

    @FXML
    private void backToLogin() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/mini_project2/login.fxml"));
        Stage stage = (Stage) usernameField.getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
        stage.setTitle("Login");
        stage.show();
    }
}
