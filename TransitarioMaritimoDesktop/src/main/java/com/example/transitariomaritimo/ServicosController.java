package com.example.transitariomaritimo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class ServicosController {

    @FXML
    private ComboBox<?> CodPostalCombo;

    @FXML
    private TableColumn<?, ?> Email;

    @FXML
    private TextField EmailText;

    @FXML
    private TableColumn<?, ?> Id;

    @FXML
    private TableColumn<?, ?> Localidade;

    @FXML
    private TableColumn<?, ?> Nif;

    @FXML
    private TextField NifText;

    @FXML
    private TableColumn<?, ?> Nome;

    @FXML
    private TextField NomeText;

    @FXML
    private Pane Pane;

    @FXML
    private TextField PortaText;

    @FXML
    private TextField RuaText;

    @FXML
    private TableColumn<?, ?> Telefone;

    @FXML
    private TextField TelefoneText;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;

    @FXML
    private Pane mainPanel;

    @FXML
    private TableView<?> table;

    @FXML
    void EditarCliente(ActionEvent event) {

    }

    @FXML
    void ExcluirCliente(ActionEvent event) {

    }

    @FXML
    void InserirCliente(ActionEvent event) {

    }

    @FXML
    void RegistarCliente(ActionEvent event) {

    }

    @FXML
    void VoltarAtrasInserirCliente(ActionEvent event) {

    }

}