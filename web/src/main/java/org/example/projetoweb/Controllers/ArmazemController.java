package org.example.projetoweb.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pt.ipvc.database.entity.ArmazemEntity;
import pt.ipvc.database.repository.ArmazemRepository;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
public class ArmazemController {

    public final ArmazemRepository repo_armazem;

    public ArmazemController(ArmazemRepository repo_armazem) {
        this.repo_armazem = repo_armazem;
    }

    // Admin

    @GetMapping("/Armazem/Admin")
    public String listarArmazens(Model model, HttpSession session){
        List<ArmazemEntity> armazens = repo_armazem.findAll();
        model.addAttribute("armazens", armazens);

        String loggedInUser = (String) session.getAttribute("username");
        model.addAttribute("loggedInUser", loggedInUser);

        return "Armazem";
    }

    @PostMapping("/Armazem/Inserir/Admin")
    public String inserirArmazem(
            @RequestParam("descricao") String descricao,
            @RequestParam("capacidadeMaxima") int capacidadeMaxima
    ) {
        ArmazemEntity novoArmazem = new ArmazemEntity();
        novoArmazem.setDescricao(descricao);
        novoArmazem.setCapacidadeMax(capacidadeMaxima);

        repo_armazem.save(novoArmazem);

        return "redirect:/Armazem/Admin";
    }

    @PostMapping("/Armazem/Editar/Admin")
    public String editarArmazem(
            @RequestParam("id") int id,
            @RequestParam("descricao") String descricao,
            @RequestParam("capacidadeMaxima") int capacidadeMaxima
    ) {
        Optional<ArmazemEntity> armazemOptional = repo_armazem.findById(id);
        if (armazemOptional.isPresent()) {
            ArmazemEntity armazem = armazemOptional.get();
            armazem.setDescricao(descricao);
            armazem.setCapacidadeMax(capacidadeMaxima);
            repo_armazem.save(armazem);
        }

        return "redirect:/Armazem/Admin";
    }

    @PostMapping("/Armazem/Remover/Admin")
    public String removerArmazem(@RequestParam("id") int id) {
        repo_armazem.deleteById(id);
        return "redirect:/Armazem";
    }

    // Gestor Logistico

    @GetMapping("/Armazem/GestorLogistico")
    public String listarArmazensGestorLogistico(Model model, HttpSession session) {
        List<ArmazemEntity> armazens = repo_armazem.findAll();
        model.addAttribute("armazens", armazens);

        String loggedInUser = (String) session.getAttribute("username");
        model.addAttribute("loggedInUser", loggedInUser);

        return "ArmazemGestorLogisticoArmazem";
    }

    @PostMapping("/Armazem/Inserir/GestorLogistico")
    public String inserirArmazemGestorLogistico(
            @RequestParam("descricao") String descricao,
            @RequestParam("capacidadeMaxima") int capacidadeMaxima
    ) {
        ArmazemEntity novoArmazem = new ArmazemEntity();
        novoArmazem.setDescricao(descricao);
        novoArmazem.setCapacidadeMax(capacidadeMaxima);

        repo_armazem.save(novoArmazem);

        return "redirect:/Armazem/GestorLogistico";
    }

    @PostMapping("/Armazem/Editar/GestorLogistico")
    public String editarArmazemGestorLogistico(
            @RequestParam("id") int id,
            @RequestParam("descricao") String descricao,
            @RequestParam("capacidadeMaxima") int capacidadeMaxima
    ) {
        Optional<ArmazemEntity> armazemOptional = repo_armazem.findById(id);
        if (armazemOptional.isPresent()) {
            ArmazemEntity armazem = armazemOptional.get();
            armazem.setDescricao(descricao);
            armazem.setCapacidadeMax(capacidadeMaxima);
            repo_armazem.save(armazem);
        }

        return "redirect:/Armazem/GestorLogistico";
    }

    @PostMapping("/Armazem/Remover/GestorLogistico")
    public String removerArmazemGestorLogistico(@RequestParam("id") int id) {
        repo_armazem.deleteById(id);
        return "redirect:/Armazem/GestorLogistico";
    }
}
