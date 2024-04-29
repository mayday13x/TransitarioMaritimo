package com.example.transitariomaritimo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pt.ipvc.database.entity.ArmazemEntity;
import pt.ipvc.database.entity.ClienteEntity;
import pt.ipvc.database.repository.ArmazemRepository;
import pt.ipvc.database.repository.ClienteRepository;

import java.io.IOException;
import java.util.List;


public class HelloApplication extends Application {

    public AnnotationConfigApplicationContext context;

    @Override
    public void start(Stage stage) throws IOException {

        context = new AnnotationConfigApplicationContext(AppConfig.class);

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1020, 768);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {

        launch();

    }


}