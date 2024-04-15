package com.example.transitariomaritimo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.concurrent.ConcurrentMap;

import org.springframework.boot.CommandLineRunner;
import pt.ipvc.transitariomaritimo.*;
import pt.ipvc.transitariomaritimo.entity.*;
import pt.ipvc.transitariomaritimo.repository.ArmazemRepository;
import pt.ipvc.transitariomaritimo.repository.ClienteRepository;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        System.out.println("Miguel Test");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        launch();

        ArmazemEntity a = new ArmazemEntity();
        a.setId(3);
        a.setCapacidadeMax(2000.0);



    }
}