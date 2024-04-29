package com.example.transitariomaritimo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CargaController {

    @FXML
    public void VoltarAtras(ActionEvent event) {

        try{
            Parent root = FXMLLoader.load(getClass().getResource("Armazem.fxml"));
            Scene regCena = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(regCena);
            stage.setTitle("Armazem");
            stage.show();
        }catch (IOException ex){
            System.out.println("Erro ao acessar menu cliente: " + ex.getMessage());
        }
    }

    @FXML
    public void InserirCarga(ActionEvent event) {

        try{
            Parent root = FXMLLoader.load(getClass().getResource("InserirCarga.fxml"));
            Scene regCena = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(regCena);
            stage.setTitle("Inserir Carga");
            stage.show();
        }catch (IOException ex){
            System.out.println("Erro ao acessar menu cliente: " + ex.getMessage());
        }
    }
    @FXML
    public void RegistarCarga(ActionEvent event) {

        try{
            Parent root = FXMLLoader.load(getClass().getResource("Carga.fxml"));
            Scene regCena = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(regCena);
            stage.setTitle("Carga");
            stage.show();
        }catch (IOException ex){
            System.out.println("Erro ao acessar menu cliente: " + ex.getMessage());
        }
    }

    @FXML
    public void VoltarAtrasInserirCarga(ActionEvent event) {

        try{
            Parent root = FXMLLoader.load(getClass().getResource("Carga.fxml"));
            Scene regCena = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(regCena);
            stage.setTitle("Carga");
            stage.show();
        }catch (IOException ex){
            System.out.println("Erro ao acessar menu cliente: " + ex.getMessage());
        }
    }
}
