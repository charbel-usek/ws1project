package com.example.mini_project2.controllers;

import com.example.mini_project2.models.Customer;
import com.example.mini_project2.models.CustomerStore;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class CustomersController {

    @FXML
    private Button addBtn;
    @FXML
    private TableColumn<Customer, String> addressCol;
    @FXML
    private TableColumn<Customer, String> statusCol;
    @FXML
    private RadioButton c1;
    @FXML
    private RadioButton c2;
    @FXML
    private RadioButton c3;
    @FXML
    private ToggleGroup statusGroup;
    @FXML
    private TextField addressFld;
    @FXML
    private TableColumn<Customer, Integer> ageCol;
    @FXML
    private TextField ageFld;
    @FXML
    private Button deleteBtn;
    @FXML
    private TableColumn<Customer, String> nameCol;
    @FXML
    private TextField nameFld;
    @FXML
    private TableView<Customer> personsTable;
    @FXML
    private Button updateBtn;
    @FXML
    private Label errorMsg;
    @FXML
    private DatePicker dateOfBirthPicker;
    @FXML
    private TextField phoneNumberFld;
    @FXML
    private TableColumn<Customer, LocalDate> dateOfBirthCol;
    @FXML
    private TableColumn<Customer, String> phoneNumberCol;

    private final CustomerStore customerStore = new CustomerStore();

    @FXML
    public void initialize() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        dateOfBirthCol.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        phoneNumberCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        statusGroup = new ToggleGroup();
        c1.setToggleGroup(statusGroup);
        c2.setToggleGroup(statusGroup);
        c3.setToggleGroup(statusGroup);

        ObservableList<Customer> customers = customerStore.getCustomersList();
        personsTable.setItems(customers);

        personsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, selectedCustomer) -> {
            if (selectedCustomer != null) {
                nameFld.setText(selectedCustomer.getName());
                ageFld.setText(Integer.toString(selectedCustomer.getAge()));
                addressFld.setText(selectedCustomer.getAddress());
                dateOfBirthPicker.setValue(selectedCustomer.getDateOfBirth());
                phoneNumberFld.setText(selectedCustomer.getPhoneNumber());
                setStatusRadioButton(selectedCustomer.getStatus());
            }
        });
    }

    private String getSelectedStatus() {
        RadioButton selectedRadioButton = (RadioButton) statusGroup.getSelectedToggle();
        return selectedRadioButton != null ? selectedRadioButton.getText() : "";
    }

    private void setStatusRadioButton(String status) {
        if (status.equals("single")) {
            c1.setSelected(true);
        } else if (status.equals("married")) {
            c2.setSelected(true);
        } else if (status.equals("divorced")) {
            c3.setSelected(true);
        }
    }

    @FXML
    void addPerson(ActionEvent event) {
        String error = "";
        boolean isValid = true;

        String name = nameFld.getText();
        if (name.isEmpty()) {
            error += "Error: Name is required\n";
            isValid = false;
        }

        Integer age = null;
        if (ageFld.getText().isEmpty()) {
            error += "Error: Age is required\n";
            isValid = false;
        } else {
            try {
                age = Integer.parseInt(ageFld.getText());
            } catch (NumberFormatException e) {
                error += "Error: Invalid age value!\n";
                isValid = false;
            }
        }

        String address = addressFld.getText();
        if (address.isEmpty()) {
            error += "Error: Address is required\n";
            isValid = false;
        }

        String status = getSelectedStatus();
        if (status.isEmpty()) {
            error += "Status is required.\n";
            isValid = false;
        }

        LocalDate dateOfBirth = dateOfBirthPicker.getValue();
        if (dateOfBirth == null) {
            error += "Error: Date of Birth is required\n";
            isValid = false;
        }

        String phoneNumber = phoneNumberFld.getText();
        if (phoneNumber.isEmpty()) {
            error += "Error: Phone Number is required\n";
            isValid = false;
        }

        if (isValid) {
            customerStore.addCustomer(new Customer(name, age, address, status, dateOfBirth, phoneNumber));
            nameFld.setText("");
            ageFld.setText("");
            addressFld.setText("");
            dateOfBirthPicker.setValue(null);
            phoneNumberFld.setText("");
            statusGroup.selectToggle(null);
            errorMsg.setText("");
        } else {
            errorMsg.setText(error);
        }
    }

    @FXML
    void deletePerson(ActionEvent event) {
        Customer selectedCustomer = personsTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            customerStore.deleteCustomer(selectedCustomer);
        }
    }

    @FXML
    void updatePerson(ActionEvent event) {
        Customer selectedCustomer = personsTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            String error = "";
            boolean isValid = true;

            String name = nameFld.getText();
            if (name.isEmpty()) {
                error += "Error: Name is required!\n";
                isValid = false;
            }

            Integer age = null;
            if (ageFld.getText().isEmpty()) {
                error += "Error: Age is required!\n";
                isValid = false;
            } else {
                try {
                    age = Integer.parseInt(ageFld.getText());
                } catch (NumberFormatException e) {
                    error += "Error: Invalid age value!\n";
                    isValid = false;
                }
            }

            String address = addressFld.getText();
            if (address.isEmpty()) {
                error += "Error: Address is required!\n";
                isValid = false;
            }

            String status = getSelectedStatus();
            if (status.isEmpty()) {
                error += "Error: Status is required!\n";
                isValid = false;
            }

            LocalDate dateOfBirth = dateOfBirthPicker.getValue();
            if (dateOfBirth == null) {
                error += "Error: Date of Birth is required!\n";
                isValid = false;
            }

            String phoneNumber = phoneNumberFld.getText();
            if (phoneNumber.isEmpty()) {
                error += "Error: Phone Number is required!\n";
                isValid = false;
            }

            if (isValid) {
                customerStore.updateCustomer(selectedCustomer, name, age, address, status, dateOfBirth, phoneNumber);
                personsTable.refresh();
                errorMsg.setText("");
            } else {
                errorMsg.setText(error);
            }
        }
    }

    @FXML
    void backToHome(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/mini_project2/home.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Home");
        stage.show();
    }
}
