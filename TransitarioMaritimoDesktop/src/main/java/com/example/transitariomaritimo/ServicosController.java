package com.example.transitariomaritimo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import pt.ipvc.database.entity.ServicoEntity;

public class ServicosController {

    @FXML
    private ComboBox<String> CodPostalCombo;

    @FXML
    private TableColumn<ServicoEntity, String> Email;

    @FXML
    private TextField EmailText;

    @FXML
    private TableColumn<ServicoEntity, String> Id;

    @FXML
    private TableColumn<ServicoEntity, String> Localidade;

    @FXML
    private TableColumn<ServicoEntity, String> Nif;

    @FXML
    private TextField NifText;

    @FXML
    private TableColumn<ServicoEntity, String> Nome;

    @FXML
    private TextField NomeText;

    @FXML
    private Pane Pane;

    @FXML
    private TextField PortaText;

    @FXML
    private TextField RuaText;

    @FXML
    private TableColumn<ServicoEntity, String> Telefone;

    @FXML
    private TextField TelefoneText;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;

    @FXML
    private Pane mainPanel;

    @FXML
    private TableView<ServicoEntity> table;

    @FXML
    void EditarServico(ActionEvent event) {

    }

    @FXML
    void ExcluirServico(ActionEvent event) {

    }

    @FXML
    void ShowInserirServico(ActionEvent event) {

    }

    @FXML
    void InserirServico(ActionEvent event) {

    }

    @FXML
    void Voltar(ActionEvent event) {

    }

}