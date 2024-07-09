package org.example.projetoweb.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pt.ipvc.database.entity.*;
import pt.ipvc.database.repository.ReservaRepository;

import java.util.List;

@Controller
public class ReservaController {

    private  final ReservaRepository repo_reserva;

    public ReservaController(ReservaRepository repo_reserva) {
        this.repo_reserva = repo_reserva;
    }

    @GetMapping("/Reservas")
    public String listarReservas(Model model){
        List<ReservaEntity> reservas = repo_reserva.findAll();
        model.addAttribute("reservas", reservas);
        return "Reservas";
    }
}
