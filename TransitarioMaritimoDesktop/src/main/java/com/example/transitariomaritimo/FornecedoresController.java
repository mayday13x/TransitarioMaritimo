package com.example.transitariomaritimo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import pt.ipvc.database.entity.FornecedorEntity;

public class FornecedoresController {

    @FXML
    private ComboBox<FornecedorEntity> CodPostalCombo;

    @FXML
    private ComboBox<?> EditCodPostalCombo;

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
    private TextField EmailText;

    @FXML
    private TableColumn<?, ?> Endereco;

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
    private Button guardarButton;

    @FXML
    private Pane mainPanel;

    @FXML
    private TableView<?> table;

    @FXML
    void EditarFornecedor(ActionEvent event) {

    }

    @FXML
    void ExcluirFornecedor(ActionEvent event) {

    }

    @FXML
    void InserirFornecedor(ActionEvent event) {

    }

    @FXML
    void RegistarFornecedor(ActionEvent event) {

    }

    @FXML
    void ShowEditarFornecedor(ActionEvent event) {

    }

    @FXML
    void Voltar(ActionEvent event) {

    }

    @FXML
    void VoltarAtras(ActionEvent event) {

    }

}
