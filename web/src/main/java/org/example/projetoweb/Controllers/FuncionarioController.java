package org.example.projetoweb.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pt.ipvc.database.entity.*;
import pt.ipvc.database.repository.ArmazemRepository;
import pt.ipvc.database.repository.FuncionarioRepository;
import pt.ipvc.database.repository.CodPostalRepository;
import pt.ipvc.database.repository.TipoFuncionarioRepository;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
public class FuncionarioController {

    private final FuncionarioRepository repo_funcionario;
    private final CodPostalRepository cod_postal_repo;
    private final TipoFuncionarioRepository tipo_funcionario_repo;
    private final ArmazemRepository armazem_repo;

    public FuncionarioController(
            FuncionarioRepository repo_funcionario,
            CodPostalRepository cod_postal_repo,
            TipoFuncionarioRepository tipo_funcionario_repo,
            ArmazemRepository armazem_repo
    ) {
        this.repo_funcionario = repo_funcionario;
        this.cod_postal_repo = cod_postal_repo;
        this.tipo_funcionario_repo = tipo_funcionario_repo;
        this.armazem_repo = armazem_repo;
    }

    // Admin

    @GetMapping("/Funcionarios/Admin")
    public String listarFuncionarios(Model model, HttpSession session) {
        List<FuncionarioEntity> funcionarios = repo_funcionario.findAll();
        List<CodPostalEntity> codPostais = cod_postal_repo.findAll();
        List<TipoFuncionarioEntity> tiposFuncionario = tipo_funcionario_repo.findAll();
        List<ArmazemEntity> armazens = armazem_repo.findArmazensSemFuncionario();
        model.addAttribute("funcionarios", funcionarios);
        model.addAttribute("codPostais", codPostais);
        model.addAttribute("tiposFuncionario", tiposFuncionario);
        model.addAttribute("armazens", armazens);

        String loggedInUser = (String) session.getAttribute("username");
        model.addAttribute("loggedInUser", loggedInUser);

        return "Funcionarios";
    }

    @GetMapping("/Funcionarios/Inserir/Admin")
    public String showAdicionarFuncionario(Model model, HttpSession session) {
        List<CodPostalEntity> codPostais = cod_postal_repo.findAll();
        List<TipoFuncionarioEntity> tiposFuncionario = tipo_funcionario_repo.findAll();
        List<ArmazemEntity> armazens = armazem_repo.findArmazensSemFuncionario();
        model.addAttribute("codPostais", codPostais);
        model.addAttribute("tiposFuncionario", tiposFuncionario);
        model.addAttribute("armazens", armazens);

        String loggedInUser = (String) session.getAttribute("username");
        model.addAttribute("loggedInUser", loggedInUser);

        return "InserirFuncionario";
    }

    @PostMapping("/Funcionarios/Inserir/Admin")
    public String adicionarFuncionario(@RequestParam String nome, @RequestParam String nif, @RequestParam String rua,
                                       @RequestParam String porta, @RequestParam String localidade, @RequestParam String email,
                                       @RequestParam String telefone, @RequestParam String tipoFuncionario, @RequestParam(required = false) Integer idArmazem) {

        FuncionarioEntity novoFuncionario = new FuncionarioEntity();
        novoFuncionario.setNome(nome);
        novoFuncionario.setNif(Integer.valueOf(nif));
        novoFuncionario.setRua(rua);
        novoFuncionario.setPorta(Integer.valueOf(porta));

        CodPostalEntity codPostalEntity = cod_postal_repo.findByNameLike(localidade);
        TipoFuncionarioEntity tipoFuncionarioEntity = tipo_funcionario_repo.findByDescLike(tipoFuncionario);

        novoFuncionario.setIdCodPostal(codPostalEntity.getIdCodPostal());
        novoFuncionario.setCodPostalByIdCodPostal(codPostalEntity);
        novoFuncionario.setIdTipoFuncionario(tipoFuncionarioEntity.getId());
        novoFuncionario.setTipoFuncionarioByIdTipoFuncionario(tipoFuncionarioEntity);
        novoFuncionario.setEmail(email);
        novoFuncionario.setTelefone(telefone);
        novoFuncionario.setUtilizador(email.substring(0, email.indexOf('@')));
        novoFuncionario.setPassword("default");

        if (tipoFuncionarioEntity.getId() == 5 && idArmazem != null) {
            novoFuncionario.setIdArmazem(idArmazem);
        }

        repo_funcionario.save(novoFuncionario);

        return "redirect:/Funcionarios/Admin";
    }

    @PostMapping("/Funcionarios/Editar/Admin")
    public String editarFuncionario(@RequestParam("id") int id, @RequestParam("nome") String nome, @RequestParam("nif") String nif,
                                    @RequestParam("rua") String rua, @RequestParam("porta") String porta, @RequestParam("localidade") String localidade,
                                    @RequestParam("email") String email, @RequestParam("telefone") String telefone, @RequestParam("tipoFuncionario") String tipoFuncionario,
                                    @RequestParam(required = false) Integer idArmazem) {

        FuncionarioEntity funcionario = repo_funcionario.findById(id).orElse(null);

        if (funcionario != null) {
            funcionario.setNome(nome);
            funcionario.setNif(Integer.valueOf(nif));
            funcionario.setRua(rua);
            funcionario.setPorta(Integer.valueOf(porta));

            CodPostalEntity codPostalEntity = cod_postal_repo.findByNameLike(localidade);
            TipoFuncionarioEntity tipoFuncionarioEntity = tipo_funcionario_repo.findByDescLike(tipoFuncionario);

            funcionario.setIdCodPostal(codPostalEntity.getIdCodPostal());
            funcionario.setCodPostalByIdCodPostal(codPostalEntity);
            funcionario.setIdTipoFuncionario(tipoFuncionarioEntity.getId());
            funcionario.setTipoFuncionarioByIdTipoFuncionario(tipoFuncionarioEntity);
            funcionario.setEmail(email);
            funcionario.setTelefone(telefone);

            if (tipoFuncionarioEntity.getId() == 5 && idArmazem != null) {
                funcionario.setIdArmazem(idArmazem);
            } else {
                funcionario.setIdArmazem(null); // Clear the warehouse ID if the type is not 5
            }

            repo_funcionario.save(funcionario);
        }

        return "redirect:/Funcionarios/Admin";
    }

    @DeleteMapping("/Funcionarios/Remover/Admin")
    @ResponseBody
    public String removerFuncionario(@RequestParam("id") int id) {
        repo_funcionario.deleteById(id);
        return "Funcionario removido com sucesso";
    }
}
