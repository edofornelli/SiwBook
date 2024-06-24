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

    public void save(Review review) {
        this.reviewRepository.save(review);
    }

}
