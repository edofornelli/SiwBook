package it.uniroma3.siw.siwbook.service;


import it.uniroma3.siw.siwbook.model.Book;
import it.uniroma3.siw.siwbook.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public Iterable<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book findById(long id) {
        return bookRepository.findById(id).get();
    }

    public void save(Book book) {
        bookRepository.save(book);
    }

}
