/*package com.example.transitariomaritimo;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pt.ipvc.database.entity.TransportemaritimoEntity;
import pt.ipvc.database.repository.TransportemaritimoRepository;

import java.net.URL;
import java.util.ResourceBundle;

public class TransporteController implements Initializable {

    public AnnotationConfigApplicationContext context;
    public TransportemaritimoRepository transportemaritim_repo;
    public TransporteterrestreRepository transporteterrestre_repo;

    @FXML
    private TableColumn<TransportemaritimoEntity, String> DestinoTm;

    @FXML
    private TableColumn<TransporteterrestreEntity, String> DestinoTr;

    @FXML
    private TableColumn<TransporteterrestreEntity, String> IdTr;

    @FXML
    private TableColumn<TransportemaritimoEntity, String> IdTm;

    @FXML
    private TableColumn<TransportemaritimoEntity, String> Imo;

    @FXML
    private TableColumn<TransporteterrestreEntity, String> Matricula;

    @FXML
    private TableColumn<TransportemaritimoEntity, String> OrigemTm;

    @FXML
    private TableColumn<TransporteterrestreEntity, String> OrigemTr;

    @FXML
    private TableView<TransportemaritimoEntity> TableTm;

    @FXML
    private TableView<TransporteterrestreEntity> TableTr;

    @FXML
    private Pane SecondPane;

    @FXML
    private ComboBox<String> TipoTranporteCombo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TipoTranporteCombo.getItems().addAll("Transporte Maritimo", "TransporteTerrestre");

        context = new AnnotationConfigApplicationContext(AppConfig.class);
        transportemaritim_repo = context.getBean(TransportemaritimoRepository.class);

        ObservableList<TransportemaritimoEntity> transportemaritimo = FXCollections.observableArrayList(transportemaritim_repo.findAll());

        IdTm.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getId().toString()));
        Imo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getImo()));
        OrigemTm.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPortoOrigem()));
        DestinoTm.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPortoDestino()));



        transporteterrestre_repo = context.getBean(TransporteterrestreRepository.class);
        ObservableList<TransporteterrestreEntity> transporteterrestre = FXCollections.observableArrayList(transporteterrestre_repo.findAll());

        IdTr.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getId().toString()));
        Matricula.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getId().toString()));
        OrigemTr.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getOrigem()));
        DestinoTr.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDestino()));


        TipoTranporteCombo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {

                if( oldValue== null && newValue.equals("Transporte Maritimo")){
                    TableTm.setItems(transportemaritimo);
                    TableTr.setItems(transporteterrestre);
                    SecondPane.setVisible(true);
                    TableTr.setVisible(false);
                }else if(oldValue == null && newValue.equals("TransporteTerrestre")){
                    TableTr.setItems(transporteterrestre);
                    TableTm.setItems(transportemaritimo);
                    SecondPane.setVisible(false);
                    TableTr.setVisible(true);
                }


                if (newValue.equals("Transporte Maritimo")) {
                    SecondPane.setVisible(true);
                    TableTr.setVisible(false);
                } else if (newValue.equals("TransporteTerrestre")) {
                    SecondPane.setVisible(false);
                    TableTr.setVisible(true);
                }
            }
        });

    }
}*/
