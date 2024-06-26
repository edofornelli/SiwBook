package it.uniroma3.siw.siwbook.controller.validator;

import it.uniroma3.siw.siwbook.model.Book;
import it.uniroma3.siw.siwbook.repository.BookRepository;
import org.springframework.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class BookValidator implements Validator {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public void validate(Object o, Errors errors) {
        Book book = (Book)o;
        if ((book.getTitle() != null) && bookRepository.existsByTitle(book.getTitle())){
            errors.reject("author.duplicate");
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Book.class.equals(aClass);
    }
}
