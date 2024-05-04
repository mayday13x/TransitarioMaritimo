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
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import pt.ipvc.database.entity.ArmazemEntity;
import pt.ipvc.database.entity.CargaEntity;
import pt.ipvc.database.repository.ArmazemRepository;
import pt.ipvc.database.repository.CargaRepository;

import javafx.scene.layout.Pane;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ArmazemController implements Initializable {

    public AnnotationConfigApplicationContext context;
    private ArmazemRepository armazem_repo;

    private CargaRepository carga_repo;

    @FXML
    private TextField CapacidadeMaximaText;


    @FXML
    private TextField descricaoText;

    @FXML
    private TableColumn<ArmazemEntity, String> Cap_max;

    @FXML
    private TableColumn<ArmazemEntity, String> Descricao;

    @FXML
    private TableColumn<ArmazemEntity, String> Id;

    @FXML
    private TableView<ArmazemEntity> table;

    @FXML
    private Pane PaneInserir;

    @FXML
    private Pane mainPanel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        context = new AnnotationConfigApplicationContext(AppConfig.class);

        armazem_repo = context.getBean(ArmazemRepository.class);
        ObservableList<ArmazemEntity> armazens = FXCollections.observableArrayList(armazem_repo.findAll());


        Id.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getId().toString()));
        Descricao.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDescricao()));
        Cap_max.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCapacidadeMax().toString()));



        table.setItems(armazens);


    }


    @FXML
    public void VoltarAtras(ActionEvent event) {

        try{
            Parent root = FXMLLoader.load(getClass().getResource("MenuAdminView.fxml"));
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
    public void InserirArmazem(ActionEvent event) {
        PaneInserir.setVisible(true);
        mainPanel.setEffect(new javafx.scene.effect.GaussianBlur(4.0));
        mainPanel.setDisable(true);
        mainPanel.setVisible(false);
    }

    @FXML
    public void VoltarAtrasInserirArmazem(ActionEvent event) {
        PaneInserir.setVisible(false);

        descricaoText.clear();
        CapacidadeMaximaText.clear();

        mainPanel.setEffect(null);
        mainPanel.setDisable(false);
        mainPanel.setVisible(true);
    }

    @FXML
    public void RegistarArmazem(ActionEvent event) {

        ArmazemEntity novoArmazem = new ArmazemEntity();
        novoArmazem.setCapacidadeMax(Double.valueOf(CapacidadeMaximaText.getText()));
        novoArmazem.setDescricao(descricaoText.getText());

        List<CargaEntity> cargas = carga_repo.findByArmazemID(novoArmazem.getId());
        novoArmazem.setCargasById(cargas);

        armazem_repo.save(novoArmazem);


        CapacidadeMaximaText.clear();
        descricaoText.clear();

        ObservableList<ArmazemEntity> armazensAtualizados = FXCollections.observableArrayList(armazem_repo.findAll());
        table.setItems(armazensAtualizados);



    }
    @FXML
    public void VisualizarCargas(ActionEvent event)  throws IOException{
        ArmazemEntity armazemSelecionado = table.getSelectionModel().getSelectedItem();

        if (armazemSelecionado != null) {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("CargaArmazem.fxml"));

            Parent root = loader.load();

            CargaController cargaController = loader.getController();
            cargaController.setArmazemId(armazemSelecionado.getId());
            cargaController.cargaArmazem();

            Scene regCena = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(regCena);
            stage.setTitle("Cargas");
            stage.show();

        } else {
            // Exibe uma mensagem de erro se nenhum armazém estiver selecionado
            //System.out.println("Selecione um armazém para visualizar as cargas.");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setContentText("Selecione um armazém para visualizar as cargas.");
            alert.showAndWait();
        }
    }


}
