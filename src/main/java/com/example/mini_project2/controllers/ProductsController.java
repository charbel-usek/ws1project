package com.example.mini_project2.controllers;

import com.example.mini_project2.models.Product;
import com.example.mini_project2.models.ProductStore;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class ProductsController {

    @FXML private TextField nameField, brandField, descField, quantityField, priceField, stockField;
    @FXML private ComboBox<String> categoryBox;
    @FXML private TableView<Product> tableView;
    @FXML private TableColumn<Product, String> nameCol, brandCol, categoryCol;
    @FXML private TableColumn<Product, Double> priceCol;
    @FXML private TableColumn<Product, Integer> quantityCol, stockCol;
    @FXML private ImageView imageView;
    @FXML private Label errorLabel;

    private final ProductStore store = new ProductStore();
    private String selectedImagePath = null;

    @FXML
    public void initialize() {
        categoryBox.setItems(FXCollections.observableArrayList("Electronics", "Sports", "Home", "Books", "Fashion"));
        tableView.setItems(store.getProductList());

        nameCol.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getName()));
        brandCol.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getBrand()));
        categoryCol.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getCategory()));
        quantityCol.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(c.getValue().getQuantity()).asObject());
        stockCol.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(c.getValue().getStock()).asObject());
        priceCol.setCellValueFactory(c -> new javafx.beans.property.SimpleDoubleProperty(c.getValue().getPrice()).asObject());

        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) showProduct(newSel);
        });
    }

    private void showProduct(Product p) {
        nameField.setText(p.getName());
        brandField.setText(p.getBrand());
        descField.setText(p.getDescription());
        categoryBox.setValue(p.getCategory());
        quantityField.setText(String.valueOf(p.getQuantity()));
        priceField.setText(String.valueOf(p.getPrice()));
        stockField.setText(String.valueOf(p.getStock()));

        if (p.getImagePath() != null && !p.getImagePath().isEmpty()) {
            imageView.setImage(new Image("file:" + p.getImagePath()));
        } else {
            imageView.setImage(null);
        }
    }

    @FXML
    private void handleChooseImage() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Choose Product Image");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File file = chooser.showOpenDialog(null);
        if (file != null) {
            selectedImagePath = file.getAbsolutePath();
            imageView.setImage(new Image("file:" + selectedImagePath));
        }
    }

    @FXML
    private void handleAdd() {
        try {
            String name = nameField.getText();
            String brand = brandField.getText();
            String category = categoryBox.getValue();
            String desc = descField.getText();
            int quantity = Integer.parseInt(quantityField.getText());
            double price = Double.parseDouble(priceField.getText());
            int stock = Integer.parseInt(stockField.getText());

            if (name.isEmpty() || category == null) {
                errorLabel.setText("Please fill in all required fields!");
                return;
            }

            Product p = new Product(name, brand, category, desc, quantity, price, stock, selectedImagePath);
            store.addProduct(p);
            errorLabel.setText("");
            clearFields();

        } catch (Exception e) {
            errorLabel.setText("Invalid input data!");
        }
    }

    @FXML
    private void handleUpdate() {
        Product selected = tableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            errorLabel.setText("Select a product to update!");
            return;
        }

        try {
            Product newP = new Product(
                    selected.getId(),
                    nameField.getText(),
                    brandField.getText(),
                    categoryBox.getValue(),
                    descField.getText(),
                    Integer.parseInt(quantityField.getText()),
                    Double.parseDouble(priceField.getText()),
                    Integer.parseInt(stockField.getText()),
                    selectedImagePath != null ? selectedImagePath : selected.getImagePath()
            );
            store.updateProduct(selected, newP);
            errorLabel.setText("");
            clearFields();

        } catch (Exception e) {
            errorLabel.setText("Invalid input for update!");
        }
    }

    @FXML
    private void handleDelete() {
        Product selected = tableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            errorLabel.setText("Select a product to delete!");
            return;
        }
        store.deleteProduct(selected);
        clearFields();
        errorLabel.setText("");
    }

    @FXML
    private void goHome(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/mini_project2/home.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Home Page");
        stage.show();
    }

    private void clearFields() {
        nameField.clear();
        brandField.clear();
        descField.clear();
        categoryBox.getSelectionModel().clearSelection();
        quantityField.clear();
        priceField.clear();
        stockField.clear();
        imageView.setImage(null);
        selectedImagePath = null;
    }
}