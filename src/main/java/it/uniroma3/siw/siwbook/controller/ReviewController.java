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


    @GetMapping ("/review/{id}")
    public String getReview (@PathVariable("id") Long id, Model model) {
        model.addAttribute("review", this.reviewService.findById(id));
        return "review.html";
    }


    @GetMapping ("/User/removeReview/{id}")
    public String deleteReview (@PathVariable("id") Long id, Model model) {
        User user = this.reviewService.findById(id).getUser();
        Book book = this.reviewService.findById(id).getBook();

        book.getReviews().remove(this.reviewService.findById(id));
        user.getReviews().remove(this.reviewService.findById(id));
        this.userService.save(user);
        this.bookService.save(book);

        this.reviewService.deleteReview(id);
        return "redirect:/user/" + user.getId();
    }



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

        try {
            this.bookService.saveReviewToBook(user, book, testoRecensione);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "/error.html";
        }

        return "redirect:/book/" + book.getId();
    }


}
