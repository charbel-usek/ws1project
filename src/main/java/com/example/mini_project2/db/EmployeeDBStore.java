package com.example.mini_project2.db;

import com.example.mini_project2.models.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;

public class EmployeeDBStore {

    public ObservableList<Employee> getAll() {
        ObservableList<Employee> list = FXCollections.observableArrayList();
        String sql = "SELECT fname, lname, email, hire_date, role, department, salary FROM Employees";

        try (Connection conn = DBUtil.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                LocalDate hire = null;
                Date d = rs.getDate("hire_date");
                if (d != null) {
                    hire = d.toLocalDate();
                }

                Employee e = new Employee(
                        rs.getString("fname"),
                        rs.getString("lname"),
                        rs.getString("email"),
                        hire,
                        rs.getString("role"),
                        rs.getString("department"),
                        rs.getInt("salary")
                );
                list.add(e);
            }
        } catch (Exception e) {
            e.printStackTrace(); // if this throws, table stays old
        }
        return list;
    }

    public void insert(Employee e) {
        String sql = "INSERT INTO Employees(fname, lname, email, hire_date, role, department, salary) VALUES (?,?,?,?,?,?,?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, e.getFname());
            ps.setString(2, e.getLname());
            ps.setString(3, e.getEmail());

            if (e.getHireDate() != null) {
                ps.setDate(4, Date.valueOf(e.getHireDate()));
            } else {
                ps.setNull(4, Types.DATE);
            }

            ps.setString(5, e.getRole());
            ps.setString(6, e.getDepartment());
            ps.setInt(7, e.getSalary());

            ps.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // update by email (since your Employee has no id)
    public void update(Employee oldEmp, Employee newEmp) {
        String sql = "UPDATE Employees SET fname=?, lname=?, email=?, hire_date=?, role=?, department=?, salary=? WHERE email=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newEmp.getFname());
            ps.setString(2, newEmp.getLname());
            ps.setString(3, newEmp.getEmail());

            if (newEmp.getHireDate() != null) {
                ps.setDate(4, Date.valueOf(newEmp.getHireDate()));
            } else {
                ps.setNull(4, Types.DATE);
            }

            ps.setString(5, newEmp.getRole());
            ps.setString(6, newEmp.getDepartment());
            ps.setInt(7, newEmp.getSalary());

            // WHERE
            ps.setString(8, oldEmp.getEmail());

            ps.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void deleteByEmail(String email) {
        String sql = "DELETE FROM Employees WHERE email=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
