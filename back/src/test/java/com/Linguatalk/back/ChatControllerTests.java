package com.Linguatalk.back;

import com.Linguatalk.back.model.ChatMessage;
import com.Linguatalk.back.model.User;
import com.Linguatalk.back.repository.ChatMessageRepository;
import com.Linguatalk.back.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ChatControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @BeforeEach
    void setUp() {
        // Clear repository before each test to ensure a clean state
        userRepository.deleteAll();
        chatMessageRepository.deleteAll();
    }

    // Positive test: Send request for translation with all parameters
    @Test
    void testTranslateChatMessageWithValidData() throws Exception {
        //TODO: Поправь потом тест под новый контракт и работающий перевод
        // First, we need to create the sender and recipient users
        User sender = new User();
        sender.setLogin("sender");
        sender.setPassword("password");
        userRepository.save(sender);

        User recipient = new User();
        recipient.setLogin("recipient");
        recipient.setPassword("password");
        userRepository.save(recipient);

        // Prepare the JSON for the translation request
        String chatMessageJson = "{\n" +
                "    \"chatId\": \"12345\",\n" +
                "    \"sender\": \"sender\",\n" +
                "    \"recipient\": \"recipient\",\n" +
                "    \"message\": \"Hello\",\n" +
                "    \"languageFrom\": \"ru\",\n" +
                "    \"languageTo\": \"en\",\n" +
                "    \"time\": \"12293913991\"\n" +
                "}";

        // Perform the translation request
        mockMvc.perform(post("/api/chat/translate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(chatMessageJson))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"sender\":\"sender\",\"recipient\":\"recipient\",\"message\":\"Hello\",\"languageFrom\":\"ru\",\"languageTo\":\"en\",\"translatedMessage\":\"Mock translation for: Hello from ru to en\",\"time\":\"12293913991\"}"))
                .andDo(MockMvcResultHandlers.print());
    }
}
