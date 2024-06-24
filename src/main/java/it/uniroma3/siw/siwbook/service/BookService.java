package it.uniroma3.siw.siwbook.service;


import it.uniroma3.siw.siwbook.model.Book;
import it.uniroma3.siw.siwbook.model.Review;
import it.uniroma3.siw.siwbook.model.User;
import it.uniroma3.siw.siwbook.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ReviewService reviewService;

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

    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    public void saveReviewToBook(User user , Book book, String text) {
        Review review = new Review();
        review.setUser(user);
        review.setBook(book);
        review.setText(text);

        book.getReviews().add(review);
        user.getReviews().add(review);

        this.reviewService.save(review);
        this.userService.save(user);

        this.save(book);
    }

}
