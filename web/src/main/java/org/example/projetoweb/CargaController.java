package org.example.projetoweb;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pt.ipvc.database.entity.*;
import pt.ipvc.database.repository.CargaRepository;

import java.util.List;

@Controller
public class CargaController {

    private final CargaRepository repo_carga;

    public CargaController(CargaRepository repo_carga) {
        this.repo_carga = repo_carga;
    }

    @GetMapping("/listar/cargas")
    public String listarCargas(Model model){
        List<CargaEntity> cargas = repo_carga.findAll();
        model.addAttribute("cargas", cargas);
        return "listar/cargas";
    }


}
