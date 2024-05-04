package com.example.transitariomaritimo;

import io.micrometer.observation.ObservationRegistry;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pt.ipvc.database.entity.*;
import pt.ipvc.database.repository.*;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class CargaController{

    public AnnotationConfigApplicationContext context;
    private CargaRepository carga_repo;
    private TipoCargaRepository tipo_carga_repo;
    private ArmazemRepository armazem_repo;
    private ContentorRepository contentor_repo;
    private CotacaoRepository cotacao_repo;
    private ReservaRepository reserva_repo;
    private EstadoCargaRepository estado_carga_repo;


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
    private TextField IdCotacao;

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
    private ComboBox<String> IdArmazemCombo;

    @FXML
    private ComboBox<String> IdContentorCombo;

    @FXML
    private ComboBox<String> IdCotacaoCombo;

    @FXML
    private ComboBox<String> IdReservaCombo;

    @FXML
    private TextField LocalAtualText;

    @FXML
    private TextField NomeText;

    @FXML
    private TextField ObservacoesText;

    @FXML
    private TextField PesoText;

    @FXML
    private TextField QuantidadeText;

    @FXML
    private ComboBox<String> TipoCargaCombo;

    @FXML
    private TextField VolumeText;

    @FXML
    private Pane Pane;

    @FXML
    private Pane mainPanel;

    @FXML
    private Pane menu_panel;

    private int armazemId;

    private int contentorCin;

    public void setArmazemId(int armazemId) {
        this.armazemId = armazemId;
    }

    public void setContentorCin(int contentorCin) {
        this.contentorCin = contentorCin;
    }

    public void cargaContentor(){
        context = new AnnotationConfigApplicationContext(AppConfig.class);
        carga_repo = context.getBean(CargaRepository.class);

        ObservableList<CargaEntity> cargas = FXCollections.observableArrayList(carga_repo.findByContentorCin(contentorCin));

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
                IdEstadoCarga.setText(newValue.getEstadoCargaByIdEstadoCarga().getDescricao());
                IdReserva.setText(newValue.getIdReserva().toString());
                IdTipoCarga.setText(newValue.getTipoCargaByIdTipoCarga().getDescricao());
                IdCotacao.setText(newValue.getIdCotacao().toString());
            }
        });

        tipo_carga_repo = context.getBean(TipoCargaRepository.class);
        armazem_repo = context.getBean(ArmazemRepository.class);
        contentor_repo = context.getBean(ContentorRepository.class);
        cotacao_repo = context.getBean(CotacaoRepository.class);
        reserva_repo = context.getBean(ReservaRepository.class);
        estado_carga_repo = context.getBean(EstadoCargaRepository.class);

        ObservableList<TipoCargaEntity> tiposCargas = FXCollections.observableArrayList(tipo_carga_repo.findAll());
        for (TipoCargaEntity tipoCarga : tiposCargas) {
            TipoCargaCombo.getItems().add(tipoCarga.getDescricao());
        }

        ObservableList<ArmazemEntity> armazem = FXCollections.observableArrayList(armazem_repo.findAll());
        for (ArmazemEntity armazemEntity : armazem) {
            IdArmazemCombo.getItems().add(String.valueOf(armazemEntity.getId()));
        }

        ObservableList<ContentorEntity> contentor = FXCollections.observableArrayList(contentor_repo.findAll());
        for (ContentorEntity contentorEntity : contentor) {
            IdContentorCombo.getItems().addAll(String.valueOf(contentorEntity.getCin()));
        }

        ObservableList<CotacaoEntity> cotacoes = FXCollections.observableArrayList(cotacao_repo.findAll());
        for (CotacaoEntity cotacaoEntity : cotacoes) {
            IdCotacaoCombo.getItems().add(String.valueOf(cotacaoEntity.getId()));
        }

        ObservableList<ReservaEntity> reservas = FXCollections.observableArrayList(reserva_repo.findAll());
        for (ReservaEntity reservaEntity : reservas) {
            IdReservaCombo.getItems().add(String.valueOf(reservaEntity.getId()));
        }
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

        try {
            FXMLLoader loaderMenu = new FXMLLoader(Objects.requireNonNull(getClass().getResource("MenuView.fxml")));
            Parent rootMenu = loaderMenu.load();
            MenuController menuController = loaderMenu.getController();
            menu_panel = menuController.getMenu_panel();
            Scene regCena = new Scene(rootMenu);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(regCena);
            stage.setTitle("Menu");
            stage.show();
        } catch (IOException ex) {
            System.out.println("Erro ao acessar meu" + ex.getMessage());
        }

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Contentor.fxml"));
        try {
            Pane cmdPane = fxmlLoader.load();
            menu_panel.getChildren().clear();
            menu_panel.getChildren().add(cmdPane);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @FXML
    public void InserirCarga(ActionEvent event) {
        Pane.setVisible(true);
        mainPanel.setEffect(new javafx.scene.effect.GaussianBlur(4.0));
        mainPanel.setDisable(true);
    }
    @FXML
    public void RegistarCarga(ActionEvent event) {

        CargaEntity novaCarga = new CargaEntity();
        novaCarga.setNome(NomeText.getText());
        novaCarga.setQuantidade(Integer.valueOf(QuantidadeText.getText()));
        novaCarga.setVolume(Double.valueOf(VolumeText.getText()));
        novaCarga.setPeso(Double.valueOf(PesoText.getText()));
        novaCarga.setLocalAtual(LocalAtualText.getText());
        novaCarga.setObservacoes(ObservacoesText.getText());
        novaCarga.setIdEstadoCarga(1);

        TipoCargaEntity tipoCarga = tipo_carga_repo.findByDescLike(TipoCargaCombo.getSelectionModel().getSelectedItem());
        ArmazemEntity armazem = armazem_repo.findByIdLike(Integer.valueOf(IdArmazemCombo.getSelectionModel().getSelectedItem()));
        ContentorEntity contentor = contentor_repo.findByIdLike(Integer.valueOf(IdContentorCombo.getSelectionModel().getSelectedItem()));
        CotacaoEntity cotacao = cotacao_repo.findByIdLike(Integer.valueOf(IdCotacaoCombo.getSelectionModel().getSelectedItem()));
        ReservaEntity reserva = reserva_repo.findByIdLike(Integer.valueOf(IdReservaCombo.getSelectionModel().getSelectedItem()));
        EstadoCargaEntity estadoCarga = estado_carga_repo.findByIdLike(1);

        novaCarga.setIdTipoCarga(tipoCarga.getId());
        novaCarga.setIdArmazem(Integer.valueOf(IdArmazemCombo.getValue()));
        novaCarga.setIdContentor(Integer.valueOf(IdContentorCombo.getValue()));
        novaCarga.setIdCotacao(Integer.valueOf(IdCotacaoCombo.getValue()));
        novaCarga.setIdReserva(Integer.valueOf(IdReservaCombo.getValue()));
        novaCarga.setArmazemByIdArmazem(armazem);
        novaCarga.setContentorByIdContentor(contentor);
        novaCarga.setCotacaoByIdCotacao(cotacao);
        novaCarga.setReservaByIdReserva(reserva);
        novaCarga.setEstadoCargaByIdEstadoCarga(estadoCarga);
        novaCarga.setTipoCargaByIdTipoCarga(tipoCarga);

        try {
            carga_repo.save(novaCarga);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucesso!");
            alert.setHeaderText("Registo efetuado com sucesso!");
            alert.showAndWait();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        NomeText.clear();
        QuantidadeText.clear();
        VolumeText.clear();
        PesoText.clear();
        LocalAtualText.clear();
        ObservacoesText.clear();
        IdArmazemCombo.getSelectionModel().clearSelection();
        IdContentorCombo.getSelectionModel().clearSelection();
        IdReservaCombo.getSelectionModel().clearSelection();
        IdCotacaoCombo.getSelectionModel().clearSelection();
        TipoCargaCombo.getSelectionModel().clearSelection();

        if(contentorCin == novaCarga.getIdTipoCarga()) {
            ObservableList<CargaEntity> cargasAtualizadas = FXCollections.observableArrayList(carga_repo.findAll());
            table.setItems(cargasAtualizadas);
        }


    }

    @FXML
    public void ExcluirCarga(ActionEvent event) {

        if (table.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Erro!");
            alert.setHeaderText("Nenhum carga selecionado!");
            alert.setContentText("Selecione uma carga para eliminar!");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Eliminar Carga");
            alert.setHeaderText("Eliminar Carga");
            alert.setContentText("Tem a certeza que pretende eliminar este carga?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                carga_repo.deleteById(table.getSelectionModel().getSelectedItem().getId());
                ObservableList<CargaEntity> cargassAtualizados = FXCollections.observableArrayList(carga_repo.findAll());
                table.setItems(cargassAtualizados);
            }
        }

    }

    @FXML
    public void VoltarAtrasInserirCarga(ActionEvent event) {

        Pane.setVisible(false);

        mainPanel.setEffect(null);
        mainPanel.setDisable(false);


    }

    public void cargaArmazem(){
        context = new AnnotationConfigApplicationContext(AppConfig.class);
        carga_repo = context.getBean(CargaRepository.class);

        ObservableList<CargaEntity> cargas = FXCollections.observableArrayList(carga_repo.findByArmazemID(armazemId));

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
                IdCotacao.setText(newValue.getIdCotacao().toString());
            }
        });
    }



}
