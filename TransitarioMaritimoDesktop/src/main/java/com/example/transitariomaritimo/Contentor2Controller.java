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
import org.springframework.boot.autoconfigure.data.cassandra.CassandraRepositoriesAutoConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pt.ipvc.database.entity.*;
import pt.ipvc.database.repository.*;

import java.net.URL;
import java.util.List;
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
    private Button adicionarCargaButton;

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

        TCargasAdd.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        TCargaContentores.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

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

        ObservableList<EstadoContentorEntity> estadosContentor = FXCollections.observableArrayList(estado_contentor_repo.findAll());
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

        ObservableList<ArmazemEntity> armazensDesc = FXCollections.observableArrayList(armazem_repo.findAll());
        ArmazemCombo.setItems(armazensDesc);
        ArmazemCombo.getSelectionModel().selectFirst();

        atualizarInfoContentores();

        // Adicionando listener na seleção da TableView TContentores
            TContentores.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {



                if (newSelection != null) {

                   // adicionarCargaButton.setDisable(newSelection.getIdEstadoContentor() != 2);
                    if (newSelection.getIdEstadoContentor() != 2) {
                        adicionarCargaButton.setDisable(true);
                        removerCargaButton.setDisable(true);
                        TCargaContentores.setDisable(true);
                        TCargaContentores.getSelectionModel().clearSelection();
                    } else {
                        TCargaContentores.setDisable(false);
                        adicionarCargaButton.setDisable(false);
                    }

                    atualizarInfoCargas();

                    removerContentorButton.setDisable(false);
                   // adicionarCargaButton.setDisable(false);
                    mudarEstadoCombo.setDisable(false);
                    mudarEstadoLabel.setDisable(false);
                    //mudarEstadoButton.setDisable(false);

                    mudarEstadoCombo.setValue(TContentores.getSelectionModel().getSelectedItem().getEstadoContentorByIdEstadoContentor());

                    Double totalVolumes = contentor_repo.sumVolumes(newSelection.getCin());
                    Double totalPesos = contentor_repo.sumPesos(newSelection.getCin());

                    capacidadeLabel.setText((totalVolumes != null ? totalVolumes : 0.0) + " / " + newSelection.getCapacidade() + " m³");
                    pesoLabel.setText((totalPesos != null ? totalPesos : 0.0) + " / " + newSelection.getPesoMax() + " Kg");

                } else {
                    capacidadeLabel.setText("0 m³");
                    pesoLabel.setText("0 Kg");
                    removerContentorButton.setDisable(true);
                    adicionarCargaButton.setDisable(true);
                    mudarEstadoCombo.setDisable(true);
                    mudarEstadoLabel.setDisable(true);

                    mudarEstadoCombo.getSelectionModel().clearSelection();
                    //mudarEstadoButton.setDisable(true);

                }
            });

            TipoContentorCombo.getSelectionModel().selectFirst();


        // Adicionando listener na seleção da TableView TCargasAdd
        TCargasAdd.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                addCargaButton.setDisable(false);
            } else {
                addCargaButton.setDisable(true);
            }
        });

        // Adicionando listener na seleção da TableView TCargaContentores
        TCargaContentores.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                removerCargaButton.setDisable(false);
            } else {
                removerCargaButton.setDisable(true);
            }
        });

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

        // atualizar tabela de cargas do panel de adicionar carga ao contentor

        ObservableList<CargaEntity> cargasArmazem = FXCollections.observableArrayList(carga_repo.findByIdArmazemAndContentorNull(ArmazemCombo.getSelectionModel().getSelectedItem().getId()));

        IdCargaAdd.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getId().toString()));
        IdReservaCargaAdd.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getIdReserva().toString()));
        designacaoCargaAdd.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNome()));
        qtdCargaAdd.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getQuantidade().toString()));
        volumeCargaAdd.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getVolume().toString()));
        pesoCargaAdd.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPeso().toString()));
        tipoCargaAdd.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTipoCargaByIdTipoCarga().getDescricao()));

        TCargasAdd.setItems(cargasArmazem);

    }

    @FXML
    public void showInserirContentor() {

        mainPanel.setEffect(new javafx.scene.effect.GaussianBlur(4.0));
        PaneInserir.setEffect(new javafx.scene.effect.DropShadow());
        PaneInserir.setVisible(true);
        mainPanel.setDisable(true);

        TipoContentorCombo.getSelectionModel().selectFirst();

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
    public void showAdicionarCarga() {

        mainPanel.setEffect(new javafx.scene.effect.GaussianBlur(4.0));
        PaneInserirCarga.setEffect(new javafx.scene.effect.DropShadow());
        PaneInserirCarga.setVisible(true);
        mainPanel.setDisable(true);

        ObservableList<CargaEntity> cargasArmazem = FXCollections.observableArrayList(carga_repo.findByIdArmazemAndContentorNull(ArmazemCombo.getSelectionModel().getSelectedItem().getId()));

        IdCargaAdd.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getId().toString()));
        IdReservaCargaAdd.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getIdReserva().toString()));
        designacaoCargaAdd.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNome()));
        qtdCargaAdd.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getQuantidade().toString()));
        volumeCargaAdd.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getVolume().toString()));
        pesoCargaAdd.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPeso().toString()));
        tipoCargaAdd.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTipoCargaByIdTipoCarga().getDescricao()));

        TCargasAdd.setItems(cargasArmazem);

    }

    @FXML
    public void closeAdicionarCarga() {
        mainPanel.setEffect(null);
        PaneInserirCarga.setVisible(false);
        mainPanel.setDisable(false);
        mainPanel.setVisible(true);
    }

    @FXML
    public void AdicionarCarga(){

        List<CargaEntity> cargasSeleccionadas = TCargasAdd.getSelectionModel().getSelectedItems();
        //ArmazemEntity armazemSelecionado = TContentores.getSelectionModel().getSelectedItem();
        ContentorEntity contentorSeleccionado = TContentores.getSelectionModel().getSelectedItem();

        double totalVolume = 0.0;
        double totalPeso = 0.0;

        for (CargaEntity carga : cargasSeleccionadas) {
            totalVolume = totalVolume + carga.getVolume();
            totalPeso = totalPeso + carga.getPeso();
        }

        if (totalVolume > contentorSeleccionado.getCapacidade() || totalPeso > contentorSeleccionado.getPesoMax() ){

            if (totalVolume > contentorSeleccionado.getCapacidade() && totalPeso > contentorSeleccionado.getPesoMax()){

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Capacidade e Peso Excedidos!");
                alert.setHeaderText("O volume total das cargas excede a capacidade do contentor e o peso total das cargas excede o peso máximo do contentor!");
                alert.setContentText("O volume total das cargas excede a capacidade do contentor e o peso total das cargas excede o peso máximo do contentor.");
                alert.showAndWait();

            } else if(totalVolume > contentorSeleccionado.getCapacidade()) {

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Capacidade Excedida!");
                alert.setHeaderText("O volume total das cargas excede a capacidade do contentor");
                alert.setContentText("O volume total das cargas excede a capacidade do contentor.");
                alert.showAndWait();

            } else if(totalPeso > contentorSeleccionado.getPesoMax()) {

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Peso Excedido!");
                alert.setHeaderText("O peso total das cargas excede o peso máximo do contentor!");
                alert.setContentText("O peso total das cargas excede o peso máximo do contentor.");
                alert.showAndWait();
            }


        } else {
            try {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Adicionar carga(s) selecionada(s)?");
                alert.setHeaderText("Adicionar Carga ?");
                alert.setContentText("A adicionar a(s) carga(s) selecionada(s)");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    for (CargaEntity carga : cargasSeleccionadas) {

                        carga.setIdContentor(contentorSeleccionado.getCin());
                        carga.setContentorByIdContentor(contentorSeleccionado);
                        carga.setLocalAtual("Contentor");
                        carga_repo.save(carga);
                    }
                }
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Erro!");
                alert.setHeaderText("Ocorreu um erro ao adicionar a carga!");
                alert.setContentText("Ocorreu um erro ao adicionar a carga, tente novamente!");
                alert.showAndWait();
            }
        }

        atualizarInfoCargas();
        atualizarLabelsCapacidade();

    }

    @FXML
    public void RemoverCarga() {

        List<CargaEntity> cargasSelecionadas = TCargaContentores.getSelectionModel().getSelectedItems();
        ArmazemEntity armazemSelecionado = ArmazemCombo.getSelectionModel().getSelectedItem();

        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Remover carga(s) selecionada(s)?");
            alert.setHeaderText("Remover Carga ?");
            alert.setContentText("A remover a(s) carga(s) selecionada(s)");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                for (CargaEntity carga : cargasSelecionadas) {

                    carga.setIdContentor(null);
                    carga.setContentorByIdContentor(null);
                    carga.setLocalAtual(armazemSelecionado.getDescricao());
                    carga_repo.save(carga);
                }
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Erro!");
            alert.setHeaderText("Ocorreu um erro ao remover a carga!");
            alert.setContentText("Ocorreu um erro ao remover a carga, tente novamente!");
            alert.showAndWait();
        }

        atualizarInfoCargas();
        atualizarLabelsCapacidade();

    }

    @FXML
    public void editarEstadoContentor(){

        ArmazemEntity armazemSelecionado = ArmazemCombo.getSelectionModel().getSelectedItem();
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
        ObservableList<ContentorEntity> contentores = FXCollections.observableArrayList(contentor_repo.findByIdArmazem(armazemSelecionado.getId()));
        TContentores.setItems(contentores);
        TContentores.getSelectionModel().select(contentorSelecionado);
    }


}
