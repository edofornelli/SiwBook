package it.uniroma3.siw.siwbook.controller;

import it.uniroma3.siw.siwbook.model.Book;
import it.uniroma3.siw.siwbook.model.Review;
import it.uniroma3.siw.siwbook.model.User;
import it.uniroma3.siw.siwbook.service.BookService;
import it.uniroma3.siw.siwbook.service.ReviewService;
import it.uniroma3.siw.siwbook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    private UserService userService;

    @GetMapping("/User/addReview/{id}")
    public String addReview (@PathVariable("id") Long id, Model model) {
        model.addAttribute("book", this.bookService.findById(id));
        model.addAttribute("review", new Review());

        return "/User/formNewReview.html";
    }

    @PostMapping("/User/formNewReview/{id}/{userId}")
    public String addReviewToBook (@RequestParam("testo") String testoRecensione, @PathVariable("id") Long id, @PathVariable("userId") Long userId, Model model) {

        Book book = this.bookService.findById(id);
        User user = this.userService.findById(userId);

        System.out.println(book.getAuthor());
        System.out.println(user.getName());

        try {
            this.bookService.saveReviewToBook(user, book, testoRecensione);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "/error.html";
        }

        return "redirect:/book/" + book.getId();
    }


}
