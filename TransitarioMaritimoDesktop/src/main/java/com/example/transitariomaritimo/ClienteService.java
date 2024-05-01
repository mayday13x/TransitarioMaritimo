package com.example.transitariomaritimo;

import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pt.ipvc.database.entity.ClienteEntity;
import pt.ipvc.database.repository.ClienteRepository;
import jakarta.persistence.EntityManager;
import java.util.List;

public class ClienteService
{

    public AnnotationConfigApplicationContext context;
    private ClienteRepository repo;

    @PersistenceContext
    private EntityManager entityManager;

    public void addCliente(ClienteEntity cliente) {
        try{
            entityManager.persist(cliente);
        } catch (Exception ex) {
            System.out.println("Erro ao inserir cliente " + ex.getMessage());
        }
    }

    public List<ClienteEntity> getAllClientes(){

        context = new AnnotationConfigApplicationContext(AppConfig.class);
        repo = context.getBean(ClienteRepository.class);

        return repo.findAll();

    }




}
