package com.example.transitariomaritimo;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pt.ipvc.database.entity.ClienteEntity;
import pt.ipvc.database.entity.CodPostalEntity;
import pt.ipvc.database.repository.ClienteRepository;
import pt.ipvc.database.repository.CodPostalRepository;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ClienteController implements Initializable{

    ClienteService clienteService = new ClienteService();

    @FXML
    private TextField Rua;

    @FXML
    private TextField Telefone;

    @FXML
    private TextField Email;

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
    private ComboBox<String> CodPostalCombo;

    @FXML
    private TextField EmailText;

    @FXML
    private TextField NifText;

    @FXML
    private TextField NomeText;

    @FXML
    private TextField PortaText;

    @FXML
    private TextField RuaText;

    @FXML
    private TextField TelefoneText;

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
    public void RegistarCliente(ActionEvent event) throws IOException {

        ClienteEntity novoCliente = new ClienteEntity();
        novoCliente.setNome(NomeText.getText());
        novoCliente.setNif(Integer.valueOf(NifText.getText()));
        novoCliente.setRua(RuaText.getText());
        novoCliente.setPorta(Integer.valueOf(PortaText.getText()));
        novoCliente.setIdCodPostal(Integer.valueOf(String.valueOf(CodPostalCombo.getValue())));
        novoCliente.setEmail(EmailText.getText());
        novoCliente.setTelefone(TelefoneText.getText());

      //  clienteService.addCliente(novoCliente);

        table.getItems().add(novoCliente);

        RuaText.clear();
        NifText.clear();
        NomeText.clear();
        PortaText.clear();
        EmailText.clear();
        TelefoneText.clear();

        Parent root = FXMLLoader.load(getClass().getResource("CriarCotacao.fxml"));
        Scene regCena = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(regCena);
        stage.setTitle("Criar Cotação");
        stage.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<ClienteEntity> clientes = FXCollections.observableArrayList(clienteService.getAllClientes());


        try{

            Id.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getId().toString()));
            Nome.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNome()));
            Nif.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNif().toString()));
            Cod_postal.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCodPostalByIdCodPostal().getLocalidade()));

            table.setItems(clientes);

            table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    Rua.setText(newValue.getRua());
                    Telefone.setText(newValue.getTelefone());
                    Email.setText(newValue.getEmail());
                }
            });
        } catch (Exception ex){
            System.out.println("Erro ao acessar menu cliente: " + ex.getMessage());
        }

    }




}
