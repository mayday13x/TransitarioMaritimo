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
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pt.ipvc.database.entity.ClienteEntity;
import pt.ipvc.database.entity.CodPostalEntity;
import pt.ipvc.database.entity.FuncionarioEntity;
import pt.ipvc.database.entity.TipoFuncionarioEntity;
import pt.ipvc.database.repository.CodPostalRepository;
import pt.ipvc.database.repository.FuncionarioRepository;
import javafx.scene.layout.Pane;
import pt.ipvc.database.repository.TipoFuncionarioRepository;


import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class FuncionarioController implements Initializable {

    public AnnotationConfigApplicationContext context;
    private FuncionarioRepository funcionario_repo;
    private CodPostalRepository cod_postal_repo;
    private TipoFuncionarioRepository tipo_funcionario_repo;

    @FXML
    private ComboBox<String> CodPostalCombo;

    @FXML
    private ComboBox<String> TipoFuncionarioCombo;

    @FXML
    private TextField EmailText;

    @FXML
    private TextField NifText;

    @FXML
    private TextField NomeText;

    @FXML
    private TextField PortaText;

    @FXML
    private TextField RuaText;

    @FXML
    private TextField TelefoneText;

    //edit
    @FXML
    private ComboBox<String> EditCodPostalCombo;

    @FXML
    private TextField EditEmailText;

    @FXML
    private TextField EditNifText;

    @FXML
    private TextField EditNomeText;

    @FXML
    private TextField EditPortaText;

    @FXML
    private TextField EditRuaText;

    @FXML
    private TextField EditTelefoneText;

    @FXML
    private ComboBox<String> EditTipoFuncionarioCombo;

    @FXML
    private TableColumn<FuncionarioEntity, String> Localidade;

    @FXML
    private TableColumn<FuncionarioEntity, String> Id;

    @FXML
    private TableColumn<FuncionarioEntity, String> Nif;

    @FXML
    private TableColumn<FuncionarioEntity, String> Nome;

    @FXML
    private TableColumn<FuncionarioEntity, String> Telefone;

    @FXML
    private TableColumn<FuncionarioEntity, String> Email;

    @FXML
    private TextField TipoFuncionario;

    @FXML
    private TableView<FuncionarioEntity> table;

    @FXML
    private Pane Pane;

    @FXML
    private Pane mainPanel;

    @FXML
    private Pane EditPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

      DoubleClickHandler handler = new DoubleClickHandler();
        table.setOnMouseClicked(handler);

        context = new AnnotationConfigApplicationContext(AppConfig.class);
        funcionario_repo = context.getBean(FuncionarioRepository.class);

        ObservableList<FuncionarioEntity> funcionarios = FXCollections.observableArrayList(funcionario_repo.findAll());


        try{

            Id.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getId().toString()));
            Nome.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNome()));
            Nif.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNif().toString()));
            Localidade.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCodPostalByIdCodPostal().getLocalidade()));
            Telefone.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTelefone()));
            Email.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEmail()));
            table.setItems(funcionarios);


            context = new AnnotationConfigApplicationContext(AppConfig.class);
            cod_postal_repo = context.getBean(CodPostalRepository.class);
            tipo_funcionario_repo = context.getBean(TipoFuncionarioRepository.class);

            ObservableList<CodPostalEntity> localidades = FXCollections.observableArrayList(cod_postal_repo.findAll());
            for (CodPostalEntity l : localidades){
                CodPostalCombo.getItems().addAll(l.getLocalidade());
                EditCodPostalCombo.getItems().addAll(l.getLocalidade());
            }

            ObservableList<TipoFuncionarioEntity> tiposFuncionarios = FXCollections.observableArrayList(tipo_funcionario_repo.findAll());
            for (TipoFuncionarioEntity t : tiposFuncionarios){
                TipoFuncionarioCombo.getItems().addAll(t.getDescricao());
                EditTipoFuncionarioCombo.getItems().addAll(t.getDescricao());
            }

            table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {

                    Optional<TipoFuncionarioEntity> tipoFuncionarioEntity = tipo_funcionario_repo.findById(newValue.getIdTipoFuncionario());
                    TipoFuncionario.setText(String.valueOf(tipoFuncionarioEntity.get().getDescricao()));
                }
            });


        } catch (Exception ex){
            System.out.println("Erro no Cliente: " + ex.getMessage());
        }
    }

    @FXML
    public void InserirFuncionario(ActionEvent event) throws IOException {
        Pane.setVisible(true);
        mainPanel.setEffect(new javafx.scene.effect.GaussianBlur(4.0));
        mainPanel.setDisable(true);

    }

    @FXML
    public void VoltarAtrasInserirFuncionario(ActionEvent event) {
        Pane.setVisible(false);
        RuaText.clear();
        NifText.clear();
        NomeText.clear();
        PortaText.clear();
        EmailText.clear();
        TelefoneText.clear();
        CodPostalCombo.getSelectionModel().clearSelection();
        TipoFuncionarioCombo.getSelectionModel().clearSelection();

        EditPane.setVisible(false);
        EditRuaText.clear();
        EditNifText.clear();
        EditNomeText.clear();
        EditPortaText.clear();
        EditEmailText.clear();
        EditTelefoneText.clear();

        mainPanel.setEffect(null);
        mainPanel.setDisable(false);

        EditPane.setEffect(null);
        EditPane.setDisable(false);

    }


    @FXML
    public void RegistarFucncionario(ActionEvent event) throws IOException {

        if(Objects.equals(RuaText.getText(), "") || Objects.equals(NifText.getText(), "") || Objects.equals(NomeText.getText(), "")
                && Objects.equals(PortaText.getText(), "") || Objects.equals(EmailText.getText(), "") ||  Objects.equals(TelefoneText.getText(), "")) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Campos Inválidos!");
            alert.setHeaderText("Campos por preencher");
            alert.setContentText("Preencha todos os campos e tente novamente!");

            alert.showAndWait();

        } else {


            FuncionarioEntity novoFuncionario = new FuncionarioEntity();
            novoFuncionario.setNome(NomeText.getText());
            novoFuncionario.setNif(Integer.valueOf(NifText.getText()));
            novoFuncionario.setRua(RuaText.getText());
            novoFuncionario.setPorta(Integer.valueOf(PortaText.getText()));

            CodPostalEntity codPostalEntity = cod_postal_repo.findByNameLike(CodPostalCombo.getSelectionModel().getSelectedItem());
            TipoFuncionarioEntity tipoFuncionarioEntity = tipo_funcionario_repo.findByDescLike(TipoFuncionarioCombo.getSelectionModel().getSelectedItem());

            novoFuncionario.setIdCodPostal(codPostalEntity.getIdCodPostal());
            novoFuncionario.setCodPostalByIdCodPostal(codPostalEntity);
            novoFuncionario.setIdTipoFuncionario(tipoFuncionarioEntity.getId());
            novoFuncionario.setTipoFuncionarioByIdTipoFuncionario(tipoFuncionarioEntity);
            novoFuncionario.setEmail(EmailText.getText());
            novoFuncionario.setTelefone(TelefoneText.getText());
            novoFuncionario.setUtilizador(EmailText.getText().substring(0,EmailText.getText().indexOf('@')));
            novoFuncionario.setPassword("default");

            try {
                funcionario_repo.save(novoFuncionario);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sucesso!");
                alert.setHeaderText("Registo efetuado com sucesso!");
                alert.showAndWait();



            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            RuaText.clear();
            NifText.clear();
            NomeText.clear();
            PortaText.clear();
            EmailText.clear();
            TelefoneText.clear();
            CodPostalCombo.getSelectionModel().clearSelection();
            TipoFuncionarioCombo.getSelectionModel().clearSelection();

            ObservableList<FuncionarioEntity> funcionarioAtualizados = FXCollections.observableArrayList(funcionario_repo.findAll());
            table.setItems(funcionarioAtualizados);

        }
    }

    @FXML
    public void ExcluirFuncionario(ActionEvent event) {

        if (table.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Erro!");
            alert.setHeaderText("Nenhum cliente selecionado!");
            alert.setContentText("Selecione um funcionario para eliminar!");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Eliminar Funcionario");
            alert.setHeaderText("Eliminar Funcionario");
            alert.setContentText("Tem a certeza que pretende eliminar este funcionario?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                funcionario_repo.deleteById(table.getSelectionModel().getSelectedItem().getId());
                ObservableList<FuncionarioEntity> funcionariosAtualizados = FXCollections.observableArrayList(funcionario_repo.findAll());
                table.setItems(funcionariosAtualizados);
            }
        }
    }

    @FXML
    public void ShowEditarFuncionario() {

        EditPane.setVisible(true);
        mainPanel.setEffect(new javafx.scene.effect.GaussianBlur(4.0));
        EditPane.setEffect(new javafx.scene.effect.DropShadow());
        mainPanel.setDisable(true);

        EditNomeText.setText(table.getSelectionModel().getSelectedItem().getNome());
        EditNifText.setText(table.getSelectionModel().getSelectedItem().getNif().toString());
        EditRuaText.setText(table.getSelectionModel().getSelectedItem().getRua());
        EditPortaText.setText(table.getSelectionModel().getSelectedItem().getPorta().toString());
        EditEmailText.setText(table.getSelectionModel().getSelectedItem().getEmail());
        EditTelefoneText.setText(table.getSelectionModel().getSelectedItem().getTelefone());
        EditCodPostalCombo.getSelectionModel().select(table.getSelectionModel().getSelectedItem().getCodPostalByIdCodPostal().getLocalidade());
        EditTipoFuncionarioCombo.getSelectionModel().select(TipoFuncionario.getText());

    }

    @FXML
    public void EditarFuncionario() {
        if(Objects.equals(EditRuaText.getText(), "") || Objects.equals(EditNifText.getText(), "") || Objects.equals(EditNomeText.getText(), "")
                && Objects.equals(EditPortaText.getText(), "") || Objects.equals(EditEmailText.getText(), "") ||  Objects.equals(EditTelefoneText.getText(), "")) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Campos Inválidos!");
            alert.setHeaderText("Campos por preencher");
            alert.setContentText("Preencha todos os campos e tente novamente!");

            alert.showAndWait();



        } else {

            FuncionarioEntity editFuncionario = table.getSelectionModel().getSelectedItem();
            editFuncionario.setNome(EditNomeText.getText());
            editFuncionario.setNif(Integer.valueOf(EditNifText.getText()));
            editFuncionario.setRua(EditRuaText.getText());
            editFuncionario.setPorta(Integer.valueOf(EditPortaText.getText()));

            CodPostalEntity codPostalEntity = cod_postal_repo.findByNameLike(EditCodPostalCombo.getSelectionModel().getSelectedItem());
            TipoFuncionarioEntity tipoFuncionarioEntity = tipo_funcionario_repo.findByDescLike(EditTipoFuncionarioCombo.getSelectionModel().getSelectedItem());

            editFuncionario.setIdCodPostal(codPostalEntity.getIdCodPostal());
            editFuncionario.setIdTipoFuncionario(tipoFuncionarioEntity.getId());
            editFuncionario.setEmail(EditEmailText.getText());
            editFuncionario.setTelefone(EditTelefoneText.getText());
            editFuncionario.setCodPostalByIdCodPostal(codPostalEntity);
            editFuncionario.setTipoFuncionarioByIdTipoFuncionario(tipoFuncionarioEntity);

            try {
                funcionario_repo.save(editFuncionario);

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

        ObservableList<FuncionarioEntity> funcionariosAtualizados = FXCollections.observableArrayList(funcionario_repo.findAll());
        table.setItems(funcionariosAtualizados);
        table.refresh();
    }

    public class DoubleClickHandler implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {
            if (event.getClickCount() == 2) {
                ShowEditarFuncionario();

            }
        }
    }
}
