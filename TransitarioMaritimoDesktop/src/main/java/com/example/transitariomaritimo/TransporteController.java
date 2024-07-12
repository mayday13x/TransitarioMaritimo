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
import pt.ipvc.database.entity.ClienteEntity;
import pt.ipvc.database.entity.CodPostalEntity;
import pt.ipvc.database.entity.FuncionarioEntity;
import pt.ipvc.database.entity.TransportemaritimoEntity;
import pt.ipvc.database.repository.FuncionarioRepository;
import pt.ipvc.database.repository.TransportemaritimoRepository;

import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class TransporteController implements Initializable {

    public AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
    public TransportemaritimoRepository transportemaritim_repo =  context.getBean(TransportemaritimoRepository.class);

    public FuncionarioRepository funcionario_repo = context.getBean(FuncionarioRepository.class);
    @FXML
    private Pane mainPanel;

    @FXML
    private TableColumn<TransportemaritimoEntity, String> Destino;

    @FXML
    private TableColumn<TransportemaritimoEntity, String> Id;

    @FXML
    private TableColumn<TransportemaritimoEntity, String> Imo;

    @FXML
    private TableColumn<TransportemaritimoEntity, String> Origem;

    @FXML
    private TableColumn<TransportemaritimoEntity, String> Funcionario;

    @FXML
    private TableView<TransportemaritimoEntity> Table;

    //edit elements

    @FXML
    private ComboBox<FuncionarioEntity> FuncTransporteEdit;

    @FXML
    private TextField IMOTextEdit;

    @FXML
    private TextField PDestinoTextEdit;

    @FXML
    private TextField POrigemTextEdit;

    //inserir elements

    @FXML
    private ComboBox<FuncionarioEntity> FuncTransporteBox;

    @FXML
    private TextField IMOText;

    @FXML
    private TextField PDestinoText;

    @FXML
    private TextField POrigemText;


    @FXML
    private Pane PaneEditar;

    @FXML
    private Pane PaneInserir;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<TransportemaritimoEntity> transportemaritimo = FXCollections.observableArrayList(transportemaritim_repo.findAll());

        Id.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getId().toString()));
        Imo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getImo()));
        Origem.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPortoOrigem()));
        Destino.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPortoDestino()));
        Funcionario.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFuncionarioByIdFuncionario().getNome()));
        Table.setItems(transportemaritimo);

        // Configurar o StringConverter para FuncTransporteBox
        FuncTransporteBox.setConverter(new StringConverter<FuncionarioEntity>() {
            @Override
            public String toString(FuncionarioEntity funcionario) {
                return funcionario != null ? funcionario.getNome() : "";
            }

            @Override
            public FuncionarioEntity fromString(String string) {
                return FuncTransporteBox.getItems().stream().filter(funcionario ->
                        funcionario.getNome().equals(string)).findFirst().orElse(null);
            }
        });

        // Configurar o StringConverter para FuncTransporteEdit
        FuncTransporteEdit.setConverter(new StringConverter<FuncionarioEntity>() {
            @Override
            public String toString(FuncionarioEntity funcionario) {
                return funcionario != null ? funcionario.getNome() : "";
            }

            @Override
            public FuncionarioEntity fromString(String string) {
                return FuncTransporteEdit.getItems().stream().filter(funcionario ->
                        funcionario.getNome().equals(string)).findFirst().orElse(null);
            }
        });

            ObservableList<FuncionarioEntity> funcionarios = FXCollections.observableArrayList(funcionario_repo.findTransporteMaritimo());
            FuncTransporteBox.setItems(funcionarios);
            FuncTransporteEdit.setItems(funcionarios);

    }


    public void RegistarTransporte(ActionEvent event) {

        //logica registar Transporte
        // se a combobox estiver vazia mostrar erro
        if(Objects.equals(IMOText.getText(), "") || Objects.equals(POrigemText.getText(), "")
                || Objects.equals(PDestinoText.getText(), "") || FuncTransporteBox.getSelectionModel().getSelectedItem() == null) {


            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Campos Inválidos!");
            alert.setHeaderText("Campos por preencher");
            alert.setContentText("Preencha todos os campos e tente novamente!");

            alert.showAndWait();

        } else {

            TransportemaritimoEntity newTransporte = new TransportemaritimoEntity();
            newTransporte.setImo(IMOText.getText());
            newTransporte.setPortoOrigem(POrigemText.getText());
            newTransporte.setPortoDestino(PDestinoText.getText());
            newTransporte.setFuncionarioByIdFuncionario(FuncTransporteBox.getSelectionModel().getSelectedItem());
            newTransporte.setIdFuncionario(FuncTransporteBox.getSelectionModel().getSelectedItem().getId());

            try {
                transportemaritim_repo.save(newTransporte);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sucesso!");
                alert.setHeaderText("Registo efetuado com sucesso!");
                alert.showAndWait();



            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            IMOText.clear();
            POrigemText.clear();
            PDestinoText.clear();
            FuncTransporteBox.getSelectionModel().clearSelection();

            atualizarTableTransportes();
            CloseInserirTransporte();

        }
    }

    public void EditarTransporte(ActionEvent event) {

        //logica registar Transporte
        // se a combobox estiver vazia mostrar erro
        if(Objects.equals(IMOTextEdit.getText(), "") || Objects.equals(POrigemTextEdit.getText(), "")
                || Objects.equals(PDestinoTextEdit.getText(), "") || FuncTransporteEdit.getSelectionModel().getSelectedItem() == null) {


            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Campos Inválidos!");
            alert.setHeaderText("Campos por preencher");
            alert.setContentText("Preencha todos os campos e tente novamente!");

            alert.showAndWait();

        } else {

            TransportemaritimoEntity editTransporte = Table.getSelectionModel().getSelectedItem();
            editTransporte.setImo(IMOTextEdit.getText());
            editTransporte.setPortoOrigem(POrigemTextEdit.getText());
            editTransporte.setPortoDestino(PDestinoTextEdit.getText());
            editTransporte.setFuncionarioByIdFuncionario(FuncTransporteEdit.getSelectionModel().getSelectedItem());
            editTransporte.setIdFuncionario(FuncTransporteEdit.getSelectionModel().getSelectedItem().getId());

            try {
                transportemaritim_repo.save(editTransporte);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sucesso!");
                alert.setHeaderText("Registo editado com sucesso!");
                alert.showAndWait();

                IMOTextEdit.clear();
                POrigemTextEdit.clear();
                PDestinoTextEdit.clear();
                FuncTransporteEdit.getSelectionModel().clearSelection();

                atualizarTableTransportes();
                CloseEditarTransporte();


            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }
    }

    public void removerTransporte() {
        if (Table.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Erro!");
            alert.setHeaderText("Nenhum transporte selecionado!");
            alert.setContentText("Selecione um transporte para eliminar!");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Eliminar Transporte");
            alert.setHeaderText("Eliminar Transporte");
            alert.setContentText("Tem a certeza que pretende eliminar este transporte?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                transportemaritim_repo.deleteById(Table.getSelectionModel().getSelectedItem().getId());
                atualizarTableTransportes();
            }
        }
    }

    public void atualizarTableTransportes() {
        ObservableList<TransportemaritimoEntity> transportesAtualizados = FXCollections.observableArrayList(transportemaritim_repo.findAll());
        Table.setItems(transportesAtualizados);
        Table.refresh();
    }

    @FXML
    public void ShowInserirTransporte() {

        PaneInserir.setVisible(true);
        mainPanel.setEffect(new javafx.scene.effect.GaussianBlur(4.0));
        PaneInserir.setEffect(new javafx.scene.effect.DropShadow());
        mainPanel.setDisable(true);


    }

    @FXML
    public void CloseInserirTransporte() {

        PaneInserir.setVisible(false);
        mainPanel.setEffect(null);
        mainPanel.setDisable(false);

    }

    @FXML
    public void ShowEditarTransporte() {

        if (Table.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Erro!");
            alert.setHeaderText("Nenhum transporte selecionado!");
            alert.setContentText("Selecione um transporte para editar!");
            alert.showAndWait();
        } else {
            PaneEditar.setVisible(true);
            mainPanel.setEffect(new javafx.scene.effect.GaussianBlur(4.0));
            PaneEditar.setEffect(new javafx.scene.effect.DropShadow());
            mainPanel.setDisable(true);

            IMOTextEdit.setText(Table.getSelectionModel().getSelectedItem().getImo());
            POrigemTextEdit.setText(Table.getSelectionModel().getSelectedItem().getPortoOrigem());
            PDestinoTextEdit.setText(Table.getSelectionModel().getSelectedItem().getPortoDestino());
            FuncTransporteEdit.getSelectionModel().select(Table.getSelectionModel().getSelectedItem().getFuncionarioByIdFuncionario());
        }
    }

    @FXML
    public void CloseEditarTransporte() {

        PaneEditar.setVisible(false);
        mainPanel.setEffect(null);
        mainPanel.setDisable(false);
    }

}
