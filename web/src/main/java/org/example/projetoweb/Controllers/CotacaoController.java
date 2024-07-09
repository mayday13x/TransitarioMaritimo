package org.example.projetoweb.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pt.ipvc.database.entity.*;
import pt.ipvc.database.entity.FornecedorEntity;
import pt.ipvc.database.repository.CotacaoRepository;

import java.util.List;

@Controller
public class CotacaoController {

    public final CotacaoRepository repo_cotacao;

    public CotacaoController(CotacaoRepository repo_cotacao) {
        this.repo_cotacao = repo_cotacao;
    }

    @GetMapping("/Cotacao")
    public String listarCotacoes(Model model){
        List<CotacaoEntity> cotacoes = repo_cotacao.findAll();
        model.addAttribute("cotacoes", cotacoes);
        return "Cotacao";
    }

}
