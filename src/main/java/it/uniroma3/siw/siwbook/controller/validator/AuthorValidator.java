package it.uniroma3.siw.siwbook.controller.validator;

import it.uniroma3.siw.siwbook.model.Author;
import it.uniroma3.siw.siwbook.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class AuthorValidator implements Validator {

    @Autowired
    private AuthorRepository authorRepository;



    @Override
    public void validate(Object o, Errors errors) {
        Author author = (Author)o;
        if ((author.getName() != null) && (author.getSurname() != null) && authorRepository.existsByNameAndSurname(author.getName(), author.getSurname())){
            errors.reject("author.duplicate");
        }
    }
    @Override
    public boolean supports(Class<?> aClass) {
        return Author.class.equals(aClass);
    }
}
