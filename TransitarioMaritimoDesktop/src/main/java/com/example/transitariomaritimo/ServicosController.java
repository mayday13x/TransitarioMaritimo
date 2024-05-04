package com.example.transitariomaritimo;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pt.ipvc.database.entity.ClienteEntity;
import pt.ipvc.database.entity.CodPostalEntity;
import pt.ipvc.database.entity.FornecedorEntity;
import pt.ipvc.database.entity.ServicoEntity;
import pt.ipvc.database.repository.ClienteRepository;
import pt.ipvc.database.repository.FornecedorRepository;
import pt.ipvc.database.repository.ServicoRepository;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ServicosController implements Initializable {

    public AnnotationConfigApplicationContext context;
    private ServicoRepository service_repo;

    private FornecedorRepository fornecedor_repo;

    @FXML
    private TextField ComissServic;

    @FXML
    private TableColumn<ServicoEntity, String> Comissao;

    @FXML
    private TextField DescServico;

    @FXML
    private TableColumn<ServicoEntity, String> Descricao;

    @FXML
    private TableColumn<ServicoEntity, String> Fornecedor;

    @FXML
    private ComboBox<String> FornecedorCombo;

    @FXML
    private TableColumn<ServicoEntity, String> Id;

    @FXML
    private Pane Pane;

    @FXML
    private TableColumn<ServicoEntity, String> Preco;

    @FXML
    private TextField PrecoServico;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;

    @FXML
    private Pane mainPanel;

    @FXML
    private TableView<ServicoEntity> table;

    @FXML
    public void showInserirServico(ActionEvent event) throws IOException {
        Pane.setVisible(true);
        mainPanel.setEffect(new javafx.scene.effect.GaussianBlur(4.0));
        mainPanel.setDisable(true);

    }

    @FXML
    void RegistarServico(ActionEvent event) {

        if(Objects.equals(DescServico.getText(), "") || Objects.equals(PrecoServico.getText(), "") || Objects.equals(ComissServic.getText(), "")
                || FornecedorCombo.getSelectionModel().getSelectedItem() == null){

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Campos Inválidos!");
            alert.setHeaderText("Campos por preencher");
            alert.setContentText("Preencha todos os campos e tente novamente!");

            alert.showAndWait();

        } else {


            ServicoEntity novoServico = new ServicoEntity();
            novoServico.setDescricao(DescServico.getText());
            novoServico.setPreco(Double.valueOf(PrecoServico.getText()));
            novoServico.setComissao(Double.valueOf(ComissServic.getText()));
            FornecedorEntity fornecedor = fornecedor_repo.findByNameLike(FornecedorCombo.getSelectionModel().getSelectedItem());
            novoServico.setFornecedorByIdFornecedor(fornecedor);
            novoServico.setIdFornecedor(fornecedor.getId());


            try {
                service_repo.save(novoServico);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sucesso!");
                alert.setHeaderText("Registo efetuado com sucesso!");
                alert.showAndWait();



            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            DescServico.clear();
            ComissServic.clear();
            PrecoServico.clear();
            FornecedorCombo.getSelectionModel().clearSelection();

            refreshTable();

        }


    }

    void refreshTable() {
        ObservableList<ServicoEntity> servicosAtualizados = FXCollections.observableArrayList(service_repo.findAll());
        table.setItems(servicosAtualizados);
    }

    @FXML
    void Voltar(ActionEvent event) {
        Pane.setVisible(false);
        //EditPane.setVisible(false);
        mainPanel.setEffect(null);
        mainPanel.setDisable(false);
        DescServico.clear();
        ComissServic.clear();
        PrecoServico.clear();
        FornecedorCombo.getSelectionModel().clearSelection();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        context = new AnnotationConfigApplicationContext(AppConfig.class);
        service_repo = context.getBean(ServicoRepository.class);

        ObservableList<ServicoEntity> servicos = FXCollections.observableArrayList(service_repo.findAll());

        try {

            Id.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getId().toString()));
            Descricao.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDescricao()));
            Preco.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPreco().toString()));
            Comissao.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getComissao().toString()));
            Fornecedor.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFornecedorByIdFornecedor().getNome()));

            table.setItems(servicos);

            fornecedor_repo = context.getBean(FornecedorRepository.class);

            ObservableList<FornecedorEntity> fornecedores = FXCollections.observableArrayList(fornecedor_repo.findAll());

            for (FornecedorEntity i : fornecedores){
                FornecedorCombo.getItems().addAll(i.getNome());
                //FornecedorCombo.getItems().addAll(i.getLocalidade());
            }

        }   catch (Exception ex){
            System.out.println("Erro no Servico: " + ex.getMessage());
        }


    }
}
