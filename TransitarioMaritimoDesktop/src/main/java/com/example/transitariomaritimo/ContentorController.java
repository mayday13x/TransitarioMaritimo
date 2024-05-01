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
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pt.ipvc.database.entity.*;
import pt.ipvc.database.repository.*;
import javafx.scene.layout.Pane;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ContentorController implements Initializable {

    public AnnotationConfigApplicationContext context;
    private ContentorRepository contentor_repo;

    private TipoContentorRepository tipo_contentor_repo;
    private EstadoContentorRepository estado_contentor_repo;

    @FXML
    private TableColumn<ContentorEntity, String> Cin;
    @FXML
    private TableColumn<ContentorEntity, String> Capacidade;

    @FXML
    private TextField IdEstadoContentor;

    @FXML
    private TableColumn<ContentorEntity, String> LocalAtual;

    @FXML
    private TableColumn<ContentorEntity, String> PesoMaximo;

    @FXML
    private TextField TipoContentor;

    @FXML
    private TableView<ContentorEntity> table;

    @FXML
    private Pane Pane;

    @FXML
    private ComboBox<String> EstadoContentorCombo;

    @FXML
    private ComboBox<String> TipoContentorCombo;

    @FXML
    private TextField PesoMaximoText;

    @FXML
    private TextField LocalAtualText;

    @FXML
    private TextField CapacidadeText;

    @FXML
    public void InserirContentor(ActionEvent event) {
        Pane.setVisible(true);
    }

    @FXML
    public void RegistarContentor(ActionEvent event) {

       ContentorEntity novoContentor = new ContentorEntity();
       novoContentor.setCapacidade(Double.valueOf(CapacidadeText.getText()));
       novoContentor.setLocalAtual(LocalAtualText.getText());
       novoContentor.setPesoMax(Double.valueOf(PesoMaximoText.getText()));

        TipoContentorEntity tipoContentor = tipo_contentor_repo.findByNameLike(TipoContentorCombo.getSelectionModel().getSelectedItem());
        EstadoContentorEntity estadoContentor = estado_contentor_repo.findByNameLike(EstadoContentorCombo.getSelectionModel().getSelectedItem());

        novoContentor.setTipoContentor(tipoContentor.getId());
        novoContentor.setIdEstadoContentor(estadoContentor.getId());

        contentor_repo.save(novoContentor);

        CapacidadeText.clear();
        PesoMaximoText.clear();
        LocalAtualText.clear();

        ObservableList<ContentorEntity> contentoresAtualizados = FXCollections.observableArrayList(contentor_repo.findAll());
        table.setItems(contentoresAtualizados);
    }

    @FXML
    public void VoltarAtrasInserirContentor(ActionEvent event) {
        Pane.setVisible(false);
    }

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
            System.out.println("Erro ao acessar menu: " + ex.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        context = new AnnotationConfigApplicationContext(AppConfig.class);

        contentor_repo = context.getBean(ContentorRepository.class);
        ObservableList<ContentorEntity> contentores = FXCollections.observableArrayList(contentor_repo.findAll());

        try {
            Cin.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCin().toString()));
            Capacidade.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCapacidade().toString()));
            PesoMaximo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPesoMax().toString()));
            LocalAtual.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getLocalAtual()));

            table.setItems(contentores);

            table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    IdEstadoContentor.setText(newValue.getIdEstadoContentor().toString());
                    TipoContentor.setText(newValue.getTipoContentor().toString());
                }
            });


            context = new AnnotationConfigApplicationContext(AppConfig.class);
            contentor_repo = context.getBean(ContentorRepository.class);
            tipo_contentor_repo = context.getBean(TipoContentorRepository.class);
            estado_contentor_repo = context.getBean(EstadoContentorRepository.class);


            ObservableList<TipoContentorEntity> tiposConstentor = FXCollections.observableArrayList(tipo_contentor_repo.findAll());
            for(TipoContentorEntity tipo : tiposConstentor){
                TipoContentorCombo.getItems().addAll(tipo.getDescricao());
            }

            ObservableList<EstadoContentorEntity> estadoContentor = FXCollections.observableArrayList(estado_contentor_repo.findAll());
            for(EstadoContentorEntity estado : estadoContentor){
                EstadoContentorCombo.getItems().addAll(estado.getDescricao());
            }
        }catch (Exception ex){
            System.out.println("Erro no Contentor: " + ex.getMessage());
        }


    }
}
