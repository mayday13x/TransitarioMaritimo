package org.example.projetoweb.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pt.ipvc.database.entity.*;
import pt.ipvc.database.entity.FornecedorEntity;
import pt.ipvc.database.repository.CodPostalRepository;
import pt.ipvc.database.repository.FornecedorRepository;

import java.util.List;
import java.util.Optional;

@Controller
public class FornecedorController {

    private final FornecedorRepository repo_fornecedor;

    public FornecedorController(FornecedorRepository repo_fornecedor, CodPostalRepository repo_codigoPostal) {
        this.repo_fornecedor = repo_fornecedor;

    }

    @GetMapping("/Fornecedor")
    public String listarFornecedores(Model model){
        List<FornecedorEntity> fornecedores = repo_fornecedor.findAll();
        model.addAttribute("fornecedores", fornecedores);
        return "Fornecedor";
    }


}
