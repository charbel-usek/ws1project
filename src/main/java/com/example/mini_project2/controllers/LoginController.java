package com.example.mini_project2.controllers;

import com.example.mini_project2.db.AccountStore;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Text errorText;

    private final AccountStore accountStore = new AccountStore();

    @FXML
    protected void handleLogin() throws IOException {
        String username = emailField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            errorText.setText("Please fill in all required fields.");
            return;
        }

        boolean ok = accountStore.isValidLogin(username, password);
        if (ok) {
            openHomePage(username);
        } else {
            errorText.setText("Invalid username or password.");
        }
    }

    @FXML
    private void openRegister() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/mini_project2/register.fxml"));
        Stage stage = (Stage) emailField.getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
        stage.setTitle("Register");
        stage.show();
    }

    private void openHomePage(String username) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/mini_project2/home.fxml"));
        Stage stage = (Stage) emailField.getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
        stage.setTitle("Home Page - Welcome " + username);
        stage.show();
    }
}
