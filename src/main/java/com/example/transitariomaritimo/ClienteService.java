package com.example.transitariomaritimo;

import pt.ipvc.transitariomaritimo.entity.ClienteEntity;
import pt.ipvc.transitariomaritimo.repository.ClienteRepository;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;


public class ClienteService {

    private ClienteRepository clienteRepository;

    public List<ClienteEntity> getAllUsers() {
        return clienteRepository.findAll();
    }

}
