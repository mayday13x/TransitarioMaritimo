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
import pt.ipvc.database.entity.*;
import pt.ipvc.database.repository.*;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import static javafx.scene.input.KeyCode.L;

public class CotacaoController implements Initializable {

    public AnnotationConfigApplicationContext context;
    private ServicoRepository service_repo;
    private ClienteRepository cliente_repo;
    private CargaRepository carga_repo;
    private CotacaoRepository cotacao_repo;
    private TipoCargaRepository tpcarga_repo;
    private LinhaCotacaoRepository linha_cotacao_repo;


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
    private Button deleteButton;

    @FXML
    private Button guardarButton;

    @FXML
    private Pane mainPane;

    @FXML
    private TableView<CotacaoEntity> table_cotacoes;

    // ADD Cotacao

    @FXML
    private TextField AlturaCarga;

    @FXML
    private TextField CompCarga;

    @FXML
    private TextField LarguraCarga;

    @FXML
    private TextField NomeCarga;

    @FXML
    private TextArea Observacoes;

    @FXML
    private TextField PesoCarga;

    @FXML
    private TextField QtdCarga;

    @FXML
    private TableColumn<ClienteEntity, String> TCilentesNIF;

    @FXML
    private TableColumn<ClienteEntity, String> TClientesID;

    @FXML
    private TableColumn<ClienteEntity, String> TClientesNome;

    @FXML
    private TableView<ServicoEntity> TCotaServ;

    @FXML
    private TableView<ServicoEntity> TServicos;

    @FXML
    private TableView<ClienteEntity> TableClientes;

    @FXML
    private ComboBox<String> TipoCarga;

    @FXML
    private TableColumn<ServicoEntity, String> ServPreco;

    @FXML
    private TableColumn<ServicoEntity, String> ServServico;

    @FXML
    private TableColumn<ServicoEntity, String> CotPreco;

    @FXML
    private TableColumn<ServicoEntity, String> CotServico;

    @FXML
    private TableColumn<ServicoEntity, String> ServComissao;

    @FXML
    private TableColumn<ServicoEntity, String> CotComissao;

    @FXML
    private Label totalCota;

    public Set<ServicoEntity> selected_services = new HashSet<>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        context = new AnnotationConfigApplicationContext(AppConfig.class);
        service_repo = context.getBean(ServicoRepository.class);
        cliente_repo = context.getBean(ClienteRepository.class);
        carga_repo = context.getBean(CargaRepository.class);
        cotacao_repo = context.getBean(CotacaoRepository.class);
        tpcarga_repo = context.getBean(TipoCargaRepository.class);
        linha_cotacao_repo = context.getBean(LinhaCotacaoRepository.class);

        ObservableList<ClienteEntity> clientes = FXCollections.observableArrayList(cliente_repo.findAll());
        ObservableList<ServicoEntity> servicos = FXCollections.observableArrayList(service_repo.findAll());
        ObservableList<CargaEntity> cargas = FXCollections.observableArrayList(carga_repo.findAll());
        ObservableList<CotacaoEntity> cotacoes = FXCollections.observableArrayList(cotacao_repo.findAll());
        ObservableList<TipoCargaEntity> tpcargas = FXCollections.observableArrayList(tpcarga_repo.findAll());

        try{

            TClientesID.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getId().toString()));
            TClientesNome.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNome()));
            TCilentesNIF.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNif().toString()));
            TableClientes.setItems(clientes);

            for (TipoCargaEntity i : tpcargas) {
                TipoCarga.getItems().add(i.getDescricao());
            }
            TipoCarga.getSelectionModel().select(2);


        } catch (Exception ex){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Erro ao carregar tabela de clientes");
            alert.showAndWait();
        }

        // Carregar Serviços

        try {

            ServServico.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDescricao()));
            ServPreco.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPreco().toString()));
            ServComissao.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getComissao().toString()));

            TServicos.setItems(servicos);


        }   catch (Exception ex){
            System.out.println("Erro no Servico: " + ex.getMessage());
        }



    }

    @FXML
    void ShowCriarCotacao(ActionEvent event) {
        CriarPane.setVisible(true);
        mainPane.setEffect(new javafx.scene.effect.GaussianBlur(4.0));
        mainPane.setDisable(true);
    }

    public void AddServico(){
        ServicoEntity servico = TServicos.getSelectionModel().getSelectedItem();

        if(servico!= null && !TCotaServ.getItems().contains(servico)){

            CotServico.setCellValueFactory(data -> new SimpleStringProperty(servico.getDescricao()));
            CotPreco.setCellValueFactory(data -> new SimpleStringProperty(servico.getPreco().toString()));
            CotComissao.setCellValueFactory(data -> new SimpleStringProperty(servico.getComissao().toString()));
            TCotaServ.getItems().add(servico);
            selected_services.add(servico);

        }


    }

    public void RemoveServico(){
        ServicoEntity servico = TCotaServ.getSelectionModel().getSelectedItem();

        TCotaServ.getItems().remove(servico);
        selected_services.remove(servico);
    }

    @FXML
    void RegistarCotacao(ActionEvent event) {

        if(TableClientes.getSelectionModel().getSelectedItem() == null || TCotaServ.getItems().size() == 0 ||
         AlturaCarga.getText().isEmpty() || CompCarga.getText().isEmpty() || LarguraCarga.getText().isEmpty() ||
         NomeCarga.getText().isEmpty() || PesoCarga.getText().isEmpty() || QtdCarga.getText().isEmpty() || Observacoes.getText().isEmpty()){

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Por favor preencha todos os campos");
            alert.showAndWait();

        } else {

        //REGISTAR COTACAO
             CotacaoEntity cotacao = new CotacaoEntity();
             LinhaCotacaoEntity linha = new LinhaCotacaoEntity();

             cotacao.setIdCliente(clienteSelecionado().getId());
             cotacao.setIdEstadoCotacao(3);
             cotacao.setData(Date.valueOf(LocalDate.now()));

            cotacao_repo.save(cotacao);

         //REGISTAR CARGA

            CargaEntity carga = new CargaEntity();
            carga.setIdCotacao(cotacao.getId());
            carga.setNome(NomeCarga.getText());
            carga.setQuantidade(Integer.parseInt(QtdCarga.getText()));
            Double altura = Double.parseDouble(AlturaCarga.getText());
            Double comp = Double.parseDouble(CompCarga.getText());
            Double largura = Double.parseDouble(LarguraCarga.getText());
            carga.setVolume(altura * comp * largura);
            carga.setPeso(Double.parseDouble(PesoCarga.getText()));
            carga.setIdTipoCarga(tpcarga_repo.findByDescLike(TipoCarga.getSelectionModel().getSelectedItem()).getId());
            carga.setObservacoes(Observacoes.getText());

            carga_repo.save(carga);

            double valorTotal = 0;
            for(ServicoEntity i : selected_services){

                valorTotal += i.getPreco() + (i.getComissao() * i.getPreco()); // val + comissão
                linha.setIdCotacao(cotacao.getId());
                linha.setIdServico(i.getId());
                linha_cotacao_repo.save(linha);     //erro aqui
            }

            cotacao.setValorTotal(valorTotal);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText(null);
            alert.setContentText("Cotação registada com sucesso");
            alert.showAndWait();

        }


    }

    public ClienteEntity clienteSelecionado() {
        ClienteEntity cliente = TableClientes.getSelectionModel().getSelectedItem();
        return cliente;
    }

    @FXML
    void Voltar(ActionEvent event) {
        CriarPane.setVisible(false);
        mainPane.setEffect(null);
        mainPane.setDisable(false);
    }

}
