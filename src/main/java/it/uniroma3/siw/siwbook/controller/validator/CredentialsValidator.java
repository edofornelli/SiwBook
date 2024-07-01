package it.uniroma3.siw.siwbook.controller.validator;


import it.uniroma3.siw.siwbook.model.Credentials;
import it.uniroma3.siw.siwbook.service.CredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CredentialsValidator implements Validator {
    @Autowired
    private CredentialsService credentialsService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Credentials.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Credentials credentials = (Credentials) o;
        if (credentialsService.isUsernameTaken(credentials.getUsername())) {
            errors.reject("username.duplicate");
        }
    }
}
