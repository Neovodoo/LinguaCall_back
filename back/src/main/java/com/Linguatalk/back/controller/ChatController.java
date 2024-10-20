package com.Linguatalk.back.controller;

import com.Linguatalk.back.model.ChatMessage;
import com.Linguatalk.back.repository.ChatMessageRepository;
import com.Linguatalk.back.service.TranslationService;
import com.Linguatalk.back.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);
    @Autowired
    private TranslationService translationService;

    @Autowired
    private UserService userService;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @PostMapping("/translate")
    public ResponseEntity <ChatMessage> translateChatMessage(@RequestBody ChatMessage chatMessage) {
        logger.info("Received request to translate message: {}", chatMessage);

        boolean isSenderValid = userService.findUserByLogin(chatMessage.getSender()).isPresent();
        boolean isRecipientValid = userService.findUserByLogin(chatMessage.getRecipient()).isPresent();

        if (!isSenderValid) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        if (!isRecipientValid) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        String translatedText = translationService.translateMessage(chatMessage.getMessage(), chatMessage.getLanguageFrom(), chatMessage.getLanguageTo());
        chatMessage.setTranslatedMessage(translatedText);

        // Сохраняем сообщение и возвращаем его
        return ResponseEntity.ok(chatMessageRepository.save(chatMessage));
    }

    @GetMapping("/history/{chatId}")
    public ResponseEntity<List<ChatMessage>> getChatHistory(@PathVariable String chatId) {
        logger.info("Received request to retrieve chat history for chatId: {}", chatId);

        // Retrieve messages by chatId sorted by time
        List<ChatMessage> chatHistory = chatMessageRepository.findByChatIdOrderByTimeAsc(chatId);

        // If no messages found, return 404
        if (chatHistory.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // Return chat history with 200 OK status
        return ResponseEntity.ok(chatHistory);
    }
}
