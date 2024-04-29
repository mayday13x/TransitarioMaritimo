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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pt.ipvc.database.entity.ClienteEntity;
import pt.ipvc.database.entity.ReservaEntity;
import pt.ipvc.database.repository.ClienteRepository;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import pt.ipvc.database.repository.ReservaRepository;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ReservasController implements Initializable {

    public AnnotationConfigApplicationContext context;
    private ReservaRepository repo;

    @FXML
    private TextField IdCliente;

    @FXML
    private TextField IdEstadoReserva;

    @FXML
    private TextField IdFuncionario;

    @FXML
    private TableColumn<ReservaEntity,String> Data;

    @FXML
    private TableColumn<ReservaEntity,String> Destino;

    @FXML
    private TableColumn<ReservaEntity,String> Id;

    @FXML
    private TableColumn<ReservaEntity,String> Origem;

    @FXML
    private TableView<ReservaEntity> Table;

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
    public void InserirReserva(ActionEvent event) {

        try{
            Parent root = FXMLLoader.load(getClass().getResource("InserirReserva.fxml"));
            Scene regCena = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(regCena);
            stage.setTitle("Inserir Reserva");
            stage.show();
        }catch (IOException ex){
            System.out.println("Erro ao acessar menu cliente: " + ex.getMessage());
        }
    }

    @FXML
    public void RegistarReserva(ActionEvent event) {

        try{
            Parent root = FXMLLoader.load(getClass().getResource("Reservas.fxml"));
            Scene regCena = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(regCena);
            stage.setTitle("Reservas");
            stage.show();
        }catch (IOException ex){
            System.out.println("Erro ao acessar menu cliente: " + ex.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        context = new AnnotationConfigApplicationContext(AppConfig.class);

        repo = context.getBean(ReservaRepository.class);
        ObservableList<ReservaEntity> reserva = FXCollections.observableArrayList(repo.findAll());

        Id.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getData().toString()));
        Data.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getData().toString()));
        Origem.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getOrigem()));
        Destino.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDestino()));



        Table.setItems(reserva);

        Table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                IdCliente.setText(newValue.getIdCliente().toString());
                IdEstadoReserva.setText(newValue.getIdEstadoReserva().toString());
                IdFuncionario.setText(newValue.getIdFuncionario().toString());
            }
        });
    }
}
