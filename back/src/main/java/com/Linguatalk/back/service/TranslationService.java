package com.Linguatalk.back.service;


import org.springframework.stereotype.Service;

@Service
public class TranslationService {

    public String translateMessage(String message, String targetLanguage) {
        //TODO: Реализовать перевод пходящего сообщения
        String translatedMessage = "Mock translation for: " + message + " to " + targetLanguage;
        return translatedMessage;
    }

}
