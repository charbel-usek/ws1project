package com.example.mini_project2.models;

import java.time.LocalDate;

public class Order {
    private int id;
    private String source;
    private String destination;
    private double weight;
    private double price;
    private String deliveryType;
    private LocalDate orderDate;
    private int estimatedArrival; // in days

    // constructor used from the controller (no id yet)
    public Order(String source,
                 String destination,
                 double weight,
                 double price,
                 String deliveryType,
                 LocalDate orderDate,
                 int estimatedArrival) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
        this.price = price;
        this.deliveryType = deliveryType;
        this.orderDate = orderDate;
        this.estimatedArrival = estimatedArrival;
    }

    // optional full constructor
    public Order(int id, String source, String destination, double weight, double price,
                 String deliveryType, LocalDate orderDate, int estimatedArrival) {
        this(source, destination, weight, price, deliveryType, orderDate, estimatedArrival);
        this.id = id;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getDeliveryType() { return deliveryType; }
    public void setDeliveryType(String deliveryType) { this.deliveryType = deliveryType; }

    public LocalDate getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDate orderDate) { this.orderDate = orderDate; }

    public int getEstimatedArrival() { return estimatedArrival; }
    public void setEstimatedArrival(int estimatedArrival) { this.estimatedArrival = estimatedArrival;}
}
