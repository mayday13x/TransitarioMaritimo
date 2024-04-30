package com.example.transitariomaritimo;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pt.ipvc.database.entity.ArmazemEntity;
import pt.ipvc.database.entity.ClienteEntity;
import pt.ipvc.database.repository.ArmazemRepository;
import pt.ipvc.database.repository.ClienteRepository;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ArmazemController implements Initializable {

    public AnnotationConfigApplicationContext context;
    private ArmazemRepository repo;


    @FXML
    private TableColumn<ArmazemEntity, String> Cap_max;

    @FXML
    private TableColumn<ArmazemEntity, String> Descricao;

    @FXML
    private TableColumn<ArmazemEntity, String> Id;

    @FXML
    private TableView<ArmazemEntity> table;


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
    public void InserirArmazem(ActionEvent event) {

        try{
            Parent root = FXMLLoader.load(getClass().getResource("InserirArmazem.fxml"));
            Scene regCena = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(regCena);
            stage.setTitle("Inserir Armazem");
            stage.show();
        }catch (IOException ex){
            System.out.println("Erro ao acessar menu cliente: " + ex.getMessage());
        }
    }

    @FXML
    public void RegistarArmazem(ActionEvent event) {

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
    public void VisualizarCargas(ActionEvent event) {

        try{
            Parent root = FXMLLoader.load(getClass().getResource("Carga.fxml"));
            Scene regCena = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(regCena);
            stage.setTitle("Cargas");
            stage.show();
        }catch (IOException ex){
            System.out.println("Erro ao acessar menu cliente: " + ex.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        context = new AnnotationConfigApplicationContext(AppConfig.class);

        repo = context.getBean(ArmazemRepository.class);
        ObservableList<ArmazemEntity> armazens = FXCollections.observableArrayList(repo.findAll());


        Id.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getId().toString()));
        Descricao.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDescricao()));
        Cap_max.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCapacidadeMax().toString()));


        table.setItems(armazens);


    }
}
