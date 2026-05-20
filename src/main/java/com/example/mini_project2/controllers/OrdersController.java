package com.example.mini_project2.controllers;

import com.example.mini_project2.db.OrderDBStore;
import com.example.mini_project2.models.Order;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class OrdersController {

    // ===== UI FIELDS (linked to FXML) =====
    @FXML private TextField sourceField, destinationField, weightField, priceField;
    @FXML private ComboBox<String> deliveryTypeComboBox;
    @FXML private DatePicker orderDatePicker;
    @FXML private Slider estimatedTimeSlider;
    @FXML private Label estimatedTimeLabel, errorLabel;

    // ===== TABLE UI =====
    @FXML private TableView<Order> orderTable;
    @FXML private TableColumn<Order, Integer> idCol;
    @FXML private TableColumn<Order, Double> priceCol;
    @FXML private TableColumn<Order, String> sourceCol;
    @FXML private TableColumn<Order, String> destinationCol;
    @FXML private TableColumn<Order, Double> weightCol;
    @FXML private TableColumn<Order, String> deliveryTypeCol;
    @FXML private TableColumn<Order, LocalDate> orderDateCol;
    @FXML private TableColumn<Order, String> estimatedArrivalCol;

    // Database / storage handler
    private final OrderDBStore orderDBStore = new OrderDBStore();

    // Stores the currently selected order in the table
    private Order selectedOrder;

    // ===== INITIALIZATION (runs automatically when screen loads) =====
    @FXML
    public void initialize() {

        // Fill dropdown with delivery options
        deliveryTypeComboBox.getItems().addAll("Standard", "Express", "Same-Day");

        // Update label when slider moves
        estimatedTimeSlider.valueProperty().addListener((obs, o, n) ->
                estimatedTimeLabel.setText(n.intValue() + " days")
        );

        // ===== TABLE COLUMN SETUP =====
        // Each column is linked to a property in Order

        idCol.setCellValueFactory(d ->
                new SimpleIntegerProperty(d.getValue().getId()).asObject());

        sourceCol.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getSource()));

        destinationCol.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getDestination()));

        weightCol.setCellValueFactory(d ->
                new SimpleDoubleProperty(d.getValue().getWeight()).asObject());

        priceCol.setCellValueFactory(d ->
                new SimpleDoubleProperty(d.getValue().getPrice()).asObject());

        deliveryTypeCol.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getDeliveryType()));

        orderDateCol.setCellValueFactory(d ->
                new SimpleObjectProperty<>(d.getValue().getOrderDate()));

        estimatedArrivalCol.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getEstimatedArrival() + " days"));

        // Load data into table
        orderTable.setItems(orderDBStore.getAll());

        // When user selects a row in the table
        orderTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {

            selectedOrder = newSel;

            if (newSel != null) {
                // Fill form fields with selected order data
                sourceField.setText(newSel.getSource());
                destinationField.setText(newSel.getDestination());
                weightField.setText(String.valueOf(newSel.getWeight()));
                priceField.setText(String.valueOf(newSel.getPrice()));
                deliveryTypeComboBox.setValue(newSel.getDeliveryType());
                orderDatePicker.setValue(newSel.getOrderDate());
                estimatedTimeSlider.setValue(newSel.getEstimatedArrival());
                estimatedTimeLabel.setText(newSel.getEstimatedArrival() + " days");

                errorLabel.setText("");
            }
        });
    }

    // ===== ADD ORDER BUTTON =====
    @FXML
    private void addOrder() {
        try {
            // Get values from input fields
            String source = sourceField.getText();
            String destination = destinationField.getText();
            String deliveryType = deliveryTypeComboBox.getValue();
            LocalDate orderDate = orderDatePicker.getValue();

            // Validate required fields
            if (source.isEmpty() || destination.isEmpty() || deliveryType == null || orderDate == null) {
                errorLabel.setText("Please fill all fields.");
                return;
            }

            // Convert text inputs to numbers
            double weight = Double.parseDouble(weightField.getText());
            double price = Double.parseDouble(priceField.getText());
            int estimatedArrival = (int) estimatedTimeSlider.getValue();

            // Create new order object
            Order newOrder = new Order(0, source, destination, weight, price,
                    deliveryType, orderDate, estimatedArrival);

            // Insert into database/store
            orderDBStore.insert(newOrder);

            // Refresh table
            orderTable.setItems(orderDBStore.getAll());

            // Clear form
            clearFields();

        } catch (NumberFormatException e) {
            errorLabel.setText("Invalid number.");
        }
    }

    // ===== UPDATE ORDER BUTTON =====
    @FXML
    private void updateOrder() {

        // Check if user selected an order
        if (selectedOrder == null) {
            errorLabel.setText("Select an order first.");
            return;
        }

        try {
            // Update selected order with new values
            selectedOrder.setSource(sourceField.getText());
            selectedOrder.setDestination(destinationField.getText());
            selectedOrder.setWeight(Double.parseDouble(weightField.getText()));
            selectedOrder.setPrice(Double.parseDouble(priceField.getText()));
            selectedOrder.setDeliveryType(deliveryTypeComboBox.getValue());
            selectedOrder.setOrderDate(orderDatePicker.getValue());
            selectedOrder.setEstimatedArrival((int) estimatedTimeSlider.getValue());

            // Update in database/store
            orderDBStore.update(selectedOrder);

            // Refresh table
            orderTable.setItems(orderDBStore.getAll());

            // Clear form
            clearFields();

        } catch (NumberFormatException e) {
            errorLabel.setText("Invalid number.");
        }
    }

    // ===== DELETE ORDER BUTTON =====
    @FXML
    private void deleteOrder() {

        // Check if an order is selected
        if (selectedOrder == null) {
            errorLabel.setText("Select an order to delete.");
            return;
        }

        // Delete using ID
        orderDBStore.delete(selectedOrder.getId());

        // Refresh table
        orderTable.setItems(orderDBStore.getAll());

        // Clear form
        clearFields();
    }

    // ===== NAVIGATION BACK TO HOME =====
    @FXML
    private void backToHome(ActionEvent event) throws IOException {

        // Load home.fxml screen
        Parent root = FXMLLoader.load(
                getClass().getResource("/com/example/mini_project2/home.fxml")
        );

        // Get current stage and change scene
        Stage stage = (Stage) ((Node) event.getSource())
                .getScene()
                .getWindow();

        stage.setScene(new Scene(root));
        stage.setTitle("Home");
        stage.show();
    }

    // ===== CLEAR FORM FIELDS =====
    private void clearFields() {

        sourceField.clear();
        destinationField.clear();
        weightField.clear();
        priceField.clear();

        deliveryTypeComboBox.setValue(null);
        orderDatePicker.setValue(null);

        estimatedTimeSlider.setValue(0);
        estimatedTimeLabel.setText("0 days");

        errorLabel.setText("");

        // reset selected order
        selectedOrder = null;
    }
}