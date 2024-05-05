package com.example.transitariomaritimo;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pt.ipvc.database.entity.TransportemaritimoEntity;
import pt.ipvc.database.repository.TransportemaritimoRepository;

import java.net.URL;
import java.util.ResourceBundle;

public class TransporteController implements Initializable {

    public AnnotationConfigApplicationContext context;
    public TransportemaritimoRepository transportemaritim_repo;


    @FXML
    private TableColumn<TransportemaritimoEntity, String> Destino;



    @FXML
    private TableColumn<TransportemaritimoEntity, String> Id;

    @FXML
    private TableColumn<TransportemaritimoEntity, String> Imo;



    @FXML
    private TableColumn<TransportemaritimoEntity, String> Origem;



    @FXML
    private TableView<TransportemaritimoEntity> Table;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        context = new AnnotationConfigApplicationContext(AppConfig.class);
        transportemaritim_repo = context.getBean(TransportemaritimoRepository.class);

        ObservableList<TransportemaritimoEntity> transportemaritimo = FXCollections.observableArrayList(transportemaritim_repo.findAll());

        Id.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getId().toString()));
        Imo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getImo()));
        Origem.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPortoOrigem()));
        Destino.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPortoDestino()));
        Table.setItems(transportemaritimo);
    }
}
