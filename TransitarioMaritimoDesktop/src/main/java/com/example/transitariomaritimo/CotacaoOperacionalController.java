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
import java.util.*;

import static javafx.scene.input.KeyCode.L;

public class CotacaoOperacionalController implements Initializable {

    public AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
    private ServicoRepository service_repo = context.getBean(ServicoRepository.class);
    private ClienteRepository cliente_repo = context.getBean(ClienteRepository.class);
    private CargaRepository carga_repo = context.getBean(CargaRepository.class);
    private CotacaoRepository cotacao_repo = context.getBean(CotacaoRepository.class);
    private TipoCargaRepository tpcarga_repo = context.getBean(TipoCargaRepository.class);
    private LinhaCotacaoRepository linha_cotacao_repo = context.getBean(LinhaCotacaoRepository.class);;
    //  private LinhaCotacaoRepositoryPK linha_cotacao_repoPK;
    private EstadoCotacaoRepository estado_cotacao_repo = context.getBean(EstadoCotacaoRepository.class);;
    private EstadoReservaRepository reserva_estado_repo = context.getBean(EstadoReservaRepository.class);
    private ReservaRepository reserva_repo = context.getBean(ReservaRepository.class);

    private TransportemaritimoRepository transporte_repo = context.getBean(TransportemaritimoRepository.class);

    /*
    *
    * service_repo = context.getBean(ServicoRepository.class);
        cliente_repo = context.getBean(ClienteRepository.class);
        carga_repo = context.getBean(CargaRepository.class);
        cotacao_repo = context.getBean(CotacaoRepository.class);
        tpcarga_repo = context.getBean(TipoCargaRepository.class);
        linha_cotacao_repo = context.getBean(LinhaCotacaoRepository.class);
        estado_cotacao_repo = context.getBean(EstadoCotacaoRepository.class);*/


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

    // Elemetos Detalhes

    @FXML
    private TableView<CotacaoEntity> TDetalhesCotacao;

    @FXML
    private TableColumn<CotacaoEntity, String> TDetalhesData;

    @FXML
    private TableColumn<CotacaoEntity, String> TDetalhesEstado;

    @FXML
    private TableColumn<CotacaoEntity, String> TDetalhesId;

    @FXML
    private TableColumn<CotacaoEntity, String> TDetalhesIdUtilizador;

    @FXML
    private TableColumn<CotacaoEntity, String> TDetalhesUtilizador;

    @FXML
    private TableColumn<CotacaoEntity, String> TDetalhesValorTotal;

    //Detalhes Servicos

    @FXML
    private TableView<ServicoEntity> TDetServicos;

    @FXML
    private TableColumn<ServicoEntity, String> TDetServicosDescricao;

    @FXML
    private TableColumn<ServicoEntity, String> TDetServicosPreco;

    @FXML
    private DatePicker DataPrevFim;

    @FXML
    private DatePicker DataPrevInicio;


    // Registar reserva fields

    @FXML
    private DatePicker DataField;
    @FXML
    private TextField OrigemField;
    @FXML
    private TextField DestinoField;

    //Registar reserva tabela transporte

    @FXML
    private TableView<TransportemaritimoEntity> TTranspMaritimo;

    @FXML
    private TableColumn<TransportemaritimoEntity, String> TransporteIMO;

    @FXML
    private TableColumn<TransportemaritimoEntity, String> TransportePDestino;

    @FXML
    private TableColumn<TransportemaritimoEntity, String> TransportePOrigem;

    public Set<ServicoEntity> selected_services = new HashSet<>();

    private int idCliente = 0;

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
    private ServicoEntity servicoSelecionado;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

       ObservableList<CotacaoEntity> cotacoes = FXCollections.observableArrayList(cotacao_repo.findByEstadoConfirmado());


        if(TCotacoes != null){
            IdCotacao.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getId().toString()));
            IdUtilizadorCotacao.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getIdCliente().toString()));
            UtilizadorCotacao.setCellValueFactory(data -> new SimpleStringProperty((data.getValue().getClienteByIdCliente()).getNome()));
            DataCotacao.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getData().toString()));
            ValorTotalCotacao.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getValorTotal().toString()));
            EstadoCotacao.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEstadoCotacaoByIdEstadoCotacao().getDescricao()));

            TCotacoes.setItems(cotacoes);

            // Listener para seleção de cotação
            TDetServicos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    servicoSelecionado = newSelection;
                    updateDatePickers();
                }
            });
        }

        // Listener para mudanças de data nos DatePicker
        DataPrevInicio.valueProperty().addListener((obs, oldDate, newDate) -> saveDateChanges());
        DataPrevFim.valueProperty().addListener((obs, oldDate, newDate) -> saveDateChanges());

    }

    private void updateDatePickers() {

        ServicoEntity servico = TDetServicos.getSelectionModel().getSelectedItem();
        CotacaoEntity cotacao = TCotacoes.getSelectionModel().getSelectedItem();

        LinhaCotacaoEntity linha = service_repo.findLinhaCotacaoByIdServicoAndCotacao(servico.getId(), cotacao.getId());
       // LocalDate currentDate = LocalDate.now();
        Date datePrevIni = linha.getDataPrevInicio();
        Date datePrevFim = linha.getDataPrevFim();

        if (datePrevIni != null && datePrevFim != null) {
            DataPrevInicio.setValue(datePrevIni.toLocalDate());
            DataPrevFim.setValue(datePrevFim.toLocalDate());
        }

    }

    private void saveDateChanges() {
        if (servicoSelecionado != null) {

            ServicoEntity servico = TDetServicos.getSelectionModel().getSelectedItem();
            CotacaoEntity cotacao = TCotacoes.getSelectionModel().getSelectedItem();
            LinhaCotacaoEntity linha = service_repo.findLinhaCotacaoByIdServicoAndCotacao(servico.getId(), cotacao.getId());

            Date prevIni = Date.valueOf(DataPrevInicio.getValue());
            Date prevFim = Date.valueOf(DataPrevFim.getValue());
            System.out.println(prevIni);
            System.out.println(prevFim);

            linha.setDataPrevInicio(Date.valueOf(prevIni.toLocalDate()));
            linha.setDataPrevFim(Date.valueOf(prevFim.toLocalDate()));
            //linha.setDataPrevFim(DataPrevFim.getValue());

            linha_cotacao_repo.save(linha);

        }
    }

    public void print(){
        System.out.println("12332432532532532523532532532532532532");
    }


    @FXML
    void ShowRegistarReserva(ActionEvent event) {

        try {
            CotacaoEntity cotacao = TCotacoes.getSelectionModel().getSelectedItem();

            ObservableList<CotacaoEntity> cotacoes = FXCollections.observableArrayList(cotacao_repo.findByIdLike(cotacao.getId()));
           // ObservableList<ServicoEntity> servicos = FXCollections.observableArrayList(service_repo.);
            ObservableList<ServicoEntity> servicos = FXCollections.observableArrayList(cotacao_repo.findByIdCotacao(cotacao.getId()));
            ObservableList<TransportemaritimoEntity> transportes = FXCollections.observableArrayList(transporte_repo.findAll());

            //dtalhes da cotacao
            TDetalhesId.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getId().toString()));
            TDetalhesIdUtilizador.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getIdCliente().toString()));
            TDetalhesUtilizador.setCellValueFactory(data -> new SimpleStringProperty((data.getValue().getClienteByIdCliente()).getNome()));
            TDetalhesData.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getData().toString()));
            TDetalhesValorTotal.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getValorTotal().toString()));
            TDetalhesEstado.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEstadoCotacaoByIdEstadoCotacao().getDescricao()));

            TDetalhesCotacao.setItems(cotacoes);

            //servicos da cotacao selecionada
            TDetServicosPreco.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPreco().toString()));
            TDetServicosDescricao.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDescricao()));
            TDetServicos.setItems(servicos);

            //transportes maritimos disponiveis

            TransporteIMO.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getImo()));
            TransportePOrigem.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPortoOrigem()));
            TransportePDestino.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPortoDestino()));

            TTranspMaritimo.setItems(transportes);


            CriarPane.setVisible(true);
            mainPane.setEffect(new javafx.scene.effect.GaussianBlur(4.0));
            mainPane.setDisable(true);


        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Erro!");
            alert.setHeaderText("Nenhuma cotação selecionada!");
            alert.setContentText("Selecione uma cotação!");
            alert.showAndWait();
        }



    /*    ObservableList<ServicoEntity> servicos = FXCollections.observableArrayList(cotacao_repo.findByIdCotacao(cotacao.getId()));
        IdServico.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getId().toString()));
        FornedorServico.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFornecedorByIdFornecedor().getNome()));
        ComissaoServico.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getComissao().toString()));
        PrecoServico.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPreco().toString()));
        DescricaoServico.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDescricao()));
        TableServico.setItems(servicos);*/

      }


    @FXML
    void RegistarReserva(ActionEvent event) {

       // context = new AnnotationConfigApplicationContext(AppConfig.class);

       // reserva_repo = context.getBean(ReservaRepository.class);

        CotacaoEntity cotacao = TCotacoes.getSelectionModel().getSelectedItem();

        if(Objects.equals(OrigemField.getText(), "") || Objects.equals(DestinoField.getText(), "") || Objects.isNull(DataField.getValue())
                 || DataField.getValue().isBefore(LocalDate.now()) || TTranspMaritimo.getSelectionModel().getSelectedItem() == null) {

            if (!Objects.isNull(DataField.getValue()) && DataField.getValue().isBefore(LocalDate.now())) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Data Inválida!");
                alert.setHeaderText("Data de reserva não pode ser anterior à data de hoje");
                alert.setContentText("Escolha uma data válida!");

                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Campos Inválidos!");
                alert.setHeaderText("Campos por preencher ou não selecionados");
                alert.setContentText("Preencha todos os campos e tente novamente!");

                alert.showAndWait();
            }


        } else {

            ReservaEntity novareserva = new ReservaEntity();
            novareserva.setOrigem(OrigemField.getText());
            novareserva.setDestino(DestinoField.getText());
            novareserva.setData(Date.valueOf(DataField.getValue()));
            novareserva.setIdCliente(cotacao.getIdCliente());

            EstadoReservaEntity estadoreserva;
            estadoreserva = reserva_estado_repo.findByDescricaoLike("Pende pagamento");

            ClienteEntity cliente = new ClienteEntity();
            cliente = cliente_repo.findByidLike(cotacao.getIdCliente().toString());

            novareserva.setIdEstadoReserva(estadoreserva.getId());  // 2 - Pende pagamento
            novareserva.setIdFuncionario(Current_Session.current_funcionario.getId());
            novareserva.setEstadoReservaByIdEstadoReserva(estadoreserva);
            novareserva.setFuncionarioByIdFuncionario(Current_Session.current_funcionario);
            novareserva.setClienteByIdCliente(cliente);
            novareserva.setIdCotacao(cotacao.getId());
            novareserva.setCotacaoByIdCotacao(cotacao);

            //add transporte
            TransportemaritimoEntity transporte = TTranspMaritimo.getSelectionModel().getSelectedItem();
            novareserva.setIdTransporteMaritimo(transporte.getId());
            novareserva.setTransportemaritimoByIdTransporteMaritimo(transporte);

            //atualizar id_reserva em carga

            try {
                reserva_repo.save(novareserva);
                //atualizar id_reserva em carga

                List<CargaEntity> cargas = carga_repo.findByIdCotacao(cotacao.getId());
                for (CargaEntity carga : cargas) {
                    System.out.println(carga.getId());
                    carga.setIdReserva(novareserva.getId());
                    carga.setReservaByIdReserva(novareserva);
                    carga_repo.save(carga);
                }

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sucesso!");
                alert.setHeaderText("Registo efetuado com sucesso!");
                alert.showAndWait();


            } catch (Exception e) {
                System.out.println(e.getMessage());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Erro ao criar reserva!");
                alert.setHeaderText("Ocorreu um erro! A reserva não foi criada!");
                alert.showAndWait();
            }

            OrigemField.clear();
            DestinoField.clear();
            DataField.setValue(null);
            Voltar();

        }

    //    ObservableList<ReservaEntity> reservasAtualizadas = FXCollections.observableArrayList(reserva_repo.findAll());
    //    Table.setItems(reservasAtualizadas);

    }

    void MostrarDetalhes() {
     /*   CotacaoEntity cotacao = TCotacoes.getSelectionModel().getSelectedItem();
        if (cotacao!= null) {
            DetalhesPane.setVisible(true);
            mainPane.setEffect(new javafx.scene.effect.GaussianBlur(4.0));
            mainPane.setDisable(true);

            DetalhesNomeCliente.setText(cotacao.getClienteByIdCliente().getNome());
            DetalhesCarga.setText(cotacao.getCargaByIdCarga().getNome());
            DetalhesValorTotal.setText(cotacao.getValorTotal().toString());
            DetalhesData.setText(cotacao.getData().toString());

            ObservableList<ServicoEntity> servicos = FXCollections.observableArrayList(servico_repo.findAllByCotacaoByIdCotacao(cotacao));
            DetalhesServicos.setItems(servicos);
        }*/
    }

    public CotacaoEntity cotacaoSelecionada() {
        return TCotacoes.getSelectionModel().getSelectedItem();
    }

    @FXML
    void Voltar() {
        CriarPane.setVisible(false);
        mainPane.setEffect(null);
        mainPane.setDisable(false);
    }

/*    @FXML
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
            alert.setTitle("Pagar Cotação");
            alert.setHeaderText("Pagar Cotação");
            alert.setContentText("Tem a certeza que pretende pagar esta Cotação?");
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
    }*/

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
