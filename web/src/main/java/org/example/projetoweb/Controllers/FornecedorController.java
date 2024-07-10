package org.example.projetoweb.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pt.ipvc.database.entity.CodPostalEntity;
import pt.ipvc.database.entity.FornecedorEntity;
import pt.ipvc.database.repository.CodPostalRepository;
import pt.ipvc.database.repository.FornecedorRepository;

import java.util.List;

@Controller
public class FornecedorController {

    private final FornecedorRepository repo_fornecedor;
    private final CodPostalRepository repo_codPostal;

    public FornecedorController(FornecedorRepository repo_fornecedor, CodPostalRepository repo_codPostal) {
        this.repo_fornecedor = repo_fornecedor;
        this.repo_codPostal = repo_codPostal;
    }

    @GetMapping("/Fornecedores")
    public String listarFornecedores(Model model){
        List<FornecedorEntity> fornecedores = repo_fornecedor.findAll();
        model.addAttribute("fornecedores", fornecedores);
        return "Fornecedores";
    }

    @GetMapping("/inserirFornecedor")
    public String showInserirFornecedorForm(Model model) {
        List<CodPostalEntity> codPostais = repo_codPostal.findAll();
        model.addAttribute("codPostais", codPostais);
        return "InserirFornecedor";
    }

    @PostMapping("/inserirFornecedor")
    public String inserirFornecedor(
            @RequestParam("nome") String nome,
            @RequestParam("nif") String nif,
            @RequestParam("rua") String rua,
            @RequestParam("porta") String porta,
            @RequestParam("idCodPostal") Integer idCodPostal,
            @RequestParam("telefone") String telefone
    ) {
        CodPostalEntity codPostal = repo_codPostal.findById(idCodPostal).orElse(null);

        if (codPostal != null) {
            FornecedorEntity novoFornecedor = new FornecedorEntity();
            novoFornecedor.setNome(nome);
            novoFornecedor.setNif(Integer.valueOf(nif));
            novoFornecedor.setRua(rua);
            novoFornecedor.setPorta(Integer.valueOf(porta));
            novoFornecedor.setIdCodPostal(codPostal.getIdCodPostal());
            novoFornecedor.setTelefone(telefone);
            novoFornecedor.setCodPostalByIdCodPostal(codPostal);

            repo_fornecedor.save(novoFornecedor);
        }

        return "redirect:/Fornecedores";
    }
}
