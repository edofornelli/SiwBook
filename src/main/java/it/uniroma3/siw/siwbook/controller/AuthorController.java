package it.uniroma3.siw.siwbook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import it.uniroma3.siw.siwbook.service.AuthorService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping("/authors")
    public String showAuthors (Model model) {
        model.addAttribute("authors", this.authorService.findAll());
        return "authors.html";
    }

}
