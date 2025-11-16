package com.example.demo;

import com.example.demo.entity.Contact;
import com.example.demo.entity.ContactRepository;
import com.example.demo.entity.LabeledValue;
import com.example.demo.entity.User;
import com.example.demo.entity.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(locations = "classpath:applicationTest.properties")
public class ContactRepositoryTest {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    void setup() {
        contactRepository.deleteAll();
        userRepository.deleteAll();
        testUser = new User();
        testUser.setEmail("user@example.com");
        testUser.setPassword("1234");
        testUser.setName("User");
        testUser.setPhone("1234567890");
        testUser = userRepository.save(testUser);
    }

    @Test
    void testSaveContact() {
        Contact contact = new Contact();
        contact.setFirstName("Aqsa");
        contact.setLastName("Khan");
        contact.setTitle("Miss");
        contact.setPassword("pass");
        contact.setUser(testUser);
        contact.getEmails().add(new LabeledValue("work", "aqsa@work.com"));
        contact.getEmails().add(new LabeledValue("personal", "aqs@gmail.com"));
        contact.getPhones().add(new LabeledValue("mobile", "123456789"));
        contact.getPhones().add(new LabeledValue("home", "987654321"));

        contact = contactRepository.save(contact);
        Contact found = contactRepository.findById(contact.getId()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getFirstName()).isEqualTo("Aqsa Khan");
        assertThat(found.getEmails()).hasSize(2);
        assertThat(found.getPhones()).hasSize(2);
        assertThat(found.getUser().getEmail()).isEqualTo("khan@example.com");
    }

    @Test
    void testFindAllContacts() {
        Contact c1 = new Contact();
        c1.setFirstName("Ali");
        c1.setUser(testUser);

        Contact c2 = new Contact();
        c2.setFirstName("Zaraa");
        c2.setUser(testUser);

        contactRepository.saveAll(List.of(c1, c2));
        List<Contact> contacts = contactRepository.findAll();
        assertThat(contacts).hasSize(2);
    }

    @Test
    void testDeleteContact() {
        Contact contact = new Contact();
        contact.setFirstName("Delete");
        contact.setUser(testUser);

        contact = contactRepository.save(contact);
        contactRepository.deleteById(contact.getId());
        assertThat(contactRepository.findById(contact.getId())).isEmpty();
    }
}
