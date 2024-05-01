package com.example.transitariomaritimo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;


public class Main extends Application {

    public AnnotationConfigApplicationContext context;

    @Override
    public void start(Stage stage) throws IOException {

        context = new AnnotationConfigApplicationContext(AppConfig.class);

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("LoginView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 820, 768);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {

        launch();

    }


}