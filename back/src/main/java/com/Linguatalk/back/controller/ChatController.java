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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

        String translatedText = translationService.translateMessage(chatMessage.getMessage(), chatMessage.getLanguage());
        chatMessage.setTranslatedMessage(translatedText);

        // Сохраняем сообщение и возвращаем его
        return ResponseEntity.ok(chatMessageRepository.save(chatMessage));
    }
}
