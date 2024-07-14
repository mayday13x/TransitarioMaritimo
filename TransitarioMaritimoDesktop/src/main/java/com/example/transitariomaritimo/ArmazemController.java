package com.example.transitariomaritimo;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraRepositoriesAutoConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import pt.ipvc.database.entity.ArmazemEntity;
import pt.ipvc.database.entity.CargaEntity;
import pt.ipvc.database.entity.ContentorEntity;
import pt.ipvc.database.entity.FuncionarioEntity;
import pt.ipvc.database.repository.ArmazemRepository;
import pt.ipvc.database.repository.CargaRepository;

import javafx.scene.layout.Pane;
import pt.ipvc.database.repository.ContentorRepository;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class ArmazemController implements Initializable {

    public AnnotationConfigApplicationContext context;
    private ArmazemRepository armazem_repo;

    private ContentorRepository contentor_repo;
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

    // ----------------------------------------------------------------

    @FXML
    private Label capacidadeLabel;

    @FXML
    private ComboBox<ArmazemEntity> ArmazemCombo;

    @FXML
    private Pane PaneInserir;

    @FXML
    private Pane PaneInserirArmazem;

    @FXML
    private Pane mainPanel;

    @FXML
    private Pane menu_Panel;

    // ---------------------------------------------------------------- TCONTENTORES
    @FXML
    private TableView<CargaEntity> TCargas;

    @FXML
    private TableColumn<CargaEntity, String> IdCarga;

    @FXML
    private TableColumn<CargaEntity, String> IdReservaCarga;

    @FXML
    private TableColumn<CargaEntity, String> designacaoCarga;

    @FXML
    private TextArea observacoesCarga;

    @FXML
    private TableColumn<CargaEntity, String> pesoCarga;

    @FXML
    private TableColumn<CargaEntity, String> qtdCarga;

    @FXML
    private TableColumn<CargaEntity, String> tipoCarga;

    @FXML
    private TableColumn<CargaEntity, String> volumeCarga;

    // -------------------------------- T. Adicionar Cargas Armazem

    @FXML
    private TableView<CargaEntity> TCargasAdd;

    @FXML
    private TableColumn<CargaEntity, String> IdCargaAdd;

    @FXML
    private TableColumn<CargaEntity, String> IdReservaCargaAdd;

    @FXML
    private TableColumn<CargaEntity, String> designacaoCargaAdd;

    @FXML
    private TextArea observacoesCargaAdd;

    @FXML
    private TableColumn<CargaEntity, String> pesoCargaAdd;

    @FXML
    private TableColumn<CargaEntity, String> qtdCargaAdd;

    @FXML
    private TableColumn<CargaEntity, String> tipoCargaAdd;

    @FXML
    private TableColumn<CargaEntity, String> volumeCargaAdd;

    @FXML
    private Button addCargaButton;

    @FXML
    private Button removeCargaButton;

    // Editar Armazem elements
    @FXML
    private Pane PaneEditarArmazem;

    @FXML
    private TextField descricaoTextEdit;

    @FXML
    private TextField CapacidadeMaximaTextEdit;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        context = new AnnotationConfigApplicationContext(AppConfig.class);

        armazem_repo = context.getBean(ArmazemRepository.class);
        contentor_repo = context.getBean(ContentorRepository.class);
        carga_repo = context.getBean(CargaRepository.class);

        ObservableList<ArmazemEntity> armazens = FXCollections.observableArrayList(armazem_repo.findAll());

        if (table != null) {

            Id.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getId().toString()));
            Descricao.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDescricao()));
            Cap_max.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCapacidadeMax().toString()));

            table.setItems(armazens);
         } else {
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

            ObservableList<ArmazemEntity> armazensDesc = FXCollections.observableArrayList(armazem_repo.findAll());
            ArmazemCombo.setItems(armazensDesc);

            if (!armazensDesc.isEmpty()) {
                ArmazemCombo.getSelectionModel().selectFirst();
                atualizarInfo(null);
            }

            // Configurando a TableView TCargasAdd para seleção múltipla
            TCargasAdd.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            TCargas.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

            // Adicionando listener na seleção da TableView TCargasAdd
            TCargasAdd.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    addCargaButton.setDisable(false);
                } else {
                    addCargaButton.setDisable(true);
                }
            });

            // Adicionando listener na seleção da TableView TCargasAdd
            TCargas.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    removeCargaButton.setDisable(false);
                } else {
                    removeCargaButton.setDisable(true);
                }
            });


        }

    }

    @FXML
    public void atualizarInfo(ActionEvent event) {

        ArmazemEntity armazemSelecionado = ArmazemCombo.getSelectionModel().getSelectedItem();
        Double capacidadeOcupada = armazem_repo.sumVolumes(armazemSelecionado.getId());


    try {
        capacidadeLabel.setText(capacidadeOcupada.toString() + " / " + armazemSelecionado.getCapacidadeMax().toString());
    } catch (Exception e) {
        capacidadeLabel.setText("0 / " + armazemSelecionado.getCapacidadeMax().toString());
    }

        ObservableList<CargaEntity> cargas = FXCollections.observableArrayList(carga_repo.findByArmazemID(armazemSelecionado.getId()));

        IdCarga.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getId().toString()));
        IdReservaCarga.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getIdReserva().toString()));
        designacaoCarga.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNome()));   // nome = designacao
        qtdCarga.setCellValueFactory(data -> new SimpleStringProperty((data.getValue().getQuantidade().toString())));
        volumeCarga.setCellValueFactory(data -> new SimpleStringProperty((data.getValue().getVolume().toString())));
        pesoCarga.setCellValueFactory(data -> new SimpleStringProperty((data.getValue().getPeso().toString())));
        tipoCarga.setCellValueFactory(data -> new SimpleStringProperty((data.getValue().getTipoCargaByIdTipoCarga()).getDescricao()));


        TCargas.setItems(cargas);

    }

    @FXML
    public void showAdicionarCarga() {

        PaneInserir.setVisible(true);
        mainPanel.setEffect(new javafx.scene.effect.GaussianBlur(4.0));
        mainPanel.setDisable(true);
        // mainPanel.setVisible(false);

        System.out.println("add carga show");

        ArmazemEntity armazemSelecionado = ArmazemCombo.getSelectionModel().getSelectedItem();

        ObservableList<CargaEntity> cargas = FXCollections.observableArrayList(carga_repo.findByIdArmazemNull());

        IdCargaAdd.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getId().toString()));
        IdReservaCargaAdd.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getIdReserva().toString()));
        designacaoCargaAdd.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNome()));   // nome = designacao
        qtdCargaAdd.setCellValueFactory(data -> new SimpleStringProperty((data.getValue().getQuantidade().toString())));
        volumeCargaAdd.setCellValueFactory(data -> new SimpleStringProperty((data.getValue().getVolume().toString())));
        pesoCargaAdd.setCellValueFactory(data -> new SimpleStringProperty((data.getValue().getPeso().toString())));
        tipoCargaAdd.setCellValueFactory(data -> new SimpleStringProperty((data.getValue().getTipoCargaByIdTipoCarga()).getDescricao()));

        TCargasAdd.setItems(cargas);
    }

    @FXML
    public void closeAdicionarCarga(ActionEvent event) {
        PaneInserir.setVisible(false);

        mainPanel.setEffect(null);
        mainPanel.setDisable(false);

    }

    @FXML
    public void AdicionarCarga(ActionEvent event) {

        List<CargaEntity> cargasSeleccionadas = TCargasAdd.getSelectionModel().getSelectedItems();
        ArmazemEntity armazemSelecionado = ArmazemCombo.getSelectionModel().getSelectedItem();
        double totalVolume = 0.0;

        for (CargaEntity carga : cargasSeleccionadas) {
           totalVolume = totalVolume + carga.getVolume();
        }

        if (totalVolume > armazemSelecionado.getCapacidadeMax()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Capacidade máxima excedida!");
            alert.setHeaderText("A capacidade máxima do armazém " + armazemSelecionado.getDescricao() + " foi excedida!");
            alert.setContentText("O volume total das cargas adicionadas excede a capacidade máxima do armazém.");
            alert.showAndWait();
            return;
        } else {
            try {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Adicionar carga(s) selecionada(s) ao " + armazemSelecionado.getDescricao() + " ?");
                alert.setHeaderText("Adicionar Carga ao " + armazemSelecionado.getDescricao() + "?");
                alert.setContentText("A adicionar a(s) carga(s) selecionada(s)");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    for (CargaEntity carga : cargasSeleccionadas) {
                        carga.setIdArmazem(armazemSelecionado.getId());
                        carga.setArmazemByIdArmazem(armazemSelecionado);
                        carga.setLocalAtual(armazemSelecionado.getDescricao());
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

        ObservableList<CargaEntity> cargas = FXCollections.observableArrayList(carga_repo.findByIdArmazemNull());
        TCargasAdd.setItems(cargas);
        atualizarInfo(null);

    }

    @FXML
    public void RemoverCarga(ActionEvent event) {

        List<CargaEntity> cargasSelecionadas = TCargas.getSelectionModel().getSelectedItems();
        ArmazemEntity armazemSelecionado = ArmazemCombo.getSelectionModel().getSelectedItem();

        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Remover a carga selecionada do " + armazemSelecionado.getDescricao() + " ?");
            alert.setHeaderText("Pretende remover a carga selecionada do " + armazemSelecionado.getDescricao() + "?");
            //alert.setContentText("A adicionar a(s) carga(s) selecionada(s)");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                for (CargaEntity carga : cargasSelecionadas) {
                    carga.setIdArmazem(null);
                    carga.setArmazemByIdArmazem(null);
                    carga.setLocalAtual(null);
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


        ObservableList<CargaEntity> cargas = FXCollections.observableArrayList(carga_repo.findByIdArmazemNull());
        TCargasAdd.setItems(cargas);
        atualizarInfo(null);

    }


    // Armazens

    @FXML
    public void InserirArmazem(ActionEvent event) {
        PaneInserirArmazem.setVisible(true);
        mainPanel.setEffect(new javafx.scene.effect.GaussianBlur(4.0));
        mainPanel.setDisable(true);
    }

    @FXML
    public void closeInserirArmazem(ActionEvent event) {

        PaneInserirArmazem.setVisible(false);
        mainPanel.setEffect(null);
        mainPanel.setDisable(false);
    }

    @FXML
    public void RegistarArmazem(ActionEvent event) {

        if(Objects.equals(descricaoText.getText(), "") || Objects.equals(CapacidadeMaximaText.getText(), "")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro!");
            alert.setHeaderText("Todos os campos devem estar preenchidos!");
            alert.setContentText("Por favor preencha todos os campos.");
            alert.showAndWait();
        } else {

            ArmazemEntity novoArmazem = new ArmazemEntity();
            novoArmazem.setCapacidadeMax(Double.parseDouble(CapacidadeMaximaText.getText()));
            novoArmazem.setDescricao(descricaoText.getText());

            //List<CargaEntity> cargas = carga_repo.findByArmazemID(novoArmazem.getId());
            //novoArmazem.setCargasById(cargas);

            armazem_repo.save(novoArmazem);

            CapacidadeMaximaText.clear();
            descricaoText.clear();
            closeInserirArmazem(null);

            ObservableList<ArmazemEntity> armazensAtualizados = FXCollections.observableArrayList(armazem_repo.findAll());
            //table.setItems(armazensAtualizados);
            ArmazemCombo.setItems(armazensAtualizados);
        }
    }

    @FXML
    public void RemoverArmazem(ActionEvent event) {

        ArmazemEntity armazemSelecionado = ArmazemCombo.getSelectionModel().getSelectedItem();
        long countCargas = armazem_repo.countCargasInArmazem(armazemSelecionado.getId());

        if (countCargas > 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro!");
            alert.setHeaderText("Não é possível remover o armazém!");
            alert.setContentText("O armazém possui cargas associadas.");
            alert.showAndWait();
            return;
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Remover armazem \"" + armazemSelecionado.getDescricao() + "\" ?");
            alert.setHeaderText("Pretende remover o armazém \" " + armazemSelecionado.getDescricao() + "\" ?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                armazem_repo.deleteById(armazemSelecionado.getId());
            }

        }

        ObservableList<ArmazemEntity> armazensAtualizados = FXCollections.observableArrayList(armazem_repo.findAll());
        //table.setItems(armazensAtualizados);
        ArmazemCombo.setItems(armazensAtualizados);

    }

    @FXML
    public void showEditarArmazem(ActionEvent event) {
        PaneEditarArmazem.setVisible(true);
        mainPanel.setEffect(new javafx.scene.effect.GaussianBlur(4.0));
        mainPanel.setDisable(true);

        ArmazemEntity armazemSelecionado = ArmazemCombo.getSelectionModel().getSelectedItem();
        descricaoTextEdit.setText(armazemSelecionado.getDescricao());
        CapacidadeMaximaTextEdit.setText(String.valueOf(armazemSelecionado.getCapacidadeMax()));
    }

    @FXML
    public void closeEditarArmazem(ActionEvent event) {

        PaneEditarArmazem.setVisible(false);
        mainPanel.setEffect(null);
        mainPanel.setDisable(false);
    }

    public void EditarArmazem(ActionEvent event) {
        ArmazemEntity armazemSelecionado = ArmazemCombo.getSelectionModel().getSelectedItem();

        if(Objects.equals(descricaoTextEdit.getText(), "") || Objects.equals(CapacidadeMaximaTextEdit.getText(), "")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro!");
            alert.setHeaderText("Todos os campos devem estar preenchidos!");
            alert.setContentText("Por favor preencha todos os campos.");
            alert.showAndWait();
        } else {

            if((armazem_repo.sumVolumes(armazemSelecionado.getId()) > Double.parseDouble(CapacidadeMaximaText.getText()))){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro!");
                alert.setHeaderText("Capacidade insuficiente para as cargas existentes!");
                alert.setContentText("Capacidade minima necessaria: " + armazem_repo.sumVolumes(armazemSelecionado.getId()));
                alert.showAndWait();
            } else {

                try {
                    armazem_repo.save(armazemSelecionado);
                    armazemSelecionado.setDescricao(descricaoTextEdit.getText());
                    armazemSelecionado.setCapacidadeMax(Double.parseDouble(CapacidadeMaximaTextEdit.getText()));
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erro!");
                    alert.setHeaderText("Erro ao editar o armazém!");
                    alert.showAndWait();
                    return;
                }

                closeEditarArmazem(null);

                ObservableList<ArmazemEntity> armazensAtualizados = FXCollections.observableArrayList(armazem_repo.findAll());
                //table.setItems(armazensAtualizados);
                ArmazemCombo.setItems(armazensAtualizados);
            }
        }

    }

    @FXML
    public void VisualizarCargas(ActionEvent event)  throws IOException{
        ArmazemEntity armazemSelecionado = table.getSelectionModel().getSelectedItem();

        if (armazemSelecionado != null) {

            try {
                FXMLLoader loaderMenu = new FXMLLoader(Objects.requireNonNull(getClass().getResource("MenuGestorLogisticoArmazemView.fxml")));
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


            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CargaArmazem.fxml"));

            try {
                Pane cmdPane = fxmlLoader.load();
                menu_Panel.getChildren().clear();
                menu_Panel.getChildren().add(cmdPane);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            CargaController cargaController = fxmlLoader.getController();
            cargaController.setArmazemId(armazemSelecionado.getId());
            cargaController.cargaArmazem();


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
    public void VisualizarCargasAdmin(ActionEvent event)  throws IOException{
        ArmazemEntity armazemSelecionado = table.getSelectionModel().getSelectedItem();

        if (armazemSelecionado != null) {

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


            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CargaArmazem.fxml"));

            try {
                Pane cmdPane = fxmlLoader.load();
                menu_Panel.getChildren().clear();
                menu_Panel.getChildren().add(cmdPane);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            CargaController cargaController = fxmlLoader.getController();
            cargaController.setArmazemId(armazemSelecionado.getId());
            cargaController.cargaArmazem();


        }else {
            // Exibe uma mensagem de erro se nenhum armazém estiver selecionado
            //System.out.println("Selecione um armazém para visualizar as cargas.");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setContentText("Selecione um armazém para visualizar as cargas.");
            alert.showAndWait();
        }
    }


}
