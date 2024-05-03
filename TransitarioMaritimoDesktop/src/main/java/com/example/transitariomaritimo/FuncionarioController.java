package com.example.transitariomaritimo;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pt.ipvc.database.entity.ClienteEntity;
import pt.ipvc.database.entity.CodPostalEntity;
import pt.ipvc.database.entity.FuncionarioEntity;
import pt.ipvc.database.repository.ClienteRepository;
import pt.ipvc.database.repository.CodPostalRepository;
import pt.ipvc.database.repository.FuncionarioRepository;

import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;

public class FuncionarioController implements Initializable {

    public AnnotationConfigApplicationContext context;
    private FuncionarioRepository funcionario_repo;
    private CodPostalRepository cod_postal_repo;

    @FXML
    private TableColumn<FuncionarioEntity, String> Localidade;

    @FXML
    private TableColumn<FuncionarioEntity, String> Id;

    @FXML
    private TableColumn<FuncionarioEntity, String> Nif;

    @FXML
    private TableColumn<FuncionarioEntity, String> Nome;

    @FXML
    private TableColumn<FuncionarioEntity, String> Telefone;

    @FXML
    private TableColumn<FuncionarioEntity, String> Email;

    @FXML
    private TableView<FuncionarioEntity> table;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        context = new AnnotationConfigApplicationContext(AppConfig.class);
        funcionario_repo = context.getBean(FuncionarioRepository.class);

        ObservableList<FuncionarioEntity> funcionarios = FXCollections.observableArrayList(funcionario_repo.findAll());


        try{

            Id.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getId().toString()));
            Nome.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNome()));
            Nif.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNif().toString()));
            Localidade.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCodPostalByIdCodPostal().getLocalidade()));
            Telefone.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTelefone()));
            Email.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEmail()));
            table.setItems(funcionarios);


            context = new AnnotationConfigApplicationContext(AppConfig.class);
            cod_postal_repo = context.getBean(CodPostalRepository.class);


        } catch (Exception ex){
            System.out.println("Erro no Cliente: " + ex.getMessage());
        }
    }
}
