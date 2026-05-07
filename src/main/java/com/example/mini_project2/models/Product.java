package com.example.mini_project2.models;

public class Product {
    private int id;
    private String name;
    private String brand;
    private String category;
    private String description;
    private int quantity;
    private double price;
    private int stock;
    private String imagePath;

    public Product(int id, String name, String brand, String category, String description,
                   int quantity, double price, int stock, String imagePath) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.category = category;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.stock = stock;
        this.imagePath = imagePath;
    }

    public Product(String name, String brand, String category, String description,
                   int quantity, double price, int stock, String imagePath) {
        this(0, name, brand, category, description, quantity, price, stock, imagePath);
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    @Override
    public String toString() {
        return name + " (" + category + ")";
    }
}