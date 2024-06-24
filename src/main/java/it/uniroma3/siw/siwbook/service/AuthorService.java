package it.uniroma3.siw.siwbook.service;

import it.uniroma3.siw.siwbook.model.Author;
import it.uniroma3.siw.siwbook.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public Author findById(long id) {
        return authorRepository.findById(id).get();
    }

    public Iterable<Author> findAll() {
        return authorRepository.findAll();
    }

    public void save(Author author) {
        authorRepository.save(author);
    }

}
