package com.example.mini_project2.db;

import com.example.mini_project2.models.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;

// This class handles ALL database operations for Order (CRUD)
public class OrderDBStore {

    // SQL query to get all orders from database
    private static final String SELECT_ALL =
            "SELECT id, source, destination, weight, price, delivery_type, order_date, estimated_arrival FROM orders";

    // SQL query to insert a new order
    private static final String INSERT_SQL =
            "INSERT INTO orders (source, destination, weight, price, delivery_type, order_date, estimated_arrival) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

    // SQL query to update an existing order
    private static final String UPDATE_SQL =
            "UPDATE orders SET source = ?, destination = ?, weight = ?, price = ?, " +
                    "delivery_type = ?, order_date = ?, estimated_arrival = ? WHERE id = ?";

    // SQL query to delete an order by ID
    private static final String DELETE_SQL =
            "DELETE FROM orders WHERE id = ?";

    // ================= READ (GET ALL ORDERS) =================
    public ObservableList<Order> getAll() {

        // List that will store orders from database
        ObservableList<Order> list = FXCollections.observableArrayList();

        // Try-with-resources (auto closes connection, statement, result set)
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {

            // Loop through database rows
            while (rs.next()) {

                // Convert SQL Date to LocalDate
                Date sqlDate = rs.getDate("order_date");
                LocalDate orderDate =
                        (sqlDate != null) ? sqlDate.toLocalDate() : null;

                // Create Order object from database row
                Order o = new Order(
                        rs.getInt("id"),
                        rs.getString("source"),
                        rs.getString("destination"),
                        rs.getDouble("weight"),
                        rs.getDouble("price"),
                        rs.getString("delivery_type"),
                        orderDate,
                        rs.getInt("estimated_arrival")
                );

                // Add order to list
                list.add(o);
            }

        } catch (Exception e) {
            // Print error if database fails
            e.printStackTrace();
        }

        // Return all orders
        return list;
    }

    // ================= CREATE (INSERT ORDER) =================
    public void insert(Order o) {

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {

            // Fill SQL placeholders with object values
            ps.setString(1, o.getSource());
            ps.setString(2, o.getDestination());
            ps.setDouble(3, o.getWeight());
            ps.setDouble(4, o.getPrice());
            ps.setString(5, o.getDeliveryType());
            ps.setDate(6, Date.valueOf(o.getOrderDate()));
            ps.setInt(7, o.getEstimatedArrival());

            // Execute insert query
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= UPDATE ORDER =================
    public void update(Order o) {

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {

            // Update fields in database
            ps.setString(1, o.getSource());
            ps.setString(2, o.getDestination());
            ps.setDouble(3, o.getWeight());
            ps.setDouble(4, o.getPrice());
            ps.setString(5, o.getDeliveryType());
            ps.setDate(6, Date.valueOf(o.getOrderDate()));
            ps.setInt(7, o.getEstimatedArrival());

            // WHERE id = ?
            ps.setInt(8, o.getId());

            // Execute update query
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= DELETE ORDER =================
    public void delete(int id) {

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_SQL)) {

            // Set ID of order to delete
            ps.setInt(1, id);

            // Execute delete query
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}