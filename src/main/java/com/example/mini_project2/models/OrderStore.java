package com.example.mini_project2.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDate;

// This class stores and manages all orders
public class OrderStore {

    // ObservableList automatically updates JavaFX tables/views
    private static final ObservableList<Order> orders =
            FXCollections.observableArrayList();

    // Used to generate unique IDs automatically
    private static int nextId = 1;

    // Static block runs once when the class loads
    // Adds sample orders for testing
    static {

        addOrder(new Order(
                "New York",
                "Los Angeles",
                10.5,
                150.0,
                "Standard",
                LocalDate.now(),
                5
        ));

        addOrder(new Order(
                "London",
                "Paris",
                7.2,
                120.0,
                "Express",
                LocalDate.now().minusDays(1),
                2
        ));

        addOrder(new Order(
                "Tokyo",
                "Osaka",
                5.0,
                80.0,
                "Same-Day",
                LocalDate.now().minusDays(2),
                1
        ));
    }

    // Returns all orders
    public static ObservableList<Order> getOrders() {
        return orders;
    }

    // Adds a new order to the list
    public static void addOrder(Order order) {

        // Assign a unique ID then increase nextId
        order.setId(nextId++);

        // Add order to the list
        orders.add(order);
    }

    // Updates an existing order
    public static void updateOrder(Order order) {

        // Loop through all orders
        for (int i = 0; i < orders.size(); i++) {

            // Find order with matching ID
            if (orders.get(i).getId() == order.getId()) {

                // Replace old order with updated order
                orders.set(i, order);

                return;
            }
        }
    }

    // Deletes an order from the list
    public static void deleteOrder(Order order) {

        // Remove order from list
        orders.remove(order);
    }
}