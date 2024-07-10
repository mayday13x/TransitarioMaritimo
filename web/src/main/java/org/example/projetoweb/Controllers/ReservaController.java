package org.example.projetoweb.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pt.ipvc.database.entity.*;
import pt.ipvc.database.repository.ReservaRepository;
import pt.ipvc.database.repository.EstadoReservaRepository;
import pt.ipvc.database.repository.FuncionarioRepository;
import pt.ipvc.database.repository.ClienteRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Controller
public class ReservaController {

    private final ReservaRepository repo_reserva;
    private final EstadoReservaRepository reserva_estado_repo;
    private final FuncionarioRepository funcionario_repo;
    private final ClienteRepository cliente_repo;

    public ReservaController(ReservaRepository repo_reserva, EstadoReservaRepository reserva_estado_repo,
                             FuncionarioRepository funcionario_repo, ClienteRepository cliente_repo) {
        this.repo_reserva = repo_reserva;
        this.reserva_estado_repo = reserva_estado_repo;
        this.funcionario_repo = funcionario_repo;
        this.cliente_repo = cliente_repo;
    }

    @GetMapping("/Reservas")
    public String listarReservas(Model model){
        List<ReservaEntity> reservas = repo_reserva.findAll();
        List<EstadoReservaEntity> estados = reserva_estado_repo.findAll();
        List<FuncionarioEntity> funcionarios = funcionario_repo.findAll();
        List<ClienteEntity> clientes = cliente_repo.findAll();
        model.addAttribute("reservas", reservas);
        model.addAttribute("estados", estados);
        model.addAttribute("funcionarios", funcionarios);
        model.addAttribute("clientes", clientes);
        return "Reservas";
    }

    @PostMapping("/inserirReserva")
    public String adicionarReserva(
            @RequestParam String origem,
            @RequestParam String destino,
            @RequestParam Date data,
            @RequestParam int idCliente,
            @RequestParam int idEstadoReserva,
            @RequestParam int idFuncionario)
            {


            ReservaEntity novareserva = new ReservaEntity();
            novareserva.setOrigem(origem);
            novareserva.setDestino(destino);
            novareserva.setData(data);
            novareserva.setIdCliente(idCliente);
            novareserva.setIdEstadoReserva(idEstadoReserva);
            novareserva.setIdFuncionario(idFuncionario);

            EstadoReservaEntity estadoreserva = reserva_estado_repo.findById(idEstadoReserva).orElse(null);
            FuncionarioEntity funcionario = funcionario_repo.findById(idFuncionario).orElse(null);
            ClienteEntity cliente = cliente_repo.findById(idCliente).orElse(null);

            novareserva.setEstadoReservaByIdEstadoReserva(estadoreserva);
            novareserva.setFuncionarioByIdFuncionario(funcionario);
            novareserva.setClienteByIdCliente(cliente);

            repo_reserva.save(novareserva);

            return "redirect:/Reservas";
        }

}
