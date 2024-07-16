package org.example.projetoweb.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pt.ipvc.database.entity.ClienteEntity;
import pt.ipvc.database.entity.FuncionarioEntity;
import pt.ipvc.database.entity.CodPostalEntity;
import pt.ipvc.database.entity.ArmazemEntity;
import pt.ipvc.database.repository.ClienteRepository;
import pt.ipvc.database.repository.FuncionarioRepository;
import pt.ipvc.database.repository.CodPostalRepository;
import pt.ipvc.database.repository.ArmazemRepository;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

@Controller
public class LoginController {

    private final ClienteRepository clienteRepo;
    private final FuncionarioRepository funcionarioRepo;
    private final CodPostalRepository codPostalRepo;
    private final ArmazemRepository armazemRepo;

    public LoginController(ClienteRepository clienteRepo, FuncionarioRepository funcionarioRepo, CodPostalRepository codPostalRepo, ArmazemRepository armazemRepo) {
        this.clienteRepo = clienteRepo;
        this.funcionarioRepo = funcionarioRepo;
        this.codPostalRepo = codPostalRepo;
        this.armazemRepo = armazemRepo;
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "index";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("userType") String userType,
            Model model,
            HttpSession session) {

        if ("cliente".equals(userType)) {
            ClienteEntity cliente = clienteRepo.findByEmailAndPassword(username, password);
            if (cliente != null) {
                session.setAttribute("username", username);
                session.setAttribute("userType", "cliente");
                session.setAttribute("userId", cliente.getId());
                if (Objects.equals(cliente.getPassword(), "default")) {
                    return "redirect:/alterarSenha";
                }
                return "redirect:/Cotacao/Cliente";
            }
        } else if ("funcionario".equals(userType)) {
            FuncionarioEntity funcionario = funcionarioRepo.findByEmailAndPassword(username, password);
            if (funcionario != null) {
                session.setAttribute("username", username);
                session.setAttribute("userType", "funcionario");
                session.setAttribute("userId", funcionario.getId());
                if (Objects.equals(funcionario.getPassword(), "default")) {
                    return "redirect:/alterarSenha";
                }
                int tipoFuncionario = funcionario.getIdTipoFuncionario();
                if (tipoFuncionario == 1) {
                    return "redirect:/Armazem/Admin";
                } else if (tipoFuncionario == 2) {
                    return "redirect:/Clientes/GestorComercial";
                } else if (tipoFuncionario == 3) {
                    return "redirect:/Reservas/GestorOperacional";
                } else if (tipoFuncionario == 4) {
                    return "redirect:/Armazem/GestorLogistico";
                } else if (tipoFuncionario == 5) {
                    return "redirect:/Contentores/FuncionarioArmazem";
                } else if (tipoFuncionario == 6) {
                    return "redirect:/Contentores/FuncionarioTransporte";
                }
            }
        }

        model.addAttribute("error", "Credenciais inválidas!");
        return "index";
    }

    @GetMapping("/alterarSenha")
    public String showChangePasswordForm(HttpSession session, Model model) {
        return "MudarPassword";
    }

    @PostMapping("/alterarSenha")
    public String changePassword(
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmPassword") String confirmPassword,
            HttpSession session,
            Model model) {

        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "As senhas não coincidem!");
            return "MudarPassword";
        }

        String userType = (String) session.getAttribute("userType");
        Integer userId = (Integer) session.getAttribute("userId");

        if ("cliente".equals(userType)) {
            ClienteEntity cliente = clienteRepo.findById(userId).orElse(null);
            if (cliente != null) {
                cliente.setPassword(newPassword);
                clienteRepo.save(cliente);
            }
        } else if ("funcionario".equals(userType)) {
            FuncionarioEntity funcionario = funcionarioRepo.findById(userId).orElse(null);
            if (funcionario != null) {
                funcionario.setPassword(newPassword);
                funcionarioRepo.save(funcionario);
            }
        }

        return "redirect:/";
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

    @GetMapping("/registar")
    public String showRegisterForm(Model model) {
        List<CodPostalEntity> codPostais = codPostalRepo.findAll();
        List<ArmazemEntity> armazens = armazemRepo.findArmazensSemFuncionario();
        model.addAttribute("codPostais", codPostais);
        model.addAttribute("armazens", armazens);
        return "registar";
    }

    @PostMapping("/registar")
    public String register(
            @RequestParam("nome") String nome,
            @RequestParam("nif") Integer nif,
            @RequestParam("rua") String rua,
            @RequestParam("porta") Integer porta,
            @RequestParam("codPostalId") Integer codPostalId,
            @RequestParam("email") String email,
            @RequestParam("telefone") String telefone,
            @RequestParam("userType") String userType,
            @RequestParam(value = "tipoFuncionario", required = false) Integer tipoFuncionario,
            @RequestParam(value = "armazemId", required = false) Integer armazemId
    ) {
        CodPostalEntity codPostal = codPostalRepo.findById(codPostalId).orElse(null);

        if (codPostal != null) {
            if ("cliente".equals(userType)) {
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

                clienteRepo.save(novoCliente);
            } else if ("funcionario".equals(userType) && tipoFuncionario != null) {
                FuncionarioEntity novoFuncionario = new FuncionarioEntity();
                novoFuncionario.setNome(nome);
                novoFuncionario.setNif(nif);
                novoFuncionario.setRua(rua);
                novoFuncionario.setPorta(porta);
                novoFuncionario.setIdCodPostal(codPostal.getIdCodPostal());
                novoFuncionario.setEmail(email);
                novoFuncionario.setTelefone(telefone);
                novoFuncionario.setUtilizador(email.substring(0, email.indexOf('@')));
                novoFuncionario.setPassword("default");
                novoFuncionario.setIdTipoFuncionario(tipoFuncionario);
                novoFuncionario.setCodPostalByIdCodPostal(codPostal);

                if (tipoFuncionario == 5 && armazemId != null) {
                    ArmazemEntity armazem = armazemRepo.findById(armazemId).orElse(null);
                    if (armazem != null) {
                        novoFuncionario.setIdArmazem(armazem.getId());
                        novoFuncionario.setArmazemByIdArmazem(armazem);
                    }
                }

                funcionarioRepo.save(novoFuncionario);
            }
        }

        return "redirect:/login";
    }
}
