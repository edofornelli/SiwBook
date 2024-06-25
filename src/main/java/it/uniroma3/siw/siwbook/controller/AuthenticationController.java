package it.uniroma3.siw.siwbook.controller;


import it.uniroma3.siw.siwbook.model.Credentials;
import it.uniroma3.siw.siwbook.model.User;
import it.uniroma3.siw.siwbook.service.CredentialsService;
import it.uniroma3.siw.siwbook.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthenticationController {

    @Autowired
    private CredentialsService credentialsService;
    @Autowired
    private UserService userService;

    @GetMapping(value = "/register")
    public String showRegisterForm (Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("credentials", new Credentials());
        return "formRegisterUser";
    }

    @GetMapping(value = "/login")
    public String showLoginForm (Model model) {
        return "login.html";
    }

    @GetMapping(value = "/")
    public String index(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return "index.html";
    }

    @GetMapping(value = "/success")
    public String defaultAfterLogin(Model model) {

        UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());

        return "index.html";
    }

    @PostMapping(value = { "/register" })
    public String registerUser(@Valid @ModelAttribute("user") User user,
                               BindingResult userBindingResult,
                               @Valid
                               @ModelAttribute("credentials") Credentials credentials,
                               BindingResult credentialsBindingResult,
                               Model model) {

         if(!userBindingResult.hasErrors() && ! credentialsBindingResult.hasErrors()) {
            userService.save(user);
            credentials.setUser(user);

            credentials.setRole(Credentials.GENERIC_ROLE);
            credentialsService.saveCredentials(credentials);
            model.addAttribute("user", user);

            return "redirect:/";
        }
        return "formRegisterUser";
    }
}
