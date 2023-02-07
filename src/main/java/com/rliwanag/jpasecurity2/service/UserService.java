package com.rliwanag.jpasecurity2.service;

import com.rliwanag.jpasecurity2.model.User;
import com.rliwanag.jpasecurity2.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    UserRepository userRepository;
    PasswordEncoder encoder;

    public UserService(UserRepository userRepository, PasswordEncoder encoder) {

        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public Optional<User> findUserByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    public List<User> getAllUsers() {
        List<User> users = this.userRepository.findAll();
        Collections.sort(users, new Comparator<User>() {
            public int compare(User p1, User p2) {
                return  p1.getLastname().compareToIgnoreCase(p2.getLastname());
            }
        });
        return users;
    }

    public void createUser(User user) throws Exception {
        if(this.userRepository.findByUsername(user.getUsername()).isEmpty()
                && this.userRepository.findByFirstnameAndLastname(user.getFirstname(), user.getLastname()).isEmpty()) {
            user.setPassword(encoder.encode(user.getPassword()));
            this.userRepository.save(user);
        } else {
            throw new Exception("The username should be unique. The firstname and lastname combination should also be unique.");
        }
    }
}
