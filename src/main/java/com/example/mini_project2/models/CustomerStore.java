package com.example.mini_project2.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;

public class CustomerStore {

    private final ObservableList<Customer> customers = FXCollections.observableArrayList();

    public CustomerStore() {
        customers.addAll(
                new Customer(
                        "raphael",
                        27,
                        "usek",
                        "married",
                        LocalDate.of(1998, 1, 1),
                        "03-123456"
                )
        );
    }

    public ObservableList<Customer> getCustomersList() {
        return customers;
    }

    public void addCustomer(Customer customer) {
        if (customer != null) {
            customers.add(customer);
        }
    }

    public void deleteCustomer(Customer customer) {
        if (customer != null) {
            customers.remove(customer);
        }
    }

    public void updateCustomer(
            Customer customer,
            String name,
            Integer age,
            String address,
            String status,
            LocalDate dateOfBirth,
            String phoneNumber
    ) {
        if (customer != null) {
            customer.setName(name);
            customer.setAge(age);
            customer.setAddress(address);
            customer.setStatus(status);
            customer.setDateOfBirth(dateOfBirth);
            customer.setPhoneNumber(phoneNumber);
        }
    }
}
