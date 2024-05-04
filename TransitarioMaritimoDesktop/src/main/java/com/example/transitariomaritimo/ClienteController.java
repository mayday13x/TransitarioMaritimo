package com.example.transitariomaritimo;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.*;

import javafx.scene.input.MouseEvent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pt.ipvc.database.entity.ClienteEntity;
import pt.ipvc.database.entity.CodPostalEntity;
import pt.ipvc.database.repository.ClienteRepository;

import javafx.scene.layout.Pane;
import pt.ipvc.database.repository.CodPostalRepository;


import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
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

    //edit
    @FXML
    private ComboBox<String> EditCodPostalCombo;

    @FXML
    private TextField EditEmailText;

    @FXML
    private TextField EditNifText;

    @FXML
    private TextField EditNomeText;

    @FXML
    private Pane EditPane;

    @FXML
    private TextField EditPortaText;

    @FXML
    private TextField EditRuaText;

    @FXML
    private TextField EditTelefoneText;

    @FXML
    private TableColumn<ClienteEntity, String> Localidade;

    @FXML
    private TableColumn<ClienteEntity, String> Id;

    @FXML
    private TableColumn<ClienteEntity, String> Nif;

    @FXML
    private TableColumn<ClienteEntity, String> Nome;

    @FXML
    private TableColumn<ClienteEntity, String> Telefone;

    @FXML
    private TableColumn<ClienteEntity, String> Email;

    @FXML
    private TableView<ClienteEntity> table;

    @FXML
    private Pane Pane;

    @FXML
    private Pane mainPanel;

    @FXML
    private Button deleteButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        DoubleClickHandler handler = new DoubleClickHandler();
        table.setOnMouseClicked(handler);

        context = new AnnotationConfigApplicationContext(AppConfig.class);
        cli_repo = context.getBean(ClienteRepository.class);

        ObservableList<ClienteEntity> clientes = FXCollections.observableArrayList(cli_repo.findAll());


        try{

            Id.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getId().toString()));
            Nome.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNome()));
            Nif.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNif().toString()));
            Localidade.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCodPostalByIdCodPostal().getLocalidade()));
            Telefone.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTelefone()));
            Email.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEmail()));
            table.setItems(clientes);


            context = new AnnotationConfigApplicationContext(AppConfig.class);
            cp_repo = context.getBean(CodPostalRepository.class);
            cli_repo = context.getBean(ClienteRepository.class);

            ObservableList<CodPostalEntity> localidades = FXCollections.observableArrayList(cp_repo.findAll());
            for (CodPostalEntity i : localidades){
                CodPostalCombo.getItems().addAll(i.getLocalidade());
                EditCodPostalCombo.getItems().addAll(i.getLocalidade());
            }


        } catch (Exception ex){
            System.out.println("Erro no Cliente: " + ex.getMessage());
        }

    }


    @FXML
    public void RegistarCliente(ActionEvent event) throws IOException {

        if(Objects.equals(RuaText.getText(), "") || Objects.equals(NifText.getText(), "") || Objects.equals(NomeText.getText(), "")
                || Objects.equals(PortaText.getText(), "") || Objects.equals(EmailText.getText(), "") ||  Objects.equals(TelefoneText.getText(), "")) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Campos Inválidos!");
            alert.setHeaderText("Campos por preencher");
            alert.setContentText("Preencha todos os campos e tente novamente!");

            alert.showAndWait();

        } else {


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

            try {
                cli_repo.save(novoCliente);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sucesso!");
                alert.setHeaderText("Registo efetuado com sucesso!");
                alert.showAndWait();



            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            RuaText.clear();
            NifText.clear();
            NomeText.clear();
            PortaText.clear();
            EmailText.clear();
            TelefoneText.clear();
            CodPostalCombo.getSelectionModel().clearSelection();

            ObservableList<ClienteEntity> clientesAtualizados = FXCollections.observableArrayList(cli_repo.findAll());
            table.setItems(clientesAtualizados);

        }
    }

    @FXML
    public void InserirCliente(ActionEvent event) throws IOException {
        Pane.setVisible(true);
        mainPanel.setEffect(new javafx.scene.effect.GaussianBlur(4.0));
        mainPanel.setDisable(true);

    }

    @FXML
    public void VoltarAtrasInserirCliente(ActionEvent event) {
        Pane.setVisible(false);
        RuaText.clear();
        NifText.clear();
        NomeText.clear();
        PortaText.clear();
        EmailText.clear();
        TelefoneText.clear();
        CodPostalCombo.getSelectionModel().clearSelection();

        EditPane.setVisible(false);
        EditRuaText.clear();
        EditNifText.clear();
        EditNomeText.clear();
        EditPortaText.clear();
        EditEmailText.clear();
        EditTelefoneText.clear();

        mainPanel.setEffect(null);
        mainPanel.setDisable(false);

        EditPane.setEffect(null);
        EditPane.setDisable(false);

    }

    @FXML
    public void ExcluirCliente() {

        if (table.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Erro!");
            alert.setHeaderText("Nenhum cliente selecionado!");
            alert.setContentText("Selecione um cliente para eliminar!");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Eliminar Cliente");
            alert.setHeaderText("Eliminar Cliente");
            alert.setContentText("Tem a certeza que pretende eliminar este cliente?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                cli_repo.deleteById(table.getSelectionModel().getSelectedItem().getId());
                ObservableList<ClienteEntity> clientesAtualizados = FXCollections.observableArrayList(cli_repo.findAll());
                table.setItems(clientesAtualizados);
            }
        }

}

    @FXML
    public void ShowEditarCliente() {

        EditPane.setVisible(true);
        mainPanel.setEffect(new javafx.scene.effect.GaussianBlur(4.0));
        EditPane.setEffect(new javafx.scene.effect.DropShadow());
        mainPanel.setDisable(true);

        EditNomeText.setText(table.getSelectionModel().getSelectedItem().getNome());
        EditNifText.setText(table.getSelectionModel().getSelectedItem().getNif().toString());
        EditRuaText.setText(table.getSelectionModel().getSelectedItem().getRua());
        EditPortaText.setText(table.getSelectionModel().getSelectedItem().getPorta().toString());
        EditEmailText.setText(table.getSelectionModel().getSelectedItem().getEmail());
        EditTelefoneText.setText(table.getSelectionModel().getSelectedItem().getTelefone());
        EditCodPostalCombo.getSelectionModel().select(table.getSelectionModel().getSelectedItem().getCodPostalByIdCodPostal().getLocalidade());

    }

    @FXML
    public void EditarCliente() {
        if(Objects.equals(EditRuaText.getText(), "") || Objects.equals(EditNifText.getText(), "") || Objects.equals(EditNomeText.getText(), "")
                && Objects.equals(EditPortaText.getText(), "") || Objects.equals(EditEmailText.getText(), "") ||  Objects.equals(EditTelefoneText.getText(), "")) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Campos Inválidos!");
            alert.setHeaderText("Campos por preencher");
            alert.setContentText("Preencha todos os campos e tente novamente!");

            alert.showAndWait();



        } else {

            ClienteEntity editCliente = table.getSelectionModel().getSelectedItem();
            editCliente.setNome(EditNomeText.getText());
            editCliente.setNif(Integer.valueOf(EditNifText.getText()));
            editCliente.setRua(EditRuaText.getText());
            editCliente.setPorta(Integer.valueOf(EditPortaText.getText()));

            CodPostalEntity codPostalEntity = cp_repo.findByNameLike(EditCodPostalCombo.getSelectionModel().getSelectedItem());

            editCliente.setIdCodPostal(codPostalEntity.getIdCodPostal());
            editCliente.setEmail(EditEmailText.getText());
            editCliente.setTelefone(EditTelefoneText.getText());
            editCliente.setCodPostalByIdCodPostal(codPostalEntity);

            try {
                cli_repo.save(editCliente);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sucesso!");
                alert.setHeaderText("Registo editado com sucesso!");
                alert.showAndWait();

                EditPane.setVisible(false);
                mainPanel.setEffect(null);
                mainPanel.setDisable(false);

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }


        }

        ObservableList<ClienteEntity> clientesAtualizados = FXCollections.observableArrayList(cli_repo.findAll());
        table.setItems(clientesAtualizados);
        table.refresh();
    }

    public class DoubleClickHandler implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {
            if (event.getClickCount() == 2) {
                ShowEditarCliente();

            }
        }
    }

}
