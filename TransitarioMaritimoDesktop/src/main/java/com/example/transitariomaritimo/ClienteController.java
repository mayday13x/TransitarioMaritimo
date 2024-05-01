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
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pt.ipvc.database.entity.ClienteEntity;
import pt.ipvc.database.entity.CodPostalEntity;
import pt.ipvc.database.repository.ClienteRepository;

import javafx.scene.layout.Pane;
import pt.ipvc.database.repository.CodPostalRepository;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ClienteController implements Initializable{

    public AnnotationConfigApplicationContext context;
    private ClienteRepository cli_repo;

    private CodPostalRepository cp_repo;

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
    private TextField Rua;

    @FXML
    private TextField Telefone;

    @FXML
    private TextField Email;

    @FXML
    private TableColumn<ClienteEntity, String> Localidade;

    @FXML
    private TableColumn<ClienteEntity, String> Id;

    @FXML
    private TableColumn<ClienteEntity, String> Nif;

    @FXML
    private TableColumn<ClienteEntity, String> Nome;

    @FXML
    private TableView<ClienteEntity> table;


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
    public void RegistarCliente(ActionEvent event) throws IOException {

        ClienteEntity novoCliente = new ClienteEntity();
        novoCliente.setNome(NomeText.getText());
        novoCliente.setNif(Integer.valueOf(NifText.getText()));
        novoCliente.setRua(RuaText.getText());
        novoCliente.setPorta(Integer.valueOf(PortaText.getText()));

        CodPostalEntity codPostalEntity = cp_repo.findByNameLike(CodPostalCombo.getSelectionModel().getSelectedItem());

        novoCliente.setIdCodPostal(codPostalEntity.getIdCodPostal());
        novoCliente.setEmail(EmailText.getText());
        novoCliente.setTelefone(TelefoneText.getText());
        novoCliente.setCodPostalByIdCodPostal(codPostalEntity);


        cli_repo.save(novoCliente);

        RuaText.clear();
        NifText.clear();
        NomeText.clear();
        PortaText.clear();
        EmailText.clear();
        TelefoneText.clear();

        ObservableList<ClienteEntity> clientesAtualizados = FXCollections.observableArrayList(cli_repo.findAll());
        table.setItems(clientesAtualizados);
        //table.refresh();

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
        context = new AnnotationConfigApplicationContext(AppConfig.class);
        cli_repo = context.getBean(ClienteRepository.class);

        ObservableList<ClienteEntity> clientes = FXCollections.observableArrayList(cli_repo.findAll());


        try{

            Id.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getId().toString()));
            Nome.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNome()));
            Nif.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNif().toString()));
            Localidade.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCodPostalByIdCodPostal().getLocalidade()));
            table.setItems(clientes);

            table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    Rua.setText(newValue.getRua());
                    Telefone.setText(newValue.getTelefone());
                    Email.setText(newValue.getEmail());
                }
            });

            context = new AnnotationConfigApplicationContext(AppConfig.class);
            cp_repo = context.getBean(CodPostalRepository.class);
            cli_repo = context.getBean(ClienteRepository.class);

            ObservableList<CodPostalEntity> localidades = FXCollections.observableArrayList(cp_repo.findAll());
            for (CodPostalEntity i : localidades){
                CodPostalCombo.getItems().addAll(i.getLocalidade());
            }


        } catch (Exception ex){
            System.out.println("Erro ao acessar menu cliente: " + ex.getMessage());
        }

    }
}
