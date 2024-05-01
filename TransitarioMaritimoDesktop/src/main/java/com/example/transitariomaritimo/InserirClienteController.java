package com.example.transitariomaritimo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pt.ipvc.database.entity.ClienteEntity;
import pt.ipvc.database.entity.CodPostalEntity;
import pt.ipvc.database.repository.ClienteRepository;
import pt.ipvc.database.repository.CodPostalRepository;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class InserirClienteController implements Initializable{

    public AnnotationConfigApplicationContext context;
    private CodPostalRepository cp_repo;
    private ClienteRepository cli_repo;

    @FXML
    private ComboBox<String> CodPostalCombo;

    @FXML
    private TextField EmailText;

    @FXML
    private TextField NifText;

    @FXML
    private TextField NomeText;

    @FXML
    private TextField PortaText;

    @FXML
    private TextField RuaText;

    @FXML
    private TextField TelefoneText;

    @FXML
    public void VoltarAtrasInserirCliente(ActionEvent event) {

        try{
            Parent root = FXMLLoader.load(getClass().getResource("Cliente.fxml"));
            Scene regCena = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(regCena);
            stage.setTitle("Cliente");
            stage.show();
        }catch (IOException ex){
            System.out.println("Erro ao acessar menu cliente: " + ex.getMessage());
        }
    }

    @FXML
    public void RegistarCliente(ActionEvent event) throws IOException {

        ClienteEntity novoCliente = new ClienteEntity();
        novoCliente.setNome(NomeText.getText());
        novoCliente.setNif(Integer.valueOf(NifText.getText()));
        novoCliente.setRua(RuaText.getText());
        novoCliente.setPorta(Integer.valueOf(PortaText.getText()));
        novoCliente.setIdCodPostal(CodPostalCombo.getSelectionModel().getSelectedIndex() + 1);
        novoCliente.setEmail(EmailText.getText());
        novoCliente.setTelefone(TelefoneText.getText());
        CodPostalEntity cp = new CodPostalEntity();
        cp.setIdCodPostal(novoCliente.getIdCodPostal());
        novoCliente.setCodPostalByIdCodPostal(cp);


        cli_repo.save(novoCliente);

        RuaText.clear();
        NifText.clear();
        NomeText.clear();
        PortaText.clear();
        EmailText.clear();
        TelefoneText.clear();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        context = new AnnotationConfigApplicationContext(AppConfig.class);
        cp_repo = context.getBean(CodPostalRepository.class);
        cli_repo = context.getBean(ClienteRepository.class);

        ObservableList<CodPostalEntity> localidades = FXCollections.observableArrayList(cp_repo.findAll());
        for (CodPostalEntity i : localidades){
            CodPostalCombo.getItems().addAll(i.getLocalidade());
        }

    }
}
