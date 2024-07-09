package org.example.projetoweb.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pt.ipvc.database.entity.*;
import pt.ipvc.database.repository.*;

import java.util.List;

@Controller
public class CargaController {

    private final CargaRepository repo_carga;
    private final ReservaRepository repo_reserva;
    private final ContentorRepository repo_contentor;
    private final ArmazemRepository repo_armazem;
    private final TipoCargaRepository repo_tipoCarga;
    private final CotacaoRepository repo_cotacao;

    public CargaController(CargaRepository repo_carga, ReservaRepository repo_reserva, ContentorRepository repo_contentor,
                           ArmazemRepository repo_armazem, TipoCargaRepository repo_tipoCarga, CotacaoRepository repo_cotacao) {
        this.repo_carga = repo_carga;
        this.repo_reserva = repo_reserva;
        this.repo_contentor = repo_contentor;
        this.repo_armazem = repo_armazem;
        this.repo_tipoCarga = repo_tipoCarga;
        this.repo_cotacao = repo_cotacao;
    }

    @GetMapping("/CargaArmazem")
    public String listarCargas(Model model) {
        List<CargaEntity> cargas = repo_carga.findAll();
        model.addAttribute("cargas", cargas);
        return "CargaArmazem";
    }

    @GetMapping("/inserirCarga")
    public String showInserirCargaForm(Model model) {
        model.addAttribute("reservas", repo_reserva.findAll());
        model.addAttribute("contentores", repo_contentor.findAll());
        model.addAttribute("armazens", repo_armazem.findAll());
        model.addAttribute("tiposCarga", repo_tipoCarga.findAll());
        model.addAttribute("cotacoes", repo_cotacao.findAll());
        return "InserirCarga";
    }

    @PostMapping("/inserirCarga")
    public String inserirCarga(
            @RequestParam("nome") String nome,
            @RequestParam("quantidade") int quantidade,
            @RequestParam("volume") double volume,
            @RequestParam("peso") double peso,
            @RequestParam("localAtual") String localAtual,
            @RequestParam("observacoes") String observacoes,
            @RequestParam("idReserva") Integer idReserva,
            @RequestParam("idContentor") Integer idContentor,
            @RequestParam("idArmazem") Integer idArmazem,
            @RequestParam("idTipoCarga") Integer idTipoCarga,
            @RequestParam("idCotacao") Integer idCotacao
    ) {
        CargaEntity novaCarga = new CargaEntity();
        novaCarga.setNome(nome);
        novaCarga.setQuantidade(quantidade);
        novaCarga.setVolume(volume);
        novaCarga.setPeso(peso);
        novaCarga.setLocalAtual(localAtual);
        novaCarga.setObservacoes(observacoes);

        ReservaEntity reserva = repo_reserva.findById(idReserva).orElse(null);
        ContentorEntity contentor = repo_contentor.findById(idContentor).orElse(null);
        ArmazemEntity armazem = repo_armazem.findById(idArmazem).orElse(null);
        TipoCargaEntity tipoCarga = repo_tipoCarga.findById(idTipoCarga).orElse(null);
        CotacaoEntity cotacao = repo_cotacao.findById(idCotacao).orElse(null);

        novaCarga.setReservaByIdReserva(reserva);
        novaCarga.setContentorByIdContentor(contentor);
        novaCarga.setArmazemByIdArmazem(armazem);
        novaCarga.setTipoCargaByIdTipoCarga(tipoCarga);
        novaCarga.setCotacaoByIdCotacao(cotacao);

        repo_carga.save(novaCarga);

        return "redirect:/CargaArmazem";
    }
}
