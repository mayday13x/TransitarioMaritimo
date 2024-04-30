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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pt.ipvc.database.entity.ClienteEntity;
import pt.ipvc.database.repository.ClienteRepository;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ClienteController implements Initializable{


    public AnnotationConfigApplicationContext context;
    private ClienteRepository repo;

    @FXML
    private TableColumn<ClienteEntity, String> Cod_postal;

    @FXML
    private TableColumn<ClienteEntity, String> Id;

    @FXML
    private TableColumn<ClienteEntity, String> Nif;

    @FXML
    private TableColumn<ClienteEntity, String> Nome;

    @FXML
    private TableView<ClienteEntity> table;

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
    public void InserirCliente(ActionEvent event) throws IOException {

            Parent root = FXMLLoader.load(getClass().getResource("InserirCliente.fxml"));
            Scene regCena = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(regCena);
            stage.setTitle("Inserir Cliente");
            stage.show();

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        context = new AnnotationConfigApplicationContext(AppConfig.class);

        repo = context.getBean(ClienteRepository.class);
        ObservableList<ClienteEntity> clientes = FXCollections.observableArrayList(repo.findAll());

    try{
        Id.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEmail()));
        Nome.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNome()));
        Nif.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNif().toString()));
        Cod_postal.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getIdCodPostal().toString()));
        table.setItems(clientes);
    } catch (Exception e) {
        System.out.println(e.getMessage());
    }




    }
}
