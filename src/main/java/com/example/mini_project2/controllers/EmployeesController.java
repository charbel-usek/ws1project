package com.example.mini_project2.controllers;

import com.example.mini_project2.db.EmployeeDBStore;
import com.example.mini_project2.models.Employee;
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

public class EmployeesController {

    @FXML
    private TextField employeeFnameFld;
    @FXML
    private TextField employeeLnameFld;
    @FXML
    private TextField employeeEmailFld;
    @FXML
    private DatePicker employeeHireDatePicker;
    @FXML
    private ComboBox<String> employeeRoleCmb;
    @FXML
    private ComboBox<String> employeeDepartmentCmb;
    @FXML
    private Slider employeeSalarySlider;
    @FXML
    private Label employeeSalaryValueLbl;
    @FXML
    private Label employeeErrorLbl;

    @FXML
    private TableView<Employee> employeeTable;
    @FXML
    private TableColumn<Employee, String> employeeFnameCol;
    @FXML
    private TableColumn<Employee, String> employeeLnameCol;
    @FXML
    private TableColumn<Employee, String> employeeEmailCol;
    @FXML
    private TableColumn<Employee, LocalDate> employeeHireDateCol;
    @FXML
    private TableColumn<Employee, String> employeeRoleCol;
    @FXML
    private TableColumn<Employee, String> employeeDepartmentCol;
    @FXML
    private TableColumn<Employee, Number> employeeSalaryCol;

    private final EmployeeDBStore employeeDBStore = new EmployeeDBStore();

    @FXML
    public void initialize() {
        // combos
        employeeRoleCmb.getItems().setAll("Team Leader", "Collaborator", "Instructor");
        employeeDepartmentCmb.getItems().setAll("Computer Science", "IT", "Other");

        // columns
        employeeFnameCol.setCellValueFactory(new PropertyValueFactory<>("fname"));
        employeeLnameCol.setCellValueFactory(new PropertyValueFactory<>("lname"));
        employeeEmailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        employeeHireDateCol.setCellValueFactory(new PropertyValueFactory<>("hireDate"));
        employeeRoleCol.setCellValueFactory(new PropertyValueFactory<>("role"));
        employeeDepartmentCol.setCellValueFactory(new PropertyValueFactory<>("department"));
        employeeSalaryCol.setCellValueFactory(new PropertyValueFactory<>("salary"));

        // first load
        loadEmployeesFromDB();

        // select → fill form
        employeeTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                employeeFnameFld.setText(newSel.getFname());
                employeeLnameFld.setText(newSel.getLname());
                employeeEmailFld.setText(newSel.getEmail());
                employeeHireDatePicker.setValue(newSel.getHireDate());
                employeeRoleCmb.setValue(newSel.getRole());
                employeeDepartmentCmb.setValue(newSel.getDepartment());
                employeeSalarySlider.setValue(newSel.getSalary());
                employeeSalaryValueLbl.setText(Integer.toString(newSel.getSalary()));
            }
        });

        // show slider value
        employeeSalarySlider.valueProperty().addListener((obs, o, n) ->
                employeeSalaryValueLbl.setText(Integer.toString(n.intValue()))
        );
    }

    private void loadEmployeesFromDB() {
        ObservableList<Employee> list = employeeDBStore.getAll();
        employeeTable.setItems(list);
    }

    @FXML
    void addEmployee(ActionEvent event) {
        if (isAnyRequiredFieldMissing()) {
            employeeErrorLbl.setText("Please fill in all required fields.");
            return;
        }

        Employee e = buildEmployeeFromForm();

        // 1) insert
        employeeDBStore.insert(e);

        // 2) reload
        loadEmployeesFromDB();
        employeeTable.refresh();

        // 3) clear
        clearForm();
        employeeErrorLbl.setText("");
    }

    @FXML
    void updateEmployee(ActionEvent event) {
        Employee selected = employeeTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            employeeErrorLbl.setText("Select an employee first.");
            return;
        }
        if (isAnyRequiredFieldMissing()) {
            employeeErrorLbl.setText("Please fill in all required fields.");
            return;
        }

        Employee newData = buildEmployeeFromForm();
        employeeDBStore.update(selected, newData);

        loadEmployeesFromDB();
        employeeTable.refresh();
        employeeErrorLbl.setText("");
    }

    @FXML
    void deleteEmployee(ActionEvent event) {
        Employee selected = employeeTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            employeeDBStore.deleteByEmail(selected.getEmail());
            loadEmployeesFromDB();
            employeeTable.refresh();
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

    private Employee buildEmployeeFromForm() {
        String fname = employeeFnameFld.getText();
        String lname = employeeLnameFld.getText();
        String email = employeeEmailFld.getText();
        LocalDate hireDate = employeeHireDatePicker.getValue();
        String role = employeeRoleCmb.getValue();
        String department = employeeDepartmentCmb.getValue();
        int salary = (int) employeeSalarySlider.getValue();

        return new Employee(fname, lname, email, hireDate, role, department, salary);
    }

    private void clearForm() {
        employeeFnameFld.clear();
        employeeLnameFld.clear();
        employeeEmailFld.clear();
        employeeHireDatePicker.setValue(null);
        employeeRoleCmb.setValue(null);
        employeeDepartmentCmb.setValue(null);
        employeeSalarySlider.setValue(0);
        employeeSalaryValueLbl.setText("0");
    }

    private boolean isAnyRequiredFieldMissing() {
        if (employeeFnameFld.getText() == null || employeeFnameFld.getText().isEmpty()) return true;
        if (employeeLnameFld.getText() == null || employeeLnameFld.getText().isEmpty()) return true;
        if (employeeEmailFld.getText() == null || employeeEmailFld.getText().isEmpty()) return true;
        if (employeeHireDatePicker.getValue() == null) return true;
        if (employeeRoleCmb.getValue() == null || employeeRoleCmb.getValue().isEmpty()) return true;
        if (employeeDepartmentCmb.getValue() == null || employeeDepartmentCmb.getValue().isEmpty()) return true;
        return false;
    }
}
