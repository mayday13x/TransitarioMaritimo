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
    private final CotacaoRepository repo_cotacao;
    private final TransportemaritimoRepository repo_transporte;
    private final LinhaCotacaoRepository linhaCotacaoRepository;

    public ReservaController(
            ReservaRepository repo_reserva,
            EstadoReservaRepository reserva_estado_repo,
            FuncionarioRepository funcionario_repo,
            ClienteRepository cliente_repo,
            CotacaoRepository repo_cotacao,
            TransportemaritimoRepository repo_transporte,
            LinhaCotacaoRepository linhaCotacaoRepository) {
        this.repo_reserva = repo_reserva;
        this.reserva_estado_repo = reserva_estado_repo;
        this.funcionario_repo = funcionario_repo;
        this.cliente_repo = cliente_repo;
        this.repo_cotacao = repo_cotacao;
        this.repo_transporte = repo_transporte;
        this.linhaCotacaoRepository = linhaCotacaoRepository;
    }

    //Admin

    @GetMapping("/Reservas/Admin")
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


    @PostMapping("/Reservas/Inserir/Admin")
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

        return "redirect:/Reservas/Admin";
    }

    @PostMapping("/Reservas/Editar/Admin")
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

        return "redirect:/Reservas/Admin";
    }

    @PostMapping("/Reservas/Remover/Admin")
    public String removerReserva(@RequestParam int id) {
        repo_reserva.deleteById(id);
        return "redirect:/Reservas/Admin";
    }

    //Cliente

    @GetMapping("/Reservas/Cliente")
    public String listarReservasCliente(Model model, HttpSession session) {
        int clienteId = (int) session.getAttribute("userId");
        List<ReservaEntity> reservas = repo_reserva.findByIdClienteLike(clienteId);
        model.addAttribute("reservas", reservas);

        String loggedInUser = (String) session.getAttribute("username");
        model.addAttribute("loggedInUser", loggedInUser);

        return "ReservasCliente";
    }

    @PostMapping("/Reservas/Pagar/Cliente")
    @Transactional
    public String pagarReserva(@RequestParam int id) {
        ReservaEntity reserva = repo_reserva.findById(id).orElse(null);
        if (reserva != null) {
            EstadoReservaEntity estadoReserva = reserva_estado_repo.findById(1).orElse(null); // Assume 2 is the paid state
            reserva.setEstadoReservaByIdEstadoReserva(estadoReserva);
            repo_reserva.save(reserva);
        }
        return "redirect:/Reservas/Cliente";
    }


    //Gestor Operacional

    @GetMapping("/Reservas/GestorOperacional")
    public String listarReservasGestorOperacional(Model model, HttpSession session){
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

        return "ReservasGestorOperacional";
    }

    @PostMapping("/Reservas/Inserir/GestorOperacional")
    public String inseirReservaGestorOperacional(
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

        return "redirect:/Reservas/GestorOperacional";
    }

    @PostMapping("Reservas/Editar/GestorOperacional")
    public String editarReservaGestorOperacional(
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

        return "redirect:/Reservas/GestorOperacional";
    }

    @PostMapping("/Reservas/Remover/GestorOperacional")
    public String removerReservaGestorOpercional(@RequestParam int id) {
        repo_reserva.deleteById(id);
        return "redirect:/Reservas/GestorOperacional";
    }

    //Gestor Operacional

    @PostMapping("/Reservas/Registar/GestorOperacional")
    public String registarReservaGestorOperacional(
            @RequestParam("cotacaoId") Integer cotacaoId,
            @RequestParam("origem") String origem,
            @RequestParam("destino") String destino,
            @RequestParam("dataReserva") Date dataReserva,
            @RequestParam("transporteMaritimo") Integer transporteMaritimoId,
            @RequestParam("dataPrevistaInicio") Date dataPrevistaInicio,
            @RequestParam("dataPrevistaFim") Date dataPrevistaFim,
            HttpSession session) {

        // Retrieve and validate the cotacao
        CotacaoEntity cotacao = repo_cotacao.findById(cotacaoId).orElse(null);
        if (cotacao == null || cotacao.getEstadoCotacaoByIdEstadoCotacao().getId() != 2) {
            return "redirect:/CotacaoCliente";
        }

        List<LinhaCotacaoEntity> linhaCotacao = linhaCotacaoRepository.findByIdCotacao(cotacaoId);
        for (LinhaCotacaoEntity linha : linhaCotacao) {
            linha.setDataPrevInicio(dataPrevistaInicio);
            linha.setDataPrevFim(dataPrevistaFim);

            linhaCotacaoRepository.save(linha);
        }

        // Create the new reserva
        ReservaEntity reserva = new ReservaEntity();
        reserva.setOrigem(origem);
        reserva.setDestino(destino);
        reserva.setData(dataReserva);
        reserva.setIdCliente(cotacao.getIdCliente());

        EstadoReservaEntity estadoreserva;
        estadoreserva = reserva_estado_repo.findByDescricaoLike("Pende pagamento");

        ClienteEntity cliente = new ClienteEntity();
        cliente = cliente_repo.findByidLike(cotacao.getIdCliente().toString());

        reserva.setIdEstadoReserva(estadoreserva.getId());
        reserva.setIdFuncionario(session.getAttribute("userId").hashCode());
        reserva.setEstadoReservaByIdEstadoReserva(estadoreserva);
        reserva.setFuncionarioByIdFuncionario(funcionario_repo.findByidLike(session.getAttribute("userId").toString()));
        reserva.setClienteByIdCliente(cliente);
        reserva.setIdCotacao(cotacao.getId());
        reserva.setCotacaoByIdCotacao(cotacao);

        TransportemaritimoEntity transporte = repo_transporte.findById(transporteMaritimoId).orElse(null);
        reserva.setTransportemaritimoByIdTransporteMaritimo(transporte);
        reserva.setIdTransporteMaritimo(transporteMaritimoId);

        repo_reserva.save(reserva);


        return "redirect:/Reservas/GestorOperacional";
    }
}
