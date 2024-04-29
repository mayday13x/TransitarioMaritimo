package com.example.transitariomaritimo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class ClienteController {


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
            System.out.println("Erro ao acessar menu cliente: " + ex.getMessage());
        }
    }

    @FXML
    public void InserirCliente(ActionEvent event) {

        try{
            Parent root = FXMLLoader.load(getClass().getResource("InserirCliente.fxml"));
            Scene regCena = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(regCena);
            stage.setTitle("Inserir Cliente");
            stage.show();
        }catch (IOException ex){
            System.out.println("Erro ao acessar menu cliente: " + ex.getMessage());
        }
    }
    @FXML
    public void VoltarAtrasInserirCliente(ActionEvent event) {

        try{
            Parent root = FXMLLoader.load(getClass().getResource("Cliente.fxml"));
            Scene regCena = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(regCena);
            stage.setTitle("Cliente");
            stage.show();
        }catch (IOException ex){
            System.out.println("Erro ao acessar menu cliente: " + ex.getMessage());
        }
    }


    @FXML
    public void RegistarCliente(ActionEvent event) {

        try{
            Parent root = FXMLLoader.load(getClass().getResource("Cliente.fxml"));
            Scene regCena = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(regCena);
            stage.setTitle("Cliente");
            stage.show();
        }catch (IOException ex){
            System.out.println("Erro ao acessar menu cliente: " + ex.getMessage());
        }
    }
}
