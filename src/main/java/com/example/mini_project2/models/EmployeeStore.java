package com.example.mini_project2.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;

public class EmployeeStore {

    private final ObservableList<Employee> employees = FXCollections.observableArrayList();

    public EmployeeStore() {
        this.employees.addAll(
                new Employee(
                        "charbel",
                        "El Khoury",
                        "charbelelkhoury@net.edu.lb",
                        LocalDate.of(2025, 7, 20),
                        "Team Leader",
                        "Computer Science",
                        3200
                ),
                new Employee(
                        "joseph",
                        "saad",
                        "josephsaad@net.usek.edu.lb",
                        LocalDate.of(2023, 11, 2),
                        "Collaborator",
                        "IT",
                        1800
                ),
                new Employee(
                        "mark",
                        "haddad",
                        "markhaddad@net.usek.edu.lb",
                        LocalDate.of(2022, 7, 1),
                        "Collaborator",
                        "Computer Science",
                        2500
                ),
                new Employee(
                        "raphael",
                        "raph",
                        "raphael@net.usek.edu.lb",
                        LocalDate.of(2018, 7, 1),
                        "Collaborator",
                        "Computer Science",
                        2000
                )
        );
    }

    public ObservableList<Employee> getEmployeesList() {
        return employees;
    }

    public void addEmployee(Employee employee) {
        if (employee != null) {
            this.employees.add(employee);
        }
    }

    public void deleteEmployee(Employee employee) {
        if (employee != null) {
            this.employees.remove(employee);
        }
    }

    public void updateEmployee(
            Employee employee,
            String fname,
            String lname,
            String email,
            LocalDate hireDate,
            String role,
            String department,
            int salary
    ) {
        if (employee != null) {
            employee.setFname(fname);
            employee.setLname(lname);
            employee.setEmail(email);
            employee.setHireDate(hireDate);
            employee.setRole(role);
            employee.setDepartment(department);
            employee.setSalary(salary);
        }
    }
}
