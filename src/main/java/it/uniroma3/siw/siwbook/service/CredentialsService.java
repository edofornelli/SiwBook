package it.uniroma3.siw.siwbook.service;


import it.uniroma3.siw.siwbook.model.Credentials;
import it.uniroma3.siw.siwbook.repository.CredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CredentialsService {

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    private CredentialsRepository credentialsRepository;

    public Credentials getCredentials(Long id){
        return credentialsRepository.findById(id).get();
    }

    public Credentials getCredentials(String username){
        return credentialsRepository.findByUsername(username).get();

    }

    public Credentials saveCredentials(Credentials credentials){
        String oldPassword = credentials.getPassword();
        credentials.setPassword(this.passwordEncoder.encode(credentials.getPassword()));
        return credentialsRepository.save(credentials);
    }

}