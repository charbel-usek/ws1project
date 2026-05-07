package com.example.mini_project2.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private void openEmployees(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/example/mini_project2/employees.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Employees Page");
        stage.show();
    }

    @FXML
    private void openCustomers(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/example/mini_project2/customers.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Customers Page");
        stage.show();
    }

    @FXML
    private void openOrders(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/example/mini_project2/orders.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Orders Page");
        stage.show();
    }
    @FXML
    private void openProducts(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/example/mini_project2/products.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Products Page");
        stage.show();
    }
}