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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pt.ipvc.database.entity.CargaEntity;
import pt.ipvc.database.entity.ContentorEntity;
import pt.ipvc.database.repository.CargaRepository;
import pt.ipvc.database.repository.ClienteRepository;
import pt.ipvc.database.repository.ContentorRepository;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ContentorController implements Initializable {

    public AnnotationConfigApplicationContext context;
    private ContentorRepository repo;

    @FXML
    private TableColumn<ContentorEntity, String> Cin;
    @FXML
    private TableColumn<ContentorEntity, String> Capacidade;

    @FXML
    private TextField IdEstadoContentor;

    @FXML
    private TableColumn<ContentorEntity, String> LocalAtual;

    @FXML
    private TableColumn<ContentorEntity, String> PesoMaximo;

    @FXML
    private TextField TipoContentor;

    @FXML
    private TableView<ContentorEntity> table;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        context = new AnnotationConfigApplicationContext(AppConfig.class);

        repo = context.getBean(ContentorRepository.class);
        ObservableList<ContentorEntity> contentores = FXCollections.observableArrayList(repo.findAll());

        Cin.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCin().toString()));
        Capacidade.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCapacidade().toString()));
        PesoMaximo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPesoMax().toString()));
        LocalAtual.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getLocalAtual()));

        table.setItems(contentores);

        table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                IdEstadoContentor.setText(newValue.getCapacidade().toString());
                TipoContentor.setText(newValue.getTipoContentor().toString());
            }
        });

    }
}
