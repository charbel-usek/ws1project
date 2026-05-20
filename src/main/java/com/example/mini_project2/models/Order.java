package com.example.mini_project2.models;

import java.time.LocalDate;

// This class represents one shipping order
public class Order {

    // Variables that store order information
    private int id;
    private String source;
    private String destination;
    private double weight;
    private double price;
    private String deliveryType;
    private LocalDate orderDate;
    private int estimatedArrival; // delivery time in days

    // Constructor used when creating a new order
    // ID is not included because it will be generated automatically
    public Order(String source,
                 String destination,
                 double weight,
                 double price,
                 String deliveryType,
                 LocalDate orderDate,
                 int estimatedArrival) {

        // Store the received values inside the object
        this.source = source;
        this.destination = destination;
        this.weight = weight;
        this.price = price;
        this.deliveryType = deliveryType;
        this.orderDate = orderDate;
        this.estimatedArrival = estimatedArrival;
    }

    // Full constructor including ID
    // Usually used when editing/loading existing orders
    public Order(int id,
                 String source,
                 String destination,
                 double weight,
                 double price,
                 String deliveryType,
                 LocalDate orderDate,
                 int estimatedArrival) {

        // Calls the first constructor to avoid repeating code
        this(source, destination, weight, price,
                deliveryType, orderDate, estimatedArrival);

        // Set the ID
        this.id = id;
    }

    // ===== Getters and Setters =====
    // Getters return values
    // Setters modify values

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public int getEstimatedArrival() {
        return estimatedArrival;
    }

    public void setEstimatedArrival(int estimatedArrival) {
        this.estimatedArrival = estimatedArrival;
    }
}