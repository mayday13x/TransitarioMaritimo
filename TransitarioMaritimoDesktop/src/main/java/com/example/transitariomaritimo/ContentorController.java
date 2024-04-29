package com.example.transitariomaritimo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ContentorController {



    @FXML
    public void InserirContentor(ActionEvent event) {

        try{
            Parent root = FXMLLoader.load(getClass().getResource("InserirContentor.fxml"));
            Scene regCena = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(regCena);
            stage.setTitle("Inserir Contentor");
            stage.show();
        }catch (IOException ex){
            System.out.println("Erro ao acessar InserirCliente: " + ex.getMessage());
        }
    }

    @FXML
    public void RegistarContentor(ActionEvent event) {

        try{
            Parent root = FXMLLoader.load(getClass().getResource("Contentor.fxml"));
            Scene regCena = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(regCena);
            stage.setTitle("Contentores");
            stage.show();
        }catch (IOException ex){
            System.out.println("Erro ao acessar Contentor: " + ex.getMessage());
        }
    }

    @FXML
    public void VoltarAtrasInserirContentor(ActionEvent event) {

        try{
            Parent root = FXMLLoader.load(getClass().getResource("Contentor.fxml"));
            Scene regCena = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(regCena);
            stage.setTitle("Contentores");
            stage.show();
        }catch (IOException ex){
            System.out.println("Erro ao acessar Contentor: " + ex.getMessage());
        }
    }

    @FXML
    public void VoltarAtras(ActionEvent event) {

        try{
            Parent root = FXMLLoader.load(getClass().getResource("menu.fxml"));
            Scene regCena = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(regCena);
            stage.setTitle("Menu");
            stage.show();
        }catch (IOException ex){
            System.out.println("Erro ao acessar menu: " + ex.getMessage());
        }
    }
}
