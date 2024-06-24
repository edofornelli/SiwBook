package it.uniroma3.siw.siwbook.repository;

import it.uniroma3.siw.siwbook.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

}

