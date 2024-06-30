package it.uniroma3.siw.siwbook.controller;

import it.uniroma3.siw.siwbook.model.Book;
import it.uniroma3.siw.siwbook.model.Credentials;
import it.uniroma3.siw.siwbook.model.Review;
import it.uniroma3.siw.siwbook.model.User;
import it.uniroma3.siw.siwbook.service.BookService;
import it.uniroma3.siw.siwbook.service.CredentialsService;
import it.uniroma3.siw.siwbook.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller

public class ReviewController {

    @Autowired
    private BookService bookService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private CredentialsService credentialsService;


    @GetMapping ("/review/{id}")
    public String getReview (@PathVariable("id") Long id, Model model) {
        model.addAttribute("review", this.reviewService.findById(id));
        return "review.html";
    }


    @GetMapping ("/User/removeReview/{id}")
    public String deleteReview (@PathVariable("id") Long id, Model model) {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
        User authenticatedUser = credentials.getUser();

        User reviewUser = this.reviewService.findById(id).getUser();

        if (!authenticatedUser.equals(reviewUser) && !credentials.isAdmin()){
            model.addAttribute("error", "Non sei autorizzato a cancellare questa recensione");
            return "/error.html";
        }

        this.reviewService.deleteById(id);

        return "redirect:/user/" + reviewUser.getId();
    }



    @GetMapping("/User/addReview/{id}")
    public String addReview (@PathVariable("id") Long id, Model model) {
        model.addAttribute("book", this.bookService.findById(id));
        model.addAttribute("review", new Review());

        return "/User/formNewReview.html";
    }

    @PostMapping("/User/formNewReview/{id}")
    public String addReviewToBook (@RequestParam("testo") String testoRecensione, @PathVariable("id") Long id, Model model) {

        Book book = this.bookService.findById(id);

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
        User user = credentials.getUser();

        try {
            this.bookService.saveReviewToBook(user, book, testoRecensione);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "/error.html";
        }

        return "redirect:/book/" + book.getId();
    }


}
