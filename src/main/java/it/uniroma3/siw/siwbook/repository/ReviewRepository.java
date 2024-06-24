package it.uniroma3.siw.siwbook.repository;

import it.uniroma3.siw.siwbook.model.Review;
import it.uniroma3.siw.siwbook.model.User;
import org.springframework.data.repository.CrudRepository;


public interface ReviewRepository extends CrudRepository<Review, Long> {
}
