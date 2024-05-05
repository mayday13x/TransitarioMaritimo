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
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class ContentorController implements Initializable {

    public AnnotationConfigApplicationContext context;
    private ContentorRepository contentor_repo;

    private TipoContentorRepository tipo_contentor_repo;
    private EstadoContentorRepository estado_contentor_repo;
    private CargaRepository carga_repo;

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
    private Pane PaneInserir;

    @FXML
    private Pane mainPanel;

    @FXML
    private Pane menu_Panel;

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
                    IdEstadoContentor.setText(newValue.getEstadoContentorByIdEstadoContentor().getDescricao());
                    TipoContentor.setText(newValue.getTipoContentorByTipoContentor().getDescricao());
                }
            });


            context = new AnnotationConfigApplicationContext(AppConfig.class);
            contentor_repo = context.getBean(ContentorRepository.class);
            tipo_contentor_repo = context.getBean(TipoContentorRepository.class);
            estado_contentor_repo = context.getBean(EstadoContentorRepository.class);
            carga_repo = context.getBean(CargaRepository.class);


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

    @FXML
    public void RegistarContentor(ActionEvent event) {

        if(Objects.equals(CapacidadeText.getText(), "") || Objects.equals(LocalAtual.getText(), "") ||
                Objects.equals(PesoMaximoText.getText(), "") || Objects.equals(TipoContentorCombo.getValue(), "") ||
                Objects.equals(EstadoContentorCombo.getValue(), "")) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Campos Inválidos!");
            alert.setHeaderText("Campos por preencher");
            alert.setContentText("Preencha todos os campos e tente novamente!");

            alert.showAndWait();

        } else {
            ContentorEntity novoContentor = new ContentorEntity();
            novoContentor.setCapacidade(Double.valueOf(CapacidadeText.getText()));
            novoContentor.setLocalAtual(LocalAtualText.getText());
            novoContentor.setPesoMax(Double.valueOf(PesoMaximoText.getText()));

            TipoContentorEntity tipoContentor = tipo_contentor_repo.findByNameLike(TipoContentorCombo.getSelectionModel().getSelectedItem());
            EstadoContentorEntity estadoContentor = estado_contentor_repo.findByNameLike(EstadoContentorCombo.getSelectionModel().getSelectedItem());

            novoContentor.setTipoContentor(tipoContentor.getId());
            novoContentor.setIdEstadoContentor(estadoContentor.getId());
            novoContentor.setEstadoContentorByIdEstadoContentor(estadoContentor);
            novoContentor.setTipoContentorByTipoContentor(tipoContentor);


            contentor_repo.save(novoContentor);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucesso!");
            alert.setHeaderText("Registo efetuado com sucesso!");
            alert.showAndWait();

            CapacidadeText.clear();
            PesoMaximoText.clear();
            LocalAtualText.clear();
            TipoContentorCombo.getSelectionModel().clearSelection();
            EstadoContentorCombo.getSelectionModel().clearSelection();

            ObservableList<ContentorEntity> contentoresAtualizados = FXCollections.observableArrayList(contentor_repo.findAll());
            table.setItems(contentoresAtualizados);
        }


    }

    public void VisualizarCargas(ActionEvent event) throws IOException {
        ContentorEntity contentorSelecionado = table.getSelectionModel().getSelectedItem();

        if(contentorSelecionado != null) {

            try {
                FXMLLoader loaderMenu = new FXMLLoader(Objects.requireNonNull(getClass().getResource("MenuFuncionarioArmazemView.fxml")));
                Parent rootMenu = loaderMenu.load();
                MenuController menuController = loaderMenu.getController();
                menu_Panel = menuController.getMenu_panel();
                Scene regCena = new Scene(rootMenu);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(regCena);
                stage.setTitle("Menu");
                stage.show();
            } catch (IOException ex) {
                System.out.println("Erro ao acessar meu" + ex.getMessage());
            }

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CargaContentor.fxml"));

            try {
                Pane cmdPane = fxmlLoader.load();
                menu_Panel.getChildren().clear();
                menu_Panel.getChildren().add(cmdPane);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            CargaController cargaController = fxmlLoader.getController();
            cargaController.setContentorCin(contentorSelecionado.getCin());
            cargaController.cargaContentor();

        }else {
            // Exibe uma mensagem de erro se nenhum armazém estiver selecionado
            //System.out.println("Selecione um armazém para visualizar as cargas.");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setContentText("Selecione um armazém para visualizar as cargas.");
            alert.showAndWait();
        }

    }

    public void VisualizarCargasAdmin(ActionEvent event) throws IOException {
        ContentorEntity contentorSelecionado = table.getSelectionModel().getSelectedItem();

        if(contentorSelecionado != null) {

            try {
                FXMLLoader loaderMenu = new FXMLLoader(Objects.requireNonNull(getClass().getResource("MenuAdminView.fxml")));
                Parent rootMenu = loaderMenu.load();
                MenuController menuController = loaderMenu.getController();
                menu_Panel = menuController.getMenu_panel();
                Scene regCena = new Scene(rootMenu);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(regCena);
                stage.setTitle("Menu");
                stage.show();
            } catch (IOException ex) {
                System.out.println("Erro ao acessar meu" + ex.getMessage());
            }

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CargaContentor.fxml"));

            try {
                Pane cmdPane = fxmlLoader.load();
                menu_Panel.getChildren().clear();
                menu_Panel.getChildren().add(cmdPane);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            CargaController cargaController = fxmlLoader.getController();
            cargaController.setContentorCin(contentorSelecionado.getCin());
            cargaController.cargaContentor();

        }else {
            // Exibe uma mensagem de erro se nenhum armazém estiver selecionado
            //System.out.println("Selecione um armazém para visualizar as cargas.");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setContentText("Selecione um armazém para visualizar as cargas.");
            alert.showAndWait();
        }

    }

    public void VisualizarCargasFuncionarioTransporte(ActionEvent event) throws IOException {
        ContentorEntity contentorSelecionado = table.getSelectionModel().getSelectedItem();

        if(contentorSelecionado != null) {

            try {
                FXMLLoader loaderMenu = new FXMLLoader(Objects.requireNonNull(getClass().getResource("MenuFuncionarioTransporteView.fxml")));
                Parent rootMenu = loaderMenu.load();
                MenuController menuController = loaderMenu.getController();
                menu_Panel = menuController.getMenu_panel();
                Scene regCena = new Scene(rootMenu);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(regCena);
                stage.setTitle("Menu");
                stage.show();
            } catch (IOException ex) {
                System.out.println("Erro ao acessar meu" + ex.getMessage());
            }

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CargaFuncionarioTransporte.fxml"));

            try {
                Pane cmdPane = fxmlLoader.load();
                menu_Panel.getChildren().clear();
                menu_Panel.getChildren().add(cmdPane);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            CargaController cargaController = fxmlLoader.getController();
            cargaController.setContentorCin(contentorSelecionado.getCin());
            cargaController.cargaContentor();

        }else {
            // Exibe uma mensagem de erro se nenhum armazém estiver selecionado
            //System.out.println("Selecione um armazém para visualizar as cargas.");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setContentText("Selecione um armazém para visualizar as cargas.");
            alert.showAndWait();
        }

    }

    @FXML
    public void InserirContentor(ActionEvent event) {

        PaneInserir.setVisible(true);
        mainPanel.setEffect(new javafx.scene.effect.GaussianBlur(4.0));
        mainPanel.setDisable(true);
        mainPanel.setVisible(false);
    }

    @FXML
    public void VoltarAtrasInserirContentor(ActionEvent event) {
        PaneInserir.setVisible(false);

        CapacidadeText.clear();
        PesoMaximoText.clear();
        LocalAtualText.clear();
        TipoContentorCombo.getSelectionModel().clearSelection();
        EstadoContentorCombo.getSelectionModel().clearSelection();

        mainPanel.setEffect(null);
        mainPanel.setDisable(false);
        mainPanel.setVisible(true);
    }

   @FXML
    public void RegistarSaida(ActionEvent event){
        ContentorEntity contentor = table.getSelectionModel().getSelectedItem();
        EstadoContentorEntity estadoContentor = estado_contentor_repo.findByNameLike("Contentor Fora");
        List<CargaEntity> cargas = FXCollections.observableArrayList(carga_repo.findByContentorCin(contentor.getCin()));

        contentor.setIdEstadoContentor(estadoContentor.getId());
        contentor.setEstadoContentorByIdEstadoContentor(estadoContentor);

        for (CargaEntity carga : cargas) {
            carga.setIdArmazem(null);
            carga.setArmazemByIdArmazem(null);
            carga_repo.save(carga);
        }

        contentor_repo.save(contentor);

       Alert alert = new Alert(Alert.AlertType.INFORMATION);
       alert.setTitle("Sucesso!");
       alert.setHeaderText("Registo de saida efetuado com sucesso!");
       alert.showAndWait();

       ObservableList<ContentorEntity> contentores = FXCollections.observableArrayList(contentor_repo.findAll());
       table.setItems(contentores);

   }


}
