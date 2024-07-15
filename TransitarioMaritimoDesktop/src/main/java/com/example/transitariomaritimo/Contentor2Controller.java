package com.example.transitariomaritimo;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.util.StringConverter;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pt.ipvc.database.entity.*;
import pt.ipvc.database.repository.*;

import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class Contentor2Controller implements Initializable {

    // repositories

    public AnnotationConfigApplicationContext context;
    private ArmazemRepository armazem_repo;
    private ContentorRepository contentor_repo;
    private CargaRepository carga_repo;

    private EstadoContentorRepository estado_contentor_repo;

    private TipoContentorRepository tipo_contentor_repo;


    @FXML
    private Pane mainPanel;

    @FXML
    private ComboBox<ArmazemEntity> ArmazemCombo;

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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        context = new AnnotationConfigApplicationContext(AppConfig.class);

        armazem_repo = context.getBean(ArmazemRepository.class);
        contentor_repo = context.getBean(ContentorRepository.class);
        carga_repo = context.getBean(CargaRepository.class);
        tipo_contentor_repo = context.getBean(TipoContentorRepository .class);
        estado_contentor_repo = context.getBean(EstadoContentorRepository.class);

        ObservableList<ArmazemEntity> armazens = FXCollections.observableArrayList(armazem_repo.findAll());
        ArmazemCombo.setConverter(new StringConverter<ArmazemEntity>() {
            @Override
            public String toString(ArmazemEntity armazem) {
                return armazem != null ? armazem.getDescricao() : "";
            }

            @Override
            public ArmazemEntity fromString(String string) {
                return ArmazemCombo.getItems().stream().filter(armazem ->
                        armazem.getDescricao().equals(string)).findFirst().orElse(null);
            }
        });

        // Configurar o StringConverter para FuncTransporteEdit
        ArmazemCombo.setConverter(new StringConverter<ArmazemEntity>() {
            @Override
            public String toString(ArmazemEntity armazem) {
                return armazem != null ? armazem.getDescricao() : "";
            }

            @Override
            public ArmazemEntity fromString(String string) {
                return ArmazemCombo.getItems().stream().filter(funcionario ->
                        funcionario.getDescricao().equals(string)).findFirst().orElse(null);
            }
        });

        ObservableList<TipoContentorEntity> tiposContentor = FXCollections.observableArrayList(tipo_contentor_repo.findAll());
        TipoContentorCombo.setItems(tiposContentor);
        TipoContentorCombo.setConverter(new StringConverter<TipoContentorEntity>() {
            @Override
            public String toString(TipoContentorEntity tipoContentor) {
                return tipoContentor != null ? tipoContentor.getDescricao() : "";
            }

            @Override
            public TipoContentorEntity fromString(String string) {
                return TipoContentorCombo.getItems().stream().filter(tipoContentor ->
                        tipoContentor.getDescricao().equals(string)).findFirst().orElse(null);
            }
        });



        ObservableList<ArmazemEntity> armazensDesc = FXCollections.observableArrayList(armazem_repo.findAll());
        ArmazemCombo.setItems(armazensDesc);
        ArmazemCombo.getSelectionModel().selectFirst();

        // Adicionando listener na seleção da TableView TContentores
            TContentores.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    atualizarInfoCargas();
                    removerContentorButton.setDisable(false);

                    double totalVolumes = contentor_repo.sumVolumes(TContentores.getSelectionModel().getSelectedItem().getCin());
                    capacidadeLabel.setText(totalVolumes + " / " + TContentores.getSelectionModel().getSelectedItem().getCapacidade());

                    double totalPesos = contentor_repo.sumPesos(TContentores.getSelectionModel().getSelectedItem().getCin());
                    pesoLabel.setText(totalPesos + " / " + TContentores.getSelectionModel().getSelectedItem().getPesoMax());


                } else {
                    capacidadeLabel.setText("0");
                    pesoLabel.setText("0");
                    removerContentorButton.setDisable(true);
                }
            });

            TipoContentorCombo.getSelectionModel().selectFirst();

    }

    @FXML
    public void atualizarInfoContentores() {

        ArmazemEntity armazemSelecionado = ArmazemCombo.getSelectionModel().getSelectedItem();

        if(armazemSelecionado == null) {
            ArmazemCombo.getSelectionModel().selectFirst();
        }

        assert armazemSelecionado != null;
        ObservableList<ContentorEntity> contentores = FXCollections.observableArrayList(contentor_repo.findByIdArmazem(armazemSelecionado.getId()));

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
    public void showInserirContentor() {

        mainPanel.setEffect(new javafx.scene.effect.GaussianBlur(4.0));
        PaneInserir.setEffect(new javafx.scene.effect.DropShadow());
        PaneInserir.setVisible(true);
        mainPanel.setDisable(true);

    }

    public void closeInserirContentor() {
        mainPanel.setEffect(null);
        PaneInserir.setVisible(false);
        mainPanel.setDisable(false);
        mainPanel.setVisible(true);
    }

    @FXML
    void RegistarContentor(ActionEvent event) {

        if (Objects.equals(PesoMaximo.getText(), "") || Objects.equals(CapacidadeText.getText(), "") ||
        Objects.equals(TipoContentorCombo.getValue(), null)) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Campos Inválidos!");
            alert.setHeaderText("Campos por preencher");
            alert.setContentText("Preencha todos os campos e tente novamente!");

            alert.showAndWait();

        } else {

            ContentorEntity newContentor = new ContentorEntity();
            newContentor.setCapacidade(Double.parseDouble(CapacidadeText.getText()));
            newContentor.setPesoMax(Double.parseDouble(PesoMaximoText.getText()));
            newContentor.setTipoContentor(TipoContentorCombo.getSelectionModel().getSelectedItem().getId());
            newContentor.setTipoContentorByTipoContentor(TipoContentorCombo.getSelectionModel().getSelectedItem());

            newContentor.setIdArmazem(ArmazemCombo.getSelectionModel().getSelectedItem().getId());
            newContentor.setArmazemByIdArmazem(ArmazemCombo.getSelectionModel().getSelectedItem());

            newContentor.setIdEstadoContentor(2); //em consolidacao
            EstadoContentorEntity estadoContentor = estado_contentor_repo.findByNameLike("Em consolidação");
            newContentor.setEstadoContentorByIdEstadoContentor(estadoContentor);

            newContentor.setLocalAtual(ArmazemCombo.getSelectionModel().getSelectedItem().getDescricao());

            try{
                contentor_repo.save(newContentor);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Contentor Registado!");
                alert.setHeaderText("Contentor registado com sucesso!");
                alert.showAndWait();

                CapacidadeText.clear();
                PesoMaximoText.clear();
                TipoContentorCombo.getSelectionModel().clearSelection();

                closeInserirContentor();

            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro ao Registar!");
                alert.setHeaderText("Ocorreu um erro ao registar o contentor!");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }

            atualizarInfoContentores();
            atualizarInfoCargas();
        }
    }

    @FXML
    public void RemoverContentor(){

        ContentorEntity contentorSelecionado = TContentores.getSelectionModel().getSelectedItem();
        long countCargas = contentor_repo.countByIdContentor(contentorSelecionado.getCin());

        if (countCargas > 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro!");
            alert.setHeaderText("Não é possível remover o contentor!");
            alert.setContentText("O contentor possui cargas associadas.");
            alert.showAndWait();

        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Remover contentor ?");
            alert.setHeaderText("Pretende remover o contentor selecionado ?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                   // armazem_repo.deleteById(contentorSelecionado.getCin());
                    contentor_repo.deleteById(contentorSelecionado.getCin());

                } catch (Exception e) {
                    Alert alert2 = new Alert(Alert.AlertType.ERROR);
                    alert2.setTitle("Erro ao Remover!");
                    alert2.setHeaderText("Ocorreu um erro ao remover o contentor!");
                    alert2.setContentText(e.getMessage());

                    System.out.println(e.getMessage());

                    alert2.showAndWait();
                }

                atualizarInfoContentores();
                atualizarInfoCargas();
            }
        }

    }

    @FXML
    void RegistarSaida(ActionEvent event) {

    }

}
