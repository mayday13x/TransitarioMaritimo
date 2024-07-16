package com.example.transitariomaritimo;

import com.example.transitariomaritimo.session.Current_Session;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.util.StringConverter;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraRepositoriesAutoConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pt.ipvc.database.entity.*;
import pt.ipvc.database.repository.*;

import java.net.URL;
import java.util.*;

public class ContentorTransporteController implements Initializable {

    // repositories

    public AnnotationConfigApplicationContext context;
    private ArmazemRepository armazem_repo;
    private ContentorRepository contentor_repo;
    private CargaRepository carga_repo;

    private EstadoContentorRepository estado_contentor_repo;

    private TipoContentorRepository tipo_contentor_repo;


    @FXML
    private Pane mainPanel;

    // -- Contentores elements
    @FXML
    private TableView<ContentorEntity> TContentores;

    @FXML
    private TableColumn<ContentorEntity, String> Cin;

    @FXML
    private TableColumn<ContentorEntity, String> Capacidade;

    @FXML
    private TableColumn<ContentorEntity, String> PesoMaximo;

    @FXML
    private TableColumn<ContentorEntity, String> Tipo;

    @FXML
    private TableColumn<ContentorEntity, String> Estado;

    @FXML
    private Label capacidadeLabel;

    @FXML
    private Label pesoLabel;

    @FXML
    private ComboBox<EstadoContentorEntity> mudarEstadoCombo;

    @FXML
    private Button mudarEstadoButton;

    @FXML
    private Label mudarEstadoLabel;

    // Cargas elements

    @FXML
    private TableView<CargaEntity> TCargaContentores;

    @FXML
    private TableColumn<CargaEntity, String> Id;

    @FXML
    private TableColumn<CargaEntity, String> IdReserva;

    @FXML
    private TableColumn<CargaEntity, String> Designacao;

    @FXML
    private TableColumn<CargaEntity, String> Quantidade;

    @FXML
    private TableColumn<CargaEntity, String> Volume;

    @FXML
    private TableColumn<CargaEntity, String> Peso;

    @FXML
    private TableColumn<CargaEntity, String> TipoCarga;

// panel Inserir contentor

    @FXML
    private Pane PaneInserir;

    @FXML
    private TextField CapacidadeText;

    @FXML
    private TextField PesoMaximoText;

    @FXML
    private ComboBox<TipoContentorEntity> TipoContentorCombo;

    // remover contentor

    @FXML
    private Button removerContentorButton;

    @FXML
    private Button removerCargaButton;

    //inserir carga

    @FXML
    private Pane PaneInserirCarga;
    @FXML
    private TableView<CargaEntity> TCargasAdd;
    @FXML
    private TableColumn<CargaEntity, String> IdCargaAdd;
    @FXML
    private TableColumn<CargaEntity, String> IdReservaCargaAdd;
    @FXML
    private TableColumn<CargaEntity, String> designacaoCargaAdd;
    @FXML
    private TableColumn<CargaEntity, String> qtdCargaAdd;
    @FXML
    private TableColumn<CargaEntity, String> volumeCargaAdd;
    @FXML
    private TableColumn<CargaEntity, String> pesoCargaAdd;
    @FXML
    private TableColumn<CargaEntity, String> tipoCargaAdd;

    @FXML
    private Button addCargaButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        context = new AnnotationConfigApplicationContext(AppConfig.class);

        armazem_repo = context.getBean(ArmazemRepository.class);
        contentor_repo = context.getBean(ContentorRepository.class);
        carga_repo = context.getBean(CargaRepository.class);
        tipo_contentor_repo = context.getBean(TipoContentorRepository .class);
        estado_contentor_repo = context.getBean(EstadoContentorRepository.class);

        TCargaContentores.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        ArrayList<Integer> in = new ArrayList<>();
        in.add(1); in.add(3);
        ObservableList<EstadoContentorEntity> estadosContentor = FXCollections.observableArrayList(estado_contentor_repo.findAllById(in));

        mudarEstadoCombo.setItems(estadosContentor);
        mudarEstadoCombo.setConverter(new StringConverter<EstadoContentorEntity>() {
            @Override
            public String toString(EstadoContentorEntity estadoContentor) {
                return estadoContentor != null ? estadoContentor.getDescricao() : "";
            }

            @Override
            public EstadoContentorEntity fromString(String string) {
                return mudarEstadoCombo.getItems().stream().filter(estadoContentor ->
                        estadoContentor.getDescricao().equals(string)).findFirst().orElse(null);
            }
        });


        // Atualiza o estado do botão mudarEstadoButton com base na seleção da ComboBox e TableView
        mudarEstadoCombo.valueProperty().addListener((obs, oldSelection, newSelection) -> {
            ContentorEntity selectedContentor = TContentores.getSelectionModel().getSelectedItem();
            if (selectedContentor != null && newSelection != null) {
                mudarEstadoButton.setDisable(newSelection.equals(selectedContentor.getEstadoContentorByIdEstadoContentor()));
            }
        });


        // Adicionando listener na seleção da TableView TContentores
        TContentores.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {

            if (newSelection != null) {

                atualizarInfoCargas();

                mudarEstadoCombo.setDisable(false);
                mudarEstadoLabel.setDisable(false);

                mudarEstadoCombo.setValue(TContentores.getSelectionModel().getSelectedItem().getEstadoContentorByIdEstadoContentor());

                Double totalVolumes = contentor_repo.sumVolumes(newSelection.getCin());
                Double totalPesos = contentor_repo.sumPesos(newSelection.getCin());

                capacidadeLabel.setText((totalVolumes != null ? totalVolumes : 0.0) + " / " + newSelection.getCapacidade() + " m³");
                pesoLabel.setText((totalPesos != null ? totalPesos : 0.0) + " / " + newSelection.getPesoMax() + " Kg");

            } else {
                capacidadeLabel.setText("0 m³");
                pesoLabel.setText("0 Kg");
                mudarEstadoCombo.setDisable(true);
                mudarEstadoLabel.setDisable(true);

                mudarEstadoCombo.getSelectionModel().clearSelection();

            }
        });
        atualizarInfoContentores();
    }

    public void atualizarLabelsCapacidade(){

        ContentorEntity contentorSelecionado = TContentores.getSelectionModel().getSelectedItem();

        Double totalVolumes = contentor_repo.sumVolumes(contentorSelecionado.getCin());
        Double totalPesos = contentor_repo.sumPesos(contentorSelecionado.getCin());

        capacidadeLabel.setText((totalVolumes != null ? totalVolumes : 0.0) + " / " + contentorSelecionado.getCapacidade() + " m³");
        pesoLabel.setText((totalPesos != null ? totalPesos : 0.0) + " / " + contentorSelecionado.getPesoMax() + " Kg");
    }

    @FXML
    public void atualizarInfoContentores() {

        ObservableList<ContentorEntity> contentores = FXCollections.observableArrayList(contentor_repo.findByNotEmConsolidacao());

        Cin.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCin().toString()));
        Capacidade.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCapacidade().toString()));
        PesoMaximo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPesoMax().toString()));
        Tipo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTipoContentorByTipoContentor().getDescricao()));
        Estado.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEstadoContentorByIdEstadoContentor().getDescricao()));

        TContentores.setItems(contentores);
        if (TContentores.getSelectionModel().getSelectedItem() == null) {
            TCargaContentores.getItems().clear();
        }

    }

    @FXML
    public void atualizarInfoCargas() {
        ContentorEntity contentorSelecionado = TContentores.getSelectionModel().getSelectedItem();

        if(contentorSelecionado == null) {
            TContentores.getSelectionModel().selectFirst();
        }

        assert contentorSelecionado != null;
        ObservableList<CargaEntity> cargas = FXCollections.observableArrayList(contentor_repo.findByIdContentor(contentorSelecionado.getCin()));

        Id.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getId().toString()));
        IdReserva.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getIdReserva().toString()));
        Designacao.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNome()));
        Quantidade.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getQuantidade().toString()));
        Volume.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getVolume().toString()));
        Peso.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPeso().toString()));
        TipoCarga.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTipoCargaByIdTipoCarga().getDescricao()));

        TCargaContentores.setItems(cargas);

    }


    @FXML
    public void editarEstadoContentor(){

       // ArmazemEntity armazemSelecionado = ArmazemCombo.getSelectionModel().getSelectedItem();
        ContentorEntity contentorSelecionado = TContentores.getSelectionModel().getSelectedItem();
        EstadoContentorEntity estadoSelecionado = mudarEstadoCombo.getSelectionModel().getSelectedItem();

        Long countCargas = contentor_repo.countByIdContentor(contentorSelecionado.getCin());

        if(countCargas > 0) {

            try {

                contentorSelecionado.setIdEstadoContentor(estadoSelecionado.getId());
                contentorSelecionado.setEstadoContentorByIdEstadoContentor(estadoSelecionado);
                contentor_repo.save(contentorSelecionado);


            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Erro!");
                alert.setHeaderText("Ocorreu um erro ao mudar o estado do contentor!");
                alert.setContentText("Ocorreu um erro ao mudar o estado do contentor, tente novamente!");
                alert.showAndWait();
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Contentor vazio!");
            alert.setHeaderText("O contentor selecionado encontra-se vazio!");
            alert.setContentText("Não é possível realizar a operação pois o contentor selecionado encontra-se vazio!");
            alert.showAndWait();
        }

        TContentores.getItems().clear();
        ObservableList<ContentorEntity> contentores = FXCollections.observableArrayList(contentor_repo.findByNotEmConsolidacao());
        TContentores.setItems(contentores);
        TContentores.getSelectionModel().select(contentorSelecionado);
    }


}
