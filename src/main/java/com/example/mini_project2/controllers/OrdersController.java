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

    @FXML private TextField sourceField, destinationField, weightField, priceField;
    @FXML private ComboBox<String> deliveryTypeComboBox;
    @FXML private DatePicker orderDatePicker;
    @FXML private Slider estimatedTimeSlider;
    @FXML private Label estimatedTimeLabel, errorLabel;

    @FXML private TableView<Order> orderTable;
    @FXML private TableColumn<Order, Integer> idCol;
    @FXML private TableColumn<Order, Double> priceCol;
    @FXML private TableColumn<Order, String> sourceCol;
    @FXML private TableColumn<Order, String> destinationCol;
    @FXML private TableColumn<Order, Double> weightCol;
    @FXML private TableColumn<Order, String> deliveryTypeCol;
    @FXML private TableColumn<Order, LocalDate> orderDateCol;
    @FXML private TableColumn<Order, String> estimatedArrivalCol;

    private final OrderDBStore orderDBStore = new OrderDBStore();
    private Order selectedOrder;

    @FXML
    public void initialize() {
        deliveryTypeComboBox.getItems().addAll("Standard", "Express", "Same-Day");

        estimatedTimeSlider.valueProperty().addListener((obs, o, n) ->
                estimatedTimeLabel.setText(n.intValue() + " days")
        );

        idCol.setCellValueFactory(d -> new SimpleIntegerProperty(d.getValue().getId()).asObject());
        sourceCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getSource()));
        destinationCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getDestination()));
        weightCol.setCellValueFactory(d -> new SimpleDoubleProperty(d.getValue().getWeight()).asObject());
        priceCol.setCellValueFactory(d -> new SimpleDoubleProperty(d.getValue().getPrice()).asObject());
        deliveryTypeCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getDeliveryType()));
        orderDateCol.setCellValueFactory(d -> new SimpleObjectProperty<>(d.getValue().getOrderDate()));
        estimatedArrivalCol.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getEstimatedArrival() + " days")
        );

        orderTable.setItems(orderDBStore.getAll());

        orderTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            selectedOrder = newSel;
            if (newSel != null) {
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

    @FXML
    private void addOrder() {
        try {
            String source = sourceField.getText();
            String destination = destinationField.getText();
            String deliveryType = deliveryTypeComboBox.getValue();
            LocalDate orderDate = orderDatePicker.getValue();

            if (source.isEmpty() || destination.isEmpty() || deliveryType == null || orderDate == null) {
                errorLabel.setText("Please fill all fields.");
                return;
            }

            double weight = Double.parseDouble(weightField.getText());
            double price = Double.parseDouble(priceField.getText());
            int estimatedArrival = (int) estimatedTimeSlider.getValue();

            Order newOrder = new Order(0, source, destination, weight, price, deliveryType, orderDate, estimatedArrival);
            orderDBStore.insert(newOrder);

            orderTable.setItems(orderDBStore.getAll());
            clearFields();
        } catch (NumberFormatException e) {
            errorLabel.setText("Invalid number.");
        }
    }

    @FXML
    private void updateOrder() {
        if (selectedOrder == null) {
            errorLabel.setText("Select an order first.");
            return;
        }
        try {
            selectedOrder.setSource(sourceField.getText());
            selectedOrder.setDestination(destinationField.getText());
            selectedOrder.setWeight(Double.parseDouble(weightField.getText()));
            selectedOrder.setPrice(Double.parseDouble(priceField.getText()));
            selectedOrder.setDeliveryType(deliveryTypeComboBox.getValue());
            selectedOrder.setOrderDate(orderDatePicker.getValue());
            selectedOrder.setEstimatedArrival((int) estimatedTimeSlider.getValue());

            orderDBStore.update(selectedOrder);

            orderTable.setItems(orderDBStore.getAll());
            clearFields();
        } catch (NumberFormatException e) {
            errorLabel.setText("Invalid number.");
        }
    }

    @FXML
    private void deleteOrder() {
        if (selectedOrder == null) {
            errorLabel.setText("Select an order to delete.");
            return;
        }

        orderDBStore.delete(selectedOrder.getId());
        orderTable.setItems(orderDBStore.getAll());
        clearFields();
    }

    @FXML
    private void backToHome(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/mini_project2/home.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Home");
        stage.show();
    }

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
        selectedOrder=null;
}
}
