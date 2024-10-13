package com.Linguatalk.back.controller;

import com.Linguatalk.back.model.ChatMessage;
import com.Linguatalk.back.repository.ChatMessageRepository;
import com.Linguatalk.back.service.TranslationService;
import com.Linguatalk.back.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ChatMessage translateChatMessage(@RequestBody ChatMessage chatMessage) {
/*
        boolean isSenderValid = userService.findUserByLogin(chatMessage.getSender()).isPresent();
        boolean isRecipientValid = userService.findUserByLogin(chatMessage.getRecipient()).isPresent();

        if (!isSenderValid || !isRecipientValid) {
            return "Error: Either sender or recipient does not exist.";
        }
 */
        logger.info("Received request to translate message: {}", chatMessage);
        String translatedText = translationService.translateMessage(chatMessage.getMessage(), chatMessage.getLanguage());
        chatMessage.setTranslatedMessage(translatedText);
        return chatMessageRepository.save(chatMessage);
    }
}
