package org.example.projetoweb.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pt.ipvc.database.entity.*;
import pt.ipvc.database.repository.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

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

    public CotacaoController(CotacaoRepository repo_cotacao, FornecedorRepository repo_fornecedor, EstadoCotacaoRepository repo_estadoCotacao, ClienteRepository repo_cliente, CargaRepository repo_carga, TipoCargaRepository repo_tipoCarga, LinhaCotacaoRepository repo_linhaCotacao, ServicoRepository repo_servico) {
        this.repo_cotacao = repo_cotacao;
        this.repo_fornecedor = repo_fornecedor;
        this.repo_estadoCotacao = repo_estadoCotacao;
        this.repo_cliente = repo_cliente;
        this.repo_carga = repo_carga;
        this.repo_tipoCarga = repo_tipoCarga;
        this.repo_linhaCotacao = repo_linhaCotacao;
        this.repo_servico = repo_servico;
    }

    @GetMapping("/Cotacao")
    public String listarCotacoes(Model model) {
        List<CotacaoEntity> cotacoes = repo_cotacao.findAll();
        model.addAttribute("cotacoes", cotacoes);
        return "Cotacao";
    }

    @GetMapping("/inserirCotacao")
    public String showInserirCotacaoForm(Model model) {
        model.addAttribute("fornecedores", repo_fornecedor.findAll());
        model.addAttribute("clientes", repo_cliente.findAll());
        model.addAttribute("tiposCarga", repo_tipoCarga.findAll());
        model.addAttribute("servicos", repo_servico.findAll());
        return "InserirCotacao";
    }

    @PostMapping("/inserirCotacao")
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

        return "redirect:/Cotacao";
    }

    @PostMapping("/editarCotacao")
    public String editarCotacao(
            @RequestParam int id,
            @RequestParam int idCliente,
            @RequestParam int idEstadoCotacao,
            @RequestParam Date data,
            @RequestParam double valorTotal) {

        CotacaoEntity cotacao = repo_cotacao.findById(id).orElse(null);
        EstadoCotacaoEntity estadoCotacao = repo_estadoCotacao.findById(idEstadoCotacao).orElse(null);

        if (cotacao != null && estadoCotacao != null) {
            cotacao.setIdCliente(idCliente);
            cotacao.setEstadoCotacaoByIdEstadoCotacao(estadoCotacao);
            cotacao.setData(data);
            cotacao.setValorTotal(valorTotal);

            repo_cotacao.save(cotacao);
        }

        return "redirect:/Cotacao";
    }

    @DeleteMapping("/removerCotacao")
    @ResponseBody
    @Transactional
    public String removerCotacao(@RequestParam("id") int id) {
        // Remover a cotação (cascata remove as linhas de cotação e cargas associadas)
        repo_cotacao.deleteById(id);
        return "Cotação removida com sucesso";
    }
}
