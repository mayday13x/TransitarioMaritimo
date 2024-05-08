package com.example.transitariomaritimo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pt.ipvc.database.entity.ClienteEntity;
import pt.ipvc.database.entity.FuncionarioEntity;
import pt.ipvc.database.repository.ClienteRepository;
import pt.ipvc.database.repository.FuncionarioRepository;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;


public class LoginController implements Initializable {

    public AnnotationConfigApplicationContext context;
    private ClienteRepository cliente_repo;
    private FuncionarioRepository funcionario_repo;

    @FXML
    private Pane PasswordPane;

    @FXML
    private Pane mainPane;

    @FXML
    private ComboBox<String> tipoUtilizadorCombo;

    @FXML
    private TextField utilizadorText;

    @FXML
    private PasswordField passwordText;

    @FXML
    private TextField NewPassword;

//    private boolean mensagem = true;

    FuncionarioEntity funcionarioAtual = new FuncionarioEntity();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tipoUtilizadorCombo.getItems().addAll("Cliente", "Funcionario");

        context = new AnnotationConfigApplicationContext(AppConfig.class);
        cliente_repo = context.getBean(ClienteRepository.class);
        funcionario_repo = context.getBean(FuncionarioRepository.class);

        tipoUtilizadorCombo.getSelectionModel().select(1);
    }

    public void mudarPagina(ActionEvent event, String pagina) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(pagina)));
            Scene regCena = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(regCena);
            stage.setTitle("Menu");
            stage.show();
        } catch (IOException ex) {
            System.out.println("Erro ao acessar " + pagina  + ex.getMessage());
        }

    }


    @FXML
    public void Login(ActionEvent event) {

        boolean found = false;

        if (Objects.equals(tipoUtilizadorCombo.getValue(), "") || Objects.equals(utilizadorText.getText(), "")
                || Objects.equals(passwordText.getText(), "")) {
            found = true;
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Campos Inválidos!");
            alert.setHeaderText("Campos por preencher");
            alert.setContentText("Preencha todos os campos e tente novamente!");

            alert.showAndWait();

        } else if (tipoUtilizadorCombo.getValue().equals("Cliente")) {

            for (ClienteEntity cliente : cliente_repo.findAll()) {
                if (!found && Objects.equals(utilizadorText.getText(), cliente.getUtilizador()) && Objects.equals(passwordText.getText(), cliente.getPassword())) {
                    //mensagem = false;
                    found = true;
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("MenuClienteView.fxml"));
                        Parent root = loader.load();
                        MenuController controller = loader.getController();
                        controller.setIdCliente(cliente.getId());
                        Scene regCena = new Scene(root);
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage.setScene(regCena);
                        stage.setTitle("Menu");
                        stage.show();
                    } catch (IOException ex) {
                        System.out.println("Erro ao acessar menu: " + ex.getMessage());
                    }
                }
            }
        } else if (tipoUtilizadorCombo.getValue().equals("Funcionario")) {


            for (FuncionarioEntity funcionario : funcionario_repo.findAll()) {

                if (!found && Objects.equals(utilizadorText.getText(), funcionario.getUtilizador()) && Objects.equals(passwordText.getText(), funcionario.getPassword())
                        && Objects.equals(passwordText.getText(), "default")) {
                    found = true;
                    PasswordPane.setVisible(true);
                    mainPane.setEffect(new javafx.scene.effect.GaussianBlur(4.0));
                    mainPane.setDisable(true);
                    mainPane.setVisible(false);

                    funcionarioAtual = funcionario;


                } else if (Objects.equals(utilizadorText.getText(), funcionario.getUtilizador()) && Objects.equals(passwordText.getText(), funcionario.getPassword())) {
                    //mensagem = false;
                    found = true;
                    switch (funcionario.getIdTipoFuncionario()) {
                        case 1: //Admin
                            mudarPagina(event, "MenuAdminView.fxml");
                            break;
                        case 2:
                            mudarPagina(event, "MenuGestorComercialView.fxml");
                            break;
                        case 3:
                            mudarPagina(event, "MenuGestorOperacionalView.fxml");
                            break;
                        case 4:
                            mudarPagina(event, "MenuGestorLogisticoArmazemView.fxml");
                            break;
                        case 5:
                            mudarPagina(event, "MenuFuncionarioArmazemView.fxml");
                            break;
                        case 6:
                            mudarPagina(event, "MenuFuncionarioTransporteView.fxml");
                            break;
                        default:
                            System.out.println("Tipo de funcionario incorreto!");
                            break;
                    }
                }
            }

        }
        if (!found) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Campos Inválidos!");
            alert.setHeaderText("Campos do utilizador ou password incorretos!");
            alert.setContentText("Preencha todos os campos e tente novamente!");

            alert.showAndWait();
        }

    }

    @FXML
    public void AlterarPassword(ActionEvent event) {

        funcionarioAtual.setPassword(NewPassword.getText());

        funcionario_repo.save(funcionarioAtual);

        try {
            funcionario_repo.save(funcionarioAtual);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucesso!");
            alert.setHeaderText("Registo editado com sucesso!");
            alert.showAndWait();

            switch (funcionarioAtual.getIdTipoFuncionario()) {
                case 1:
                    mudarPagina(event, "MenuAdminView.fxml");
                    break;
                case 2:
                    mudarPagina(event, "MenuGestorComercialView.fxml");
                    break;
                case 3:
                    mudarPagina(event, "MenuGestorOperacionalView.fxml");
                    break;
                case 4:
                    mudarPagina(event, "MenuGestorLogisticoArmazemView.fxml");
                    break;
                case 5:
                    mudarPagina(event, "MenuFuncionarioArmazemView.fxml");
                    break;
                case 6:
                    mudarPagina(event, "MenuFuncionarioTransporteView.fxml");
                    break;
                default:
                    System.out.println("Tipo de funcionario incorreto!");
                    break;
            }


        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
    }

    @FXML
    public void  VoltarAtras(ActionEvent event) {
        PasswordPane.setVisible(false);
        mainPane.setEffect(null);
        mainPane.setDisable(false);
        mainPane.setVisible(true);
    }

}