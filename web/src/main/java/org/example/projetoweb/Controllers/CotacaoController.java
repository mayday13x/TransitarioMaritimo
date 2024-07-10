package org.example.projetoweb.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public String listarCotacoes(Model model){
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
            @RequestParam("descricao") String descricao,
            @RequestParam("preco") double preco,
            @RequestParam("comissao") double comissao,
            @RequestParam("idFornecedor") Integer idFornecedor,
            @RequestParam("nomeCarga") String nomeCarga,
            @RequestParam("quantidadeCarga") int quantidadeCarga,
            @RequestParam("alturaCarga") double alturaCarga,
            @RequestParam("compCarga") double compCarga,
            @RequestParam("larguraCarga") double larguraCarga,
            @RequestParam("pesoCarga") double pesoCarga,
            @RequestParam("observacoesCarga") String observacoesCarga,
            @RequestParam("idTipoCarga") Integer idTipoCarga,
            @RequestParam("servicosSelecionados") List<Integer> servicosSelecionados
    ) {
        FornecedorEntity fornecedor = repo_fornecedor.findById(idFornecedor).orElse(null);
        EstadoCotacaoEntity estadoCotacao = repo_estadoCotacao.findByDescricaoLike("Em análise");
        ClienteEntity cliente = repo_cliente.findById(idCliente).orElse(null);
        TipoCargaEntity tipoCarga = repo_tipoCarga.findById(idTipoCarga).orElse(null);

        if (fornecedor != null && estadoCotacao != null && cliente != null && tipoCarga != null) {
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

            double valorTotal = preco + (comissao * preco);

            for (Integer idServico : servicosSelecionados) {
                ServicoEntity servico = repo_servico.findById(idServico).orElse(null);
                if (servico != null) {
                    LinhaCotacaoEntity linha = new LinhaCotacaoEntity();
                    linha.setIdCotacao(novaCotacao.getId());
                    linha.setIdServico(servico.getId());
                    linha.setCotacaoByIdCotacao(novaCotacao);
                    linha.setServicoByIdServico(servico);

                    repo_linhaCotacao.save(linha);
                    valorTotal += servico.getPreco() + (servico.getComissao() * servico.getPreco());
                }
            }

            novaCotacao.setValorTotal(valorTotal);
            repo_cotacao.save(novaCotacao);
        }

        return "redirect:/Cotacao";
    }
}
