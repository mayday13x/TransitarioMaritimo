package org.example.projetoweb.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pt.ipvc.database.entity.*;
import pt.ipvc.database.repository.FuncionarioRepository;
import pt.ipvc.database.repository.CodPostalRepository;
import pt.ipvc.database.repository.TipoFuncionarioRepository;

import java.util.List;

@Controller
public class FuncionarioController {

    private final FuncionarioRepository repo_funcionario;
    private final CodPostalRepository cod_postal_repo;
    private final TipoFuncionarioRepository tipo_funcionario_repo;

    public FuncionarioController(FuncionarioRepository repo_funcionario, CodPostalRepository cod_postal_repo, TipoFuncionarioRepository tipo_funcionario_repo) {
        this.repo_funcionario = repo_funcionario;
        this.cod_postal_repo = cod_postal_repo;
        this.tipo_funcionario_repo = tipo_funcionario_repo;
    }

    @GetMapping("/Funcionarios")
    public String listarFuncionarios(Model model) {
        List<FuncionarioEntity> funcionarios = repo_funcionario.findAll();
        List<CodPostalEntity> codPostais = cod_postal_repo.findAll();
        List<TipoFuncionarioEntity> tiposFuncionario = tipo_funcionario_repo.findAll();
        model.addAttribute("funcionarios", funcionarios);
        model.addAttribute("codPostais", codPostais);
        model.addAttribute("tiposFuncionario", tiposFuncionario);
        return "Funcionarios";
    }

    @GetMapping("/inserirFuncionario")
    public String showAdicionarFuncionario(Model model) {
        List<CodPostalEntity> codPostais = cod_postal_repo.findAll();
        List<TipoFuncionarioEntity> tiposFuncionario = tipo_funcionario_repo.findAll();
        model.addAttribute("codPostais", codPostais);
        model.addAttribute("tiposFuncionario", tiposFuncionario);
        return "InserirFuncionario";
    }

    @PostMapping("/inserirFuncionario")
    public String adicionarFuncionario(@RequestParam String nome, @RequestParam String nif, @RequestParam String rua,
                                       @RequestParam String porta, @RequestParam String localidade, @RequestParam String email,
                                       @RequestParam String telefone, @RequestParam String tipoFuncionario) {

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

        repo_funcionario.save(novoFuncionario);

        return "redirect:/Funcionarios";
    }

    @PostMapping("/editarFuncionario")
    public String editarFuncionario(@RequestParam("id") int id, @RequestParam("nome") String nome, @RequestParam("nif") String nif,
                                    @RequestParam("rua") String rua, @RequestParam("porta") String porta, @RequestParam("localidade") String localidade,
                                    @RequestParam("email") String email, @RequestParam("telefone") String telefone, @RequestParam("tipoFuncionario") String tipoFuncionario) {

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

            repo_funcionario.save(funcionario);
        }

        return "redirect:/Funcionarios";
    }

    @DeleteMapping("/removerFuncionario")
    @ResponseBody
    public String removerFuncionario(@RequestParam("id") int id) {
        repo_funcionario.deleteById(id);
        return "Funcionario removido com sucesso";
    }
}
