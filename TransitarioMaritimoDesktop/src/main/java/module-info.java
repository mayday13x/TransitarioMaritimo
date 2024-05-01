module com.example.transitariomaritimo {
        requires javafx.controls;
        requires javafx.fxml;
        requires java.sql;
        requires spring.context;
        requires spring.boot;
        requires spring.beans;
        requires spring.data.jpa;
        requires pt.ipvc.database;
    requires spring.boot.autoconfigure;
    requires jakarta.persistence;

    opens com.example.transitariomaritimo to javafx.fxml, spring.core;
        exports com.example.transitariomaritimo;
        //exports ipvc....controllers
        //opens exports ipvc....controllers to javafx.fxml
}