package it.uniroma3.siw.siwbook.service;

import it.uniroma3.siw.siwbook.model.Author;
import it.uniroma3.siw.siwbook.model.Book;
import it.uniroma3.siw.siwbook.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookService bookService;

    public Author findById(long id) {
        return authorRepository.findById(id).get();
    }

    public Iterable<Author> findAll() {
        return authorRepository.findAll();
    }

    public void save(Author author) {
        authorRepository.save(author);
    }

    public void addAuthortoBook(Author author, Book book) {
        author.getBooks().add(book);
        book.setAuthor(author);
        this.save(author);
        bookService.save(book);
    }

}
