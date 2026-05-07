package com.example.mini_project2.db;

import com.example.mini_project2.models.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class ProductDBStore {

    public ObservableList<Product> getAll() {
        ObservableList<Product> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM products"; // table name in lowercase

        try (Connection conn = DBUtil.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Product p = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("brand"),
                        rs.getString("category"),
                        rs.getString("description"),
                        rs.getInt("quantity"),
                        rs.getDouble("price"),
                        rs.getInt("stock"),
                        rs.getString("image_path") // fixed column name
                );
                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void insert(Product p) {
        String sql = "INSERT INTO products(name, brand, category, description, quantity, price, stock, image_path) VALUES (?,?,?,?,?,?,?,?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getName());
            ps.setString(2, p.getBrand());
            ps.setString(3, p.getCategory());
            ps.setString(4, p.getDescription());
            ps.setInt(5, p.getQuantity());
            ps.setDouble(6, p.getPrice());
            ps.setInt(7, p.getStock());
            ps.setString(8, p.getImagePath());

            ps.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void update(Product newP) {
        String sql = "UPDATE products SET name=?, brand=?, category=?, description=?, quantity=?, price=?, stock=?, image_path=? WHERE id=?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newP.getName());
            ps.setString(2, newP.getBrand());
            ps.setString(3, newP.getCategory());
            ps.setString(4, newP.getDescription());
            ps.setInt(5, newP.getQuantity());
            ps.setDouble(6, newP.getPrice());
            ps.setInt(7, newP.getStock());
            ps.setString(8, newP.getImagePath());
            ps.setInt(9, newP.getId());

            ps.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM products WHERE id=?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}