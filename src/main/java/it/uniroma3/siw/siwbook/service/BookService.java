package it.uniroma3.siw.siwbook.service;


import it.uniroma3.siw.siwbook.model.Book;
import it.uniroma3.siw.siwbook.model.Review;
import it.uniroma3.siw.siwbook.model.User;
import it.uniroma3.siw.siwbook.repository.BookRepository;
import it.uniroma3.siw.siwbook.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserService userService;


    public Iterable<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book findById(long id) {
        return bookRepository.findById(id).get();
    }

    public void save(Book book) {
        bookRepository.save(book);
    }

    public void deleteById(Book book) {

        List<Review> reviews = new ArrayList<>(book.getReviews());

        for (Review review : reviews) {
            User user = this.reviewRepository.findById(review.getId()).get().getUser();
            book.getReviews().remove(review);
            this.save(book);
            user.getReviews().remove(review);
            this.userService.save(user);
            reviewRepository.deleteById(review.getId());
        }

        book.getAuthor().getBooks().remove(book);
        bookRepository.deleteById(book.getId());
    }

    public void saveReviewToBook(User user , Book book, String text) {
        Review review = new Review();
        review.setUser(user);
        review.setBook(book);
        review.setText(text);

        book.getReviews().add(review);
        user.getReviews().add(review);

        this.reviewRepository.save(review);
        this.userService.save(user);
        this.save(book);
    }

}
