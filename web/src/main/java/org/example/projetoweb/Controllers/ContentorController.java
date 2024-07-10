package org.example.projetoweb.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pt.ipvc.database.entity.*;
import pt.ipvc.database.repository.*;

import java.util.List;
import java.util.Optional;

@Controller
public class ContentorController {

    private final ContentorRepository repo_contentor;
    private final TipoContentorRepository repo_tipoContentor;
    private final EstadoContentorRepository repo_estadoContentor;

    public ContentorController(ContentorRepository repo_contentor, TipoContentorRepository repo_tipoContentor, EstadoContentorRepository repo_estadoContentor) {
        this.repo_contentor = repo_contentor;
        this.repo_tipoContentor = repo_tipoContentor;
        this.repo_estadoContentor = repo_estadoContentor;
    }

    @GetMapping("/Contentores")
    public String listarContentores(Model model){
        List<ContentorEntity> contentores = repo_contentor.findAll();
        List<TipoContentorEntity> tiposContentor = repo_tipoContentor.findAll();
        List<EstadoContentorEntity> estadosContentor = repo_estadoContentor.findAll();
        model.addAttribute("tiposContentor", tiposContentor);
        model.addAttribute("estadosContentor", estadosContentor);
        model.addAttribute("contentores", contentores);
        return "Contentores";
    }

    @PostMapping("/inserirContentor")
    public String inserirContentor(
            @RequestParam("capacidade") double capacidade,
            @RequestParam("localAtual") String localAtual,
            @RequestParam("pesoMaximo") double pesoMaximo,
            @RequestParam("tipoContentor") Integer tipoContentorId,
            @RequestParam("estadoContentor") Integer estadoContentorId
    ) {
        TipoContentorEntity tipoContentor = repo_tipoContentor.findById(tipoContentorId).orElse(null);
        EstadoContentorEntity estadoContentor = repo_estadoContentor.findById(estadoContentorId).orElse(null);

        if (tipoContentor != null && estadoContentor != null) {
            ContentorEntity novoContentor = new ContentorEntity();
            novoContentor.setCapacidade(capacidade);
            novoContentor.setLocalAtual(localAtual);
            novoContentor.setPesoMax(pesoMaximo);
            novoContentor.setTipoContentor(tipoContentor.getId());
            novoContentor.setIdEstadoContentor(estadoContentor.getId());
            novoContentor.setEstadoContentorByIdEstadoContentor(estadoContentor);
            novoContentor.setTipoContentorByTipoContentor(tipoContentor);

            repo_contentor.save(novoContentor);
        }

        return "redirect:/Contentores";
    }

    @PostMapping("/editarContentor")
    public String editarContentor(
            @RequestParam("cin") Integer cin,
            @RequestParam("capacidade") double capacidade,
            @RequestParam("localAtual") String localAtual,
            @RequestParam("pesoMaximo") double pesoMaximo,
            @RequestParam("tipoContentor") Integer tipoContentorId,
            @RequestParam("estadoContentor") Integer estadoContentorId
    ) {
        Optional<ContentorEntity> contentorOptional = repo_contentor.findById(cin);
        if (contentorOptional.isPresent()) {
            ContentorEntity contentor = contentorOptional.get();
            contentor.setCapacidade(capacidade);
            contentor.setLocalAtual(localAtual);
            contentor.setPesoMax(pesoMaximo);

            TipoContentorEntity tipoContentor = repo_tipoContentor.findById(tipoContentorId).orElse(null);
            EstadoContentorEntity estadoContentor = repo_estadoContentor.findById(estadoContentorId).orElse(null);

            if (tipoContentor != null && estadoContentor != null) {
                contentor.setTipoContentorByTipoContentor(tipoContentor);
                contentor.setEstadoContentorByIdEstadoContentor(estadoContentor);
                contentor.setTipoContentor(tipoContentor.getId());
                contentor.setIdEstadoContentor(estadoContentor.getId());
            }

            repo_contentor.save(contentor);
        }

        return "redirect:/Contentores";
    }

    @PostMapping("/removerContentor")
    public String removerContentor(@RequestParam("cin") Integer cin) {
        repo_contentor.deleteById(cin);
        return "redirect:/Contentores";
    }
}
