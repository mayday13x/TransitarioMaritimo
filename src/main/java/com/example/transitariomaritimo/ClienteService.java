package com.example.transitariomaritimo;

import pt.ipvc.transitariomaritimo.entity.ClienteEntity;
import pt.ipvc.transitariomaritimo.repository.ClienteRepository;

import java.util.List;

public class ClienteService {

    private ClienteRepository userRepository;

    public List<ClienteEntity> getAllUsers() {
        return userRepository.findAll();
    }

}
