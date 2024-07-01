package it.uniroma3.siw.siwbook.repository;

import it.uniroma3.siw.siwbook.model.Credentials;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface  CredentialsRepository extends CrudRepository<Credentials, Long> {
    public Optional<Credentials> findByUsername(String username);
    public Optional<Credentials> findById(Long id);
    public boolean existsByUsername(String username);
}