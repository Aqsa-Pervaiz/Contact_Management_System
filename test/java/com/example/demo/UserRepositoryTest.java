package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.demo.entity.User;
import com.example.demo.entity.UserRepository;

@DataJpaTest
@ActiveProfiles("test") 
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindByEmail() {
        User user = new User();
        user.setName("User");
        user.setEmail("user@email.com");
        user.setPhone("12345677");
        user.setPassword("password");
        userRepository.save(user);

        User found = userRepository.findByEmail("user@email.com");
        assertThat(found).isNotNull();
        assertThat(found.getEmail()).isEqualTo("user@email.com");
    }

    @Test
    void testFindByPhone() {
        User user = new User();
        user.setName("Phone User");
        user.setEmail("abc@gmail.com");
        user.setPhone("033127898");
        user.setPassword("test123");
        userRepository.save(user);

        User found = userRepository.findByPhone("033127898");

        assertThat(found).isNotNull();
        assertThat(found.getPhone()).isEqualTo("033127898");
    }

    @Test
    void testFindByEmailNotFound() {
        User result = userRepository.findByEmail("notexist@test.com");
        assertThat(result).isNull();
    }

    @Test
    void testFindByPhoneNotFound() {
        User result = userRepository.findByPhone("03312789");
        assertThat(result).isNull();
    }
}
