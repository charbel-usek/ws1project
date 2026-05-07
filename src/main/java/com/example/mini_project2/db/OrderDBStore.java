package com.example.mini_project2.db;

import com.example.mini_project2.models.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;

public class OrderDBStore {

    private static final String SELECT_ALL =
            "SELECT id, source, destination, weight, price, delivery_type, order_date, estimated_arrival FROM orders";

    private static final String INSERT_SQL =
            "INSERT INTO orders (source, destination, weight, price, delivery_type, order_date, estimated_arrival) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String UPDATE_SQL =
            "UPDATE orders SET source = ?, destination = ?, weight = ?, price = ?, " +
                    "delivery_type = ?, order_date = ?, estimated_arrival = ? WHERE id = ?";

    private static final String DELETE_SQL =
            "DELETE FROM orders WHERE id = ?";

    public ObservableList<Order> getAll() {
        ObservableList<Order> list = FXCollections.observableArrayList();

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Date sqlDate = rs.getDate("order_date");
                LocalDate orderDate = (sqlDate != null) ? sqlDate.toLocalDate() : null;

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
                list.add(o);
            }

        } catch (Exception e) {   // <--- catch broad because DBUtil throws broad
            e.printStackTrace();
        }

        return list;
    }

    public void insert(Order o) {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {

            ps.setString(1, o.getSource());
            ps.setString(2, o.getDestination());
            ps.setDouble(3, o.getWeight());
            ps.setDouble(4, o.getPrice());
            ps.setString(5, o.getDeliveryType());
            ps.setDate(6, Date.valueOf(o.getOrderDate()));
            ps.setInt(7, o.getEstimatedArrival());

            ps.executeUpdate();

        } catch (Exception e) {   // <--- same reason
            e.printStackTrace();
        }
    }

    public void update(Order o) {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {

            ps.setString(1, o.getSource());
            ps.setString(2, o.getDestination());
            ps.setDouble(3, o.getWeight());
            ps.setDouble(4, o.getPrice());
            ps.setString(5, o.getDeliveryType());
            ps.setDate(6, Date.valueOf(o.getOrderDate()));
            ps.setInt(7, o.getEstimatedArrival());
            ps.setInt(8, o.getId());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_SQL)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}