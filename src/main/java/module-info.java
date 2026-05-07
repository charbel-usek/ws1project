module com.example.mini_project2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.mini_project2 to javafx.fxml;
    opens com.example.mini_project2.controllers to javafx.fxml;


    opens com.example.mini_project2.models to javafx.base;

    exports com.example.mini_project2;
    exports com.example.mini_project2.controllers;
}
