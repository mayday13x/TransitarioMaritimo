package com.example.transitariomaritimo;

import com.example.transitariomaritimo.session.Current_Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import pt.ipvc.database.entity.ClienteEntity;
import pt.ipvc.database.entity.FuncionarioEntity;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {


    @FXML
    private AnchorPane menu_panel;

    private int idCliente;

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public AnchorPane getMenu_panel() {
        return menu_panel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("StatisticsView.fxml"));
            try {
                Pane cmdPane = fxmlLoader.load();
                menu_panel.getChildren().clear();
                menu_panel.getChildren().add(cmdPane);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            try {
                System.out.println(Current_Session.current_client.getNome());
                System.out.println(Current_Session.current_funcionario.getNome());
            } catch (Exception e) {
                System.out.println("Erro current session!");
            }

    }

    @FXML
    public void Funcionario(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FuncionarioView.fxml"));
        try {
            Pane cmdPane = fxmlLoader.load();
            menu_panel.getChildren().clear();
            menu_panel.getChildren().add(cmdPane);
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

    @FXML
    public void Cliente(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ClienteView.fxml"));
        try {
            Pane cmdPane = fxmlLoader.load();
            menu_panel.getChildren().clear();
            menu_panel.getChildren().add(cmdPane);
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

    @FXML
    public void Reserva(ActionEvent event)  throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Reservas.fxml"));
        try {
            Pane cmdPane = fxmlLoader.load();
            menu_panel.getChildren().clear();
            menu_panel.getChildren().add(cmdPane);
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

    @FXML
    public void ReservaCliente(ActionEvent event)  throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ReservaCliente.fxml"));
        try {
            Pane cmdPane = fxmlLoader.load();
          //  ReservasController reservasController = fxmlLoader.getController();
          //  reservasController.setIdCliente(idCliente);
          //  reservasController.ReservaCliente();
            menu_panel.getChildren().clear();
            menu_panel.getChildren().add(cmdPane);
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

    @FXML
    public void Armazem(ActionEvent event)  throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Armazem.fxml"));
        try {
            Pane cmdPane = fxmlLoader.load();
            menu_panel.getChildren().clear();
            menu_panel.getChildren().add(cmdPane);
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

    @FXML
    public void ArmazemAdmin(ActionEvent event)  throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ArmazemAdmin.fxml"));
        try {
            Pane cmdPane = fxmlLoader.load();
            menu_panel.getChildren().clear();
            menu_panel.getChildren().add(cmdPane);
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

    @FXML
    public void Contentor(ActionEvent event) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Contentor.fxml"));
        try {
            Pane cmdPane = fxmlLoader.load();
            menu_panel.getChildren().clear();
            menu_panel.getChildren().add(cmdPane);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @FXML
    public void ContentorAdmin(ActionEvent event) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ContentorAdmin.fxml"));
        try {
            Pane cmdPane = fxmlLoader.load();
            menu_panel.getChildren().clear();
            menu_panel.getChildren().add(cmdPane);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @FXML
    public void ContentorListar(ActionEvent event) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ContentorListar.fxml"));
        try {
            Pane cmdPane = fxmlLoader.load();
            menu_panel.getChildren().clear();
            menu_panel.getChildren().add(cmdPane);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @FXML
    public void ContentorFuncionario(ActionEvent event) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ContentorFuncionarioTransporte.fxml"));
        try {
            Pane cmdPane = fxmlLoader.load();
            menu_panel.getChildren().clear();
            menu_panel.getChildren().add(cmdPane);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @FXML
    public void Servicos(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ServicosView.fxml"));
        try {
            Pane cmdPane = fxmlLoader.load();
            menu_panel.getChildren().clear();
            menu_panel.getChildren().add(cmdPane);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void Cotacao(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CotacaoView.fxml"));
        try {
            Pane cmdPane = fxmlLoader.load();
            menu_panel.getChildren().clear();
            menu_panel.getChildren().add(cmdPane);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void CotacaoCliente(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CotacaoCliente.fxml"));
        try {
            Pane cmdPane = fxmlLoader.load();
            CotacaoController cotacaoController = fxmlLoader.getController();
            cotacaoController.setIdCliente(idCliente);
            cotacaoController.CotacaoCliente();
            menu_panel.getChildren().clear();
            menu_panel.getChildren().add(cmdPane);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void Fornecedores(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FornecedoresView.fxml"));
        try {
            Pane cmdPane = fxmlLoader.load();
            menu_panel.getChildren().clear();
            menu_panel.getChildren().add(cmdPane);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void Transporte(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TransporteView.fxml"));
        try {
            Pane cmdPane = fxmlLoader.load();
            menu_panel.getChildren().clear();
            menu_panel.getChildren().add(cmdPane);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void CotacaoOperacional(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CotacoesOperacionalView.fxml"));
        try {
            Pane cmdPane = fxmlLoader.load();
            menu_panel.getChildren().clear();
            menu_panel.getChildren().add(cmdPane);
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

    @FXML
    public void Logout(MouseEvent event) {

        try{
            Current_Session.current_funcionario = new FuncionarioEntity();
            Current_Session.current_client = new ClienteEntity();

            Parent root = FXMLLoader.load(getClass().getResource("LoginView.fxml"));
            Scene regCena = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(regCena);
            stage.setTitle("Login");
            stage.show();
        }catch (IOException ex){
            System.out.println("Erro ao acessar login: " + ex.getMessage());
        }
    }

}
