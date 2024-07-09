package org.example.projetoweb.Controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pt.ipvc.database.entity.*;
import pt.ipvc.database.repository.ClienteRepository;

import java.util.List;

@Controller
public class ClienteController {

    public final ClienteRepository repo_cliente;

    public ClienteController(ClienteRepository repo_cliente) {
        this.repo_cliente = repo_cliente;
    }

    @GetMapping("/Clientes")
    public String listarClientes(Model model){
        List<ClienteEntity> clientes = repo_cliente.findAll();
        model.addAttribute("clientes", clientes);
        return "Clientes";
    }
}
