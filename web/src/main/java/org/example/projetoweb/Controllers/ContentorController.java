package org.example.projetoweb.Controllers;

import org.example.projetoweb.Dto.CargaDto;
import org.example.projetoweb.Dto.ServicoDto;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pt.ipvc.database.entity.*;
import pt.ipvc.database.repository.*;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class ContentorController {

    private final ContentorRepository repo_contentor;
    private final TipoContentorRepository repo_tipoContentor;
    private final EstadoContentorRepository repo_estadoContentor;
    private final FuncionarioRepository repo_funcionario;
    private final CargaRepository repo_carga;
    private final ArmazemRepository repo_armazem;

    public ContentorController(
            ContentorRepository repo_contentor,
            TipoContentorRepository repo_tipoContentor,
            EstadoContentorRepository repo_estadoContentor,
            FuncionarioRepository repo_funcionario,
            CargaRepository repo_carga,
            ArmazemRepository repo_armazem) {
        this.repo_contentor = repo_contentor;
        this.repo_tipoContentor = repo_tipoContentor;
        this.repo_estadoContentor = repo_estadoContentor;
        this.repo_funcionario = repo_funcionario;
        this.repo_carga = repo_carga;
        this.repo_armazem = repo_armazem;
    }

    // Admin

    @GetMapping("/Contentores/Admin")
    public String listarContentores(Model model, HttpSession session){
        List<ContentorEntity> contentores = repo_contentor.findAll();
        List<TipoContentorEntity> tiposContentor = repo_tipoContentor.findAll();
        List<EstadoContentorEntity> estadosContentor = repo_estadoContentor.findAll();
        List<ArmazemEntity> armazens = repo_armazem.findAll();
        model.addAttribute("tiposContentor", tiposContentor);
        model.addAttribute("estadosContentor", estadosContentor);
        model.addAttribute("contentores", contentores);
        model.addAttribute("armazens", armazens);

        String loggedInUser = (String) session.getAttribute("username");
        model.addAttribute("loggedInUser", loggedInUser);

        return "Contentores";
    }

    @PostMapping("/Contentores/Inserir/Admin")
    public String inserirContentor(
            @RequestParam("capacidade") double capacidade,
            @RequestParam("localAtual") String localAtual,
            @RequestParam("pesoMaximo") double pesoMaximo,
            @RequestParam("tipoContentor") Integer tipoContentorId,
            @RequestParam("estadoContentor") Integer estadoContentorId,
            @RequestParam("armazemId") int armazemId

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
            novoContentor.setIdArmazem(armazemId);
            novoContentor.setArmazemByIdArmazem(repo_armazem.findById(armazemId).orElse(null));


            repo_contentor.save(novoContentor);
        }

        return "redirect:/Contentores";
    }

    @PostMapping("/Contentores/Editar/Admin")
    public String editarContentor(
            @RequestParam("cin") Integer cin,
            @RequestParam("capacidade") double capacidade,
            @RequestParam("localAtual") String localAtual,
            @RequestParam("pesoMaximo") double pesoMaximo,
            @RequestParam("tipoContentor") Integer tipoContentorId,
            @RequestParam("estadoContentor") Integer estadoContentorId,
            @RequestParam("armazemId") int armazemId
    ) {
        Optional<ContentorEntity> contentorOptional = repo_contentor.findById(cin);
        if (contentorOptional.isPresent()) {
            ContentorEntity contentor = contentorOptional.get();
            contentor.setCapacidade(capacidade);
            contentor.setLocalAtual(localAtual);
            contentor.setPesoMax(pesoMaximo);

            contentor.setIdArmazem(armazemId);
            contentor.setArmazemByIdArmazem(repo_armazem.findById(armazemId).orElse(null));

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

        return "redirect:/Contentores/Admin";
    }

    @PostMapping("/Contentores/Remover/Admin")
    public String removerContentor(@RequestParam("cin") Integer cin) {
        repo_contentor.deleteById(cin);
        return "redirect:/Contentores/Admin";
    }

    // Gestor Logístico

    @GetMapping("/Contentores/GestorLogistico")
    public String listarContentoresGestorLogistico(Model model, HttpSession session) {
        List<ContentorEntity> contentores = repo_contentor.findAll();
        List<TipoContentorEntity> tiposContentor = repo_tipoContentor.findAll();
        List<EstadoContentorEntity> estadosContentor = repo_estadoContentor.findAll();
        model.addAttribute("tiposContentor", tiposContentor);
        model.addAttribute("estadosContentor", estadosContentor);
        model.addAttribute("contentores", contentores);

        String loggedInUser = (String) session.getAttribute("username");
        model.addAttribute("loggedInUser", loggedInUser);

        return "ContentorGestorLogisticoArmazem";
    }

    // Funcionário Armazém

    @GetMapping("/Contentores/FuncionarioArmazem")
    public String listarContentoresFuncionario(Model model, HttpSession session) {

        String loggedInUser = (String) session.getAttribute("username");
        model.addAttribute("loggedInUser", loggedInUser);

        int funcionarioId = (int) session.getAttribute("userId");
        FuncionarioEntity funcionario = repo_funcionario.findById(funcionarioId).orElse(null);
        if (funcionario != null) {
            int armazemId = funcionario.getIdArmazem();
            List<ContentorEntity> contentores = repo_contentor.findByIdArmazemLike(armazemId);
            model.addAttribute("contentores", contentores);
        }
        List<TipoContentorEntity> tiposContentor = repo_tipoContentor.findAll();
        List<EstadoContentorEntity> estadosContentor = repo_estadoContentor.findAll();
        List<ArmazemEntity> armazens = repo_armazem.findAll();
        model.addAttribute("tiposContentor", tiposContentor);
        model.addAttribute("estadosContentor", estadosContentor);
        model.addAttribute("armazens", armazens);
        return "ContentoresArmazem";
    }

    @PostMapping("/Contentores/Inserir/FuncionarioArmazem")
    public String adicionarContentor(
            @RequestParam double capacidade,
            @RequestParam double pesoMaximo,
            @RequestParam String localAtual,
            @RequestParam int tipoContentorId,
            @RequestParam int estadoContentorId,
            @RequestParam int armazemId,
            HttpSession session) {

        int funcionarioId = (int) session.getAttribute("userId");
        FuncionarioEntity funcionario = repo_funcionario.findById(funcionarioId).orElse(null);
        if (funcionario != null) {
            ContentorEntity novoContentor = new ContentorEntity();
            novoContentor.setCapacidade(capacidade);
            novoContentor.setPesoMax(pesoMaximo);
            novoContentor.setLocalAtual(localAtual);

            novoContentor.setIdArmazem(armazemId);
            novoContentor.setArmazemByIdArmazem(repo_armazem.findById(armazemId).orElse(null));

            TipoContentorEntity tipoContentor = repo_tipoContentor.findById(tipoContentorId).orElse(null);
            EstadoContentorEntity estadoContentor = repo_estadoContentor.findById(estadoContentorId).orElse(null);


            if (tipoContentor != null && estadoContentor != null) {
                novoContentor.setTipoContentorByTipoContentor(tipoContentor);
                novoContentor.setEstadoContentorByIdEstadoContentor(estadoContentor);
                novoContentor.setTipoContentor(tipoContentor.getId());
                novoContentor.setIdEstadoContentor(estadoContentor.getId());
            }

            repo_contentor.save(novoContentor);
        }

        return "redirect:/Contentores/FuncionarioArmazem";
    }

    @PostMapping("/Contentores/Editar/FuncionarioArmazem")
    public String editarContentorArmazem(
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

        return "redirect:/Contentores/FuncionarioArmazem";
    }

    @PostMapping("/Contentores/Remover/FuncionarioArmazem")
    public String removerContentorArmazem(@RequestParam("cin") Integer cin) {
        repo_contentor.deleteById(cin);
        return "redirect:/Contentores/FuncionarioArmazem";
    }

    @PostMapping("/Contentores/RegistarSaida/FuncionarioArmazem")
    @Transactional
    public String registrarSaidaContentor(@RequestParam int cin) {
        ContentorEntity contentor = repo_contentor.findById(cin).orElse(null);
        if (contentor != null) {
            // Atualiza o armazém do contentor para null
            contentor.setIdArmazem(null);
            repo_contentor.save(contentor);

            repo_carga.updateArmazemIdByContentorId(cin);
        }

        return "redirect:/Contentores/FuncionarioArmazem";
    }

    @GetMapping("/Contentores/VerCargas/FuncionarioArmazem")
    @ResponseBody
    public List<CargaDto> verCargasContentor(@RequestParam int cin) {
        List<CargaEntity> cargas = repo_carga.findByContentorCin(cin);
        return cargas.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private CargaDto convertToDTO(CargaEntity carga) {
        CargaDto dto = new CargaDto(carga.getId(), carga.getObservacoes(), carga.getPeso(), carga.getVolume());
        return dto;
    }

}
