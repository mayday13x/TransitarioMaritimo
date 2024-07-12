package org.example.projetoweb.Controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pt.ipvc.database.entity.*;
import pt.ipvc.database.repository.*;

import jakarta.servlet.http.HttpSession;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

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
    public String listarReservas(Model model, HttpSession session){
        List<ReservaEntity> reservas = repo_reserva.findAll();
        List<EstadoReservaEntity> estados = reserva_estado_repo.findAll();
        List<FuncionarioEntity> funcionarios = funcionario_repo.findAll();
        List<ClienteEntity> clientes = cliente_repo.findAll();
        model.addAttribute("reservas", reservas);
        model.addAttribute("estados", estados);
        model.addAttribute("funcionarios", funcionarios);
        model.addAttribute("clientes", clientes);

        String loggedInUser = (String) session.getAttribute("username");
        model.addAttribute("loggedInUser", loggedInUser);

        return "Reservas";
    }

    @GetMapping("/ReservasCliente")
    public String listarReservasCliente(Model model, HttpSession session) {
        int clienteId = (int) session.getAttribute("userId");
        List<ReservaEntity> reservas = repo_reserva.findByIdClienteLike(clienteId);
        model.addAttribute("reservas", reservas);

        String loggedInUser = (String) session.getAttribute("username");
        model.addAttribute("loggedInUser", loggedInUser);

        return "ReservasCliente";
    }

    @PostMapping("/inserirReserva")
    public String adicionarReserva(
            @RequestParam String origem,
            @RequestParam String destino,
            @RequestParam Date data,
            @RequestParam int idCliente,
            @RequestParam int idEstadoReserva,
            @RequestParam int idFuncionario) {

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

    @PostMapping("/editarReserva")
    public String editarReserva(
            @RequestParam int id,
            @RequestParam String origem,
            @RequestParam String destino,
            @RequestParam Date data,
            @RequestParam int idCliente,
            @RequestParam int idEstadoReserva,
            @RequestParam int idFuncionario) {

        Optional<ReservaEntity> reservaOptional = repo_reserva.findById(id);
        if (reservaOptional.isPresent()) {
            ReservaEntity reserva = reservaOptional.get();
            reserva.setOrigem(origem);
            reserva.setDestino(destino);
            reserva.setData(data);
            reserva.setIdCliente(idCliente);
            reserva.setIdEstadoReserva(idEstadoReserva);
            reserva.setIdFuncionario(idFuncionario);

            EstadoReservaEntity estadoreserva = reserva_estado_repo.findById(idEstadoReserva).orElse(null);
            FuncionarioEntity funcionario = funcionario_repo.findById(idFuncionario).orElse(null);
            ClienteEntity cliente = cliente_repo.findById(idCliente).orElse(null);

            reserva.setEstadoReservaByIdEstadoReserva(estadoreserva);
            reserva.setFuncionarioByIdFuncionario(funcionario);
            reserva.setClienteByIdCliente(cliente);

            repo_reserva.save(reserva);
        }

        return "redirect:/Reservas";
    }

    @PostMapping("/removerReserva")
    public String removerReserva(@RequestParam int id) {
        repo_reserva.deleteById(id);
        return "redirect:/Reservas";
    }

    @PostMapping("/pagarReserva")
    @Transactional
    public String pagarReserva(@RequestParam int id) {
        ReservaEntity reserva = repo_reserva.findById(id).orElse(null);
        if (reserva != null) {
            EstadoReservaEntity estadoReserva = reserva_estado_repo.findById(1).orElse(null); // Assume 2 is the paid state
            reserva.setEstadoReservaByIdEstadoReserva(estadoReserva);
            repo_reserva.save(reserva);
        }
        return "redirect:/ReservasCliente";
    }
}
