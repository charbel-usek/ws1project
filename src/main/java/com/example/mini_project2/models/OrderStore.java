package com.example.mini_project2.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDate;

public class OrderStore {

    private static final ObservableList<Order> orders = FXCollections.observableArrayList();
    private static int nextId = 1;

    // Static block to add initial orders
    static {
        addOrder(new Order("New York", "Los Angeles", 10.5, 150.0, "Standard", LocalDate.now(), 5));
        addOrder(new Order("London", "Paris", 7.2, 120.0, "Express", LocalDate.now().minusDays(1), 2));
        addOrder(new Order("Tokyo", "Osaka", 5.0, 80.0, "Same-Day", LocalDate.now().minusDays(2), 1));
    }

    public static ObservableList<Order> getOrders() {
        return orders;
    }

    public static void addOrder(Order order) {
        // assign id here
        order.setId(nextId++);
        orders.add(order);
    }

    public static void updateOrder(Order order) {
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getId() == order.getId()) {
                orders.set(i, order);
                return;
            }
        }
    }

    public static void deleteOrder(Order order) {
        orders.remove(order);
    }
}
