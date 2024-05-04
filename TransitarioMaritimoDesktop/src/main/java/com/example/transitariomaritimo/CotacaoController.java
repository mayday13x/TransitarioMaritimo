package com.example.transitariomaritimo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import pt.ipvc.database.entity.CotacaoEntity;

public class CotacaoController {

    @FXML
    private Pane CriarPane;

    @FXML
    private TableColumn<CotacaoEntity, String> Data;

    @FXML
    private Pane EditarPane;

    @FXML
    private TableColumn<CotacaoEntity, String> Estado;

    @FXML
    private TableColumn<CotacaoEntity, String> Funcionario;

    @FXML
    private TableColumn<CotacaoEntity, String> IdCliente;

    @FXML
    private TableColumn<CotacaoEntity, String> Utilizador;

    @FXML
    private TableColumn<CotacaoEntity, String> ValorTotal;

    @FXML
    private Button deleteButton;

    @FXML
    private Button guardarButton;

    @FXML
    private Pane mainPane;

    @FXML
    private TableView<CotacaoEntity> table_cotacoes;

    @FXML
    void EditarCliente(ActionEvent event) {

    }

    @FXML
    void InserirCotacao(ActionEvent event) {
        CriarPane.setVisible(true);
        mainPane.setEffect(new javafx.scene.effect.GaussianBlur(4.0));
        mainPane.setDisable(true);
    }

    @FXML
    void RegistarCotacao(ActionEvent event) {

    }

    @FXML
    void VoltarAtrasInserirCliente(ActionEvent event) {
        CriarPane.setVisible(false);
        mainPane.setEffect(null);
        mainPane.setDisable(false);
    }

}
