package org.example.projetoweb.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pt.ipvc.database.entity.*;
import pt.ipvc.database.repository.*;
import org.example.projetoweb.Dto.ServicoDto;

import jakarta.servlet.http.HttpSession;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CotacaoController {

    private final CotacaoRepository repo_cotacao;
    private final FornecedorRepository repo_fornecedor;
    private final EstadoCotacaoRepository repo_estadoCotacao;
    private final ClienteRepository repo_cliente;
    private final CargaRepository repo_carga;
    private final TipoCargaRepository repo_tipoCarga;
    private final LinhaCotacaoRepository repo_linhaCotacao;
    private final ServicoRepository repo_servico;
    private final TransportemaritimoRepository repo_transporte;

    public CotacaoController(
            CotacaoRepository repo_cotacao,
            FornecedorRepository repo_fornecedor,
            EstadoCotacaoRepository repo_estadoCotacao,
            ClienteRepository repo_cliente,
            CargaRepository repo_carga,
            TipoCargaRepository repo_tipoCarga,
            LinhaCotacaoRepository repo_linhaCotacao,
            ServicoRepository repo_servico,
            TransportemaritimoRepository repo_transporte
    ) {
        this.repo_cotacao = repo_cotacao;
        this.repo_fornecedor = repo_fornecedor;
        this.repo_estadoCotacao = repo_estadoCotacao;
        this.repo_cliente = repo_cliente;
        this.repo_carga = repo_carga;
        this.repo_tipoCarga = repo_tipoCarga;
        this.repo_linhaCotacao = repo_linhaCotacao;
        this.repo_servico = repo_servico;
        this.repo_transporte = repo_transporte;
    }

    //Admin

    @GetMapping("/Cotacao/Admin")
    public String listarCotacoes(Model model, HttpSession session) {
        List<CotacaoEntity> cotacoes = repo_cotacao.findAll();
        model.addAttribute("cotacoes", cotacoes);

        String loggedInUser = (String) session.getAttribute("username");
        model.addAttribute("loggedInUser", loggedInUser);
        return "Cotacao";
    }


    @GetMapping("/Cotacao/Inserir/Admin")
    public String showInserirCotacaoForm(Model model, HttpSession session) {
        model.addAttribute("fornecedores", repo_fornecedor.findAll());
        model.addAttribute("clientes", repo_cliente.findAll());
        model.addAttribute("cargas", repo_carga.findAll());
        model.addAttribute("tipoCargas", repo_tipoCarga.findAll());
        model.addAttribute("servicos", repo_servico.findAll());

        String loggedInUser = (String) session.getAttribute("username");
        model.addAttribute("loggedInUser", loggedInUser);

        return "InserirCotacao";
    }

    @PostMapping("/Cotacao/Inserir/Admin")
    public String inserirCotacao(
            @RequestParam("idCliente") Integer idCliente,
            @RequestParam("nomeCarga") String nomeCarga,
            @RequestParam("quantidadeCarga") int quantidadeCarga,
            @RequestParam("alturaCarga") double alturaCarga,
            @RequestParam("compCarga") double compCarga,
            @RequestParam("larguraCarga") double larguraCarga,
            @RequestParam("pesoCarga") double pesoCarga,
            @RequestParam("observacoesCarga") String observacoesCarga,
            @RequestParam("idTipoCarga") Integer idTipoCarga,
            @RequestParam(value = "servicosSelecionados", required = false) List<Integer> servicosSelecionados
    ) {
        EstadoCotacaoEntity estadoCotacao = repo_estadoCotacao.findByDescricaoLike("Em análise");
        ClienteEntity cliente = repo_cliente.findById(idCliente).orElse(null);
        TipoCargaEntity tipoCarga = repo_tipoCarga.findById(idTipoCarga).orElse(null);

        if (estadoCotacao != null && cliente != null && tipoCarga != null) {
            CotacaoEntity novaCotacao = new CotacaoEntity();
            novaCotacao.setIdCliente(idCliente);
            novaCotacao.setIdEstadoCotacao(estadoCotacao.getId());
            novaCotacao.setEstadoCotacaoByIdEstadoCotacao(estadoCotacao);
            novaCotacao.setData(Date.valueOf(LocalDate.now()));
            novaCotacao.setClienteByIdCliente(cliente);

            repo_cotacao.save(novaCotacao);

            CargaEntity carga = new CargaEntity();
            carga.setIdCotacao(novaCotacao.getId());
            carga.setNome(nomeCarga);
            carga.setQuantidade(quantidadeCarga);
            carga.setVolume(alturaCarga * compCarga * larguraCarga);
            carga.setPeso(pesoCarga);
            carga.setIdTipoCarga(tipoCarga.getId());
            carga.setTipoCargaByIdTipoCarga(tipoCarga);
            carga.setObservacoes(observacoesCarga);
            carga.setCotacaoByIdCotacao(novaCotacao);

            repo_carga.save(carga);

            double valorTotal = 0;

            if (servicosSelecionados != null) {
                for (Integer idServico : servicosSelecionados) {
                    ServicoEntity servico = repo_servico.findById(idServico).orElse(null);
                    if (servico != null) {
                        valorTotal += servico.getPreco() + (servico.getComissao() * servico.getPreco());

                        LinhaCotacaoEntity linha = new LinhaCotacaoEntity();
                        linha.setIdCotacao(novaCotacao.getId());
                        linha.setIdServico(servico.getId());
                        linha.setCotacaoByIdCotacao(novaCotacao);
                        linha.setServicoByIdServico(servico);

                        repo_linhaCotacao.save(linha);
                    }
                }
            }

            novaCotacao.setValorTotal(valorTotal);
            repo_cotacao.save(novaCotacao);
        }

        return "redirect:/Cotacao/Admin";
    }

    @PostMapping("/Cotacao/Remover/Admin")
    public String removerCotacao(@RequestParam("id") Integer id) {
        repo_cotacao.deleteById(id);
        return "redirect:/Cotacao/Admin";
    }

    //Cliente


    @GetMapping("/Cotacao/Cliente")
    public String listarCotacoesCliente(Model model, HttpSession session) {
        int clienteId = (int) session.getAttribute("userId");
        List<CotacaoEntity> cotacoes = repo_cotacao.findByIdClienteLike(clienteId);
        String loggedInUser = (String) session.getAttribute("username");
        model.addAttribute("loggedInUser", loggedInUser);
        model.addAttribute("cotacoes", cotacoes);
        return "CotacaoCliente";
    }

    @PostMapping("/Cotacao/ConfirmarCotacao/Cliente")
    @Transactional
    public String confirmarCotacao(@RequestParam int id) {
        CotacaoEntity cotacao = repo_cotacao.findById(id).orElse(null);
        if (cotacao != null) {
            EstadoCotacaoEntity estadoCotacao = repo_estadoCotacao.findById(2).orElse(null); // Assume 2 is the confirmed state
            cotacao.setEstadoCotacaoByIdEstadoCotacao(estadoCotacao);
            repo_cotacao.save(cotacao);
        }
        return "redirect:/Cotacao/Cliente";
    }

    @PostMapping("/Cotacao/RejeitarCotacao/Cliente")
    @Transactional
    public String rejeitarCotacao(@RequestParam int id) {
        CotacaoEntity cotacao = repo_cotacao.findById(id).orElse(null);
        if (cotacao != null) {
            EstadoCotacaoEntity estadoCotacao = repo_estadoCotacao.findById(3).orElse(null); // Assume 3 is the rejected state
            cotacao.setEstadoCotacaoByIdEstadoCotacao(estadoCotacao);
            repo_cotacao.save(cotacao);
        }
        return "redirect:/Cotacao/Cliente";
    }

    @GetMapping("/Cotacao/VerServicos/Cliente")
    @ResponseBody
    public List<ServicoDto> verServicosCotacao(@RequestParam int id) {
        List<ServicoEntity> servicos = repo_servico.findByIdCotacao(id);
        return servicos.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private ServicoDto convertToDTO(ServicoEntity servico) {
        ServicoDto dto = new ServicoDto();
        dto.setId(servico.getId());
        dto.setDescricao(servico.getDescricao());
        dto.setPreco(servico.getPreco());
        return dto;
    }

    //Gestor Operacional

    @GetMapping("/Cotacao/GestorOperacional")
    public String listarCotacoesGestorOperacional(Model model, HttpSession session) {
        List<CotacaoEntity> cotacoes = repo_cotacao.findByEstadoConfirmadoSemReserva();
        model.addAttribute("cotacoes", cotacoes);

        String loggedInUser = (String) session.getAttribute("username");
        model.addAttribute("loggedInUser", loggedInUser);
        return "CotacaoGestorOperacional";
    }

    @PostMapping("/Cotacao/Remover/GestorOperacional")
    public String removerCotacaoGestorOperacional(@RequestParam("id") Integer id) {
        repo_cotacao.deleteById(id);
        return "redirect:/Cotacao/GestorOperacional";
    }

    @GetMapping("/Reservas/Inserir/GestorOperacional")
    public String showRegistarReservaForm(@RequestParam("cotacaoId") int cotacaoId, Model model, HttpSession session) {
        CotacaoEntity cotacao = repo_cotacao.findById(cotacaoId).orElse(null);
        if (cotacao == null || cotacao.getIdEstadoCotacao() != 2) {
            return "redirect:/Cotacao/GestorOperacional";
        }

        List<ServicoEntity> servicos = repo_servico.findByIdCotacao(cotacaoId);
        model.addAttribute("cotacao", cotacao);
        model.addAttribute("servicos", servicos);
        model.addAttribute("transportes", repo_transporte.findAll());

        String loggedInUser = (String) session.getAttribute("username");
        model.addAttribute("loggedInUser", loggedInUser);

        return "inserirReservaGestorOperacional";
    }


}
