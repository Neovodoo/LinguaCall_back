package com.Linguatalk.back.model;


import jakarta.persistence.*;
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

    @Column(length = 10000)
    private String message;

    private String languageTo;

    private String languageFrom;

    @Column(length = 10000)
    private String translatedMessage;

    private String time;
}
