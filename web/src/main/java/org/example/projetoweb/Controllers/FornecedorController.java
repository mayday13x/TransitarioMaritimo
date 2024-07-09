package org.example.projetoweb.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pt.ipvc.database.entity.FornecedorEntity;
import pt.ipvc.database.repository.CodPostalRepository;
import pt.ipvc.database.repository.FornecedorRepository;

import java.util.List;

@Controller
public class FornecedorController {

    private final FornecedorRepository repo_fornecedor;

    public FornecedorController(FornecedorRepository repo_fornecedor, CodPostalRepository repo_codigoPostal) {
        this.repo_fornecedor = repo_fornecedor;

    }

    @GetMapping("/Fornecedores")
    public String listarFornecedores(Model model){
        List<FornecedorEntity> fornecedores = repo_fornecedor.findAll();
        model.addAttribute("fornecedores", fornecedores);
        return "Fornecedores";
    }


}
