package com.example.transitariomaritimo;

import com.example.transitariomaritimo.session.Current_Session;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pt.ipvc.database.entity.*;
import pt.ipvc.database.repository.*;
import javafx.event.EventHandler;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
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
  //  private LinhaCotacaoRepositoryPK linha_cotacao_repoPK;
    private EstadoCotacaoRepository estado_cotacao_repo;


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
    private TableView<CotacaoEntity> table_cotacoes;

    @FXML
    private TableColumn<CotacaoEntity, String> Id;

    @FXML
    private Button deleteButton;

    @FXML
    private Button guardarButton;

    @FXML
    private Pane mainPane;

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
    private TableView<ServicoEntity> TableServico;

    @FXML
    private Label totalCota;

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
    private Pane CotacaoClientePane;

    //Elementos da View para mostrar todas as cotações

    @FXML
    private TableView<CotacaoEntity> TCotacoes;

    @FXML
    private TableColumn<CotacaoEntity, String> IdCotacao;

    @FXML
    private TableColumn<CotacaoEntity, String> IdUtilizadorCotacao;

    @FXML
    private TableColumn<CotacaoEntity, String> UtilizadorCotacao;    //nome do user

    @FXML
    private TableColumn<CotacaoEntity, String> DataCotacao;

    @FXML
    private TableColumn<CotacaoEntity, String> ValorTotalCotacao;

    @FXML
    private TableColumn<CotacaoEntity, String> EstadoCotacao;


    public Set<ServicoEntity> selected_services = new HashSet<>();

    private int idCliente = 0;

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        context = new AnnotationConfigApplicationContext(AppConfig.class);
        service_repo = context.getBean(ServicoRepository.class);
        cliente_repo = context.getBean(ClienteRepository.class);
        carga_repo = context.getBean(CargaRepository.class);
        cotacao_repo = context.getBean(CotacaoRepository.class);
        tpcarga_repo = context.getBean(TipoCargaRepository.class);
        linha_cotacao_repo = context.getBean(LinhaCotacaoRepository.class);
        estado_cotacao_repo = context.getBean(EstadoCotacaoRepository.class);
        //linha_cotacao_repoPK = context.getBean(LinhaCotacaoRepositoryPK.class);

        ObservableList<ClienteEntity> clientes = FXCollections.observableArrayList(cliente_repo.findAll());
        ObservableList<ServicoEntity> servicos = FXCollections.observableArrayList(service_repo.findAll());
        ObservableList<CargaEntity> cargas = FXCollections.observableArrayList(carga_repo.findAll());
        ObservableList<CotacaoEntity> cotacoes = FXCollections.observableArrayList(cotacao_repo.findAll());
        ObservableList<TipoCargaEntity> tpcargas = FXCollections.observableArrayList(tpcarga_repo.findAll());

        try{
            if(TableClientes != null){
                TClientesID.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getId().toString()));
                TClientesNome.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNome()));
                TCilentesNIF.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNif().toString()));
                TableClientes.setItems(clientes);

                for (TipoCargaEntity i : tpcargas) {
                    TipoCarga.getItems().add(i.getDescricao());
                }
                TipoCarga.getSelectionModel().select(2);
            }

        } catch (Exception ex){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Erro ao carregar tabela de clientes");
            alert.showAndWait();
        }

        // Carregar Serviços

        try {
            if(TableClientes != null){
                ServServico.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDescricao()));
                ServPreco.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPreco().toString()));
                ServComissao.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getComissao().toString()));

                TServicos.setItems(servicos);

            }


        }   catch (Exception ex){
            System.out.println("Erro no Servico: " + ex.getMessage());
        }

        if(TCotaServ != null){
            ObservableList<ServicoEntity> selectedServicos = TCotaServ.getItems();

            selectedServicos.addListener((ListChangeListener<ServicoEntity>) change -> {
                double total = 0;
                for( ServicoEntity servico: selectedServicos){
                    total += servico.getPreco() + (servico.getComissao() * servico.getPreco());
                }

                totalCota.setText(String.valueOf(total) + " €");
            });
        }

        if(TCotacoes != null){
            IdCotacao.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getId().toString()));
            IdUtilizadorCotacao.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getIdCliente().toString()));
            UtilizadorCotacao.setCellValueFactory(data -> new SimpleStringProperty((data.getValue().getClienteByIdCliente()).getNome()));
            DataCotacao.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getData().toString()));
            ValorTotalCotacao.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getValorTotal().toString()));
            EstadoCotacao.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEstadoCotacaoByIdEstadoCotacao().getDescricao()));

            TCotacoes.setItems(cotacoes);
        }
    }

    public void print(){
        System.out.println("12332432532532532523532532532532532532");
    }

    public void CotacaoCliente() {
        System.out.println("cotacaoCliente()");
        DoubleClickHandler handler = new DoubleClickHandler();
        table_cotacoes.setOnMouseClicked(handler);
        context = new AnnotationConfigApplicationContext(AppConfig.class);
        cotacao_repo = context.getBean(CotacaoRepository.class);
        service_repo = context.getBean(ServicoRepository.class);

        if(idCliente != 0) {
            ObservableList<CotacaoEntity> cotacoes = FXCollections.observableArrayList(cotacao_repo.findByIdClienteLike(Current_Session.current_client.getId()));

            Id.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getId().toString()));
            IdCliente.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getIdCliente().toString()));
            Estado.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEstadoCotacaoByIdEstadoCotacao().getDescricao()));
            Data.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getData().toString()));
            ValorTotal.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getValorTotal().toString()));
            table_cotacoes.setItems(cotacoes);

        }
    }

    @FXML
    void ShowCriarCotacao(ActionEvent event) {
        CriarPane.setVisible(true);
        mainPane.setEffect(new javafx.scene.effect.GaussianBlur(4.0));
        mainPane.setDisable(true);
       // System.out.println(Current_Session.current_client.getId() + ": " + Current_Session.current_client.getNome());
       // System.out.println(Current_Session.current_funcionario.getId() + ": " + Current_Session.current_funcionario.getNome());
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
             EstadoCotacaoEntity estadoCotacao = estado_cotacao_repo.findByDescricaoLike("Em analise");
             cotacao.setIdCliente(clienteSelecionado().getId());
             cotacao.setIdEstadoCotacao(3);
             cotacao.setEstadoCotacaoByIdEstadoCotacao(estadoCotacao);
             cotacao.setData(Date.valueOf(LocalDate.now()));
             cotacao.setClienteByIdCliente(TableClientes.getSelectionModel().getSelectedItem());

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
            TipoCargaEntity cargaEntity = tpcarga_repo.findByDescLike(TipoCarga.getSelectionModel().getSelectedItem());;
            carga.setTipoCargaByIdTipoCarga(cargaEntity);
            carga.setObservacoes(Observacoes.getText());
            carga.setCotacaoByIdCotacao(cotacao);

            carga_repo.save(carga);

            LinhaCotacaoEntity linha = new LinhaCotacaoEntity();


            double valorTotal = 0;
            for(ServicoEntity i : selected_services){

                valorTotal += i.getPreco() + (i.getComissao() * i.getPreco()); // val + comissão

               linha.setIdCotacao(cotacao.getId());
               linha.setIdServico(i.getId());
               linha.setCotacaoByIdCotacao(cotacao);
               linha.setServicoByIdServico(i);

               linha_cotacao_repo.save(linha);

            }

            cotacao.setValorTotal(valorTotal);
            cotacao_repo.save(cotacao);

            System.out.println(valorTotal);

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

    @FXML
    public void ConfirmarCotacao(ActionEvent event) {

        if (table_cotacoes.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Erro!");
            alert.setHeaderText("Nenhuma cotação selecionada!");
            alert.setContentText("Selecione uma cotação para pagar!");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Pagar Cotação");
            alert.setHeaderText("Pagar Cotação");
            alert.setContentText("Tem a certeza que pretende pagar esta Cotação?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                CotacaoEntity cotacao = table_cotacoes.getSelectionModel().getSelectedItem();
                EstadoCotacaoEntity estadoCotacao = estado_cotacao_repo.findByDescricaoLike("Confirmado");

                cotacao.setIdEstadoCotacao(estadoCotacao.getId());
                cotacao.setEstadoCotacaoByIdEstadoCotacao(estadoCotacao);

                try {
                    cotacao_repo.save(cotacao);

                    table_cotacoes.getItems().clear();
                    ObservableList<CotacaoEntity> cotacoes = FXCollections.observableArrayList(cotacao_repo.findByIdClienteLike(idCliente));
                    table_cotacoes.setItems(cotacoes);

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    @FXML
    public void RejeitarCotacao(ActionEvent event) {

        if (table_cotacoes.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Erro!");
            alert.setHeaderText("Nenhuma cotação selecionada!");
            alert.setContentText("Selecione uma cotação para pagar!");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Rejeitar Cotação");
            alert.setHeaderText("Rejeitar Cotação");
            alert.setContentText("Tem a certeza que pretende rejeitar esta Cotação?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                CotacaoEntity cotacao = table_cotacoes.getSelectionModel().getSelectedItem();
                EstadoCotacaoEntity estadoCotacao = estado_cotacao_repo.findByDescricaoLike("Rejeitado");

                cotacao.setIdEstadoCotacao(estadoCotacao.getId());
                cotacao.setEstadoCotacaoByIdEstadoCotacao(estadoCotacao);

                try {
                    cotacao_repo.save(cotacao);

                    table_cotacoes.getItems().clear();
                    ObservableList<CotacaoEntity> cotacoes = FXCollections.observableArrayList(cotacao_repo.findByIdClienteLike(idCliente));
                    table_cotacoes.setItems(cotacoes);

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    @FXML
    void VisualizarServicos(ActionEvent event) {
        ServicoPane.setVisible(true);
        CotacaoClientePane.setEffect(new javafx.scene.effect.GaussianBlur(4.0));
        CotacaoClientePane.setDisable(true);

        CotacaoEntity cotacao = table_cotacoes.getSelectionModel().getSelectedItem();

        ObservableList<ServicoEntity> servicos = FXCollections.observableArrayList(cotacao_repo.findByIdCotacao(cotacao.getId()));
        IdServico.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getId().toString()));
        FornedorServico.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFornecedorByIdFornecedor().getNome()));
        ComissaoServico.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getComissao().toString()));
        PrecoServico.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPreco().toString()));
        DescricaoServico.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDescricao()));
        TableServico.setItems(servicos);

    }

    @FXML
    void VoltarAtrasVisualizarServicos(ActionEvent event) {
        ServicoPane.setVisible(false);
        CotacaoClientePane.setEffect(null);
        CotacaoClientePane.setDisable(false);
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

    @FXML
    public void ShowVisualizarServicos() {
        ServicoPane.setVisible(true);
        CotacaoClientePane.setEffect(new javafx.scene.effect.GaussianBlur(4.0));
        CotacaoClientePane.setDisable(true);

        CotacaoEntity cotacao = table_cotacoes.getSelectionModel().getSelectedItem();

        ObservableList<ServicoEntity> servicos = FXCollections.observableArrayList(cotacao_repo.findByIdCotacao(cotacao.getId()));
        IdServico.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getId().toString()));
        FornedorServico.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFornecedorByIdFornecedor().getNome()));
        ComissaoServico.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getComissao().toString()));
        PrecoServico.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPreco().toString()));
        DescricaoServico.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDescricao()));
        TableServico.setItems(servicos);

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
