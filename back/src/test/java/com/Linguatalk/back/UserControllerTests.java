package com.Linguatalk.back;

import com.Linguatalk.back.model.User;
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
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        // Clear repository before each test to ensure a clean state
        userRepository.deleteAll();
    }

    // Positive test: Create user with valid data
    @Test
    void testCreateUserWithValidData() throws Exception {
        String userJson = "{\"login\":\"validUser\", \"password\":\"validPassword\"}";

        mockMvc.perform(post("/api/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"login\":\"validUser\"}"))
                .andDo(MockMvcResultHandlers.print());
    }

    // Positive test: Login with existing user
    @Test
    void testLoginWithExistingUser() throws Exception {
        // First create a user
        User user = new User();
        user.setLogin("existingUser");
        user.setPassword("password123");
        userRepository.save(user);

        // Now attempt to login with the correct credentials
        String loginJson = "{\"login\":\"existingUser\", \"password\":\"password123\"}";

        mockMvc.perform(post("/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Login successful"))
                .andDo(MockMvcResultHandlers.print());
    }

    // Negative test: Create user with existing login
    @Test
    void testCreateUserWithExistingLogin() throws Exception {
        // First create a user
        User user = new User();
        user.setLogin("existingUser");
        user.setPassword("password123");
        userRepository.save(user);

        // Now try to create another user with the same login
        String duplicateUserJson = "{\"login\":\"existingUser\", \"password\":\"newPassword\"}";

        mockMvc.perform(post("/api/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(duplicateUserJson))
                .andExpect(status().isConflict())  // Expecting a conflict error
                .andDo(MockMvcResultHandlers.print());
    }

    // Negative test: Login with invalid data (wrong password)
    @Test
    void testLoginWithInvalidPassword() throws Exception {
        // First create a user
        User user = new User();
        user.setLogin("validUser");
        user.setPassword("validPassword");
        userRepository.save(user);

        // Now attempt to login with the wrong password
        String loginJson = "{\"login\":\"validUser\", \"password\":\"wrongPassword\"}";

        mockMvc.perform(post("/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
                .andExpect(status().isUnauthorized())  // Expecting an unauthorized error
                .andExpect(content().string("Invalid login or password"))
                .andDo(MockMvcResultHandlers.print());
    }
}
