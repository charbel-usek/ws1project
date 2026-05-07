package com.example.mini_project2.db;

import com.example.mini_project2.models.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;

public class CustomerDBStore {

    public ObservableList<Customer> getAllCustomers() {
        ObservableList<Customer> list = FXCollections.observableArrayList();
        String sql = "SELECT name, age, address, status, date_of_birth, phone_number FROM customers";

        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String address = rs.getString("address");
                String status = rs.getString("status");
                LocalDate dob = rs.getDate("date_of_birth").toLocalDate();
                String phone = rs.getString("phone_number");

                list.add(new Customer(name, age, address, status, dob, phone));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean addCustomer(Customer c) {
        String sql = "INSERT INTO customers (name, age, address, status, date_of_birth, phone_number) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getName());
            ps.setInt(2, c.getAge());
            ps.setString(3, c.getAddress());
            ps.setString(4, c.getStatus());
            ps.setDate(5, Date.valueOf(c.getDateOfBirth()));
            ps.setString(6, c.getPhoneNumber());

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateCustomer(String originalName, Customer c) {
        String sql = "UPDATE customers SET name = ?, age = ?, address = ?, status = ?, date_of_birth = ?, phone_number = ? WHERE name = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getName());
            ps.setInt(2, c.getAge());
            ps.setString(3, c.getAddress());
            ps.setString(4, c.getStatus());
            ps.setDate(5, Date.valueOf(c.getDateOfBirth()));
            ps.setString(6, c.getPhoneNumber());
            ps.setString(7, originalName);

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteCustomer(String name) {
        String sql = "DELETE FROM customers WHERE name = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
