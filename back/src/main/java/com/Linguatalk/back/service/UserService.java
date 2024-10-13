package com.Linguatalk.back.service;

import com.Linguatalk.back.model.User;
import com.Linguatalk.back.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(String login, String password) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password); // Здесь можно добавить шифрование пароля
        return userRepository.save(user);
    }

    public Optional<User> findUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public boolean authenticateUser(String login, String password) {
        Optional<User> user = userRepository.findByLogin(login);
        return user.isPresent() && user.get().getPassword().equals(password);
    }
}
