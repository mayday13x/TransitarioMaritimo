package org.example.projetoweb.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pt.ipvc.database.entity.ArmazemEntity;
import pt.ipvc.database.entity.CargaEntity;
import pt.ipvc.database.entity.EstadoCargaEntity;
import pt.ipvc.database.repository.ArmazemRepository;

import jakarta.servlet.http.HttpSession;
import pt.ipvc.database.repository.CargaRepository;
import pt.ipvc.database.repository.EstadoCargaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class ArmazemController {

    public final ArmazemRepository repo_armazem;
    public final CargaRepository repo_carga;
    public final EstadoCargaRepository repo_estadoCarga;

    public ArmazemController(ArmazemRepository repo_armazem, CargaRepository repo_carga, EstadoCargaRepository repo_estadoCarga) {
        this.repo_armazem = repo_armazem;
        this.repo_carga = repo_carga;
        this.repo_estadoCarga = repo_estadoCarga;

    }

    // Admin

    @GetMapping("/Armazem/Admin")
    public String listarArmazens(Model model, HttpSession session){
        List<ArmazemEntity> armazens = repo_armazem.findAll();
        List<CargaEntity> cargasDisponiveis = repo_carga.findByIdArmazemNullAndReservaPago();
        model.addAttribute("armazens", armazens);
        model.addAttribute("cargasDisponiveis", cargasDisponiveis);

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
        return "redirect:/Armazem/Admin";
    }

    @PostMapping("/Armazem/AdicionarCarga/Admin")
    public String adicionarCargaArmazem(
            @RequestParam("cargaId") int cargaId,
            @RequestParam("armazemId") int armazemId
    ) {

       CargaEntity carga = repo_carga.findById(cargaId).orElse(null);
       ArmazemEntity armazem = repo_armazem.findById(armazemId).orElse(null);
       EstadoCargaEntity estadoCarga = repo_estadoCarga.findById(2).orElse(null);


        if (carga != null && armazem != null && estadoCarga != null) {

            if (carga.getVolume() <= armazem.getCapacidadeMax() - calcularCapacidadeAtual(armazem)) {
                carga.setArmazemByIdArmazem(armazem);
                carga.setIdArmazem(armazem.getId());
                carga.setIdEstadoCarga(estadoCarga.getId());
                carga.setEstadoCargaByIdEstadoCarga(estadoCarga);
                repo_carga.save(carga);
            }
        }

        return "redirect:/Armazem/Admin";
    }

    @GetMapping("/Armazem/ArmazensDisponiveis/Admin")
    public String getArmazensDisponiveis(@RequestParam("cargaId") int cargaId, Model model) {
        CargaEntity carga = repo_carga.findById(cargaId).orElse(null);

        if (carga != null) {

            List<ArmazemEntity> armazens = repo_armazem.findAll().stream()
                    .filter(armazem -> carga.getVolume() <= armazem.getCapacidadeMax() - calcularCapacidadeAtual(armazem))
                    .collect(Collectors.toList());
            model.addAttribute("armazens", armazens);
        }

        return "fragments/armazemSelect";
    }

    private double calcularCapacidadeAtual(ArmazemEntity armazem) {
        return repo_carga.findByArmazemID(armazem.getId()).stream()
                .mapToDouble(CargaEntity::getVolume)
                .sum();
    }

    // Gestor Logistico

    @GetMapping("/Armazem/GestorLogistico")
    public String listarArmazensGestorLogistico(Model model, HttpSession session) {
        List<ArmazemEntity> armazens = repo_armazem.findAll();
        List<CargaEntity> cargasDisponiveis = repo_carga.findByIdArmazemNullAndReservaPago();
        model.addAttribute("armazens", armazens);
        model.addAttribute("cargasDisponiveis", cargasDisponiveis);


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
      ArmazemEntity armazem = repo_armazem.findById(id).orElse(null);
        if (armazem != null)  {
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

    @PostMapping("/Armazem/AdicionarCarga/GestorLogistico")
    public String adicionarCargaArmazemGestorLogistico(
            @RequestParam("cargaId") int cargaId,
            @RequestParam("armazemId") int armazemId
    ) {

        CargaEntity carga = repo_carga.findById(cargaId).orElse(null);
        ArmazemEntity armazem = repo_armazem.findById(armazemId).orElse(null);
        EstadoCargaEntity estadoCarga = repo_estadoCarga.findById(2).orElse(null);

        if (carga != null && armazem != null && estadoCarga != null) {


            if (carga.getVolume() <= armazem.getCapacidadeMax() - calcularCapacidadeAtual(armazem)) {
                carga.setArmazemByIdArmazem(armazem);
                carga.setIdArmazem(armazem.getId());
                carga.setIdEstadoCarga(estadoCarga.getId());
                carga.setEstadoCargaByIdEstadoCarga(estadoCarga);
                repo_carga.save(carga);
            }
        }

        return "redirect:/Armazem/GestorLogistico";
    }

    @GetMapping("/Armazem/ArmazensDisponiveis/GestorLogistico")
    public String getArmazensDisponiveisGestorLogistico(@RequestParam("cargaId") int cargaId, Model model) {
      CargaEntity carga = repo_carga.findById(cargaId).orElse(null);

        if (carga != null)  {
            List<ArmazemEntity> armazens = repo_armazem.findAll().stream()
                    .filter(armazem -> carga.getVolume() <= armazem.getCapacidadeMax() - calcularCapacidadeAtual(armazem))
                    .collect(Collectors.toList());
            model.addAttribute("armazens", armazens);
        }

        return "fragments/armazemSelect";
    }
}
