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

    public List<ClienteEntity> getAllClientes(){

        context = new AnnotationConfigApplicationContext(AppConfig.class);
        repo = context.getBean(ClienteRepository.class);

        return repo.findAll();

    }




}
