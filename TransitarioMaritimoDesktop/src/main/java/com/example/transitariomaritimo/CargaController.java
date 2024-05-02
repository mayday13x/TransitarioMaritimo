package com.example.transitariomaritimo;

import javafx.animation.PauseTransition;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pt.ipvc.database.entity.CargaEntity;
import pt.ipvc.database.entity.ClienteEntity;
import pt.ipvc.database.repository.ArmazemRepository;
import pt.ipvc.database.repository.CargaRepository;
import pt.ipvc.database.repository.ClienteRepository;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CargaController{

    public AnnotationConfigApplicationContext context;
    private CargaRepository repo;


    @FXML
    private TableView<CargaEntity> table;
    @FXML
    private TextField IdArmazem;

    @FXML
    private TextField IdContentor;

    @FXML
    private TextField IdEstadoCarga;

    @FXML
    private TextField IdReserva;

    @FXML
    private TextField IdTipoCarga;

    @FXML
    private TextField LocalAtual;

    @FXML
    private TableColumn<CargaEntity, String> Nome;

    @FXML
    private TextField Observacoes;

    @FXML
    private TableColumn<CargaEntity, String> Peso;

    @FXML
    private TableColumn<CargaEntity, String> Quantidade;

    @FXML
    private TableColumn<CargaEntity, String> Volume;

    @FXML
    private Pane Pane;
    private int armazemId;

    private int contentorCin;

    public void setArmazemId(int armazemId) {
        this.armazemId = armazemId;
    }

    public void setContentorCin(int contentorCin) {
        this.contentorCin = contentorCin;
    }

    @FXML
    public void VoltarAtrasArmazem(ActionEvent event) throws IOException {

            Parent root = FXMLLoader.load(getClass().getResource("Armazem.fxml"));
            Scene regCena = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(regCena);
            stage.setTitle("Armazem");
            stage.show();

    }

    @FXML
    public void VoltarAtrasContentor(ActionEvent event) throws IOException {

            Parent root = FXMLLoader.load(getClass().getResource("Contentor.fxml"));
            Scene regCena = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(regCena);
            stage.setTitle("Contentor");
            stage.show();

    }

    @FXML
    public void InserirCarga(ActionEvent event) {
        Pane.setVisible(true);
    }
    @FXML
    public void RegistarCarga(ActionEvent event) {

        try{
            Parent root = FXMLLoader.load(getClass().getResource("CargaArmazem.fxml"));
            Scene regCena = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(regCena);
            stage.setTitle("Carga");
            stage.show();
        }catch (IOException ex){
            System.out.println("Erro ao acessar menu cliente: " + ex.getMessage());
        }
    }

    @FXML
    public void VoltarAtrasInserirCarga(ActionEvent event) {
        Pane.setVisible(false);
    }

    public void cargaArmazem(){
        context = new AnnotationConfigApplicationContext(AppConfig.class);
        repo = context.getBean(CargaRepository.class);

        ObservableList<CargaEntity> cargas = FXCollections.observableArrayList(repo.findByArmazemID(armazemId));

        Nome.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNome()));
        Quantidade.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getQuantidade().toString()));
        Volume.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getVolume().toString()));
        Peso.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPeso().toString()));

        table.setItems(cargas);

        table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                LocalAtual.setText(newValue.getLocalAtual());
                Observacoes.setText(newValue.getObservacoes());
                IdArmazem.setText(newValue.getIdArmazem().toString());
                IdContentor.setText(newValue.getIdContentor().toString());
                IdEstadoCarga.setText(newValue.getIdEstadoCarga().toString());
                IdReserva.setText(newValue.getIdReserva().toString());
                IdTipoCarga.setText(newValue.getIdTipoCarga().toString());
            }
        });
    }

    public void cargaContentor(){
        context = new AnnotationConfigApplicationContext(AppConfig.class);
        repo = context.getBean(CargaRepository.class);

        ObservableList<CargaEntity> cargas = FXCollections.observableArrayList(repo.findByContentorCin(contentorCin));

        Nome.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNome()));
        Quantidade.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getQuantidade().toString()));
        Volume.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getVolume().toString()));
        Peso.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPeso().toString()));

        table.setItems(cargas);

        table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                LocalAtual.setText(newValue.getLocalAtual());
                Observacoes.setText(newValue.getObservacoes());
                IdArmazem.setText(newValue.getIdArmazem().toString());
                IdContentor.setText(newValue.getIdContentor().toString());
                IdEstadoCarga.setText(newValue.getIdEstadoCarga().toString());
                IdReserva.setText(newValue.getIdReserva().toString());
                IdTipoCarga.setText(newValue.getIdTipoCarga().toString());
            }
        });
    }

}
