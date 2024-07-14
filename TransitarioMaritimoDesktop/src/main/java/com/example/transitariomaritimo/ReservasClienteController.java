package com.example.transitariomaritimo;

import com.example.transitariomaritimo.session.Current_Session;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pt.ipvc.database.entity.ClienteEntity;
import pt.ipvc.database.entity.EstadoReservaEntity;
import pt.ipvc.database.entity.FuncionarioEntity;
import pt.ipvc.database.entity.ReservaEntity;
import pt.ipvc.database.repository.ClienteRepository;
import pt.ipvc.database.repository.EstadoReservaRepository;
import pt.ipvc.database.repository.FuncionarioRepository;
import pt.ipvc.database.repository.ReservaRepository;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class ReservasClienteController implements Initializable {

    public AnnotationConfigApplicationContext context;
    private ReservaRepository reserva_repo;
    private EstadoReservaRepository reserva_estado_repo;
    private ClienteRepository cliente_repo;
    private FuncionarioRepository funcionario_repo;

    @FXML
    private TextField OrigemText;

    @FXML
    private TextField DestinoText;

    @FXML
    private DatePicker DataText;

    @FXML
    private ComboBox<String> IdClienteCombo;

    @FXML
    private ComboBox<String> IdEstadoReservaCombo;

    @FXML
    private ComboBox<String> IdFuncionarioCombo;

    @FXML
    private TextField IdCliente;

    @FXML
    private TextField IdEstadoReserva;

    @FXML
    private TextField IdFuncionario;

    @FXML
    private TableColumn<ReservaEntity,String> Data;

    @FXML
    private TableColumn<ReservaEntity,String> Destino;

    @FXML
    private TableColumn<ReservaEntity, String> Estado;

    @FXML
    private TableColumn<ReservaEntity,String> Id;

    @FXML
    private TableColumn<ReservaEntity,String> Origem;

    @FXML
    private TableView<ReservaEntity> Table;

    @FXML
    private TableColumn<ReservaEntity, String> ValorTotal;

    @FXML
    private Pane PaneInserir;

    @FXML
    private Pane mainPanel;

    @FXML
    private ToggleButton ReservasPagarButton;


  /*  private int idCliente = 0;

    public void setIdCliente(int idCliente) {
       this.idCliente = idCliente;
    }
*/

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        context = new AnnotationConfigApplicationContext(AppConfig.class);

        reserva_repo = context.getBean(ReservaRepository.class);

        if (Table != null) {
            ObservableList<ReservaEntity> reserva = FXCollections.observableArrayList(reserva_repo.findByIdClienteLike(Current_Session.current_client.getId()));
            Id.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getId().toString()));
            Data.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getData().toString()));
            Origem.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getOrigem()));
            Destino.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDestino()));
            Estado.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEstadoReservaByIdEstadoReserva().getDescricao()));
            Table.setItems(reserva);
        }


            context = new AnnotationConfigApplicationContext(AppConfig.class);
            reserva_estado_repo = context.getBean(EstadoReservaRepository.class);
            cliente_repo = context.getBean(ClienteRepository.class);
            funcionario_repo = context.getBean(FuncionarioRepository.class);


            if(IdEstadoReservaCombo != null){
                ObservableList<EstadoReservaEntity> estadosReserva = FXCollections.observableArrayList(reserva_estado_repo.findAll());
                for(EstadoReservaEntity e : estadosReserva){
                    IdEstadoReservaCombo.getItems().addAll(e.getDescricao());
                }
            }

            if(IdClienteCombo != null){
                ObservableList<ClienteEntity> clientes = FXCollections.observableArrayList(cliente_repo.findAll());
                for(ClienteEntity c : clientes){
                    IdClienteCombo.getItems().addAll(c.getId().toString());
                }
            }

            if(IdFuncionarioCombo != null){
                ObservableList<FuncionarioEntity> funcionarios = FXCollections.observableArrayList(funcionario_repo.findAll());
                for(FuncionarioEntity f : funcionarios){
                    IdFuncionarioCombo.getItems().addAll(f.getId().toString());
                }
            }

    }

    public void ReservaCliente() {  //reservas de um cliente
        context = new AnnotationConfigApplicationContext(AppConfig.class);

        reserva_repo = context.getBean(ReservaRepository.class);


        ObservableList<ReservaEntity> reserva = FXCollections.observableArrayList(reserva_repo.findByIdClienteLike(Current_Session.current_client.getId()));

        Id.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getId().toString()));
        Data.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getData().toString()));
        Origem.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getOrigem()));
        Destino.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDestino()));
        Table.setItems(reserva);


        context = new AnnotationConfigApplicationContext(AppConfig.class);
        reserva_estado_repo = context.getBean(EstadoReservaRepository.class);
        cliente_repo = context.getBean(ClienteRepository.class);
        funcionario_repo = context.getBean(FuncionarioRepository.class);


        if(IdEstadoReservaCombo != null){
            ObservableList<EstadoReservaEntity> estadosReserva = FXCollections.observableArrayList(reserva_estado_repo.findAll());
            for(EstadoReservaEntity e : estadosReserva){
                IdEstadoReservaCombo.getItems().addAll(e.getDescricao());
            }
        }

        if(IdClienteCombo != null){
            ObservableList<ClienteEntity> clientes = FXCollections.observableArrayList(cliente_repo.findAll());
            for(ClienteEntity c : clientes){
                IdClienteCombo.getItems().addAll(c.getId().toString());
            }
        }

        if(IdFuncionarioCombo != null){
            ObservableList<FuncionarioEntity> funcionarios = FXCollections.observableArrayList(funcionario_repo.findAll());
            for(FuncionarioEntity f : funcionarios){
                IdFuncionarioCombo.getItems().addAll(f.getId().toString());
            }
        }
    }


    @FXML
    public void InserirReserva(ActionEvent event) {
        PaneInserir.setVisible(true);
        mainPanel.setEffect(new javafx.scene.effect.GaussianBlur(4.0));
        mainPanel.setDisable(true);
        mainPanel.setVisible(false);
    }

    @FXML
    public void VoltarAtrasInserirReserva(ActionEvent event) {
        PaneInserir.setVisible(false);

        OrigemText.clear();
        DestinoText.clear();
        DataText.setValue(null);
        IdFuncionarioCombo.getSelectionModel().clearSelection();
        IdClienteCombo.getSelectionModel().clearSelection();
        IdEstadoReservaCombo.getSelectionModel().clearSelection();

        mainPanel.setEffect(null);
        mainPanel.setDisable(false);
        mainPanel.setVisible(true);
    }

    @FXML
    public void RegistarReserva(ActionEvent event) {

        if(Objects.equals(OrigemText.getText(), "") || Objects.equals(DestinoText.getText(), "") || Objects.isNull(DataText.getValue())
                && Objects.equals(IdFuncionarioCombo.getValue(), "") || Objects.equals(IdClienteCombo.getValue(), "") ||
                Objects.equals(IdEstadoReservaCombo.getValue(), "") || DataText.getValue().isBefore(LocalDate.now())) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Campos Inválidos!");
            alert.setHeaderText("Campos por preencher");
            alert.setContentText("Preencha todos os campos e tente novamente!");

            alert.showAndWait();

        } else {

            ReservaEntity novareserva = new ReservaEntity();
            novareserva.setOrigem(OrigemText.getText());
            novareserva.setDestino(DestinoText.getText());
            novareserva.setData(Date.valueOf(DataText.getValue()));
            novareserva.setIdCliente(Integer.valueOf(IdClienteCombo.getValue()));

            EstadoReservaEntity estadoreserva = new EstadoReservaEntity();
            estadoreserva = reserva_estado_repo.findByDescricaoLike(IdEstadoReservaCombo.getSelectionModel().getSelectedItem());

            FuncionarioEntity funcionario = new FuncionarioEntity();
            funcionario = funcionario_repo.findByidLike(IdFuncionarioCombo.getSelectionModel().getSelectedItem());

            ClienteEntity cliente = new ClienteEntity();
            cliente = cliente_repo.findByidLike(IdClienteCombo.getSelectionModel().getSelectedItem());

            novareserva.setIdEstadoReserva(estadoreserva.getId());
            novareserva.setIdFuncionario(Integer.valueOf(IdFuncionarioCombo.getValue()));
            novareserva.setEstadoReservaByIdEstadoReserva(estadoreserva);
            novareserva.setFuncionarioByIdFuncionario(funcionario);
            novareserva.setClienteByIdCliente(cliente);


            try {
                reserva_repo.save(novareserva);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sucesso!");
                alert.setHeaderText("Registo efetuado com sucesso!");
                alert.showAndWait();


            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            OrigemText.clear();
            DestinoText.clear();
            DataText.setValue(null);
            IdFuncionarioCombo.getSelectionModel().clearSelection();
            IdClienteCombo.getSelectionModel().clearSelection();
            IdEstadoReservaCombo.getSelectionModel().clearSelection();
        }

        ObservableList<ReservaEntity> reservasAtualizadas = FXCollections.observableArrayList(reserva_repo.findAll());
        Table.setItems(reservasAtualizadas);
    }

    @FXML
    public  void CancelarReserva(ActionEvent event) {

        if(Table.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Erro!");
            alert.setHeaderText("Nenhuma reserva selecionada!");
            alert.setContentText("Selecione uma reserva para cancelar!");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Cancelar Reserva");
            alert.setHeaderText("Cancelar Reserva");
            alert.setContentText("Tem a certeza que pretende cancelar este Reserva?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                ReservaEntity reserva = Table.getSelectionModel().getSelectedItem();
                EstadoReservaEntity estadoReserva = reserva_estado_repo.findByDescricaoLike("Cancelado");

                reserva.setIdEstadoReserva(estadoReserva.getId());
                reserva.setEstadoReservaByIdEstadoReserva(estadoReserva);

                reserva_repo.save(reserva);

                ObservableList<ReservaEntity> reservaAtualizados = FXCollections.observableArrayList(reserva_repo.findAll());
                Table.setItems(reservaAtualizados);
            }
        }
    }

    public void ReservasPorPagar(ActionEvent event) {

        if(ReservasPagarButton.isSelected()){
            ObservableList<ReservaEntity> reservasnaoPagas = FXCollections.observableArrayList(reserva_repo.findByIdEstadoReserva());
            Table.setItems(reservasnaoPagas);
        }

        if(!ReservasPagarButton.isSelected()){
            ObservableList<ReservaEntity> reservas = FXCollections.observableArrayList(reserva_repo.findByIdClienteLike(Current_Session.current_client.getId()));
            Table.setItems(reservas);
        }
    }


    @FXML
    public  void PagarReserva(ActionEvent event) {

        if (Table.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Erro!");
            alert.setHeaderText("Nenhuma reserva selecionada!");
            alert.setContentText("Selecione uma reserva para pagar!");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Pagar Reserva");
            alert.setHeaderText("Pagar Reserva");
            alert.setContentText("Tem a certeza que pretende pagar este Reserva?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                ReservaEntity reserva = Table.getSelectionModel().getSelectedItem();
                EstadoReservaEntity estadoReserva = reserva_estado_repo.findByDescricaoLike("Pago"); // mudei de Confirmado para Pago

                reserva.setIdEstadoReserva(estadoReserva.getId());
                reserva.setEstadoReservaByIdEstadoReserva(estadoReserva);

                try{
                    reserva_repo.save(reserva);

                    if(ReservasPagarButton.isSelected()){
                        ObservableList<ReservaEntity> reservasnaoPagas = FXCollections.observableArrayList(reserva_repo.findByIdEstadoReserva());
                        Table.setItems(reservasnaoPagas);
                    }

                    if(!ReservasPagarButton.isSelected()){
                        ObservableList<ReservaEntity> reservas = FXCollections.observableArrayList(reserva_repo.findByIdClienteLike(Current_Session.current_client.getId()));
                        Table.setItems(reservas);
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

              //  IdEstadoReserva.clear();
              //  IdCliente.clear();
              //  IdFuncionario.clear();

            }
        }
    }
}
