package it.uniroma3.siw.siwbook.service;


import it.uniroma3.siw.siwbook.model.Book;
import it.uniroma3.siw.siwbook.model.Review;
import it.uniroma3.siw.siwbook.model.User;
import it.uniroma3.siw.siwbook.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;


    public Review findById(Long id) {
        return this.reviewRepository.findById(id).get();
    }

    public void save(Review review) {
        this.reviewRepository.save(review);
    }


    public void deleteById(Long id) {
        User user = this.findById(id).getUser();
        Book book = this.findById(id).getBook();

        book.getReviews().remove(this.findById(id));
        user.getReviews().remove(this.findById(id));
        this.userService.save(user);
        this.bookService.save(book);

        this.reviewRepository.deleteById(id);
    }


}
