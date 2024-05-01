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

import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import javafx.stage.Stage;
import pt.ipvc.database.entity.ClienteEntity;
import javafx.scene.layout.Pane;



import java.io.IOException;
import java.net.URL;
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
    private Pane Pane;

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
        Pane.setVisible(true);

    }

    @FXML
    public void VoltarAtrasInserirCliente(ActionEvent event) {
        Pane.setVisible(false);

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
