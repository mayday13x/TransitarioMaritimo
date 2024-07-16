package org.example.projetoweb.Controllers;

import org.example.projetoweb.Dto.CargaDto;
import org.example.projetoweb.Dto.ContentorDto;
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

import java.util.Collection;
import java.util.List;
import java.util.Map;
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
    private final EstadoCargaRepository repo_estadoCarga;

    public ContentorController(
            ContentorRepository repo_contentor,
            TipoContentorRepository repo_tipoContentor,
            EstadoContentorRepository repo_estadoContentor,
            FuncionarioRepository repo_funcionario,
            CargaRepository repo_carga,
            ArmazemRepository repo_armazem,
            EstadoCargaRepository repo_estadoCarga
    ) {
        this.repo_contentor = repo_contentor;
        this.repo_tipoContentor = repo_tipoContentor;
        this.repo_estadoContentor = repo_estadoContentor;
        this.repo_funcionario = repo_funcionario;
        this.repo_carga = repo_carga;
        this.repo_armazem = repo_armazem;
        this.repo_estadoCarga = repo_estadoCarga;

    }

    // Admin

    @GetMapping("/Contentores/Admin")
    public String listarContentores(Model model, HttpSession session){
        List<ContentorEntity> contentores = repo_contentor.findAll();
        List<TipoContentorEntity> tiposContentor = repo_tipoContentor.findAll();
        List<EstadoContentorEntity> estadosContentor = repo_estadoContentor.findAll();
        List<ArmazemEntity> armazens = repo_armazem.findAll();

        Map<Integer, Double> capacidadeAtualMap = contentores.stream()
                .collect(Collectors.toMap(ContentorEntity::getCin, this::calcularCapacidadeAtual));

        model.addAttribute("tiposContentor", tiposContentor);
        model.addAttribute("estadosContentor", estadosContentor);
        model.addAttribute("contentores", contentores);
        model.addAttribute("capacidadeAtualMap", capacidadeAtualMap);
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
            @RequestParam("armazemId") int armazemId

    ) {
        TipoContentorEntity tipoContentor = repo_tipoContentor.findById(tipoContentorId).orElse(null);
        EstadoContentorEntity estadoContentor = repo_estadoContentor.findById(2).orElse(null);

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

        return "redirect:/Contentores/Admin";
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

    @GetMapping("/Cargas/Disponiveis/Admin")
    @ResponseBody
    public List<CargaDto> listarCargasDisponiveis(HttpSession session) {
        List<CargaEntity> cargas = repo_carga.findByIdContentorNullAndReservaPago();
        return cargas.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping("/Contentores/Disponiveis/Admin")
    @ResponseBody
    public List<ContentorDto> listarContentoresDisponiveis(@RequestParam("cargaId") int cargaId) {
        CargaEntity carga = repo_carga.findById(cargaId).orElse(null);
        if (carga == null) return null;

        return repo_contentor.findAll().stream()
                .filter(contentor -> {
                    double cargaTotal = repo_carga.findByContentorCin(contentor.getCin()).stream()
                            .mapToDouble(CargaEntity::getPeso)
                            .sum();
                    return (cargaTotal + carga.getPeso() <= contentor.getPesoMax())
                            && (contentor.getCapacidade() >= carga.getVolume());
                })
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @PostMapping("/Contentores/AdicionarCarga/Admin")
    @Transactional
    public String adicionarCargaContentor(
            @RequestParam("contentorCin") Integer contentorCin,
            @RequestParam("cargaId") Integer cargaId
    ) {
        ContentorEntity contentor = repo_contentor.findById(contentorCin).orElse(null);
        CargaEntity carga = repo_carga.findById(cargaId).orElse(null);

        if (contentor != null && carga != null) {
            carga.setIdContentor(contentorCin);
            carga.setContentorByIdContentor(contentor);
            carga.setLocalAtual(contentor.getLocalAtual());
            repo_carga.save(carga);

        }

        return "redirect:/Contentores/Admin";
    }

    @PostMapping("/Contentores/AlterarEstado/Admin")
    @Transactional
    public String alterarEstadoParaPronto(@RequestParam("cin") int cin) {
        ContentorEntity contentor = repo_contentor.findById(cin).orElse(null);
        if (contentor != null) {
            Long countCargas = repo_contentor.countByIdContentor(cin);
            if (countCargas > 0) {
                EstadoContentorEntity estadoPronto = repo_estadoContentor.findById(1).orElse(null);
                if (estadoPronto != null) {
                    contentor.setIdEstadoContentor(estadoPronto.getId());
                    contentor.setEstadoContentorByIdEstadoContentor(estadoPronto);
                    repo_contentor.save(contentor);
                }
            }
        }
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

    private double calcularCapacidadeAtual(ContentorEntity contentor) {
        Double capacidadeAtual = repo_contentor.sumVolumes(contentor.getCin());
        return capacidadeAtual != null ? capacidadeAtual : 0.0;
    }

    @GetMapping("/Contentores/FuncionarioArmazem")
    public String listarContentoresFuncionarioArmazem(Model model, HttpSession session) {
        List<ContentorEntity> contentores = repo_contentor.findAll();
        List<TipoContentorEntity> tiposContentor = repo_tipoContentor.findAll();
        List<EstadoContentorEntity> estadosContentor = repo_estadoContentor.findAll();
        List<ArmazemEntity> armazens = repo_armazem.findAll();

        Map<Integer, Double> capacidadeAtualMap = contentores.stream()
                .collect(Collectors.toMap(ContentorEntity::getCin, this::calcularCapacidadeAtual));

        model.addAttribute("tiposContentor", tiposContentor);
        model.addAttribute("estadosContentor", estadosContentor);
        model.addAttribute("contentores", contentores);
        model.addAttribute("capacidadeAtualMap", capacidadeAtualMap);
        model.addAttribute("armazens", armazens);

        String loggedInUser = (String) session.getAttribute("username");
        model.addAttribute("loggedInUser", loggedInUser);

        return "ContentoresArmazem";
    }

    @PostMapping("/Contentores/Inserir/FuncionarioArmazem")
    public String adicionarContentor(
            @RequestParam double capacidade,
            @RequestParam double pesoMaximo,
            @RequestParam String localAtual,
            @RequestParam int tipoContentorId,
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
            EstadoContentorEntity estadoContentor = repo_estadoContentor.findById(2).orElse(null);


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

    @PostMapping("/Contentores/AlterarEstado/FuncionarioArmazem")
    @Transactional
    public String alterarEstadoParaProntoFuncionarioArmazem(@RequestParam("cin") int cin) {
        ContentorEntity contentor = repo_contentor.findById(cin).orElse(null);
        if (contentor != null) {
            Long countCargas = repo_contentor.countByIdContentor(cin);
            if (countCargas > 0) {
                EstadoContentorEntity estadoPronto = repo_estadoContentor.findById(1).orElse(null);
                if (estadoPronto != null) {
                    contentor.setIdEstadoContentor(estadoPronto.getId());
                    contentor.setEstadoContentorByIdEstadoContentor(estadoPronto);
                    repo_contentor.save(contentor);
                }
            }
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
        return new CargaDto(carga.getId(), carga.getObservacoes(), carga.getPeso(), carga.getVolume(), carga.getEstadoCargaByIdEstadoCarga().getDescricao());
    }

    private ContentorDto convertToDTO(ContentorEntity contentor) {
        return new ContentorDto(contentor.getCin(), contentor.getCapacidade(), contentor.getPesoMax(), contentor.getLocalAtual());
    }

    @GetMapping("/Cargas/Disponiveis/FuncionarioArmazem")
    @ResponseBody
    public List<CargaDto> listarCargasDisponiveisFuncionarioArmazem(HttpSession session) {
        int funcionarioId = (int) session.getAttribute("userId");
        FuncionarioEntity funcionario = repo_funcionario.findById(funcionarioId).orElse(null);
        if (funcionario != null) {
            int armazemId = funcionario.getIdArmazem();
            List<CargaEntity> cargas = repo_carga.findByIdArmazemAndIdContentorNullAndReservaPago(armazemId);
            return cargas.stream().map(this::convertToDTO).collect(Collectors.toList());
        }
        return null;
    }

    @GetMapping("/Contentores/Disponiveis/FuncionarioArmazem")
    @ResponseBody
    public List<ContentorDto> listarContentoresDisponiveisFuncionarioArmazem(@RequestParam("cargaId") int cargaId, HttpSession session) {
        CargaEntity carga = repo_carga.findById(cargaId).orElse(null);
        if (carga == null) return null;

        int funcionarioId = (int) session.getAttribute("userId");
        FuncionarioEntity funcionario = repo_funcionario.findById(funcionarioId).orElse(null);

        if (funcionario != null) {
            int armazemId = funcionario.getIdArmazem();

            return repo_contentor.findByIdArmazem(armazemId).stream()
                    .filter(contentor -> {
                        double cargaTotal = repo_carga.findByContentorCin(contentor.getCin()).stream()
                                .mapToDouble(CargaEntity::getPeso)
                                .sum();
                        return (cargaTotal + carga.getPeso() <= contentor.getPesoMax())
                                && (contentor.getCapacidade() >= carga.getVolume());
                    })
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());

        } else {
            return null;
        }

    }

    @PostMapping("/Contentores/AdicionarCarga/FuncionarioArmazem")
    @Transactional
    public String adicionarCargaContentorFuncionarioArmazem(
            @RequestParam("contentorCin") Integer contentorCin,
            @RequestParam("cargaId") Integer cargaId,
            HttpSession session
    ) {
        int funcionarioId = (int) session.getAttribute("userId");
        FuncionarioEntity funcionario = repo_funcionario.findById(funcionarioId).orElse(null);
        if (funcionario != null) {
            ContentorEntity contentor = repo_contentor.findById(contentorCin).orElse(null);
            CargaEntity carga = repo_carga.findById(cargaId).orElse(null);

            if (contentor != null && carga != null && contentor.getIdArmazem() == funcionario.getIdArmazem()) {
                Double cargaTotal = repo_contentor.sumPesos(contentorCin);
                if (cargaTotal == null) {
                    cargaTotal = 0.0;
                }
                if ((cargaTotal + carga.getPeso() <= contentor.getPesoMax()) &&
                        (carga.getVolume() <= contentor.getCapacidade())) {

                    carga.setIdContentor(contentorCin);
                    carga.setContentorByIdContentor(contentor);
                    carga.setLocalAtual(contentor.getLocalAtual());
                    repo_carga.save(carga);
                }
            }
        }
        return "redirect:/Contentores/FuncionarioArmazem";
    }


    // Funcionário Transporte

    @GetMapping("/Contentores/FuncionarioTransporte")
    public String listarContentoresFuncionarioTransporte(Model model, HttpSession session) {
        String loggedInUser = (String) session.getAttribute("username");
        model.addAttribute("loggedInUser", loggedInUser);

        int funcionarioId = (int) session.getAttribute("userId");
        FuncionarioEntity funcionario = repo_funcionario.findById(funcionarioId).orElse(null);

        if (funcionario != null) {
            List<ContentorEntity> contentores = repo_contentor.findByEstadoProntoAndFora();
            model.addAttribute("contentores", contentores);
        }

        List<TipoContentorEntity> tiposContentor = repo_tipoContentor.findAll();
        List<EstadoContentorEntity> estadosContentor = repo_estadoContentor.findAll();
        List<ArmazemEntity> armazens = repo_armazem.findAll();
        model.addAttribute("tiposContentor", tiposContentor);
        model.addAttribute("estadosContentor", estadosContentor);
        model.addAttribute("armazens", armazens);
        return "ContentoresFuncionarioTransporte";
    }

    @PostMapping("/Contentores/Editar/FuncionarioTransporte")
    public String editarLocalAtualContentorFuncionarioTransporte(
            @RequestParam("cin") Integer cin,
            @RequestParam("localAtual") String localAtual
    ) {

      ContentorEntity contentor = repo_contentor.findById(cin).orElse(null);
        if (contentor != null) {
            contentor.setLocalAtual(localAtual);
            repo_contentor.save(contentor);
        }

        return "redirect:/Contentores/FuncionarioTransporte";
    }

    @PostMapping("/Contentores/AlterarEstadoParaFora/FuncionarioTransporte")
    @Transactional
    public String alterarEstadoParaFora(@RequestParam("cin") int cin, HttpSession session) {
        ContentorEntity contentor = repo_contentor.findById(cin).orElse(null);
        if (contentor != null) {
            EstadoContentorEntity estadoFora = repo_estadoContentor.findById(3).orElse(null);
            if (estadoFora != null) {
                contentor.setIdEstadoContentor(estadoFora.getId());
                contentor.setEstadoContentorByIdEstadoContentor(estadoFora);
                contentor.setIdArmazem(null);
                contentor.setArmazemByIdArmazem(null);

                List<CargaEntity> cargas = repo_carga.findByContentorCin(cin);

                int funcionarioId = (int) session.getAttribute("userId");
                FuncionarioEntity funcionario = repo_funcionario.findById(funcionarioId).orElse(null);

                if(funcionario != null){
                   Collection<TransportemaritimoEntity> transportemaritimo = funcionario.getTransportemaritimosById();

                    for (TransportemaritimoEntity transporte : transportemaritimo) {
                        contentor.setTransportemaritimoByIdTransporteMaritimo(transporte);
                        contentor.setIdTransporteMaritimo(transporte.getId());
                    }
                }

                repo_contentor.save(contentor);



                EstadoCargaEntity estadoCarga = repo_estadoCarga.findById(3).orElse(null);
                for (CargaEntity carga : cargas) {
                    if (estadoCarga != null){
                        carga.setIdArmazem(null);
                        carga.setArmazemByIdArmazem(null);
                        carga.setIdEstadoCarga(estadoCarga.getId());
                        carga.setEstadoCargaByIdEstadoCarga(estadoCarga);
                        repo_carga.save(carga);
                    }

                }
            }
        }
        return "redirect:/Contentores/FuncionarioTransporte";
    }

    @PostMapping("/Contentores/SimularEntrega/FuncionarioTransporte")
    @Transactional
    public String simularEntregaContentor(@RequestParam("cin") int cin) {
        ContentorEntity contentor = repo_contentor.findById(cin).orElse(null);
        if (contentor != null) {
            EstadoCargaEntity estadoEntregue = repo_estadoCarga.findById(1).orElse(null);
                List<CargaEntity> cargas = repo_carga.findByContentorCin(cin);
                for (CargaEntity carga : cargas) {
                    if (estadoEntregue != null){
                        carga.setIdEstadoCarga(estadoEntregue.getId());
                        carga.setEstadoCargaByIdEstadoCarga(estadoEntregue);
                        repo_carga.save(carga);
                    }

                }
        }
        return "redirect:/Contentores/FuncionarioTransporte";
    }

}
