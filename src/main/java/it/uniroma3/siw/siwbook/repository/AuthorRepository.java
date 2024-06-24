package it.uniroma3.siw.siwbook.repository;

import it.uniroma3.siw.siwbook.model.Author;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<Author, Long> {
    public Author existsByNameAndSurname(String name, String surname);
}
