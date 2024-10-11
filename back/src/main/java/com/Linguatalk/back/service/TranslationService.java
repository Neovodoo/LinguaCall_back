package com.Linguatalk.back.service;


import org.springframework.stereotype.Service;

@Service
public class TranslationService {

    public String translateMessage(String message, String targetLanguage) {
        // Логика перевода с использованием локальной модели
        // Замените эту строку реальной логикой обращения к модели
        String translatedMessage = "Mock translation for: " + message + " to " + targetLanguage;
        return translatedMessage;
    }

}
