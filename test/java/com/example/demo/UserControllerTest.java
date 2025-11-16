package com.example.demo;

import com.example.demo.entity.User;
import com.example.demo.entity.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = DemoApplication.class)
@AutoConfigureMockMvc
@Transactional
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void cleanDatabase() {
        userRepository.deleteAll();
    }

    @Test
    public void testRegisterUser_Success() throws Exception {
        User user = new User();
        user.setEmail("abc@example.com");
        user.setPassword("123456");

        mockMvc.perform(post("/register")  .contentType(MediaType.APPLICATION_JSON) .content(objectMapper.writeValueAsString(user))).andExpect(status().isOk()) .andExpect(jsonPath("$.message").value("User Registered Successfully!"));

        List<User> users = userRepository.findAll();
        assertThat(users).hasSize(1);
        assertThat(users.get(0).getEmail()).isEqualTo("abc@example.com");
    }

    @Test
    public void testRegisterUser_Fail_EmailExists() throws Exception {
        User existing = new User();
        existing.setEmail("existing@example.com");
        existing.setPassword("abc123");
        userRepository.save(existing);

        User newUser = new User();
        newUser.setEmail("existing@example.com");
        newUser.setPassword("123456");

        mockMvc.perform(post("/register")  .contentType(MediaType.APPLICATION_JSON) .content(objectMapper.writeValueAsString(newUser))) .andExpect(status().isBadRequest()) .andExpect(jsonPath("$.message").value("Email already registered!"));
    }

    @Test
    public void testLoginUser_Success() throws Exception {
        User user = new User();
        user.setEmail("login12@example.com");
        user.setPassword("123");
        userRepository.save(user);

        User loginRequest = new User();
        loginRequest.setEmail("login12@example.com");
        loginRequest.setPassword("123");

        mockMvc.perform(post("/login") .contentType(MediaType.APPLICATION_JSON) .content(objectMapper.writeValueAsString(loginRequest))) .andExpect(status().isOk()) .andExpect(jsonPath("$.message").value("Login Successful!"));
    }

    @Test
    public void testLoginUser_Fail_InvalidPassword() throws Exception {
        User user = new User();
        user.setEmail("user@example.com");
        user.setPassword("correct");
        userRepository.save(user);

        User loginRequest = new User();
        loginRequest.setEmail("user@example.com");
        loginRequest.setPassword("wrong");

        mockMvc.perform(post("/login") .contentType(MediaType.APPLICATION_JSON) .content(objectMapper.writeValueAsString(loginRequest))) .andExpect(status().isUnauthorized()) .andExpect(jsonPath("$.message").value("Invalid email/phone or password!"));
    }

    @Test
    public void testGetAllUsers() throws Exception {
        User u1 = new User();
        u1.setEmail("a@example.com");
        u1.setPassword("pass1");
        User u2 = new User();
        u2.setEmail("b@example.com");
        u2.setPassword("pass2");
        userRepository.saveAll(List.of(u1, u2));

        mockMvc.perform(get("/all"))  .andExpect(status().isOk()) .andExpect(jsonPath("$[0].email").value("a@example.com")) .andExpect(jsonPath("$[1].email").value("b@example.com"));
    }

    @Test
    public void testGetUserByEmail() throws Exception {
        User user = new User();
        user.setEmail("me@example.com");
        user.setPassword("mypassword");
        userRepository.save(user);
        mockMvc.perform(get("/me@example.com"))  .andExpect(status().isOk()) .andExpect(jsonPath("$.email").value("me@example.com"));
    }

    @Test
    public void testDeleteUser() throws Exception {
        User user = new User();
        user.setEmail("delete@example.com");
        user.setPassword("delete");
        user = userRepository.save(user);
        mockMvc.perform(delete("/" + user.getId()))  .andExpect(status().isOk()) .andExpect(content().string("User deleted successfully!"));

        assertThat(userRepository.findById(user.getId())).isEmpty();
    }
}
