package org.example.projetoweb.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pt.ipvc.database.entity.*;
import pt.ipvc.database.entity.FornecedorEntity;
import pt.ipvc.database.repository.TransportemaritimoRepository;

import java.util.List;

@Controller
public class TransporteController {

    public final TransportemaritimoRepository repo_transporte;

    public TransporteController(TransportemaritimoRepository repo_transporte) {
        this.repo_transporte = repo_transporte;
    }

    @GetMapping("/Transportes")
    public String listarTransportes(Model model){
        List<TransportemaritimoEntity> transportes = repo_transporte.findAll();
        model.addAttribute("transportes", transportes);
        return "Transportes";
    }
}
