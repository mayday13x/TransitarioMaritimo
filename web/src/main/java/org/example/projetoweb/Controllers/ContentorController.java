package org.example.projetoweb.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pt.ipvc.database.entity.*;
import pt.ipvc.database.repository.ContentorRepository;

import java.util.List;

@Controller
public class ContentorController {

    public final ContentorRepository repo_contentor;

    public ContentorController(ContentorRepository repo_contentor) {
        this.repo_contentor = repo_contentor;
    }

    @GetMapping("/Contentor")
    public String listarContentores(Model model){
        List<ContentorEntity> contentores = repo_contentor.findAll();
        model.addAttribute("contentores", contentores);
        return "Contentor";
    }
}
