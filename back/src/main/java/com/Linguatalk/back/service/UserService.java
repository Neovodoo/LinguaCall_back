package com.Linguatalk.back.service;

import com.Linguatalk.back.model.ChatMessage;
import com.Linguatalk.back.model.User;
import com.Linguatalk.back.repository.ChatMessageRepository;
import com.Linguatalk.back.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    public User createUser(String login, String password) {

        if (login == null || login.trim().isEmpty()) {
            throw new IllegalArgumentException("Login cannot be empty or null.");
        }

        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty or null.");
        }

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

    public Optional<String> getChatIdIfExists(String login1, String login2) {
        List<ChatMessage> chatMessages = chatMessageRepository.findBySenderAndRecipient(login1, login2);
        if (!chatMessages.isEmpty()) {
            return Optional.of(chatMessages.get(0).getChatId());  // Возвращаем chatId первой записи
        }
        return Optional.empty();
    }
}
