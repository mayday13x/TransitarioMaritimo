package org.example.projetoweb.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pt.ipvc.database.repository.ClienteRepository;
import pt.ipvc.database.repository.FuncionarioRepository;

@Controller
public class LoginController {

    private final ClienteRepository clienteRepo;
    private final FuncionarioRepository funcionarioRepo;

    public LoginController(ClienteRepository clienteRepo, FuncionarioRepository funcionarioRepo) {
        this.clienteRepo = clienteRepo;
        this.funcionarioRepo = funcionarioRepo;
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("userType") String userType,
            Model model) {

        if ("cliente".equals(userType)) {
            // Lógica de autenticação para cliente
            if (clienteRepo.existsByEmailAndPassword(username, password)) {
                return "redirect:/Clientes";
            }
        } else if ("funcionario".equals(userType)) {
            // Lógica de autenticação para funcionário
            if (funcionarioRepo.existsByEmailAndPassword(username, password)) {
                return "redirect:/Funcionarios";
            }
        }

        model.addAttribute("error", "Credenciais inválidas!");
        return "login";
    }
}
