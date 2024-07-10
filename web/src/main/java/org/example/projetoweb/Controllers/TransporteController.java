package org.example.projetoweb.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pt.ipvc.database.entity.*;
import pt.ipvc.database.repository.TransportemaritimoRepository;
import pt.ipvc.database.repository.FuncionarioRepository;

import java.util.List;
import java.util.Objects;

@Controller
public class TransporteController {

    private final TransportemaritimoRepository repo_transporte;
    private final FuncionarioRepository funcionario_repo;

    public TransporteController(TransportemaritimoRepository repo_transporte, FuncionarioRepository funcionario_repo) {
        this.repo_transporte = repo_transporte;
        this.funcionario_repo = funcionario_repo;
    }

    @GetMapping("/Transportes")
    public String listarTransportes(Model model){
        List<TransportemaritimoEntity> transportes = repo_transporte.findAll();
        List<FuncionarioEntity> funcionarios = funcionario_repo.findAll();
        model.addAttribute("transportes", transportes);
        model.addAttribute("funcionarios", funcionarios);
        return "Transportes";
    }

    @PostMapping("/inserirTransporte")
    public String adicionarTransporte(@RequestParam String imo, @RequestParam String portoOrigem, @RequestParam String portoDestino, @RequestParam int idFuncionario, Model model) {

            TransportemaritimoEntity novoTransporte = new TransportemaritimoEntity();
            novoTransporte.setImo(imo);
            novoTransporte.setPortoOrigem(portoOrigem);
            novoTransporte.setPortoDestino(portoDestino);

            FuncionarioEntity funcionario = funcionario_repo.findById(idFuncionario).orElse(null);
            novoTransporte.setFuncionarioByIdFuncionario(funcionario);
            novoTransporte.setIdFuncionario(funcionario.getId());


            repo_transporte.save(novoTransporte);


            return "redirect:/Transportes";
        }

}
