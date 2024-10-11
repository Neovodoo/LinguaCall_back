package com.Linguatalk.back.controller;

import com.Linguatalk.back.model.ChatMessage;
import com.Linguatalk.back.repository.ChatMessageRepository;
import com.Linguatalk.back.service.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
    @Autowired
    private TranslationService translationService;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @PostMapping("/translate")
    public ChatMessage translateChatMessage(@RequestBody ChatMessage chatMessage) {
        String translatedText = translationService.translateMessage(chatMessage.getMessage(), chatMessage.getLanguage());
        chatMessage.setTranslatedMessage(translatedText);
        return chatMessageRepository.save(chatMessage);
    }
}
