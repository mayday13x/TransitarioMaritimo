package org.example.projetoweb.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pt.ipvc.database.entity.ArmazemEntity;
import pt.ipvc.database.repository.ArmazemRepository;

import java.util.List;

@Controller
public class ArmazemController {

    public final ArmazemRepository repo_armazem;

    public ArmazemController(ArmazemRepository repo_armazem) {
        this.repo_armazem = repo_armazem;
    }

    @GetMapping("/Armazem")
    public String listarArmazens(Model model){
        List<ArmazemEntity> armazens = repo_armazem.findAll();
        model.addAttribute("armazens", armazens);
        return "Armazem";
    }

    @PostMapping("/inserirArmazem")
    public String inserirArmazem(
            @RequestParam("descricao") String descricao,
            @RequestParam("capacidadeMaxima") int capacidadeMaxima
    ) {
        ArmazemEntity novoArmazem = new ArmazemEntity();
        novoArmazem.setDescricao(descricao);
        novoArmazem.setCapacidadeMax(capacidadeMaxima);

        repo_armazem.save(novoArmazem);

        return "redirect:/Armazem";
    }
}
