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

public class ReservasController implements Initializable {

    public AnnotationConfigApplicationContext context;
    private ReservaRepository reserva_repo;
    private EstadoReservaRepository reserva_estado_repo;
    private ClienteRepository cliente_repo;
    private FuncionarioRepository funcionario_repo;


    // ----------------------------------------------------------------

    @FXML
    private TableView<ReservaEntity> Table2;

    @FXML
    private TableColumn<ReservaEntity, String> Data2;

    @FXML
    private TableColumn<ReservaEntity, String> Destino2;
    @FXML
    private TableColumn<ReservaEntity, String> Estado2;

    @FXML
    private TableColumn<ReservaEntity, String> Id2;

    @FXML
    private TableColumn<ReservaEntity, String> Origem2;

    @FXML
    private TableColumn<ReservaEntity, String> ValorTotal2;


  /*  private int idCliente = 0;

    public void setIdCliente(int idCliente) {
       this.idCliente = idCliente;
    }
*/

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        context = new AnnotationConfigApplicationContext(AppConfig.class);

        reserva_repo = context.getBean(ReservaRepository.class);

        if (Table2 != null) {
            ObservableList<ReservaEntity> reserva = FXCollections.observableArrayList(reserva_repo.findAll());
            Id2.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getId().toString()));
            Data2.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getData().toString()));
            Origem2.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getOrigem()));
            Destino2.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDestino()));
            Estado2.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEstadoReservaByIdEstadoReserva().getDescricao()));
            Table2.setItems(reserva);
        }

        context = new AnnotationConfigApplicationContext(AppConfig.class);
        reserva_estado_repo = context.getBean(EstadoReservaRepository.class);
        cliente_repo = context.getBean(ClienteRepository.class);
        funcionario_repo = context.getBean(FuncionarioRepository.class);

    }


    @FXML
    public  void CancelarReserva(ActionEvent event) {

        if(Table2.getSelectionModel().getSelectedItem() == null){
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
                ReservaEntity reserva = Table2.getSelectionModel().getSelectedItem();
                EstadoReservaEntity estadoReserva = reserva_estado_repo.findByDescricaoLike("Cancelado");

                reserva.setIdEstadoReserva(estadoReserva.getId());
                reserva.setEstadoReservaByIdEstadoReserva(estadoReserva);

                reserva_repo.save(reserva);

                ObservableList<ReservaEntity> reservaAtualizados = FXCollections.observableArrayList(reserva_repo.findAll());
                Table2.setItems(reservaAtualizados);
            }
        }
    }

 /*   public void ReservasPorPagar(ActionEvent event) {

        if(ReservasPagarButton.isSelected()){
            ObservableList<ReservaEntity> reservasnaoPagas = FXCollections.observableArrayList(reserva_repo.findByIdEstadoReserva());
            Table.setItems(reservasnaoPagas);
        }

        if(!ReservasPagarButton.isSelected()){
            ObservableList<ReservaEntity> reservas = FXCollections.observableArrayList(reserva_repo.findByIdClienteLike(Current_Session.current_client.getId()));
            Table.setItems(reservas);
        }
    }*/

}
