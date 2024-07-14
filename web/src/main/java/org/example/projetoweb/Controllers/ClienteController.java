package org.example.projetoweb.Controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pt.ipvc.database.entity.ClienteEntity;
import pt.ipvc.database.entity.CodPostalEntity;
import pt.ipvc.database.repository.ClienteRepository;
import pt.ipvc.database.repository.CodPostalRepository;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
public class ClienteController {

    private final ClienteRepository repo_cliente;
    private final CodPostalRepository repo_codPostal;

    public ClienteController(ClienteRepository repo_cliente, CodPostalRepository repo_codPostal) {
        this.repo_cliente = repo_cliente;
        this.repo_codPostal = repo_codPostal;
    }

    // Admin

    @GetMapping("/Clientes/Admin")
    public String listarClientes(Model model, HttpSession session){
        List<ClienteEntity> clientes = repo_cliente.findAll();
        List<CodPostalEntity> codPostais = repo_codPostal.findAll();
        model.addAttribute("clientes", clientes);
        model.addAttribute("codPostais", codPostais);

        String loggedInUser = (String) session.getAttribute("username");
        model.addAttribute("loggedInUser", loggedInUser);

        return "Clientes";
    }

    @GetMapping("/Clientes/Inserir/Admin")
    public String showInserirCliente(Model model, HttpSession session) {
        List<CodPostalEntity> codPostais = repo_codPostal.findAll();
        model.addAttribute("codPostais", codPostais);

        String loggedInUser = (String) session.getAttribute("username");
        model.addAttribute("loggedInUser", loggedInUser);

        return "InserirCliente";
    }

    @PostMapping("/Clientes/Inserir/Admin")
    public String inserirCliente(
            @RequestParam("nome") String nome,
            @RequestParam("nif") Integer nif,
            @RequestParam("rua") String rua,
            @RequestParam("porta") Integer porta,
            @RequestParam("codPostal") Integer codPostalId,
            @RequestParam("email") String email,
            @RequestParam("telefone") String telefone
    ) {
        CodPostalEntity codPostal = repo_codPostal.findById(codPostalId).orElse(null);
        System.out.println(codPostal);

        if (codPostal != null) {
            ClienteEntity novoCliente = new ClienteEntity();
            novoCliente.setNome(nome);
            novoCliente.setNif(nif);
            novoCliente.setRua(rua);
            novoCliente.setPorta(porta);
            novoCliente.setIdCodPostal(codPostal.getIdCodPostal());
            novoCliente.setEmail(email);
            novoCliente.setTelefone(telefone);
            novoCliente.setUtilizador(email.substring(0, email.indexOf('@')));
            novoCliente.setPassword("default");
            novoCliente.setCodPostalByIdCodPostal(codPostal);

            repo_cliente.save(novoCliente);
        }

        return "redirect:/Clientes/Admin";
    }

    @PostMapping("/Clientes/Editar/Admin")
    public String editarCliente(
            @RequestParam("id") Integer id,
            @RequestParam("nome") String nome,
            @RequestParam("nif") Integer nif,
            @RequestParam("rua") String rua,
            @RequestParam("porta") Integer porta,
            @RequestParam("codPostal") Integer codPostalId,
            @RequestParam("email") String email,
            @RequestParam("telefone") String telefone
    ) {
        Optional<ClienteEntity> clienteOptional = repo_cliente.findById(id);
        CodPostalEntity codPostal = repo_codPostal.findById(codPostalId).orElse(null);

        if (clienteOptional.isPresent() && codPostal != null) {
            ClienteEntity cliente = clienteOptional.get();
            cliente.setNome(nome);
            cliente.setNif(nif);
            cliente.setRua(rua);
            cliente.setPorta(porta);
            cliente.setIdCodPostal(codPostal.getIdCodPostal());
            cliente.setEmail(email);
            cliente.setTelefone(telefone);
            cliente.setCodPostalByIdCodPostal(codPostal);

            repo_cliente.save(cliente);
        }

        return "redirect:/Clientes/Admin";
    }

    @PostMapping("/Clientes/Remover/Admin")
    public String removerCliente(@RequestParam("id") Integer id) {
        repo_cliente.deleteById(id);
        return "redirect:/Clientes/Admin";
    }

    // Gestor Comercial

    @GetMapping("/Clientes/GestorComercial")
    public String listarClientesGestorComercial(Model model, HttpSession session){
        List<ClienteEntity> clientes = repo_cliente.findAll();
        List<CodPostalEntity> codPostais = repo_codPostal.findAll();
        model.addAttribute("clientes", clientes);
        model.addAttribute("codPostais", codPostais);

        String loggedInUser = (String) session.getAttribute("username");
        model.addAttribute("loggedInUser", loggedInUser);

        return "ClientesGestorComercial";
    }

    @GetMapping("/Clientes/Inserir/GestorComercial")
    public String showInserirClienteGestorComercial(Model model, HttpSession session) {
        List<CodPostalEntity> codPostais = repo_codPostal.findAll();
        model.addAttribute("codPostais", codPostais);

        String loggedInUser = (String) session.getAttribute("username");
        model.addAttribute("loggedInUser", loggedInUser);

        return "InserirClienteGestorComercial";
    }

    @PostMapping("/Clientes/Inserir/GestorComercial")
    public String inserirClienteGestorComercial(
            @RequestParam("nome") String nome,
            @RequestParam("nif") Integer nif,
            @RequestParam("rua") String rua,
            @RequestParam("porta") Integer porta,
            @RequestParam("codPostal") Integer codPostalId,
            @RequestParam("email") String email,
            @RequestParam("telefone") String telefone
    ) {
        CodPostalEntity codPostal = repo_codPostal.findById(codPostalId).orElse(null);
        System.out.println(codPostal);

        if (codPostal != null) {
            ClienteEntity novoCliente = new ClienteEntity();
            novoCliente.setNome(nome);
            novoCliente.setNif(nif);
            novoCliente.setRua(rua);
            novoCliente.setPorta(porta);
            novoCliente.setIdCodPostal(codPostal.getIdCodPostal());
            novoCliente.setEmail(email);
            novoCliente.setTelefone(telefone);
            novoCliente.setUtilizador(email.substring(0, email.indexOf('@')));
            novoCliente.setPassword("default");
            novoCliente.setCodPostalByIdCodPostal(codPostal);

            repo_cliente.save(novoCliente);
        }

        return "redirect:/Clientes/GestorComercial";
    }

    @PostMapping("/Clientes/Editar/GestorComercial")
    public String editarClienteGestorComercial(
            @RequestParam("id") Integer id,
            @RequestParam("nome") String nome,
            @RequestParam("nif") Integer nif,
            @RequestParam("rua") String rua,
            @RequestParam("porta") Integer porta,
            @RequestParam("codPostal") Integer codPostalId,
            @RequestParam("email") String email,
            @RequestParam("telefone") String telefone
    ) {
        Optional<ClienteEntity> clienteOptional = repo_cliente.findById(id);
        CodPostalEntity codPostal = repo_codPostal.findById(codPostalId).orElse(null);

        if (clienteOptional.isPresent() && codPostal != null) {
            ClienteEntity cliente = clienteOptional.get();
            cliente.setNome(nome);
            cliente.setNif(nif);
            cliente.setRua(rua);
            cliente.setPorta(porta);
            cliente.setIdCodPostal(codPostal.getIdCodPostal());
            cliente.setEmail(email);
            cliente.setTelefone(telefone);
            cliente.setCodPostalByIdCodPostal(codPostal);

            repo_cliente.save(cliente);
        }

        return "redirect:/Clientes/GestorComercial";
    }

    @PostMapping("/Clientes/Remover/GestorComercial")
    public String removerClienteGestorComercial(@RequestParam("id") Integer id) {
        repo_cliente.deleteById(id);
        return "redirect:/Clientes/GestorComercial";
    }
}
