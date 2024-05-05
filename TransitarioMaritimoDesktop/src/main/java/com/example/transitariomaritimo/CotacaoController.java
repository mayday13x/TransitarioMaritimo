package com.example.transitariomaritimo;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pt.ipvc.database.entity.CotacaoEntity;
import pt.ipvc.database.entity.ServicoEntity;
import pt.ipvc.database.repository.CotacaoRepository;
import pt.ipvc.database.repository.ServicoRepository;

import java.io.IOException;

public class CotacaoController {

    private AnnotationConfigApplicationContext context;
    private CotacaoRepository cotacao_repo;
    private ServicoRepository servico_repo;

    @FXML
    private Pane CriarPane;

    @FXML
    private TableColumn<CotacaoEntity, String> Data;

    @FXML
    private Pane EditarPane;

    @FXML
    private TableColumn<CotacaoEntity, String> Estado;

    @FXML
    private TableColumn<CotacaoEntity, String> Funcionario;

    @FXML
    private TableColumn<CotacaoEntity, String> IdCliente;

    @FXML
    private TableColumn<CotacaoEntity, String> Utilizador;

    @FXML
    private TableColumn<CotacaoEntity, String> ValorTotal;

    @FXML
    private TableColumn<CotacaoEntity, String> Id;

    @FXML
    private TableColumn<ServicoEntity, String> ComissaoServico;

    @FXML
    private TableColumn<ServicoEntity, String> DescricaoServico;

    @FXML
    private TableColumn<ServicoEntity, String> FornedorServico;

    @FXML
    private TableColumn<ServicoEntity, String> IdServico;

    @FXML
    private TableColumn<ServicoEntity, String> PrecoServico;

    @FXML
    private Pane ServicoPane;

    @FXML
    private Button deleteButton;

    @FXML
    private Button guardarButton;

    @FXML
    private Pane mainPane;

    @FXML
    private Pane CotacaoClientePane;

    @FXML
    private TableView<CotacaoEntity> table_cotacoes;

    @FXML
    private TableView<ServicoEntity> TableServico;

    private int idCliente = 0;

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public void CotacaoCliente() {

        DoubleClickHandler handler = new DoubleClickHandler();
        table_cotacoes.setOnMouseClicked(handler);

        context = new AnnotationConfigApplicationContext(AppConfig.class);
        cotacao_repo = context.getBean(CotacaoRepository.class);
        servico_repo = context.getBean(ServicoRepository.class);

        if(idCliente != 0) {
            ObservableList<CotacaoEntity> cotacoes = FXCollections.observableArrayList(cotacao_repo.findByIdClienteLike(idCliente));

            Id.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getId().toString()));
            IdCliente.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getIdCliente().toString()));
            Estado.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEstadoCotacaoByIdEstadoCotacao().getDescricao()));
            Data.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getData().toString()));
            ValorTotal.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getValorTotal().toString()));
            table_cotacoes.setItems(cotacoes);

            ObservableList<ServicoEntity> servicos = FXCollections.observableArrayList(cotacao_repo.findByIdClienteServico(idCliente));
            IdServico.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getId().toString()));
            FornedorServico.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFornecedorByIdFornecedor().getNome()));
            ComissaoServico.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getComissao().toString()));
            PrecoServico.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPreco().toString()));
            DescricaoServico.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDescricao()));
            TableServico.setItems(servicos);
        }



    }

    @FXML
    void EditarCliente(ActionEvent event) {

    }

    @FXML
    void InserirCotacao(ActionEvent event) {
        CriarPane.setVisible(true);
        mainPane.setEffect(new javafx.scene.effect.GaussianBlur(4.0));
        mainPane.setDisable(true);
    }

    @FXML
    void RegistarCotacao(ActionEvent event) {

    }

    @FXML
    void VoltarAtrasInserirCliente(ActionEvent event) {
        CriarPane.setVisible(false);
        mainPane.setEffect(null);
        mainPane.setDisable(false);
    }

    @FXML
    void VisualizarServicos(ActionEvent event) {
        ServicoPane.setVisible(true);
        CotacaoClientePane.setEffect(new javafx.scene.effect.GaussianBlur(4.0));
        CotacaoClientePane.setDisable(true);
    }

    @FXML
    void VoltarAtrasVisualizarServicos(ActionEvent event) {
        ServicoPane.setVisible(false);
        CotacaoClientePane.setEffect(null);
        CotacaoClientePane.setDisable(false);
    }

    @FXML
    public void ShowVisualizarServicos() {
        ServicoPane.setVisible(true);
        CotacaoClientePane.setEffect(new javafx.scene.effect.GaussianBlur(4.0));
        CotacaoClientePane.setDisable(true);

    }

    @FXML
    void VoltarAtrasCliente(ActionEvent event) {

        try{
            Parent root = FXMLLoader.load(getClass().getResource("MenuClienteView.fxml"));
            Scene regCena = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(regCena);
            stage.setTitle("Menu");
            stage.show();
        }catch (IOException ex){
            System.out.println("Erro ao acessar menu cliente: " + ex.getMessage());
        }
    }

    public class DoubleClickHandler implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {
            if (event.getClickCount() == 2) {
                ShowVisualizarServicos();

            }
        }
    }
}
