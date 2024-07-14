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

import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
public class FornecedorController {

    private final FornecedorRepository repo_fornecedor;
    private final CodPostalRepository repo_codPostal;

    public FornecedorController(FornecedorRepository repo_fornecedor, CodPostalRepository repo_codPostal) {
        this.repo_fornecedor = repo_fornecedor;
        this.repo_codPostal = repo_codPostal;
    }

    //Admin

    @GetMapping("/Fornecedores/Admin")
    public String listarFornecedores(Model model, HttpSession session){
        List<FornecedorEntity> fornecedores = repo_fornecedor.findAll();
        List<CodPostalEntity> codPostais = repo_codPostal.findAll();
        model.addAttribute("fornecedores", fornecedores);
        model.addAttribute("codPostais", codPostais);

        String loggedInUser = (String) session.getAttribute("username");
        model.addAttribute("loggedInUser", loggedInUser);

        return "Fornecedores";
    }

    @PostMapping("/Fornecedores/Inserir/Admin")
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

        return "redirect:/Fornecedores/Admin";
    }

    @PostMapping("/Fornecedores/Editar/Admin")
    public String editarFornecedor(
            @RequestParam("id") Integer id,
            @RequestParam("nome") String nome,
            @RequestParam("nif") String nif,
            @RequestParam("rua") String rua,
            @RequestParam("porta") String porta,
            @RequestParam("idCodPostal") Integer idCodPostal,
            @RequestParam("telefone") String telefone
    ) {
        Optional<FornecedorEntity> fornecedorOptional = repo_fornecedor.findById(id);
        CodPostalEntity codPostal = repo_codPostal.findById(idCodPostal).orElse(null);

        if (fornecedorOptional.isPresent() && codPostal != null) {
            FornecedorEntity fornecedor = fornecedorOptional.get();
            fornecedor.setNome(nome);
            fornecedor.setNif(Integer.valueOf(nif));
            fornecedor.setRua(rua);
            fornecedor.setPorta(Integer.valueOf(porta));
            fornecedor.setIdCodPostal(codPostal.getIdCodPostal());
            fornecedor.setTelefone(telefone);
            fornecedor.setCodPostalByIdCodPostal(codPostal);

            repo_fornecedor.save(fornecedor);
        }

        return "redirect:/Fornecedores/Admin";
    }

    @PostMapping("/Fornecedores/Remover/Admin")
    public String removerFornecedor(@RequestParam("id") Integer id) {
        repo_fornecedor.deleteById(id);
        return "redirect:/Fornecedores/Admin";
    }
}
