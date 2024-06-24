package it.uniroma3.siw.siwbook.service;

import it.uniroma3.siw.siwbook.model.User;
import it.uniroma3.siw.siwbook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findById(Long id){
        return userRepository.findById(id).get();
    }



    public User saveUser(User user){
        return userRepository.save(user);
    }

    public void save(User user) {
        userRepository.save(user);
    }
}