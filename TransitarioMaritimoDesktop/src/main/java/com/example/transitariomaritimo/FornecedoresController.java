package com.example.transitariomaritimo;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pt.ipvc.database.entity.ClienteEntity;
import pt.ipvc.database.entity.CodPostalEntity;
import pt.ipvc.database.entity.FornecedorEntity;
import pt.ipvc.database.repository.ClienteRepository;
import pt.ipvc.database.repository.CodPostalRepository;
import pt.ipvc.database.repository.FornecedorRepository;

import java.lang.invoke.VolatileCallSite;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class FornecedoresController implements Initializable {


    public AnnotationConfigApplicationContext context;
    private FornecedorRepository forn_repo;

    private CodPostalRepository cp_repo;

    @FXML
    private ComboBox<String> CodPostalCombo;

    @FXML
    private ComboBox<String> EditCodPostalCombo;

    @FXML
    private TextField EditNifText;

    @FXML
    private TextField EditNomeText;

    @FXML
    private Pane EditPane;

    @FXML
    private TextField EditPortaText;

    @FXML
    private TextField EditRuaText;

    @FXML
    private TextField EditTelefoneText;

    @FXML
    private TableColumn<FornecedorEntity, String> Endereco;

    @FXML
    private TableColumn<FornecedorEntity, String> Id;

    @FXML
    private TableColumn<FornecedorEntity, String> Localidade;

    @FXML
    private TableColumn<FornecedorEntity, String> Nif;

    @FXML
    private TextField NifText;

    @FXML
    private TableColumn<FornecedorEntity, String> Nome;

    @FXML
    private TextField NomeText;

    @FXML
    private Pane Pane;

    @FXML
    private TextField PortaText;

    @FXML
    private TextField RuaText;

    @FXML
    private TableColumn<FornecedorEntity, String> Telefone;

    @FXML
    private TextField TelefoneText;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;

    @FXML
    private Button guardarButton;

    @FXML
    private Pane mainPanel;

    @FXML
    private TableView<FornecedorEntity> table;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        FornecedoresController.DoubleClickHandler handler = new FornecedoresController.DoubleClickHandler();
        table.setOnMouseClicked(handler);

        context = new AnnotationConfigApplicationContext(AppConfig.class);
        forn_repo = context.getBean(FornecedorRepository.class);

        ObservableList<FornecedorEntity> fornecedores = FXCollections.observableArrayList(forn_repo.findAll());

        try{

            Id.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getId().toString()));
            Nome.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNome()));
            Nif.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNif().toString()));
            Localidade.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCodPostalByIdCodPostal().getLocalidade()));
            Telefone.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTelefone()));
            Endereco.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getRua()));
            table.setItems(fornecedores);


            context = new AnnotationConfigApplicationContext(AppConfig.class);
            cp_repo = context.getBean(CodPostalRepository.class);
            forn_repo = context.getBean(FornecedorRepository.class);

            ObservableList<CodPostalEntity> localidades = FXCollections.observableArrayList(cp_repo.findAll());
            for (CodPostalEntity i : localidades){
                CodPostalCombo.getItems().addAll(i.getLocalidade());
                EditCodPostalCombo.getItems().addAll(i.getLocalidade());
            }


        } catch (Exception ex){
            System.out.println("Erro no Fornecedor: " + ex.getMessage());
        }


    }

    @FXML
    void EditarFornecedor(ActionEvent event) {

        if(Objects.equals(EditRuaText.getText(), "") || Objects.equals(EditNifText.getText(), "") || Objects.equals(EditNomeText.getText(), "")
                && Objects.equals(EditPortaText.getText(), "") || Objects.equals(EditTelefoneText.getText(), "")) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Campos Inválidos!");
            alert.setHeaderText("Campos por preencher");
            alert.setContentText("Preencha todos os campos e tente novamente!");

            alert.showAndWait();



        } else {

            FornecedorEntity editFornecedor = table.getSelectionModel().getSelectedItem();
            editFornecedor.setNome(EditNomeText.getText());
            editFornecedor.setNif(Integer.valueOf(EditNifText.getText()));
            editFornecedor.setRua(EditRuaText.getText());
            editFornecedor.setPorta(Integer.valueOf(EditPortaText.getText()));

            CodPostalEntity codPostalEntity = cp_repo.findByNameLike(EditCodPostalCombo.getSelectionModel().getSelectedItem());

            editFornecedor.setIdCodPostal(codPostalEntity.getIdCodPostal());
            editFornecedor.setTelefone(EditTelefoneText.getText());
            editFornecedor.setCodPostalByIdCodPostal(codPostalEntity);

            try {
                forn_repo.save(editFornecedor);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sucesso!");
                alert.setHeaderText("Registo editado com sucesso!");
                alert.showAndWait();

                EditPane.setVisible(false);
                mainPanel.setEffect(null);
                mainPanel.setDisable(false);

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }


        }

        refreshTable();

    }

    void refreshTable(){
        ObservableList<FornecedorEntity> fornecedores = FXCollections.observableArrayList(forn_repo.findAll());
        table.setItems(fornecedores);
        table.refresh();
    }


    @FXML
    void ShowInserirFornecedor() {
        Pane.setVisible(true);
        mainPanel.setEffect(new javafx.scene.effect.GaussianBlur(4.0));
        mainPanel.setDisable(true);
    }

    @FXML
    void ShowEditarFornecedor() {

        EditPane.setVisible(true);
        mainPanel.setEffect(new javafx.scene.effect.GaussianBlur(4.0));
        EditPane.setEffect(new javafx.scene.effect.DropShadow());
        mainPanel.setDisable(true);

        EditNomeText.setText(table.getSelectionModel().getSelectedItem().getNome());
        EditNifText.setText(table.getSelectionModel().getSelectedItem().getNif().toString());
        EditRuaText.setText(table.getSelectionModel().getSelectedItem().getRua());
        EditPortaText.setText(table.getSelectionModel().getSelectedItem().getPorta().toString());
        EditTelefoneText.setText(table.getSelectionModel().getSelectedItem().getTelefone());
        EditCodPostalCombo.getSelectionModel().select(table.getSelectionModel().getSelectedItem().getCodPostalByIdCodPostal().getLocalidade());

    }

    @FXML
    void RegistarFornecedor(ActionEvent event) {

        if(Objects.equals(RuaText.getText(), "") || Objects.equals(NifText.getText(), "") || Objects.equals(NomeText.getText(), "")
                || Objects.equals(PortaText.getText(), "") ||  Objects.equals(TelefoneText.getText(), "") || Objects.equals(CodPostalCombo.getSelectionModel().getSelectedItem(), "")){

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Campos Inválidos!");
            alert.setHeaderText("Campos por preencher");
            alert.setContentText("Preencha todos os campos e tente novamente!");

            alert.showAndWait();

        } else {


            FornecedorEntity novoFornecedor = new FornecedorEntity();
            novoFornecedor.setNome(NomeText.getText());
            novoFornecedor.setNif(Integer.valueOf(NifText.getText()));
            novoFornecedor.setRua(RuaText.getText());
            novoFornecedor.setPorta(Integer.valueOf(PortaText.getText()));

            CodPostalEntity codPostalEntity = cp_repo.findByNameLike(CodPostalCombo.getSelectionModel().getSelectedItem());

            novoFornecedor.setIdCodPostal(codPostalEntity.getIdCodPostal());
            novoFornecedor.setTelefone(TelefoneText.getText());
            novoFornecedor.setCodPostalByIdCodPostal(codPostalEntity);

            try {
                forn_repo.save(novoFornecedor);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sucesso!");
                alert.setHeaderText("Registo efetuado com sucesso!");
                alert.showAndWait();

                Pane.setVisible(false);
                mainPanel.setEffect(null);
                mainPanel.setDisable(false);

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            RuaText.clear();
            NifText.clear();
            NomeText.clear();
            PortaText.clear();
            TelefoneText.clear();
            CodPostalCombo.getSelectionModel().clearSelection();

           refreshTable();
        }

    }

    @FXML
    void ExcluirFornecedor(ActionEvent event) {

        if (table.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Erro!");
            alert.setHeaderText("Nenhum cliente selecionado!");
            alert.setContentText("Selecione um cliente para eliminar!");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Eliminar Cliente");
            alert.setHeaderText("Eliminar Cliente");
            alert.setContentText("Tem a certeza que pretende eliminar este cliente?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                forn_repo.deleteById(table.getSelectionModel().getSelectedItem().getId());
                refreshTable();
            }
        }

    }

    @FXML
    void Voltar(ActionEvent event) {

        Pane.setVisible(false);
        RuaText.clear();
        NifText.clear();
        NomeText.clear();
        PortaText.clear();
        TelefoneText.clear();
        CodPostalCombo.getSelectionModel().clearSelection();

        EditPane.setVisible(false);
        EditRuaText.clear();
        EditNifText.clear();
        EditNomeText.clear();
        EditPortaText.clear();
        EditTelefoneText.clear();

        mainPanel.setEffect(null);
        mainPanel.setDisable(false);

        EditPane.setEffect(null);
        EditPane.setDisable(false);

    }

    public class DoubleClickHandler implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {
            if (event.getClickCount() == 2) {
                ShowEditarFornecedor();
            }
        }
    }

}
