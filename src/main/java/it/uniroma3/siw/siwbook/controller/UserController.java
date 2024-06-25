package it.uniroma3.siw.siwbook.controller;


import it.uniroma3.siw.siwbook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/{id}")
    public String getUser (@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", this.userService.findById(id));
        return "user.html";
    }




}
