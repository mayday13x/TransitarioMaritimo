package org.example.projetoweb.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pt.ipvc.database.entity.ClienteEntity;
import pt.ipvc.database.entity.FuncionarioEntity;
import pt.ipvc.database.repository.ClienteRepository;
import pt.ipvc.database.repository.FuncionarioRepository;

import jakarta.servlet.http.HttpSession;

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
            Model model,
            HttpSession session) {

        if ("cliente".equals(userType)) {
            // Lógica de autenticação para cliente
            ClienteEntity cliente = clienteRepo.findByEmailAndPassword(username, password);
            if (cliente != null) {
                session.setAttribute("username", username);
                session.setAttribute("userType", "cliente");
                session.setAttribute("userId", cliente.getId()); // Store client ID in session
                return "redirect:/Cotacao/Cliente";
            }
        } else if ("funcionario".equals(userType)) {
            // Lógica de autenticação para funcionário
            FuncionarioEntity funcionario = funcionarioRepo.findByEmailAndPassword(username, password);
            if (funcionario != null) {
                session.setAttribute("username", username);
                session.setAttribute("userType", "funcionario");
                session.setAttribute("userId", funcionario.getId());

                int tipoFuncionario = funcionario.getIdTipoFuncionario();
                if (tipoFuncionario == 4) {
                    return "redirect:/Armazem/GestorLogistico";
                } else if (tipoFuncionario == 1)  {
                    return "redirect:/Armazem/Admin";
                } else if (tipoFuncionario == 5) {
                    return "redirect:/Contentores/FuncionarioArmazem";
                } else if (tipoFuncionario == 3) {
                    return "redirect:/Reservas/GestorOperacional";
                }
            }
        }

        model.addAttribute("error", "Credenciais inválidas!");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    public static String getLoggedInUser(HttpSession session) {
        String username = (String) session.getAttribute("username");
        String userType = (String) session.getAttribute("userType");
        if (username != null && userType != null) {
            return userType + ": " + username;
        }
        return "No user logged in";
    }
}
