package org.example.projetoweb.Controllers;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pt.ipvc.database.entity.*;
import pt.ipvc.database.repository.ServicoRepository;


import java.util.List;


@Controller
public class ServicoController {

    public final ServicoRepository repo_servico;

    public ServicoController(ServicoRepository repo_servico) {
        this.repo_servico = repo_servico;
    }

    @GetMapping("/Servicos")
    public String listarServicos(Model model){
        List<ServicoEntity> servicos = repo_servico.findAll();
        model.addAttribute("servicos", servicos);
        return "Servicos";
    }

}
