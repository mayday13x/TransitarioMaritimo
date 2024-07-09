package org.example.projetoweb.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pt.ipvc.database.entity.*;
import pt.ipvc.database.repository.FuncionarioRepository;

import java.util.List;

@Controller
public class FuncionarioController {

    public final FuncionarioRepository repo_funcionario;

    public FuncionarioController(FuncionarioRepository repo_funcionario) {
        this.repo_funcionario = repo_funcionario;
    }

    @GetMapping("/Funcionarios")
    public String listarFuncionarios(Model model){
        List<FuncionarioEntity> funcionarios = repo_funcionario.findAll();
        model.addAttribute("funcionarios", funcionarios);
        return "Funcionarios";
    }
}
