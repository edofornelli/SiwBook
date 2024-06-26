package it.uniroma3.siw.siwbook.repository;

import it.uniroma3.siw.siwbook.model.Author;
import it.uniroma3.siw.siwbook.model.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {
    public boolean existsByTitle(String title);
}
