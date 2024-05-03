package com.example.transitariomaritimo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
    private ComboBox<String> tipoUtilizadorCombo;

    @FXML
    private TextField utilizadorText;

    @FXML
    private PasswordField passwordText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tipoUtilizadorCombo.getItems().addAll("Cliente", "Funcionario");

        context = new AnnotationConfigApplicationContext(AppConfig.class);
        cliente_repo = context.getBean(ClienteRepository.class);
        funcionario_repo = context.getBean(FuncionarioRepository.class);
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

        if (Objects.equals(tipoUtilizadorCombo.getValue(), "") || Objects.equals(utilizadorText.getText(), "")
                || Objects.equals(passwordText.getText(), "")) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Campos Inválidos!");
            alert.setHeaderText("Campos por preencher");
            alert.setContentText("Preencha todos os campos e tente novamente!");

            alert.showAndWait();

        } else if(tipoUtilizadorCombo.getValue().equals("Cliente") ){


            for(ClienteEntity cliente : cliente_repo.findAll()){
                if(Objects.equals(utilizadorText.getText(), cliente.getUtilizador()) && Objects.equals(passwordText.getText(), cliente.getPassword())){
                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("MenuView.fxml"));
                        Scene regCena = new Scene(root);
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage.setScene(regCena);
                        stage.setTitle("Menu");
                        stage.show();
                    } catch (IOException ex) {
                        System.out.println("Erro ao acessar menu: " + ex.getMessage());
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Campos Inválidos!");
                    alert.setHeaderText("Campos do utilizador ou password incorretos!");
                    alert.setContentText("Preencha todos os campos e tente novamente!");

                    alert.showAndWait();
                    break;
                }
            }
        } else if (tipoUtilizadorCombo.getValue().equals("Funcionario")){

            for(FuncionarioEntity funcionario : funcionario_repo.findAll()){
                if(Objects.equals(utilizadorText.getText(), funcionario.getUtilizador()) && Objects.equals(passwordText.getText(), funcionario.getPassword())){
                    switch (funcionario.getIdTipoFuncionario()){
                        case 1:
                            mudarPagina(event, "MenuView.fxml");
                            break;
                        case 2:
                            mudarPagina(event, "MenuView.fxml");
                            break;
                        case 3:
                            mudarPagina(event, "MenuView.fxml");
                            break;
                        case 4:
                            mudarPagina(event, "MenuView.fxml");
                            break;
                        case 5:
                            mudarPagina(event, "MenuView.fxml");
                            break;
                        case 6:
                            mudarPagina(event, "MenuView.fxml");
                            break;
                        case 7:
                            mudarPagina(event, "MenuView.fxml");
                            break;
                        default:
                            System.out.println("Tipo de funcinario incorreto!");
                            break;
                    }

                }else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Campos Inválidos!");
                    alert.setHeaderText("Campos do utilizador ou password incorretos!");
                    alert.setContentText("Preencha todos os campos e tente novamente!");

                    alert.showAndWait();
                    break;
                }
            }
        }
    }


}