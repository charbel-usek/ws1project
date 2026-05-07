package com.example.mini_project2.models;

import com.example.mini_project2.db.ProductDBStore;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProductStore {

    private final ObservableList<Product> productList = FXCollections.observableArrayList();
    private final ProductDBStore db = new ProductDBStore();

    public ProductStore() {
        productList.addAll(db.getAll());
    }

    public ObservableList<Product> getProductList() {
        return productList;
    }

    public void addProduct(Product p) {
        db.insert(p);
        productList.add(p);
    }

    public void updateProduct(Product oldP, Product newP) {
        db.update(newP);
        int index = productList.indexOf(oldP);
        if (index >= 0) {
            productList.set(index, newP);
        }
    }

    public void deleteProduct(Product p) {
        db.delete(p.getId());
        productList.remove(p);
    }
}