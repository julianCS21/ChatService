package com.example.demo.application;

import com.example.demo.domain.exceptions.UserException;
import com.example.demo.domain.models.User;
import com.example.demo.infrastructure.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder; // Inyección de PasswordEncoder
    }

    public User createUser(User newUser) throws UserException {
        try {
            String encodedPassword = this.passwordEncoder.encode(newUser.getPassword());
            newUser.setPassword(encodedPassword);
            return this.userRepository.save(newUser);
        } catch (RuntimeException e) {
            throw new UserException(e.getMessage());
        }
    }

    public List<User> getUsers() {
        return this.userRepository.findAll();
    }
}
