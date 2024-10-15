package com.Linguatalk.back.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String chatId;

    private String sender;

    private String recipient;

    private String message;

    private String languageFrom;

    private String languageTo;

    private String translatedMessage;
}
