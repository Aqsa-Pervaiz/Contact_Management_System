package com.example.demo;

import com.example.demo.entity.Contact;
import com.example.demo.entity.LabeledValue;
import com.example.demo.entity.ContactRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ContactControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ContactRepository contactRepository;

    @Test
    public void testGetAllContacts() throws Exception {
        Contact contact = new Contact();
        contact.setTitle("Admin");
        contact.setFirstName("Aqsa");
        contact.setLastName("Pervaiz");
        List<LabeledValue> emails = new ArrayList<>();
        emails.add(new LabeledValue("work", "aqsa@work.com"));
        emails.add(new LabeledValue("personal", "aqsa@gmail.com"));
        contact.setEmails(emails);
        List<LabeledValue> phones = new ArrayList<>();
        phones.add(new LabeledValue("mobile", "0123456789"));
        phones.add(new LabeledValue("home", "0123456789"));
        contact.setPhones(phones);
        contactRepository.save(contact);
        mockMvc.perform(get("/contacts")) .andExpect(status().isOk()) .andExpect(jsonPath("$[0].firstName").value("Aqsa")) .andExpect(jsonPath("$[0].emails[0].value").value("aqsa@work.com")) .andExpect(jsonPath("$[0].phones[1].value").value("0123456789"));
    }
    @Test
    public void testCreateContact() throws Exception {
        Contact contact = new Contact();
        contact.setTitle("Admin");
        contact.setFirstName("Aqsa");
        contact.setLastName("Pervaiz");
        List<LabeledValue> emails = new ArrayList<>();
        emails.add(new LabeledValue("work", "aqsa@work.com"));
        contact.setEmails(emails);
        List<LabeledValue> phones = new ArrayList<>();
        phones.add(new LabeledValue("mobile", "0123456789"));
        contact.setPhones(phones);
        mockMvc.perform(post("/contacts") .contentType(MediaType.APPLICATION_JSON) .content(objectMapper.writeValueAsString(contact))) .andExpect(status().isOk()) .andExpect(jsonPath("$.firstName").value("Aqsa")) .andExpect(jsonPath("$.emails[0].value").value("aqsa@work.com")) .andExpect(jsonPath("$.phones[0].value").value("0123456789"));
    }

    @Test
    public void testUpdateContact() throws Exception {
        Contact contact = new Contact();
        contact.setTitle("Admin");
        contact.setFirstName("Aqsa");
        contact.setLastName("Pervaiz");

        List<LabeledValue> emails = new ArrayList<>();
        emails.add(new LabeledValue("personal", "aqsa@example.com"));
        contact.setEmails(emails);

        List<LabeledValue> phones = new ArrayList<>();
        phones.add(new LabeledValue("mobile", "012356789"));
        contact.setPhones(phones);
        contact = contactRepository.save(contact);
        contact.setFirstName("Aqsa updated");
        mockMvc.perform(put("/contacts/" + contact.getId()) .contentType(MediaType.APPLICATION_JSON) .content(objectMapper.writeValueAsString(contact))) .andExpect(status().isOk()) .andExpect(jsonPath("$.firstName").value("Aqsa updated"));
    }
    @Test
    public void testDeleteContact() throws Exception {
        Contact contact = new Contact();
        contact.setTitle("Admin");
        contact.setFirstName("Aqsa");
        contact.setLastName("Pervaiz");

        List<LabeledValue> emails = new ArrayList<>();
        emails.add(new LabeledValue("work", "aqsa@work.com"));
        contact.setEmails(emails);

        List<LabeledValue> phones = new ArrayList<>();
        phones.add(new LabeledValue("mobile", "0123456789"));
        contact.setPhones(phones);

        contact = contactRepository.save(contact);
        mockMvc.perform(delete("/contacts/" + contact.getId()))
             .andExpect(status().isOk());

    }
}
