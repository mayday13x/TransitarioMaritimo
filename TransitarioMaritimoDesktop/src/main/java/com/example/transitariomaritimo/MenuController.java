package com.example.transitariomaritimo;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {

    @FXML
    public void Cliente(ActionEvent event) throws IOException {

            Parent root = FXMLLoader.load(getClass().getResource("Cliente.fxml"));
            Scene regCena = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(regCena);
            stage.setTitle("Cliente");
            stage.show();

    }

    @FXML
    public void Reserva(ActionEvent event)  throws IOException {

            Parent root = FXMLLoader.load(getClass().getResource("Reservas.fxml"));
            Scene regCena = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(regCena);
            stage.setTitle("Reserva");
            stage.show();

    }

    @FXML
    public void Armazem(ActionEvent event) {

        try{
            Parent root = FXMLLoader.load(getClass().getResource("Armazem.fxml"));
            Scene regCena = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(regCena);
            stage.setTitle("Armazem");
            stage.show();
        }catch (IOException ex){
            System.out.println("Erro ao acessar menu Armazem: " + ex.getMessage());
        }
    }

    @FXML
    public void Contentor(ActionEvent event) {

        try{
            Parent root = FXMLLoader.load(getClass().getResource("Contentor.fxml"));
            Scene regCena = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(regCena);
            stage.setTitle("Contentores");
            stage.show();
        }catch (IOException ex){
            System.out.println("Erro ao acessar menu contentores: " + ex.getMessage());
        }
    }
}
