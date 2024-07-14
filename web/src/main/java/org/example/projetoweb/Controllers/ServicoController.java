package org.example.projetoweb.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pt.ipvc.database.entity.*;
import pt.ipvc.database.repository.ServicoRepository;
import pt.ipvc.database.repository.FornecedorRepository;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import java.util.Objects;

@Controller
public class ServicoController {

    private final ServicoRepository repo_servico;
    private final FornecedorRepository fornecedor_repo;

    public ServicoController(ServicoRepository repo_servico, FornecedorRepository fornecedor_repo) {
        this.repo_servico = repo_servico;
        this.fornecedor_repo = fornecedor_repo;
    }

    //Admin

    @GetMapping("/Servicos/Admin")
    public String listarServicos(Model model, HttpSession session){
        List<ServicoEntity> servicos = repo_servico.findAll();
        List<FornecedorEntity> fornecedores = fornecedor_repo.findAll();
        model.addAttribute("servicos", servicos);
        model.addAttribute("fornecedores", fornecedores);

        String loggedInUser = (String) session.getAttribute("username");
        model.addAttribute("loggedInUser", loggedInUser);

        return "Servicos";
    }

    @PostMapping("/Servicos/Inserir/Admin")
    public String adicionarServico(@RequestParam String descricao, @RequestParam double preco, @RequestParam double comissao, @RequestParam int idFornecedor, Model model) {

        if (Objects.equals(descricao, "") || preco <= 0 || comissao < 0 || idFornecedor == 0) {
            model.addAttribute("error", "Preencha todos os campos corretamente e tente novamente!");
            return "redirect:/Servicos";
        } else {
            ServicoEntity novoServico = new ServicoEntity();
            novoServico.setDescricao(descricao);
            novoServico.setPreco(preco);
            novoServico.setComissao(comissao);

            FornecedorEntity fornecedor = fornecedor_repo.findById(idFornecedor).orElse(null);
            novoServico.setFornecedorByIdFornecedor(fornecedor);
            novoServico.setIdFornecedor(fornecedor.getId());

            try {
                repo_servico.save(novoServico);
                model.addAttribute("success", "Serviço inserido com sucesso!");
            } catch (Exception e) {
                model.addAttribute("error", e.getMessage());
            }

            return "redirect:/Servicos/Admin";
        }
    }

    @PostMapping("/Servicos/Editar/Admin")
    public String editarServico(
            @RequestParam int id,
            @RequestParam String descricao,
            @RequestParam double preco,
            @RequestParam double comissao,
            @RequestParam int idFornecedor, Model model) {

        Optional<ServicoEntity> servicoOptional = repo_servico.findById(id);
        if (servicoOptional.isPresent()) {
            ServicoEntity servico = servicoOptional.get();
            servico.setDescricao(descricao);
            servico.setPreco(preco);
            servico.setComissao(comissao);

            FornecedorEntity fornecedor = fornecedor_repo.findById(idFornecedor).orElse(null);
            servico.setFornecedorByIdFornecedor(fornecedor);
            servico.setIdFornecedor(fornecedor.getId());

            try {
                repo_servico.save(servico);
                model.addAttribute("success", "Serviço editado com sucesso!");
            } catch (Exception e) {
                model.addAttribute("error", e.getMessage());
            }
        }

        return "redirect:/Servicos/Admin";
    }

    @PostMapping("/Servicos/Remover/Admin")
    public String removerServico(@RequestParam int id, Model model) {
        try {
            repo_servico.deleteById(id);
            model.addAttribute("success", "Serviço removido com sucesso!");
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/Servicos/Admin";
    }
}
